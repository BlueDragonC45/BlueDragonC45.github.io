package dentalclinic.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Appointment;
import dentalclinic.entities.Branch;

import java.util.ArrayList;

public class ListEmployeeAppointmentsServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		//MUST set employeeSIN again, it will not be sent otherwise
		String employeeSIN = req.getParameter("employeeSIN");
		req.setAttribute("employeeSIN", employeeSIN);

		PostgreSqlConn con = new PostgreSqlConn();
		ArrayList<Appointment> appointments = con.getAppointmentsByEmployeeSIN(employeeSIN);
		req.setAttribute("appointments", appointments);
		
		ArrayList<Branch> branches = con.getAllBranches();
		req.setAttribute("branches", branches);

		req.getRequestDispatcher("dentist_view.jsp").forward(req, resp);
		return;	
		
	}
}