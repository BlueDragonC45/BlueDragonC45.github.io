package dentalclinic.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Employee;
import dentalclinic.entities.PatientRecord;

public class RecordSearchServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		//dentist must have their SIN at all times to
		//be able to search for their appointments
		String employeeSIN = req.getParameter("employeeSIN");
		req.setAttribute("employeeSIN", employeeSIN);
		
		PostgreSqlConn con = new PostgreSqlConn();

		String patientSIN = req.getParameter("patientSIN");
		
		ArrayList<PatientRecord> patientRecords = con.getPatientRecordsByPatientSIN(patientSIN);
		req.setAttribute("patientRecords", patientRecords);

		Employee employee = con.getUserInfoByEmployeeSIN(employeeSIN);

		if (employee.getRole().equals("dentist")) {

			req.getRequestDispatcher("dentist_view.jsp").forward(req, resp);
			
		} else if (employee.getRole().equals("hygienist")) {

			req.getRequestDispatcher("hygienist_view.jsp").forward(req, resp);
			
		}
		return;	
		
	}
}