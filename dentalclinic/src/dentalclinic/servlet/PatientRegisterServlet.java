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

		//12 columns total
		String patientSIN = req.getParameter("patientSIN");
		String userName = req.getParameter("userName");
		String patientPwd = req.getParameter("patientPwd");
		String firstName = req.getParameter("firstName");
		String middleName = req.getParameter("middleName");
		String lastName = req.getParameter("lastName");
		String dateOfBirth = req.getParameter("dateOfBirth");
		String age = req.getParameter("age");
		String gender = req.getParameter("gender");
		String patientEmail = req.getParameter("patientEmail");
		String patientPhoneNumber = req.getParameter("patientPhoneNumber");
		String address = req.getParameter("address");
		
		
		Patient patient = new Patient();
		patient.setPatientSIN(patientSIN);
		patient.setUserName(userName);
		//password goes separately
		patient.setFirstName(firstName);
		patient.setMiddleName(middleName);
		patient.setLastName(lastName);
		patient.setDateofBirth(dateOfBirth);
		patient.setAge(age);
		patient.setGender(gender);
		patient.setPatientEmail(patientEmail);
		patient.setPatientPhoneNumber(patientPhoneNumber);
		patient.setAddress(address);
		
		PostgreSqlConn con = new PostgreSqlConn();
		
		boolean isInserted = con.insertNewPatient(patient, patientPwd);
		if (isInserted) {			
				System.out.println("Successfully inserted a new patient");
				
				//firstName and lastName might have been used already
				//...at receptionist_view.jsp
				req.setAttribute("firstNameNEW", firstName);
				req.setAttribute("lastNameNEW", lastName);

				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				return;			
		}
		//Send to failed page
		resp.sendRedirect("register_failure.jsp");
		return;
	}
	

}
