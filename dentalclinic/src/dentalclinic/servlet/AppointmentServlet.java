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
import dentalclinic.entities.AppointmentProcedure;
import dentalclinic.entities.Treatment;
import dentalclinic.entities.Branch;
import dentalclinic.entities.Room;
import dentalclinic.entities.Employee;
import dentalclinic.entities.Patient;
import dentalclinic.entities.FeeCharge;

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
		
		String procedureTypeList = req.getParameter("pFormTypeList");
		if (procedureTypeList == null) {
			
			String aFormSIN = req.getParameter("aFormSIN");
			if (aFormSIN == null) {
				
				//give information about available locations
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
				
			} else {
				
				//aFormSIN
				String aFormBranchID = req.getParameter("aFormBranchID");
				String aFormBranchLocation = req.getParameter("aFormBranchLocation");
				String aFormDate = req.getParameter("aFormDate");
				String aFormTime = req.getParameter("aFormTime");
				String aFormRoomID = req.getParameter("aFormRoomID");
				String aFormType = req.getParameter("aFormType");
				String aFormEmployees = req.getParameter("aFormEmployees");

				req.setAttribute("pFormBranchLocation", aFormBranchLocation);
				
				System.out.println(aFormBranchID+" "+ aFormBranchLocation+" "+aFormDate+" "+ aFormTime+" "+aFormRoomID+" "+
				aFormType+" "+aFormEmployees);
				
				Appointment appointment =
						new Appointment(aFormBranchID, 
								aFormDate, aFormTime.split("-")[0],
								aFormTime.split("-")[1], aFormSIN, aFormRoomID,
								aFormBranchID, null, null,
								aFormType, null);

				req.setAttribute("newAppointment", appointment);
				req.setAttribute("pFormTime", aFormBranchLocation);
				req.setAttribute("pFormEmployees", aFormBranchLocation);
				
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
				}
				

				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);

			}
		} else {//Three main steps below;
			    //invoice insert => appointment insert => procedure and feecharge insertion
		
			//Fetch all appointment information from user input
			Integer newAppointmentID = con.getMostRecentAppointmentID();
			String pFormDate = req.getParameter("pFormDate");
			String pFormTime = req.getParameter("pFormTime");
			//time contains start and end
			String pFormSIN = req.getParameter("pFormSIN");//patientSIN
			String pFormRoomID = req.getParameter("pFormRoomID");
			String pFormBranchID = req.getParameter("pFormBranchID");
			//invoiceID set in func
			String pFormEmployees = req.getParameter("pFormEmployees");
			String pFormAType = req.getParameter("pFormAType");
			//status set in func
			
			Integer newInvoiceID = con.getMostRecentInvoiceID();
			
			//insert this appointment (function fills in missing values
			//like determining what the next ID to use is)
			con.insertNewAppointment(
					new Appointment(newAppointmentID.toString(), pFormDate,
							pFormTime.split("-")[0],
					        pFormTime.split("-")[1], pFormSIN, pFormRoomID, 
					        pFormBranchID, newInvoiceID.toString(), pFormEmployees.split(", "),
					        pFormAType, "pending"));

			//Fetch info on all procedures to insert to this appointment
			//col1: newAppointmentID gotten from above
			String pFormToothList = req.getParameter("pFormToothList");
			//type list contains code, type and fee
			String pFormTypeList = req.getParameter("pFormTypeList");
			String pFormAmtAndMatList = req.getParameter("pFormBranchID");
			String pFormDescriptionList = req.getParameter("pFormBranchID");
			//proceduredate == pFormDate
			
			//

			//Since some values above store multiple values (possibly multiple procedures)
			//we have to index them and do insertions individually by index
			String[] allTeeth = pFormToothList.split("|");
			String[] codeTypesAndFees = pFormTypeList.split("|");
			String[] amountAndMats = pFormAmtAndMatList.split("|");
			String[] descriptions = pFormDescriptionList.split("|");
			
 			for (int i = 0 ; i < allTeeth.length ; i++) {
 				String code = codeTypesAndFees[i].split(" ")[0];
 				String type = codeTypesAndFees[i].split(" ")[1];
 				String fee = codeTypesAndFees[i].split(" ")[2];
 				
 				con.insertNewAppointmentProcedure(
 						new AppointmentProcedure(newAppointmentID.toString(), allTeeth[i],
 											     code, type, amountAndMats[i].split(", "), 
 												 descriptions[i], pFormDate));
 				con.insertFeeCharge(
 						new FeeCharge(null, ));
			}

			
		}
	}
}
