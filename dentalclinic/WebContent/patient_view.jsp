<%@page import="java.util.ArrayList"%>
<%@page import="dentalclinic.entities.Patient"%>
<%@page import="dentalclinic.entities.Appointment"%>
<%@page import="dentalclinic.entities.Branch"%>
<%@page import="dentalclinic.entities.PatientRecord"%>
<%@page import="dentalclinic.entities.Invoice"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
Patient patient = (Patient) request.getAttribute("patient");
String firstName = patient.getFirstName();

String appointmentIDFetched = (String) request.getAttribute("appointmentIDFetched");



%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="ch-cn">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
	crossorigin="anonymous">
<link href="./styles/styles.css" rel="stylesheet" type="text/css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="./scripts/main.js"></script>
<script>
$(document).ready(function() {
	
	<%
	String appointmentToReview = (String) request.getAttribute("appointmentToReview");
	Appointment appointmentInfo = (Appointment) request.getAttribute("appointmentInfo");
	if (appointmentToReview != null && appointmentInfo != null) { 
		%>
		document.getElementById("appointmentIDFetched").value = "<%=appointmentToReview%>";
		document.getElementById("appointmentFetchedDate").value = "<%=appointmentInfo.getAppointmentDate()%>";
		console.log("<%=appointmentToReview%>");
		viewReviewForm();<%
		}; %>
});
</script>
<title>Sunshine Dentist Clinic</title>
<script>

function validateAppointmentCancel() {
	var chosen = document.getElementById("appointmentIDCancel").value;
	
	if (chosen != ""){
		return true;
	} else
		alert("There are no pending appointments.");
		return false;
}

function validateAppointmentChoose() {
	var chosen = document.getElementById("appointmentToReview").value;
	
	if (chosen != ""){
		return true;
	} else
		alert("No completed appointments; Must have had our services to post a review.");
		return false;
}

	function validateReview() {
		var employeeProf = document.getElementById("employeeProf").value;
		var communication = document.getElementById("communication").value;
		var cleanliness = document.getElementById("cleanliness").value;
		var value = document.getElementById("value").value;
		var review = document.getElementById("review").value;
		
		console.log(review + " " + review.length);
		if (review.length >= 20 && employeeProf != "" && communication != ""
				&& cleanliness != "" && value != ""){
			return true;
		} else
			alert("Type at least 20 characters.");
			return false;
	}

	function resetAppointmentView() {
		document.getElementById("upcomingAppt").style.display = "block";
		document.getElementById("allAppointments").style.display = "none";
	}
	
	function viewAllAppointments() {
		document.getElementById("allAppointments").style.display = "block";
		document.getElementById("upcomingAppt").style.display = "none";
	}
	
	function viewReviewableAppointments() {
		document.getElementById("reviewableAppointments").style.display = "block";
		document.getElementById("writeReview").style.display = "none";
	}
	function viewReviewForm() {
		document.getElementById("reviewForm").style.display = "block";
		document.getElementById("writeReview").style.display = "none";
	}
	
	<%String outcome = (String) request.getAttribute("outcome");
	if (outcome != null) { 
		
		if (outcome.equals("reviewSubmitted")) {%>

			alert("Review successfully submitted. Thanks for taking your time!");
			
		<%} else if (outcome.equals("reviewFailedNoFinished")) {%>
			
			alert("Could not submit review; you must have at least one finished appointment!");
			<%
		} else if (outcome.equals("reviewExists")) {%>
		
			alert("Could not submit review; you have already reviewed this appointment!");
		<%
		} else if (outcome.equals("cancelSuccess")) {%>
		
			alert("Appointment cancelled succesfully; no fees were applied.");
			<%
		} else if (outcome.equals("cancelSuccessFee")) {%>
	
			alert("Appointment cancelled, but within 24 hours. Thus, fees were added to your account for 14 CAD.");
		<%
		} else if (outcome.equals("cancelLate")) {%>
		
			alert("Failed to cancel; cannot cancel after appointment start.");
		<%
		}  else {
			%>
			alert("Error; already processed this request.");
			<%
		%>
		<% }%>
		
	<% }; %>
</script>
</head>
<body>


	<div class="container-fluid">

		<nav class="navbar text-white px-3 pt-3 mt-3">
				<h1 style="font-size: 400%">Welcome to Sunshine Dentist Clinic!</h1>
		</nav>

		<div class="content p-3 my-3 h-100 border" id="patients">
			<h1>Patient View</h1>
			<div class="p-1 my-1 border border-dark"
				id="receptionistNav">
				<div class="row justify-content-around">
				<button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('upcomingAppt')">View Appointments</button>
				<button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('cancelAppointment')">Cancel Appointment</button>
				<button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('pendingInvoices')">View Pending Invoices</button>
				<button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('patientRecords')">View My Records</button>
				</div>
				
				<div class="row justify-content-around">
				<button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('viewProcedures')">Procedures & Treatments</button>
				<button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('writeReview')">Submit Review</button>
				<button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('patientInfo')">View My Information</button>
				<button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="location.href='/dentalclinic/'">Go Back</button>
				</div>
			</div>

			<div class="tab p-3 my-3" style="display: none;" id="upcomingAppt">
				<h2>Pending Appointments</h2><br>
	          	<%//Appointment List
	          	  //Will show up only when appointments is non-empty
				  //i.e. when the employee has clicked on search at least once
				Object obj5 = request.getAttribute("pendingAppointments");
				ArrayList<Appointment> pendingAppointments = null;
				if (obj5 instanceof ArrayList) {
					pendingAppointments = (ArrayList<Appointment>) obj5;
				}
				Object obj2 = request.getAttribute("branches");
				ArrayList<Branch> branchList = null;
				if (obj2 instanceof ArrayList) {
					branchList = (ArrayList<Branch>) obj2;
				}
				
				if (pendingAppointments != null && branchList != null) {
					if (pendingAppointments.size() == 0 || branchList.size() == 0) {
						%><li>No appointments found. If you think this is an error, contact the clinic.</li><%
					} else {
						
						Integer branchID = 0;
						for (Appointment appointment : pendingAppointments) {
							
							branchID = Integer.parseInt(appointment.getBranchID());
							%>
							<li><%=appointment.toString()+" Branch location: "+branchList.get(branchID-1)+"."%></li>
							<%
						}
					}
				} else {
				%><li>No appointments found. If you think this is an error, contact the clinic.</li><%
				}
				%>
				<br><button onclick="viewAllAppointments()">View All Appointments</button>
			</div>

			<div class="tab p-3 my-3" style="display: none;" id="allAppointments">
				<h2>All Appointments</h2><br>
	          	<%//Appointment List
	          	  //Will show up only when appointments is non-empty
				  //i.e. when the employee has clicked on search at least once
				Object obj4 = request.getAttribute("appointmentsAll");
				ArrayList<Appointment> allAppointmentList = null;
				if (obj4 instanceof ArrayList) {
					allAppointmentList = (ArrayList<Appointment>) obj4;
				}
				
				if (allAppointmentList != null && branchList != null) {
					if (allAppointmentList.size() == 0 || branchList.size() == 0) {
						%><li>No appointments found. If you think this is an error, contact the clinic.</li><%
					} else {
						
						Integer branchID = 0;
						for (Appointment appointment : allAppointmentList) {
							
							branchID = Integer.parseInt(appointment.getBranchID());
							%>
							<li><%=appointment.toString()+" Branch location: "+branchList.get(branchID-1)+"."%></li>
							<%
						}
					}
				} else {
				%><li>No appointments found. If you think this is an error, contact the clinic.</li><%
				}
				%>
				
				<br><button onclick="resetAppointmentView()">View Unfinished</button>
			</div>
			
      <div class="tab p-3 my-3" style="display: none;" id="cancelAppointment">
						<h3>Cancel an Appointment:</h3><br>
				<form method="post" action="patientLogin">
						<select class="m-1 form-control" type="text" id="appointmentIDCancel" name="appointmentIDCancel">
							<%

							if (pendingAppointments != null && branchList != null) {
								//appointmentTypeTreatmentList treatmentCountList
								Integer branchID = 0;
								String appointmentID = "";
								for (int i = 0 ; i < pendingAppointments.size() ; i++) {

									branchID = Integer.parseInt(pendingAppointments.get(i).getBranchID());
									appointmentID = pendingAppointments.get(i).getAppointmentID();
									%>
									<option value=<%=appointmentID%>><%=pendingAppointments.get(i).toString()+" Branch location: "+branchList.get(branchID-1)+"."%></option>
									<%
								}
							}
							%>
						</select>
						<input class="m-1 form-control" type="hidden" id="patientUsername" name="patientUsername" value="<%=patient.getUserName()%>" readonly>
						<button type="submit" value="submit" onclick="return validateAppointmentCancel();">Select</button>
						<button type="reset" value="reset" onclick="history.back();">Go Back</button>
				</form>
	  </div>
			
			<div class="tab p-3 my-3" style="display: none;" id="pendingInvoices">
				<h2>Pending Invoices</h2><br>
	          	<%//Appointment List
	          	  //Will show up only when appointments is non-empty
				  //i.e. when the employee has clicked on search at least once
				Object obj7 = request.getAttribute("pendingInvoices");
				ArrayList<Invoice> pendingInvoices = null;
				if (obj7 instanceof ArrayList) {
					pendingInvoices = (ArrayList<Invoice>) obj7;
				}
				
				if (pendingInvoices != null) {
					if (pendingInvoices.size() == 0) {
						%><li>No invoices found. If you think this is an error, contact the clinic.</li><%
					} else {
						
						for (int i = 0 ; i < pendingInvoices.size() ; i++) {
							%>
							<li><%=pendingInvoices.get(i).toString()
								+" For an appointment on the day "+pendingAppointments.get(i).getAppointmentDate()
								+", of type "
								+pendingAppointments.get(i).getAppointmentType()+"."%></li>
							<%
						}
						
					}
				} else {
				%><li>No invoices found. If you think this is an error, contact the clinic.</li><%
				}
				%>
			</div>
			
			
			<div class="tab p-3 my-3" style="display: none;" id="patientRecords">
				<h2>Patient Records</h2>
	          	<%//Appointment List
	          	  //Will show up only when appointments is non-empty
				  //i.e. when the employee has clicked on search at least once
				Object obj3 = request.getAttribute("records");
				ArrayList<PatientRecord> recordList = null;
				if (obj3 instanceof ArrayList) {
					recordList = (ArrayList<PatientRecord>) obj3;
				}
				
				
				if (recordList != null) {
					if (recordList.size() == 0) {
						%><li>You have not completed any treatments yet. If you think this is an error, contact the clinic.</li><%
					} else {
						
						for (PatientRecord record : recordList) {
							
							%>
							<li><%=record.toString()%></li>
							<%
						}
						%><br><%
					}
				} else {
				%><li>You have not completed any treatments yet. If you think this is an error, contact the clinic.</li><%
				}
				%>
			</div>

      <div class="tab p-3 my-3" style="display: none;" id="writeReview">
        <h2> Submit a Review</h2>
        <div class="p-3 my-3" id="patientRecordSearchWrite">
			Search Completed Appointments By My SIN:
			<input class="m-1 form-control" type="number" id="patientSIN" name="patientSIN" value = "<%=patient.getPatientSIN()%>" readonly> 
			<button onclick="viewReviewableAppointments()">Search</button>
        </div>
      </div>
      
      <div class="tab p-3 my-3" style="display: none;" id="viewProcedures">
       				<h3>Procedures & Cost:</h3>
					<table class="table table-sm table-bordered">
						<tr>
							<td>Evaluation</td>
							<td>50 CAD</td>
						</tr>
						<tr>
							<td>Resin</td>
							<td>30 CAD</td>
						</tr>
						<tr>
							<td>Sealant</td>
							<td>25 CAD</td>
						</tr>
						<tr>
							<td>Fluoride</td>
							<td>25 CAD</td>
						</tr>
						<tr>
							<td>Prophylaxis</td>
							<td>50 CAD</td>
						</tr>
						<tr>
							<td>Varnish</td>
							<td>25 CAD</td>
						</tr>
					</table>
       				<h3>Treatments & Cost:</h3>
					<table class="table table-sm table-bordered">
						<tr>
							<td>Extraction</td>
							<td>60 CAD</td>
						</tr>
						<tr>
							<td>Scaling</td>
							<td>40 CAD</td>
						</tr>
						<tr>
							<td>Amalgam</td>
							<td>80 CAD</td>
						</tr>
						<tr>
							<td>Crown</td>
							<td>90 CAD</td>
						</tr>
						<tr>
							<td>Bridge</td>
							<td>100 CAD</td>
						</tr>
					</table>
      </div>
      
      <div class="tab p-3 my-3" style="display: none;" id="reviewableAppointments">
						<h3>Completed Appointments:</h3><br>
				<form method="post" action="patientLogin">
						<select class="m-1 form-control" type="text" id="appointmentToReview" name="appointmentToReview">
							<%
							Object obj6 = request.getAttribute("finishedAppointments");
							ArrayList<Appointment> finishedList = null;
							if (obj6 instanceof ArrayList) {
								finishedList = (ArrayList<Appointment>) obj6;
							}
							if (finishedList != null && branchList != null) {
								//appointmentTypeTreatmentList treatmentCountList
								Integer branchID = 0;
								String appointmentID = "";
								for (int i = 0 ; i < finishedList.size() ; i++) {

									branchID = Integer.parseInt(finishedList.get(i).getBranchID());
									appointmentID = finishedList.get(i).getAppointmentID();
									%>
									<option value=<%=appointmentID%>><%=finishedList.get(i).toString()+" Branch location: "+branchList.get(branchID-1)+"."%></option>
									<%
								}
							}
							%>
						</select>
						<input class="m-1 form-control" type="hidden" id="patientUsername" name="patientUsername" value="<%=patient.getUserName()%>" readonly>
						<button type="submit" value="submit" onclick="return validateAppointmentChoose();">Select</button>
						<button type="reset" value="reset" onclick="window.location.reload();">Go Back</button>
				</form>
	  </div>

		<div class="tab p-3 my-3" style="display: none;" id="reviewForm">
			<form method="post" action="patientLogin">
				Appointment ID:<input class="m-1 form-control" type="text" id="appointmentIDFetched" name="appointmentIDFetched" readonly>
				Appointment Date:<input class="m-1 form-control" type="text" id="appointmentFetchedDate" name="appointmentFetchedDate" readonly>
				Employee Professionalism:<select class="m-1 form-control" id="employeeProf" name="employeeProf">
						<option value="5">5/5</option>
						<option value="4">4/5</option>
						<option value="3">3/5</option>
						<option value="2">2/5</option>
						<option value="1">1/5</option>
					</select> 
				Communication:<select class="m-1 form-control" id="communication" name="communication">
						<option value="5">5/5</option>
						<option value="4">4/5</option>
						<option value="3">3/5</option>
						<option value="2">2/5</option>
						<option value="1">1/5</option>
					</select> 
				Cleanliness:<select class="m-1 form-control" id="cleanliness" name="cleanliness">
						<option value="5">5/5</option>
						<option value="4">4/5</option>
						<option value="3">3/5</option>
						<option value="2">2/5</option>
						<option value="1">1/5</option>
					</select> 
				Value:<select class="m-1 form-control" id="value" name="value">
						<option value="5">5/5</option>
						<option value="4">4/5</option>
						<option value="3">3/5</option>
						<option value="2">2/5</option>
						<option value="1">1/5</option>
					</select> 
				<input class="m-1 form-control" type="hidden" id="patientSIN" name="patientSIN" value = "<%=patient.getPatientSIN()%>" readonly> 
				<input class="m-1 form-control" type="hidden" id="patientUsername" name="patientUsername" value="<%=patient.getUserName()%>" readonly>
				Comments: <textarea class="m-1 form-control" cols="15" rows="10" id="review" name="review"></textarea>
				<!-- <input type="text" style="height:200px;font-size:14pt;" class="m-1 form-control" id="treatmentDetails" name="treatmentDetails">
				 --> 
				<small>Note: Leave an honest review.</small><br>
				<button type="submit" value="submit" onclick="return validateReview();">Submit</button>
				<button type="reset" value="reset" onclick="history.back();">Go Back</button>
			</form>
		</div>

			<div class="tab p-3 my-3" style="display: none;" id="patientInfo">
				<h2>Personal Information</h2>
				<div class="p-3 my-3" id="patientInfo">
					<table class="table table-sm table-bordered">
						<tr>
							<td>Social Insurance Number</td>
							<td><%=patient.getPatientSIN()%></td>
						</tr>
						<tr>
							<td>Username</td>
							<td><%=patient.getUserName()%></td>
						</tr>
						<tr>
							<td>First Name</td>
							<td><%=patient.getFirstName()%></td>
						</tr>
						<tr>
							<td>Middle Name</td>
							<td><%=patient.getMiddleName()%></td>
						</tr>
						<tr>
							<td>Last Name</td>
							<td><%=patient.getLastName()%></td>
						</tr>
						<tr>
							<td>Date of Birth</td>
							<td><%=patient.getDateOfBirth()%></td>
						</tr>
						<tr>
							<td>Age</td>
							<td><%=patient.getAge()%></td>
						</tr>
						<tr>
							<td>Gender</td>
							<td><%=patient.getGender()%></td>
						</tr>
						<tr>
							<td>Email</td>
							<td><%=patient.getPatientEmail()%></td>
						</tr>
						<tr>
							<td>Phone Number</td>
							<td><%=patient.getPatientPhoneNumber()%></td>
						</tr>
						<tr>
							<td>Address</td>
							<td><%=patient.getAddress()%></td>
						</tr>
						<tr>
							<td>Guardian</td>
							<%
							String guardianSIN = patient.getGuardianSIN();
							if (guardianSIN == null) {
								%><td>None</td><%
							} else {
								%><td><%=patient.getGuardianSIN()%></td><%
							}
							%>
							
						</tr>
					</table>
				</div>
			</div>
		

		</div>
	</div>
</body>
</html>