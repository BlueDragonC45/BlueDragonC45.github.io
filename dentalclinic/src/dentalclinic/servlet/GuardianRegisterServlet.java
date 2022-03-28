package dentalclinic.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Guardian;

public class GuardianRegisterServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		//13 columns total
		String guardianSIN = req.getParameter("patientSIN");
		String userName = req.getParameter("userName");
		String guardianPwd = req.getParameter("guardianPwd");
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
		
		
		Guardian guardian = new Guardian();
		guardian.setGuardianSIN(guardianSIN);
		guardian.setUserName(userName);
		//password goes separately
		guardian.setFirstName(firstName);
		guardian.setMiddleName(middleName);
		guardian.setLastName(lastName);
		guardian.setDateOfBirth(dateOfBirth);
		guardian.setAge(age);
		guardian.setGender(gender);
		guardian.setGuardianEmail(patientEmail);
		guardian.setGuardianPhoneNumber(patientPhoneNumber);
		guardian.setAddress(address);
		
		PostgreSqlConn con = new PostgreSqlConn();
		
		boolean isInserted = con.insertNewGuardian(guardian, guardianPwd);
		if (isInserted) {			
				
				//firstName and lastName might have been used already
				//...at receptionist_view.jsp
				req.setAttribute("firstNameNEW", firstName);
				req.setAttribute("lastNameNEW", lastName);

				req.setAttribute("outcomeG", "registerSuccess");

				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				return;			
		} else {

			req.setAttribute("firstNameNEW", firstName);
			req.setAttribute("lastNameNEW", lastName);
			
			req.setAttribute("outcomeG", "registerFailed");

			req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			return;
		}
		
	}
	

}
