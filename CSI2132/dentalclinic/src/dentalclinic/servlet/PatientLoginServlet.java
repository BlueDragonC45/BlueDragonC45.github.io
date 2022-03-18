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
import dentalclinic.entities.Employee;

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

		//Get all the attributes of this user, matching the username (unique; enforced)
		//We do this to get the patient's first name, which is userData[2]
		String[] userData = con.getUserInforByPatientUsername(userName);
		
		if (con.isCorrectPwd("patient",userName,pwd)) {			
			
			ArrayList<Room> bookedRooms = con.getBookedRooms(userName);
			
			ArrayList<Room> allRooms = con.getAllAvailRooms();

			req.setAttribute("userSIN", userData[0]);
			req.setAttribute("userName", userName);
			req.setAttribute("firstName", userData[2]);
			req.setAttribute("bookedRooms", bookedRooms);
			req.setAttribute("allRooms", allRooms);

			req.getRequestDispatcher("booking.jsp").forward(req, resp);
			return;	
		}
		resp.sendRedirect("login_failure.jsp");
		return;
	}
}