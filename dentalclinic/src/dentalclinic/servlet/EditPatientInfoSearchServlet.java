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

		PostgreSqlConn con = new PostgreSqlConn();
		
		String patientSIN = req.getParameter("patientSINEP");
		System.out.println(patientSIN);
		Patient patient = con.getUserInfoByPatientSIN(patientSIN);
		
		req.setAttribute("patient", patient);
		
		req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
	}
}

