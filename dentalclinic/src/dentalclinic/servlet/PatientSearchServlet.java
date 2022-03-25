package dentalclinic.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Patient;

public class PatientSearchServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		//Both userName and pwd were obtained from patient_login.html
		String patientSIN = req.getParameter("patientSIN");
		
		//Establish a connection
		PostgreSqlConn con = new PostgreSqlConn();
		
		Patient patient = con.getUserInfoByPatientSIN(patientSIN);
		String patientStr = patient.toString();
		
		req.setAttribute("patientSIN", patientSIN);
		req.setAttribute("patientStr", patientStr);

		req.getRequestDispatcher("dentist_view.jsp").forward(req, resp);
		return;	
		
	}
}