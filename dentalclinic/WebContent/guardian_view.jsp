<%@page import="java.util.ArrayList"%>
<%@page import="dentalclinic.entities.Guardian"%>
<%@page import="dentalclinic.entities.Patient"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
Guardian guardian = (Guardian) request.getAttribute("guardian");
String firstName = guardian.getFirstName();


Object obj = request.getAttribute("minors");
ArrayList<Patient> minors = null;
if (obj instanceof ArrayList) {
	minors = (ArrayList<Patient>) obj;
}
String minorsStr = "None";
if (minors != null) {
	minorsStr = "";
	for (int i = 0 ; i < minors.size() ; i++) {
		if (i < minors.size()-1) {
			minorsStr += minors.get(i).getPatientSIN()+", ";
		}
	}
	minorsStr += minors.get(minors.size()-1).getPatientSIN();
	
}
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
</head>
<body>


	<div class="container-fluid">

		<nav class="navbar text-white px-3 pt-3 mt-3">
			<h1 style="font-size: 400%">
				<h1 style="font-size: 400%">Welcome to Sunshine Dentist Clinic!</h1>
			</h1>
		</nav>

		<div class="content p-3 my-3 h-100 border" id="guardians">
			<h1>Guardian View</h1>
			<div class="p-1 my-1 border border-dark row justify-content-around"
				id="guardianNav">
				<button class="p-1 m-1 mx-auto" style="width: 20rem;"
					onclick="openTab('guardianInfo')">View My Information</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;"
					onclick="location.href='/dentalclinic/'">Go Back</button>
			</div>

			<div class="tab p-3 my-3" style="display: none;" id="guardianInfo">
				<h2>Personal Information</h2>
				<div class="p-3 my-3" id="guardianInfo">
					<table class="table table-sm table-bordered">
						<tr>
							<td>Social Insurance Number</td>
							<td><%=guardian.getGuardianSIN()%></td>
						</tr>
						<tr>
							<td>Username</td>
							<td><%=guardian.getUserName()%></td>
						</tr>
						<tr>
							<td>First Name</td>
							<td><%=guardian.getFirstName()%></td>
						</tr>
						<tr>
							<td>Middle Name</td>
							<td><%=guardian.getMiddleName()%></td>
						</tr>
						<tr>
							<td>Last Name</td>
							<td><%=guardian.getLastName()%></td>
						</tr>
						<tr>
							<td>Date of Birth</td>
							<td><%=guardian.getDateOfBirth()%></td>
						</tr>
						<tr>
							<td>Age</td>
							<td><%=guardian.getAge()%></td>
						</tr>
						<tr>
							<td>Gender</td>
							<td><%=guardian.getGender()%></td>
						</tr>
						<tr>
							<td>Email</td>
							<td><%=guardian.getGuardianEmail()%></td>
						</tr>
						<tr>
							<td>Phone Number</td>
							<td><%=guardian.getGuardianPhoneNumber()%></td>
						</tr>
						<tr>
							<td>Address</td>
							<td><%=guardian.getAddress()%></td>
						</tr>
						<tr>
							<td>Minors' SIN</td>
							<td><%=minorsStr%></td>
						</tr>
					</table>
				</div>
			</div>

		</div>
	</div>
</body>
</html>