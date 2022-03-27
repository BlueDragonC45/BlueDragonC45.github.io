package dentalclinic.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Patient;

public class EditPatientInfoSearchServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		PostgreSqlConn con = new PostgreSqlConn();
		
		String patientSIN = req.getParameter("patientSINEP");
//		Patient patient = con.getUserInfoByPatientSIN(patientSIN);
		Patient patient = new Patient("741258963", "userName", "firstName", "middleName", "lastName",
				   "1474-11-14", "20", "gender",
				   "patientEmail", "705-698-4569", "address", "guardian");
		
		req.setAttribute("patient", patient);
		req.setAttribute("patientSIN", patient.getPatientSIN());
		req.setAttribute("username", patient.getUserName());
		req.setAttribute("fName", patient.getFirstName());
		req.setAttribute("mName", patient.getMiddleName());
		req.setAttribute("lName", patient.getLastName());
		req.setAttribute("dob", patient.getDateofBirth());
		req.setAttribute("age", patient.getAge());
		req.setAttribute("gender", patient.getGender());
		req.setAttribute("email", patient.getPatientEmail());
		req.setAttribute("phone", patient.getPatientPhoneNumber());
		req.setAttribute("address", patient.getAddress());
		
		req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
	}
}

