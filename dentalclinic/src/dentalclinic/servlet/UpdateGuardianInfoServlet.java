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
		
		String patientSINEdit = req.getParameter("guardianSINEdit");
		if (patientSINEdit == null) {
			
			String patientSIN = req.getParameter("guardianSIN");
			System.out.println(patientSIN);
			Guardian patient = con.getUserInfoByGuardianSIN(patientSIN);
			
			req.setAttribute("patient", patient);
			
			req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
		} else {
			System.out.println("Patient to update: "+patientSINEdit);
			
			Guardian newGuardianInfo = new Guardian();

			String userName = req.getParameter("userNameEP");
			newGuardianInfo.setUserName(userName);
			
			String firstName = req.getParameter("fNameEP");
			newGuardianInfo.setFirstName(firstName);
			
			String middleName = req.getParameter("mNameEP");
			newGuardianInfo.setMiddleName(middleName);
			
			String lastName = req.getParameter("lNameEP");
			newGuardianInfo.setLastName(lastName);
			
			String dateOfBirth = req.getParameter("dobEP");
			newGuardianInfo.setDateOfBirth(dateOfBirth);
			
			String age = req.getParameter("ageEP");//TODO calculate in a function
			newGuardianInfo.setAge(age);
			
			String gender = req.getParameter("genderEP");
			newGuardianInfo.setGender(gender);
			
			String patientEmail = req.getParameter("emailEP");
			newGuardianInfo.setGuardianEmail(patientEmail);
			
			String patientPhoneNumber = req.getParameter("phoneEP");
			newGuardianInfo.setGuardianPhoneNumber(patientPhoneNumber);
			
			String address = req.getParameter("addressEP");
			newGuardianInfo.setAddress(address);
			
			if (con.updateGuardianInfo(newGuardianInfo, patientSINEdit)) {//successful
				
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

