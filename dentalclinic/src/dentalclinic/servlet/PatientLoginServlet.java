package dentalclinic.servlet;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Room;
import dentalclinic.entities.Patient;

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
			

			//Get all the attributes of this user, matching the username (unique; enforced)
			//We do this to get the patient's first name, which is userData[2]
			Patient patient = con.getUserInfoByPatientUsername(userName);

//			req.setAttribute("userSIN", patient.getPatientSIN());
//			req.setAttribute("userName", userName);
//			req.setAttribute("firstName", patient.getFirstName());
//			req.setAttribute("middleName", patient.getMiddleName());
//			req.setAttribute("lastName", patient.getLastName());
			req.setAttribute("patient", patient);

			req.getRequestDispatcher("patient_view.jsp").forward(req, resp);
			return;	
		}
		resp.sendRedirect("login_failure.jsp");
		return;
	}
}