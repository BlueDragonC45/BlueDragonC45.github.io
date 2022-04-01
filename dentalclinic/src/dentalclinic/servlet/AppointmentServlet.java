package dentalclinic.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Appointment;

@SuppressWarnings("serial")
public class AppointmentServlet extends HttpServlet {

	//Restriction: appointments last an hour, thus 16:00:00 is the last
	//             since we have 16:00:00-17:00:00 and we close at 5pm
	private ArrayList<String> availableStartHours =
			new ArrayList<String>(
				Arrays.asList( 
					new String[]{"08:00:00",
								 "09:00:00",
								 "10:00:00",
								 "11:00:00",
								 "12:00:00",
								 "13:00:00",
								 "14:00:00",
								 "15:00:00",
								 "16:00:00",//8th index
								 }));
	
	private String[] availableTimeslots =
			        new String[]{"08:00:00-09:00:00",
								 "09:00:00-10:00:00",
								 "10:00:00-11:00:00",
								 "11:00:00-12:00:00",
								 "12:00:00-13:00:00",
								 "13:00:00-14:00:00",
								 "14:00:00-15:00:00",
								 "15:00:00-16:00:00",
								 "16:00:00-17:00:00",//8th index
								 };

	private ArrayList<String> test =
			new ArrayList<String>(
				Arrays.asList( 
					new String[]{"08:00:00",
								 "09:00:00",
								 "11:00:00",
								 "12:00:00",
								 "13:00:00",
								 "16:00:00",//8th index
								 }));
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PostgreSqlConn con = new PostgreSqlConn();
		
		//selected day
		String appointmentDateQuery = req.getParameter("appointmentDateQuery");
		req.setAttribute("appointmentDateQueryForward", appointmentDateQuery);
		
		ArrayList<Appointment> appointmentsDay = con.getAppointmentsByDate(appointmentDateQuery);
		ArrayList<String> occupiedHours = new ArrayList<String>();
		for (int i = 0 ; i < appointmentsDay.size(); i++) {
			occupiedHours.add(appointmentsDay.get(i).getAppointmentStartTime());
		}
		
		if (occupiedHours.size() == 9) {
			
			req.setAttribute("availability", "full");
			req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			
		} else if (occupiedHours.size() == 0) {

			req.setAttribute("resultHours", availableTimeslots);
			req.setAttribute("availability", "available");
			req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			
		} else {
			
			for (String occupiedHour : occupiedHours) {
				if (availableStartHours.contains(occupiedHour)) {
					int index = availableStartHours.indexOf(occupiedHour);
					availableTimeslots[index] = null;
				}
			}
			
			System.out.println(availableStartHours.size()+" "+availableTimeslots.length);
			
			ArrayList<String> resultHours = new ArrayList<String>();
			for (int i = 0 ; i < availableTimeslots.length ; i++) {
				if (availableTimeslots[i] != null) {
					resultHours.add(availableTimeslots[i]);
				}
			}
			
			System.out.println(resultHours.toString());
			
			
			req.setAttribute("resultHours", resultHours);
			req.setAttribute("availability", "available");
			req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
		}
		
		
		/*System.out.println(occupiedHours.get(0)+"  "+ availableStartHours[5] + " "+
				occupiedHours.get(0).equals(availableStartHours[5]));*/
			//s
			/*
			int outcomeCode = con.billUser(newBill, insuranceClaim);
			if (outcomeCode == 0) {

				req.setAttribute("patientSINAfterBill", patientSINBillForm);
				req.setAttribute("billTotal", total);
				
				req.setAttribute("outcomeB", "bill success");
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				
			} else if (outcomeCode == 1) {
				
				req.setAttribute("outcomeB", "employee not found");
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			} else if (outcomeCode == 2) {
				
				req.setAttribute("outcomeB", "already paid in full");
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			} else if (outcomeCode == 3) {
				
				req.setAttribute("outcomeB", "unknown error");
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			}*/
			
		
	}
}
