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
import dentalclinic.entities.Appointment;

public class EmployeeLoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		String userName = req.getParameter("userName");//Entered userName
		String pwd = req.getParameter("pwd");//Entered password
		System.out.println(userName + " " + pwd);
		
		PostgreSqlConn con = new PostgreSqlConn();
		
		//Sends entered pwd to DB, where it is checked
		//Pwd from the DB is always encrypted
		if (con.isCorrectPwd("employee", userName, pwd)) {	

			//Get all the attributes of this user, matching the username (unique; enforced)
			//We do this to get the patient's first name, which is userData[2]
			Employee employee = con.getUserInfoByEmployeeUsername(userName);
			String employeeSIN = employee.getEmployeeSIN();
			String role = employee.getRole();
			String branchid = employee.getBranchID();

			req.setAttribute("employeeSIN", employeeSIN);
			req.setAttribute("userName", userName);
			req.setAttribute("role", role);
			
			System.out.println(role);
			
			if (role.equals("manager")) {
				
				req.getRequestDispatcher("manager_view.jsp").forward(req, resp);
				
			} else if (role.equals("receptionist")) {
				
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				
			} else if (role.equals("dentist")) {
				
				req.getRequestDispatcher("dentist_view.jsp").forward(req, resp);
				
			} else if (role.equals("hygienist")) {
				
				req.getRequestDispatcher("hygienist_view.jsp").forward(req, resp);
				
			}
			
			return;		
		}
		resp.sendRedirect("login_failure.jsp");
		return;
	}
}