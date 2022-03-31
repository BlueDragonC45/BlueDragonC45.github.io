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
		
		if (con.isCorrectPwd("patient", userName, pwd)) {	
			
			//To display patient info
			Patient patient = con.getUserInfoByPatientUsername(userName);
			req.setAttribute("patient", patient);
			
			String patientSIN = patient.getPatientSIN();
			
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