<%@page import="java.util.ArrayList"%>
<%@page import="dentalclinic.entities.Room"%>
<%@page import="dentalclinic.entities.Patient"%>
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
 	$(document).ready(function() {
		
	});
</script>
</head>
<body>


	<div class="container-fluid">

		<nav class="navbar text-white px-3 pt-3 mt-3">
			<h1 style="font-size: 400%">
				<img src="images/logo.png" width="80" height="80"> Welcome to
				Sunshine Dentist Clinic!
			</h1>
		</nav>

		<div class="content p-3 my-3 h-100 border" id="patients">
			<h1>Patient View</h1>
			<div class="p-1 my-1 border border-dark row justify-content-around"
				id="patientNav">
				<button class="p-1 m-1 mx-auto" style="width: 20rem;"
					onclick="openTab('upcomingAppt')">Check Upcoming
					Appointments</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;"
					onclick="openTab('patientInfo')">View Patient Information</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;"
					onclick="location.href='index.html'">Go Back</button>
			</div>

			<div class="tab p-3 my-3" style="display: none;" id="upcomingAppt">
				<h2>Upcoming Appointments</h2>
	        <div class="p-3 my-3" id="patientApptList">
	        </div>
			</div>

			<div class="tab p-3 my-3" style="display: none;" id="patientInfo">
				<h2>Patient Information</h2>
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
					</table>
				</div>
			</div>

		</div>
	</div>
</body>
</html>