package dentalclinic.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Guardian;

public class UpdateGuardianInfoServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PostgreSqlConn con = new PostgreSqlConn();
		
		String guardianSINEdit = req.getParameter("guardianSINEdit");
		if (guardianSINEdit == null) {
			
			String guardianSIN = req.getParameter("guardianSINSearch");
			System.out.println(guardianSIN);
			Guardian guardian = con.getUserInfoByGuardianSIN(guardianSIN);
			
			req.setAttribute("guardian", guardian);
			
			req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
		} else {
			System.out.println("Patient to update: "+guardianSINEdit);
			
			Guardian newGuardianInfo = new Guardian();

			String userName = req.getParameter("userNameEG");
			newGuardianInfo.setUserName(userName);
			
			String firstName = req.getParameter("fNameEG");
			newGuardianInfo.setFirstName(firstName);
			
			String middleName = req.getParameter("mNameEG");
			newGuardianInfo.setMiddleName(middleName);
			
			String lastName = req.getParameter("lNameEG");
			newGuardianInfo.setLastName(lastName);
			
			String dateOfBirth = req.getParameter("dobEG");
			newGuardianInfo.setDateOfBirth(dateOfBirth);
			
			String age = req.getParameter("ageEG");
			newGuardianInfo.setAge(age);
			
			String gender = req.getParameter("genderEG");
			newGuardianInfo.setGender(gender);
			
			String patientEmail = req.getParameter("emailEG");
			newGuardianInfo.setGuardianEmail(patientEmail);
			
			String patientPhoneNumber = req.getParameter("phoneEG");
			newGuardianInfo.setGuardianPhoneNumber(patientPhoneNumber);
			
			String address = req.getParameter("addressEG");
			newGuardianInfo.setAddress(address);
			
			if (con.updateGuardianInfo(newGuardianInfo, guardianSINEdit)) {//successful
				
				//something happens if successful
				req.setAttribute("firstNameNEW", firstName);
				req.setAttribute("lastNameNEW", lastName);
				req.setAttribute("outcomeG", "updateSuccess");
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				
			} else {//failed; could be an SQL error
				
				//something happens if failed
				req.setAttribute("firstNameNEW", firstName);
				req.setAttribute("lastNameNEW", lastName);
				req.setAttribute("outcomeG", "updateFailed");
				
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				
			}
		}
	}
}

