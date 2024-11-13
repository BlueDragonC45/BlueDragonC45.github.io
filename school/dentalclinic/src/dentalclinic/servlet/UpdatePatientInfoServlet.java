package dentalclinic.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Patient;

public class UpdatePatientInfoServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PostgreSqlConn con = new PostgreSqlConn();
		
		String patientSINEdit = req.getParameter("sinEP");
		if (patientSINEdit == null) {
			
			String patientSIN = req.getParameter("patientSINEP");
			System.out.println(patientSIN);
			Patient patient = con.getUserInfoByPatientSIN(patientSIN);
			
			req.setAttribute("patient", patient);
			
			req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
		} else {
			System.out.println("Patient to update: "+patientSINEdit);
			
			Patient newPatientInfo = new Patient();

			String userName = req.getParameter("userNameEP");
			newPatientInfo.setUserName(userName);
			
			String firstName = req.getParameter("fNameEP");
			newPatientInfo.setFirstName(firstName);
			
			String middleName = req.getParameter("mNameEP");
			newPatientInfo.setMiddleName(middleName);
			
			String lastName = req.getParameter("lNameEP");
			newPatientInfo.setLastName(lastName);
			
			String dateOfBirth = req.getParameter("dobEP");
			newPatientInfo.setDateOfBirth(dateOfBirth);
			
			String age = req.getParameter("ageEP");//TODO calculate in a function
			newPatientInfo.setAge(age);
			
			String gender = req.getParameter("genderEP");
			newPatientInfo.setGender(gender);
			
			String patientEmail = req.getParameter("emailEP");
			newPatientInfo.setPatientEmail(patientEmail);
			
			String patientPhoneNumber = req.getParameter("phoneEP");
			newPatientInfo.setPatientPhoneNumber(patientPhoneNumber);
			
			String address = req.getParameter("addressEP");
			newPatientInfo.setAddress(address);
			
			String guardian = req.getParameter("guardianEP");
			newPatientInfo.setGuardianSIN(guardian);
			
			if (con.updatePatientInfo(newPatientInfo, patientSINEdit)) {//successful
				
				//something happens if successful
				req.setAttribute("firstNameNEW", firstName);
				req.setAttribute("lastNameNEW", lastName);
				req.setAttribute("outcome", "updateSuccess");
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				
			} else {//failed; could be an SQL error
				
				//something happens if failed
				req.setAttribute("firstNameNEW", firstName);
				req.setAttribute("lastNameNEW", lastName);
				req.setAttribute("outcome", "updateFailed");
				
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				
			}
		}
	}
}

