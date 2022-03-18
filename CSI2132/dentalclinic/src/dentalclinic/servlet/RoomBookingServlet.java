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

public class RoomBookingServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
//		employee account = new employee();
		String userName = req.getParameter("userName");
		String firstName = req.getParameter("firstName");
		String roomid = req.getParameter("roomid");
		String branchid = req.getParameter("branchid");
		
		
		PostgreSqlConn con = new PostgreSqlConn();
		
		String userSIN = con.bookRoom(userName, roomid, branchid);
		
		if (userSIN.length()!=0) {			
			
			ArrayList<Room> bookedRooms = con.getBookedRooms(userSIN);
			
			ArrayList<Room> allRooms = con.getAllAvailRooms();
			

			req.setAttribute("userSIN", userSIN);
			req.setAttribute("userName", userName);
			req.setAttribute("firstName", firstName);
			req.setAttribute("bookedRooms", bookedRooms);
			req.setAttribute("allRooms", allRooms);

			req.getRequestDispatcher("booking.jsp").forward(req, resp);
			return;	
		}
		resp.sendRedirect("booking_failure.jsp");
		return;
	}
}