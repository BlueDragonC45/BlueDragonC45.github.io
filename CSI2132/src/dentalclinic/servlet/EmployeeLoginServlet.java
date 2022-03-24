package dentalclinic.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Employee;

public class EmployeeLoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
//		employee account = new employee();
		String userName = req.getParameter("userName");//Entered userName
		String pwd = req.getParameter("pwd");//Entered password
		
		PostgreSqlConn con = new PostgreSqlConn();
		
		//Sends entered pwd to DB, where it is checked
		//Pwd from the DB is always encrypted
		if (con.isCorrectPwd("employee",userName,pwd)) {			
				System.out.println("Login Successful");
				req.setAttribute("userName", userName);
				resp.sendRedirect("login_success.jsp?userName="+userName);
				return;			
		}
		resp.sendRedirect("login_failure.jsp");
		return;
	}
}