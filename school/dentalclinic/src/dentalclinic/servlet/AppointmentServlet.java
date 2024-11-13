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
import dentalclinic.entities.Invoice;
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
		
		String treatmentTypeList = req.getParameter("tFormTypeList");
		if (treatmentTypeList == null) {
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
						
						ArrayList<Employee> allEmployees = con.getEmployeesByBranchID(appointmentBranchID);
						
						ArrayList<Employee> dentistsAndHygienists = new ArrayList<Employee>();
						for (int i = 0 ; i < allEmployees.size() ; i++) {
						    String tmpRole = allEmployees.get(i).getRole();
							if (tmpRole.equals("dentist") || tmpRole.equals("hygienist")) {
								dentistsAndHygienists.add(allEmployees.get(i));							}
						}

						req.setAttribute("branch", branch);
						req.setAttribute("rooms", rooms);
						req.setAttribute("employees", dentistsAndHygienists);
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
					req.setAttribute("pFormTime", aFormTime);
					req.setAttribute("pFormEmployees", aFormEmployees);
					
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
							
						} else if (occupiedHours.size() == 0) {

							req.setAttribute("outcomeA", aFormType);
							req.setAttribute("resultHours", availableTimeslots);
						
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
							
							if (resultHours.contains(aFormTime)) {
								req.setAttribute("outcomeA", aFormType);
							} else {
								req.setAttribute("outcomeA", "not free");
								req.setAttribute("resultHours", resultHours);
							}
						}
					}

					req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);

				}
			} else {//Three main steps below;
				    //invoice insert => appointment insert => procedure and feecharge insertion
			
				//Fetch all appointment information from user input
				Integer newAppointmentID = con.getMostRecentAppointmentID()+1;
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
				
				//Get next invoiceID and create invoice for appointment
				Integer newInvoiceID = con.getMostRecentInvoiceID()+1;
				Integer result = con.insertInvoice(
						new Invoice(newInvoiceID.toString(), null,
								pFormSIN, null, "0", "0", "0", "0", "0", "0"));
				
				System.out.println(newAppointmentID.toString() + " "+ pFormDate+" "+pFormTime+" "+pFormSIN+
						" "+pFormRoomID+" "+pFormBranchID+" "+pFormEmployees+" "+pFormAType);			
				//insert this appointment (function fills in missing values
				//like determining what the next ID to use is)
				int outcome = con.insertAppointment(
						new Appointment(newAppointmentID.toString(), pFormDate,
								pFormTime.split("\\-")[0],
						        pFormTime.split("\\-")[1], pFormSIN, pFormRoomID, 
						        pFormBranchID, newInvoiceID.toString(), pFormEmployees.split(", "),
						        pFormAType, "pending"));

				if (outcome == 0) {//success
					//Fetch info on all procedures to insert to this appointment
					//col1: newAppointmentID gotten from above
					String pFormToothList = req.getParameter("pFormToothList");
					//type list contains code, type and fee
					String pFormTypeList = req.getParameter("pFormTypeList");
					String pFormAmtAndMatList = req.getParameter("pFormAmtAndMatList");
					String pFormDescriptionList = req.getParameter("pFormDescriptionList");
					//proceduredate == pFormDate
					
					System.out.println(pFormToothList+ " "+pFormTypeList+ " "+pFormAmtAndMatList+ " "+pFormDescriptionList);

					//Since some values above store multiple values (possibly multiple procedures)
					//we have to index them and do insertions individually by index
					String[] allTeeth = pFormToothList.split("\\|");
					String[] codeTypesAndFees = pFormTypeList.split("\\|");
					String[] amountAndMats = pFormAmtAndMatList.split("\\|");
					String[] descriptions = pFormDescriptionList.split("\\|");
					Integer newFeeID = con.getMostRecentFeeID()+1;
					
		 			for (int i = 0 ; i < allTeeth.length ; i++) {
		 				String procedureCode = codeTypesAndFees[i].split(" ")[0];
		 				String type = codeTypesAndFees[i].split(" ")[1];
		 				String fee = codeTypesAndFees[i].split(" ")[2];
		 				String feeID = codeTypesAndFees[i].split(" ")[3];
		 				
		 				System.out.println(amountAndMats[i]);
		 				
		 				con.insertAppointmentProcedure(
		 						new AppointmentProcedure(newAppointmentID.toString(), allTeeth[i],
		 												 procedureCode, type, amountAndMats[i].split("\\,\\s"), 
		 												 descriptions[i], pFormDate));
		 				con.insertFeeCharge(
		 						new FeeCharge(newFeeID.toString(), newInvoiceID.toString(),
		 									  feeID, fee));
		 				newFeeID++;
					}
					req.setAttribute("outcomeA", "successAddAppointment");
					req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				} else {//failed; already exists
					req.setAttribute("outcomeA", "failedAddAppointment");
					req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				}

			}
		} else {//Three main steps below
		        //invoice insert => treatment insert => treatment and feecharge insertion
			
			//Fetch all appointment information from user input
			Integer newAppointmentID = con.getMostRecentAppointmentID()+1;
			String tFormDate = req.getParameter("tFormDate");
			String tFormTime = req.getParameter("tFormTime");
			//time contains start and end
			String tFormSIN = req.getParameter("tFormSIN");//patientSIN
			String tFormRoomID = req.getParameter("tFormRoomID");
			String tFormBranchID = req.getParameter("tFormBranchID");
			//invoiceID set in func
			String tFormEmployees = req.getParameter("tFormEmployees");
			String tFormAType = req.getParameter("tFormAType");
			//status set in func
			
			//Get next invoiceID and create invoice for appointment
			Integer newInvoiceID = con.getMostRecentInvoiceID()+1;
			Integer result = con.insertInvoice(
					new Invoice(newInvoiceID.toString(), null,
							tFormSIN, null, "0", "0", "0", "0", "0", "0"));
			
			System.out.println(newAppointmentID.toString() + " "+ tFormDate+" "+tFormTime+" "+tFormSIN+
					" "+tFormRoomID+" "+tFormBranchID+" "+tFormEmployees+" "+tFormAType);			
			//insert this appointment (function fills in missing values
			//like determining what the next ID to use is)
			int outcome = con.insertAppointment(
					new Appointment(newAppointmentID.toString(), tFormDate,
							tFormTime.split("\\-")[0],
					        tFormTime.split("\\-")[1], tFormSIN, tFormRoomID, 
					        tFormBranchID, newInvoiceID.toString(), tFormEmployees.split(", "),
					        tFormAType, "pending"));
	
			if (outcome == 0) {//success
				//Fetch info on all procedures to insert to this appointment
				//col1: newAppointmentID gotten from above
				String tFormToothList = req.getParameter("tFormToothList");
				//type list contains code, type and fee
				String tFormTypeList = req.getParameter("tFormTypeList");
				String tFormAmtAndMedList = req.getParameter("tFormAmtAndMedList");
				String tFormSymptomList = req.getParameter("tFormSymptomList");
				String tFormCommentsList = req.getParameter("tFormCommentsList");
				//proceduredate == pFormDate
				
				System.out.println(tFormToothList+ " "+tFormTypeList+ " "+tFormAmtAndMedList+ " "+tFormSymptomList+" "+tFormCommentsList);
	
				//Since some values above store multiple values (possibly multiple procedures)
				//we have to index them and do insertions individually by index
				String[] allTeeth = tFormToothList.split("\\|");
				String[] codeTypesAndFees = tFormTypeList.split("\\|");
				String[] amountAndMeds = tFormAmtAndMedList.split("\\|");
				String[] symptoms = tFormSymptomList.split("\\|");
				String[] comments = tFormCommentsList.split("\\|");
				Integer newFeeID = con.getMostRecentFeeID()+1;
				
					for (int i = 0 ; i < allTeeth.length ; i++) {
						String treatmentCode = codeTypesAndFees[i].split(" ")[0];
						String type = codeTypesAndFees[i].split(" ")[1];
						String fee = codeTypesAndFees[i].split(" ")[2];
						String feeID = codeTypesAndFees[i].split(" ")[3];
						
						System.out.println(amountAndMeds[i]);
						
						con.insertTreatment(
								new Treatment(newAppointmentID.toString(), allTeeth[i],
											  treatmentCode, type, amountAndMeds[i].split("\\,\\s"), 
											  symptoms[i].split("\\,\\s"),
											  comments[i], tFormDate));
						con.insertFeeCharge(
								new FeeCharge(newFeeID.toString(), newInvoiceID.toString(),
											  feeID, fee));
						newFeeID++;
				}
				req.setAttribute("outcomeA", "successAddTreatment");
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			} else {
				req.setAttribute("outcomeA", "failedAddTreatment");
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			}

		}
		

	}
}
