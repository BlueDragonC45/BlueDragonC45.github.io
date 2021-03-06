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

public class PatientRegisterServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		//13 columns total
		String patientSIN = req.getParameter("patientSIN");
		String userName = req.getParameter("userName");
		String patientPwd = req.getParameter("patientPwd");
		String firstName = req.getParameter("firstName");
		String middleName = req.getParameter("middleName");
		String lastName = req.getParameter("lastName");
		String dateOfBirth = req.getParameter("dateOfBirth");
		String age = req.getParameter("age");
		System.out.println(age+" years");
		String gender = req.getParameter("gender");
		String patientEmail = req.getParameter("patientEmail");
		String patientPhoneNumber = req.getParameter("patientPhoneNumber");
		String address = req.getParameter("address");
		String guardian = req.getParameter("guardian");
		
		
		Patient patient = new Patient();
		patient.setPatientSIN(patientSIN);
		patient.setUserName(userName);
		//password goes separately
		patient.setFirstName(firstName);
		patient.setMiddleName(middleName);
		patient.setLastName(lastName);
		patient.setDateOfBirth(dateOfBirth);
		patient.setAge(age);
		patient.setGender(gender);
		patient.setPatientEmail(patientEmail);
		patient.setPatientPhoneNumber(patientPhoneNumber);
		patient.setAddress(address);
		patient.setGuardianSIN(guardian);
		
		PostgreSqlConn con = new PostgreSqlConn();

		req.setAttribute("firstNameNEW", firstName);
		req.setAttribute("lastNameNEW", lastName);
		
		int insertedCode = con.insertPatient(patient, patientPwd);
		if (insertedCode == 0) {			
				
				req.setAttribute("outcome", "registerSuccess");

				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				return;			
		} else if (insertedCode == 1) {
			
			req.setAttribute("outcome", "patientsin repeated");

			req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			return;
		} else if (insertedCode == 2) {
			
			req.setAttribute("outcome", "username repeated");

			req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			return;
		} else if (insertedCode == 3) {
			
			req.setAttribute("outcome", "guardian does not exist");

			req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			return;
		} else {
			
			req.setAttribute("outcome", "unknown error");

			req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
		}
		
	}
	

}
