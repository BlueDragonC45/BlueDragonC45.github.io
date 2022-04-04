package dentalclinic.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Guardian;
import dentalclinic.entities.Patient;

public class GuardianLoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		//Both userName and pwd were obtained from guardian_login.html
		String userName = req.getParameter("userName");
		String pwd = req.getParameter("pwd");
		
		
		//Establish a connection
		PostgreSqlConn con = new PostgreSqlConn();
		
		if (con.isCorrectPwd("guardian", userName, pwd)) {	
			
			//To display patient info
			Guardian guardian = con.getUserInfoByGuardianUsername(userName);
			req.setAttribute("guardian", guardian);
			
			ArrayList<Patient> minors = con.getMinors(guardian.getGuardianSIN());
			req.setAttribute("minors", minors);
			
			req.getRequestDispatcher("guardian_view.jsp").forward(req, resp);
			return;	
		}
		
		req.setAttribute("outcome", "loginFailed");
		req.getRequestDispatcher("guardian_login.jsp").forward(req, resp);
		return;
	}
}