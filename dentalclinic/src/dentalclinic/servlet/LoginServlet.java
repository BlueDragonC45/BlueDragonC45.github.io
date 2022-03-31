package dentalclinic.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Employee;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		//Get type to redirect to correct login page
		/*String employee = req.getParameter("employee");
		String patient = req.getParameter("patient");
		String guardian = req.getParameter("guardian");*/
		String user = req.getParameter("user");

		
		if (user.equals("employee")) {
			
			req.getRequestDispatcher("employee_login.jsp").forward(req, resp);
			return;		
			
		} else if (user.equals("patient")) {
			
			req.getRequestDispatcher("patient_login.jsp").forward(req, resp);
			return;		
			
		} else if (user.equals("guardian")) {
			
			req.getRequestDispatcher("guardian_login.jsp").forward(req, resp);
			return;		
		}
		return;		
	}
}