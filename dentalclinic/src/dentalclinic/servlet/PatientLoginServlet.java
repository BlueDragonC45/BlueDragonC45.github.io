package dentalclinic.servlet;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Patient;
import dentalclinic.entities.Appointment;
import dentalclinic.entities.Branch;
import dentalclinic.entities.PatientRecord;
import dentalclinic.entities.Review;
import dentalclinic.entities.FeeCharge;
import dentalclinic.entities.Invoice;
import lombok.Getter;
import lombok.Setter;

public class PatientLoginServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		//Both userName and pwd were obtained from patient_login.html
		String userName = req.getParameter("userName");
		String pwd = req.getParameter("pwd");
		
		
		//Establish a connection
		PostgreSqlConn con = new PostgreSqlConn();
		
		//if not null, brings user to appointment cancel tab
		String appointmentIDCancel = req.getParameter("appointmentIDCancel");
		if (appointmentIDCancel == null) {
			
			//if not null, lets user know if the review they wrote is valid
			String appointmentIDFetched = req.getParameter("appointmentIDFetched");
			if (appointmentIDFetched == null) {
				
				//if not null, brings user to review writing tab
				String appointmentToReview = req.getParameter("appointmentToReview");
				if (appointmentToReview == null) {
					if (con.isCorrectPwd("patient", userName, pwd)) {	
						
						setReqParams(req, resp, userName);
	
						req.getRequestDispatcher("patient_view.jsp").forward(req, resp);
						return;	
					} else {
						req.setAttribute("outcome", "loginFailed");
						req.getRequestDispatcher("patient_login.jsp").forward(req, resp);
					}
				} else {
					
					String patientUsername = req.getParameter("patientUsername");
					//To display patient info
					Patient patient = con.getUserInfoByPatientUsername(patientUsername);
					
					String patientSIN = patient.getPatientSIN();
	
					req.setAttribute("appointmentToReview", appointmentToReview);
					
					if (con.getReviewByKey(patientSIN, appointmentToReview).getAppointmentID() != null) {
						req.setAttribute("outcome", "reviewExists");
					} else {
						Appointment appointmentInfo = con.getAppointmentByAppointmentID(appointmentToReview);
						req.setAttribute("appointmentInfo", appointmentInfo);
					}
	
					setReqParams(req, resp, patientUsername);
	
					req.getRequestDispatcher("patient_view.jsp").forward(req, resp);
					return;	
				}
			} else {
				
				//appointmentIDFetched appointmentID of review form
				String patientSIN = req.getParameter("patientSIN");
				Review findReview = con.getReviewByKey(patientSIN, appointmentIDFetched);
				if (findReview.getAppointmentID() != null) {
					
					req.setAttribute("outcome", "reviewExists");
					
				} else {
	
					String employeeProf = req.getParameter("employeeProf");
					String communication = req.getParameter("communication");
					String cleanliness = req.getParameter("cleanliness");
					String value = req.getParameter("value");
					String reviewText = req.getParameter("review");
					Review review = new Review(patientSIN, appointmentIDFetched, null, null,
							                   employeeProf, communication, cleanliness,
							                   value, reviewText);
					
					boolean resultInserted = con.insertReview(review);
					if (resultInserted) {
						req.setAttribute("outcome", "reviewSubmitted");
					} else {
						req.setAttribute("outcome", "reviewFailed");//sql error
					}
				}
	
				String patientUsername = req.getParameter("patientUsername");
	
				setReqParams(req, resp, patientUsername);
	
				req.getRequestDispatcher("patient_view.jsp").forward(req, resp);
				return;
			}
		} else {
			
			String patientUsername = req.getParameter("patientUsername");

			Patient patient = con.getUserInfoByPatientUsername(patientUsername);
			Appointment appointmentToCancel = con.getAppointmentByAppointmentID(appointmentIDCancel);
			
			if (!con.dateIsWithinADayFromAppointment(appointmentToCancel)) {
				
				con.updateAppointmentStatusByAppointmentID(appointmentToCancel.getAppointmentID(), "cancelled");
				con.removeAllFees(appointmentToCancel.getInvoiceID());
				req.setAttribute("outcome", "cancelSuccess");
				
			} else {
				
				con.updateAppointmentStatusByAppointmentID(appointmentToCancel.getAppointmentID(), "cancelled");
				con.removeAllFees(appointmentToCancel.getInvoiceID());
				
				Integer nextFeeID = con.getMostRecentFeeID()+1;
				con.insertFeeCharge(new FeeCharge(nextFeeID.toString(), appointmentToCancel.getInvoiceID(), "94303", "14"));
				req.setAttribute("outcome", "cancelSuccessFee");
			}
			//set params will recalculate invoice total; will be 0 or 14
			setReqParams(req, resp, patientUsername);
			req.getRequestDispatcher("patient_view.jsp").forward(req, resp);
			
		}
	}

	public void setReqParams(HttpServletRequest req, HttpServletResponse resp, String userName) throws ServletException, IOException {

		PostgreSqlConn con = new PostgreSqlConn();
		
		
		//To display patient info
		Patient patient = con.getUserInfoByPatientUsername(userName);
		req.setAttribute("patient", patient);
		
		String patientSIN = patient.getPatientSIN();

		//Update "pending" appointments to "no show" if the date & time is after appointment
		//Replaces all fees with the single fee of $14 for not showing up
		ArrayList<Appointment> allAppointmentsToUpdate = con.getAllAppointmentsByPatientSIN(patientSIN);
		for (Appointment appointment : allAppointmentsToUpdate) {
			if (appointment.getStatus().equals("pending") && con.dateIsAfterAppointment(appointment)) {
				con.updateAppointmentStatusByAppointmentID(appointment.getAppointmentID(), "no show");
				con.removeAllFees(appointment.getInvoiceID());
				Integer nextFeeID = con.getMostRecentFeeID()+1;
				con.insertFeeCharge(new FeeCharge(nextFeeID.toString(), appointment.getInvoiceID(), "94303", "14"));
			}
			con.updateInvoiceTotal(appointment);
		}

		//To display all completed appointments (for review submission)
		ArrayList<Appointment> finishedAppointments = con.getAppointmentsByPatientSINAndStatus(patientSIN, "finished");
		req.setAttribute("finishedAppointments", finishedAppointments);
		
		//To display all non completed appointments
		ArrayList<Appointment> unfinishedAppointments = con.getUnfinishedAppointmentsByPatientSIN(patientSIN);
		req.setAttribute("appointments", unfinishedAppointments);
		//To display all non completed appointments
		ArrayList<Appointment> pendingAppointments = con.getAppointmentsByPatientSINAndStatus(patientSIN, "pending");
		req.setAttribute("pendingAppointments", pendingAppointments);
		
		ArrayList<Invoice> pendingInvoices = new ArrayList<Invoice>();
		for (Appointment pending : pendingAppointments) {
			pendingInvoices.add(con.getInvoiceByID(pending.getInvoiceID()));
		}
		req.setAttribute("pendingInvoices", pendingInvoices);
		System.out.println(pendingInvoices.size());
		
		
		//To display all non completed appointments
		ArrayList<Appointment> allAppointments = con.getAllAppointmentsByPatientSIN(patientSIN);
		req.setAttribute("appointmentsAll", allAppointments);

		//To display location of appointment
		ArrayList<Branch> branches = con.getAllBranches();
		req.setAttribute("branches", branches);
		
		//To display patient records 
		ArrayList<PatientRecord> records = con.getPatientRecordsByPatientSIN(patientSIN);
		req.setAttribute("records", records);
	}
}