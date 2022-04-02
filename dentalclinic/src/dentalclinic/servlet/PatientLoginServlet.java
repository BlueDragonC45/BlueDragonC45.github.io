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
		
		
		
		String appointmentIDFetched = req.getParameter("appointmentIDFetched");
		if (appointmentIDFetched == null) {
			String appointmentToReview = req.getParameter("appointmentToReview");

			if (appointmentToReview == null) {
				if (con.isCorrectPwd("patient", userName, pwd)) {	
					
					//To display patient info
					Patient patient = con.getUserInfoByPatientUsername(userName);
					req.setAttribute("patient", patient);
					
					String patientSIN = patient.getPatientSIN();

					//To display all completed appointments (for review submission)
					ArrayList<Appointment> finishedAppointments = con.getFinishedAppointmentsByPatientSIN(patientSIN);
					req.setAttribute("finishedAppointments", finishedAppointments);
					
					//To display all non completed appointments
					ArrayList<Appointment> unfinishedAppointments = con.getUnfinishedAppointmentsByPatientSIN(patientSIN);
					req.setAttribute("appointments", unfinishedAppointments);
					
					//To display all non completed appointments
					ArrayList<Appointment> allAppointments = con.getAllAppointmentsByPatientSIN(patientSIN);
					req.setAttribute("appointmentsAll", allAppointments);

					//To display location of appointment
					ArrayList<Branch> branches = con.getAllBranches();
					req.setAttribute("branches", branches);
					
					//To display patient records 
					ArrayList<PatientRecord> records = con.getPatientRecordsByPatientSIN(patientSIN);
					req.setAttribute("records", records);

					req.getRequestDispatcher("patient_view.jsp").forward(req, resp);
					return;	
				}
			} else {
				
				String patientUsername = req.getParameter("patientUsername");
				//To display patient info
				Patient patient = con.getUserInfoByPatientUsername(patientUsername);
				req.setAttribute("patient", patient);
				
				String patientSIN = patient.getPatientSIN();

				req.setAttribute("appointmentToReview", appointmentToReview);
				
				if (con.getReviewByKey(patientSIN, appointmentToReview).getAppointmentID() != null) {
					req.setAttribute("outcome", "reviewExists");
				} else {
					Appointment appointmentInfo = con.getAppointmentByAppointmentID(appointmentToReview);
					req.setAttribute("appointmentInfo", appointmentInfo);
				}

				//To display all completed appointments (for review submission)
				ArrayList<Appointment> finishedAppointments = con.getFinishedAppointmentsByPatientSIN(patientSIN);
				req.setAttribute("finishedAppointments", finishedAppointments);
				
				//To display all non completed appointments
				ArrayList<Appointment> unfinishedAppointments = con.getUnfinishedAppointmentsByPatientSIN(patientSIN);
				req.setAttribute("appointments", unfinishedAppointments);
				
				//To display all non completed appointments
				ArrayList<Appointment> allAppointments = con.getAllAppointmentsByPatientSIN(patientSIN);
				req.setAttribute("appointmentsAll", allAppointments);

				//To display location of appointment
				ArrayList<Branch> branches = con.getAllBranches();
				req.setAttribute("branches", branches);
				
				//To display patient records 
				ArrayList<PatientRecord> records = con.getPatientRecordsByPatientSIN(patientSIN);
				req.setAttribute("records", records);

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
				String reviewText = req.getParameter("review");
				Review review = new Review(patientSIN, appointmentIDFetched, null, null,
						                   employeeProf, communication, cleanliness, reviewText);
				
				boolean resultInserted = con.insertReview(review);
				if (resultInserted) {
					req.setAttribute("outcome", "reviewSubmitted");
				} else {
					req.setAttribute("outcome", "reviewFailed");//sql error
				}
			}

			String patientUsername = req.getParameter("patientUsername");
			
			Patient patient = con.getUserInfoByPatientUsername(patientUsername);
			req.setAttribute("patient", patient);
			
			//To display all completed appointments (for review submission)
			ArrayList<Appointment> finishedAppointments = con.getFinishedAppointmentsByPatientSIN(patientSIN);
			req.setAttribute("finishedAppointments", finishedAppointments);
			
			//To display all non completed appointments
			ArrayList<Appointment> unfinishedAppointments = con.getUnfinishedAppointmentsByPatientSIN(patientSIN);
			req.setAttribute("appointments", unfinishedAppointments);
			
			//To display all non completed appointments
			ArrayList<Appointment> allAppointments = con.getAllAppointmentsByPatientSIN(patientSIN);
			req.setAttribute("appointmentsAll", allAppointments);

			//To display location of appointment
			ArrayList<Branch> branches = con.getAllBranches();
			req.setAttribute("branches", branches);
			
			//To display patient records 
			ArrayList<PatientRecord> records = con.getPatientRecordsByPatientSIN(patientSIN);
			req.setAttribute("records", records);

			req.getRequestDispatcher("patient_view.jsp").forward(req, resp);
			return;	
			
		}
		
		

		
		req.setAttribute("outcome", "loginFailed");
		req.getRequestDispatcher("patient_login.jsp").forward(req, resp);
		return;
	}
}