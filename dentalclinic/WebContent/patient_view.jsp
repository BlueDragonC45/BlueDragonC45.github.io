<%@page import="java.util.ArrayList"%>
<%@page import="dentalclinic.entities.Patient"%>
<%@page import="dentalclinic.entities.Appointment"%>
<%@page import="dentalclinic.entities.Branch"%>
<%@page import="dentalclinic.entities.PatientRecord"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
Patient patient = (Patient) request.getAttribute("patient");
String firstName = patient.getFirstName();
//String middleName = (String) request.getAttribute("middleName");
//String lastName = (String) request.getAttribute("lastName");
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
<script src="./scripts/main.js"></script>
<title>Sunshine Dentist Clinic</title>
<script>
	function resetAppointmentView() {
		document.getElementById("upcomingAppt").style.display = "block";
		document.getElementById("allAppointments").style.display = "none";
	}
	
	function viewAllAppointments() {
		document.getElementById("allAppointments").style.display = "block";
		document.getElementById("upcomingAppt").style.display = "none";
	}
</script>
</head>
<body>


	<div class="container-fluid">

		<nav class="navbar text-white px-3 pt-3 mt-3">
			<h1 style="font-size: 400%">
				<h1 style="font-size: 400%">Welcome to Sunshine Dentist Clinic!</h1>
			</h1>
		</nav>

		<div class="content p-3 my-3 h-100 border" id="patients">
			<h1>Patient View</h1>
			<div class="p-1 my-1 border border-dark row justify-content-around"
				id="patientNav">
				<button class="p-1 m-1 mx-auto" style="width: 20rem;"
					onclick="openTab('upcomingAppt')">View
					Appointments</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;"
					onclick="openTab('patientRecords')">View My Records</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;"
					onclick="openTab('patientInfo')">View My Information</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;"
					onclick="location.href='index.html'">Go Back</button>
			</div>

			<div class="tab p-3 my-3" style="display: none;" id="upcomingAppt">
				<h2>Unfinished Appointments</h2>
	          	<%//Appointment List
	          	  //Will show up only when appointments is non-empty
				  //i.e. when the employee has clicked on search at least once
				Object obj = request.getAttribute("appointments");
				ArrayList<Appointment> appointmentList = null;
				if (obj instanceof ArrayList) {
					appointmentList = (ArrayList<Appointment>) obj;
				}
				
				Object obj2 = request.getAttribute("branches");
				ArrayList<Branch> branchList = null;
				if (obj2 instanceof ArrayList) {
					branchList = (ArrayList<Branch>) obj2;
				}
				
				if (appointmentList != null && branchList != null) {
					if (appointmentList.size() == 0 || branchList.size() == 0) {
						%><br><h6>No appointments found. If you think this is an error, contact the clinic.</h6><%
					} else {
						
						Integer branchID = 0;
						for (Appointment appointment : appointmentList) {
							
							branchID = Integer.parseInt(appointment.getBranchID());
							%>
							<li><%=appointment.toString()+" Branch location: "+branchList.get(branchID-1)+"."%></li>
							<%
						}
						%><br><%
					}
				} else {
				%><br><h6>No appointments found. If you think this is an error, contact the clinic.</h6><%
				}
				%>
				<button onclick="viewAllAppointments()">View All Appointments</button>
			</div>

			<div class="tab p-3 my-3" style="display: none;" id="allAppointments">
				<h2>All Appointments</h2>
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
						%><br><h6>No appointments found. If you think this is an error, contact the clinic.</h6><%
					} else {
						
						Integer branchID = 0;
						for (Appointment appointment : allAppointmentList) {
							
							branchID = Integer.parseInt(appointment.getBranchID());
							%>
							<li><%=appointment.toString()+" Branch location: "+branchList.get(branchID-1)+"."%></li>
							<%
						}
						%><br><%
					}
				} else {
				%><br><h6>No appointments found. If you think this is an error, contact the clinic.</h6><%
				}
				%>
				
				<button onclick="resetAppointmentView()">View Unfinished</button>
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
						%><br><h6>No records found. If you think this is an error, contact the clinic.</<h6>><%
					} else {
						
						for (PatientRecord record : recordList) {
							
							%>
							<li><%=record.toString()%></li>
							<%
						}
						%><br><%
					}
				} else {
				%><br><h6>No records found. If you think this is an error, contact the clinic.</h6><%
				}
				%>
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