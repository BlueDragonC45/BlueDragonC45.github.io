package dentalclinic.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Employee;
import dentalclinic.entities.PatientRecord;
import dentalclinic.entities.Appointment;
import dentalclinic.entities.Branch;
import dentalclinic.entities.Treatment;

public class WritePatientRecordServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		//dentist must have their SIN at all times to
		//be able to search for their appointments
		String employeeSIN = req.getParameter("employeeSIN");
		req.setAttribute("employeeSIN", employeeSIN);

		PostgreSqlConn con = new PostgreSqlConn();

		//When the employee submits the form for a new patient record
		String patientSINWrite = req.getParameter("patientSINWrite");
		if (patientSINWrite == null) {
			
			//If this is null, send user options (appointments) to select from
			//to write a new patient record for it
			String appointmentIDSelected = req.getParameter("appointmentIDSelected");
			if (appointmentIDSelected == null) {
				ArrayList<Appointment> appointments = con.getAppointmentsByEmployeeSIN(employeeSIN);
				
				ArrayList<Appointment> appointmentTypeTreatment = new ArrayList<Appointment>();
				for (int i = 0 ; i < appointments.size(); i++) {
					if (appointments.get(i).getAppointmentType().equals("treatment")) {
						appointmentTypeTreatment.add(appointments.get(i));
					}
				}
				req.setAttribute("appointmentTypeTreatment", appointmentTypeTreatment);
				
				String tmp = "";
				ArrayList<Integer> treatmentCount = new ArrayList<Integer>();
				for (int i = 0 ; i < appointmentTypeTreatment.size() ; i++) {
					tmp = appointmentTypeTreatment.get(i).getAppointmentID();
					
					treatmentCount.add(con.getTreatmentCountByAppointmentID(tmp));
				}
				req.setAttribute("treatmentCount", treatmentCount);

				ArrayList<Branch> branches = con.getAllBranches();
				req.setAttribute("branches", branches);
			} else {
				
				Appointment appointment = con.getAppointmentByAppointmentID(appointmentIDSelected);
				
				ArrayList<Treatment> treatments = con.getTreatmentsByAppointmentID(appointmentIDSelected);
				
				ArrayList<String> teethInvolved = new ArrayList<String>();
				for (int i = 0 ; i < treatments.size() ; i++) {
					teethInvolved.add(treatments.get(i).getToothInvolved());
				}
				
				PatientRecord foundRecord = con.getPatientRecordByKey(appointment.getPatientSIN(), appointment.getAppointmentID());
				if (foundRecord.getPatientSIN() != null) {
					req.setAttribute("outcome", "recordAlreadyExists");
				}

				req.setAttribute("patientSINWrite", appointment.getPatientSIN());
				req.setAttribute("appointmentIDWrite", appointmentIDSelected);
				req.setAttribute("teethInvolved", teethInvolved);
			}
		} else {

			String appointmentIDWrite = req.getParameter("appointmentIDWrite");
			String teethInvolved = req.getParameter("teethInvolved");
			String treatmentDetails = req.getParameter("treatmentDetails");
			
			teethInvolved = teethInvolved.replaceAll(" ","");
			System.out.println(teethInvolved);
			String[] teethInvolvedStrArr = teethInvolved.split(",");
			
			PatientRecord patientRecord = new PatientRecord(patientSINWrite, appointmentIDWrite,
															teethInvolvedStrArr, treatmentDetails);
			int isInserted = con.insertPatientRecord(patientRecord);
			if (isInserted == 0) {

				req.setAttribute("outcome", "recordWriteSuccess");
			} else if (isInserted == 1) {

				req.setAttribute("outcome", "recordAlreadyExists");
			} else {

				req.setAttribute("outcome", "recordWriteFailure");
			}
		}


		Employee employee = con.getUserInfoByEmployeeSIN(employeeSIN);
		if (employee.getRole().equals("dentist")) {

			req.getRequestDispatcher("dentist_view.jsp").forward(req, resp);
			
		} else if (employee.getRole().equals("hygienist")) {

			req.getRequestDispatcher("hygienist_view.jsp").forward(req, resp);
			
		}
		return;	
		
	}
}