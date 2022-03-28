package dentalclinic.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Branch;
import dentalclinic.entities.Employee;

public class ListBranchEmployeesServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PostgreSqlConn con = new PostgreSqlConn();
		
		String branchID = req.getParameter("branchIDSelected");
		if (branchID == null) {
			ArrayList<Branch> branches = con.getAllBranches();
			req.setAttribute("branches", branches);
			
			req.getRequestDispatcher("manager_view.jsp").forward(req, resp);
			return;	
		}
		
		ArrayList<Employee> employees = con.getEmployeesByBranchID(branchID);
		req.setAttribute("employees", employees);
		
		req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
	}
}
