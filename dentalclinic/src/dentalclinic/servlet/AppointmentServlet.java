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
import dentalclinic.entities.Branch;
import dentalclinic.entities.Room;
import dentalclinic.entities.Employee;
import dentalclinic.entities.Patient;

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
		String aFormSIN = req.getParameter("aFormSIN");
		if (aFormSIN != null) {//check patient exists and time availability
			

			String aFormBranchID = req.getParameter("aFormBranchID");
			String aFormBranchLocation = req.getParameter("aFormBranchLocation");
			String aFormDate = req.getParameter("aFormDate");
			String aFormTime = req.getParameter("aFormTime");
			String aFormRoomID = req.getParameter("aFormRoomID");
			String aFormType = req.getParameter("aFormType");
			String aFormEmployees = req.getParameter("aFormEmployees");
			
			Integer recentInvoiceID = Integer.parseInt(con.getMostRecentInvoiceID());
			Integer nextInvoiceID = recentInvoiceID+1;
			
			System.out.println(aFormBranchID+" "+ aFormBranchLocation+" "+aFormDate+" "+ aFormTime+" "+aFormRoomID+" "+
			aFormType+" "+aFormEmployees+" "+nextInvoiceID);;
			
			Appointment appointment =
					new Appointment(aFormBranchID, 
							aFormDate, 
							aFormTime.split("-")[0],
									aFormTime.split("-")[1], aFormSIN, aFormRoomID,
									aFormBranchID, nextInvoiceID.toString(), aFormEmployees.split(", "),
									aFormType, "pending");

			req.setAttribute("pFormBranchLocation", aFormBranchLocation);
			req.setAttribute("newAppointment", appointment);
			
			//patient exists?
			Patient findPatient = con.getUserInfoByPatientSIN(aFormSIN);
			System.out.println(findPatient.getAddress());
			if (findPatient.getAddress() == null) {
				req.setAttribute("outcomeA", "patientNotFound");
			} else {
				ArrayList<Appointment> appointmentsDay = con.getAppointmentsByDateAndLocation(aFormDate, aFormBranchID, aFormRoomID);
				ArrayList<String> occupiedHours = new ArrayList<String>();
				for (int i = 0 ; i < appointmentsDay.size(); i++) {
					occupiedHours.add(appointmentsDay.get(i).getAppointmentStartTime());
				}
				
				if (occupiedHours.size() == 9) {

						
					req.setAttribute("outcomeA", "unavailable");
					
				} else {
					
					req.setAttribute("outcomeA", aFormType);
				}
			}
			req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			

			
			/* else if (occupiedHours.size() == 0) {

				req.setAttribute("resultHours", availableTimeslots);
				req.setAttribute("outcomeA", "availableAll");
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
				req.setAttribute("outcomeA", "availablePartial");
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				
			}*/
			
		} else {
			

			String appointmentBranchID = req.getParameter("appointmentBranchID");
			if (appointmentBranchID == null) {
				
				ArrayList<Branch> branches = con.getAllBranches();
				req.setAttribute("branchesAppointment", branches);
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				
			} else {//redirect user to form

				Branch branch = con.getBranchByBranchID(appointmentBranchID);
				ArrayList<Room> rooms = con.getAllRoomsByBranchID(appointmentBranchID);
				
				ArrayList<Employee> employees = con.getEmployeesByBranchID(appointmentBranchID);
				

				req.setAttribute("branch", branch);
				req.setAttribute("rooms", rooms);
				req.setAttribute("employees", employees);
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				
			}
		}
	}
}
