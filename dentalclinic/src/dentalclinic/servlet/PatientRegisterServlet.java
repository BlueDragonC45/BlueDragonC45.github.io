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

public class PatientRegisterServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
//		employee account = new employee();
		String patientSIN = req.getParameter("patientSIN");
		String userName = req.getParameter("userName");
		String patientPwd = req.getParameter("patientPwd");
		String firstName = req.getParameter("firstName");
		String middleName = req.getParameter("middleName");
		String lastName = req.getParameter("lastName");
		String dateOfBirth = req.getParameter("dateOfBirth");
		String age = req.getParameter("age");
		String gender = req.getParameter("gender");
		String patientEmail = req.getParameter("patientEmail");
		String patientPhoneNumber = req.getParameter("patientPhoneNumber");
		String address = req.getParameter("address");
		
		String[] param = new String[] {patientSIN,userName,patientPwd,firstName,middleName,lastName,
				           dateOfBirth,age,gender,patientEmail,patientPhoneNumber,address};
		
		PostgreSqlConn con = new PostgreSqlConn();
		boolean pwdfromdb = con.insertNewPatient(param);
		
		System.out.println(pwdfromdb);
		
		if (pwdfromdb) {			
				System.out.println("Success");
				
				ArrayList<Room> bookedRooms = con.getBookedRooms(patientSIN);
				
				ArrayList<Room> allRooms = con.getAllAvailRooms();
				
				System.out.println(allRooms);
				
				req.setAttribute("firstName", firstName);
				req.setAttribute("middleName", middleName);
				req.setAttribute("lastName", lastName);
				req.setAttribute("bookedRooms", bookedRooms);
				req.setAttribute("allRooms", allRooms);

				req.getRequestDispatcher("booking.jsp").forward(req, resp);
				return;			
		}
		resp.sendRedirect("register_failure.jsp");
		return;
	}
	

}
