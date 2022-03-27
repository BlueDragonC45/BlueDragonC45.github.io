<%@page import="java.util.ArrayList"%>
<%@page import="dentalclinic.entities.Appointment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
<link href="./styles/styles.css" rel="stylesheet" type="text/css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
<script src="./scripts/main.js"></script>
<title>Sunshine Dentist Clinic</title>
</head>
<body>

<% //Get employeeSIN; must always be available to search the appointments
   String employeeSIN = (String) request.getAttribute("employeeSIN");
   String role = (String) request.getAttribute("role");
   System.out.println(employeeSIN+" "+role);
%>
<div class = "container-fluid">

    <nav class="navbar text-white px-3 pt-3 mt-3">
      <h1 style="font-size: 400%"><img src="images/logo.png" width="80" height="80"> Welcome to Sunshine Dentist Clinic!</h1>
    </nav>
    
    <div class="content p-3 my-3 h-100 border" id="dentists">
      <h1> Hygienist View</h1>
      <div class="p-1 my-1 border border-dark row justify-content-around" id="dentistNav">
        <button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('listAppointments')" >List Appointments</button>
        <button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('searchPatient')" >Search Patient</button>
        <button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('searchPatientRecord')" >Search Patient Record</button>
        <button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="location.href='index.html'" >Go Back</button>
      </div>

      <div class="tab p-3 my-3" style="display: none;" id="listAppointments">
        <h2> Appointments</h2>
        <div class="p-3 my-3" id="patientSearch">

			<form method="post" action="listAppointmentsEmployee">
				<input type="hidden" id="employeeSIN" name="employeeSIN" value="<%=employeeSIN%>">
				<input type="hidden" id="role" name="role" value="<%=role%>">
				<button type="submit" value="submit" onclick="return validateSIN();">Search</button>
			</form>
        </div>
      </div>

      <div class="tab p-3 my-3" style="display: none;" id="searchPatient">
        <h2> Patient Search</h2>
        <div class="p-3 my-3" id="patientSearch">
			Search by Patient SIN:
			<form method="post" action="patientSearch">
				<input type="hidden" id="employeeSIN" name="employeeSIN" value="<%=employeeSIN%>">
				<input type="hidden" id="role" name="role" value="<%=role%>">
				<input class="m-1 form-control" type="text" id="patientSIN" name="patientSIN" pattern="[0-9]{9}" placeholder="123456789">
				<button type="reset" value="reset">Reset</button>
				<button type="submit" value="submit" onclick="return validateSIN();">Submit</button>
			</form>
        </div>
      </div>

      <div class="tab p-3 my-3" style="display: none;" id="searchPatientRecord">
        <h2> Patient Records</h2>
        <div class="p-3 my-3" id="patientRecordSearch">
			Search by Patient SIN:
			<form method="post" action="recordSearch">
				<input type="hidden" id="employeeSIN" name="employeeSIN" value="<%=employeeSIN%>">
				<input type="hidden" id="role" name="role" value="<%=role%>">
				<input class="m-1 form-control" type="text" id="patientSIN" name="patientSIN" pattern="[0-9]{9}" placeholder="123456789">
				<button type="reset" value="reset">Reset</button>
				<button type="submit" value="submit" onclick="return validateSIN();">Submit</button>
			</form>
        </div>
      </div>
      
    </div>	
          	<%//Appointment List
          	  //Will show up only when appointments is non-empty
			  //i.e. when the employee has clicked on search at least once
			Object obj = request.getAttribute("appointments");
			ArrayList<Appointment> appointmentList = null;
			if (obj instanceof ArrayList) {
				appointmentList = (ArrayList<Appointment>) obj;
			}
			if (appointmentList != null) {
				if (appointmentList.size() == 0) {
					%><h3>No Appointments Found</h3><br><%
				} else {
					%><h3>Appointments Found!</h3><br><%
					for (Appointment appointment : appointmentList) {
						%>
						<li><%=appointment.toString()%></li>
						<%
					}
					%><br><%
				}
				

			}
			%>
			
			<%//Patient Search
			  //Won't show up if patientStr is empty
			String patientStr = (String) request.getAttribute("patientStr");
			if (patientStr != null) {
				if (!patientStr.split(" ")[0].equals("null")) {
					
					%><h3>Patient Found!</h3><br><h6><%=patientStr%></h6><br><%
				} else {
					%><h3>No Patients Found!</h3><br><%
				}
			}
			%>
			
			<%//Patient Record Search
			  //Won't show up if patientRecordStr is empty
			String patientRecordStr = (String) request.getAttribute("patientRecordStr");
			if (patientRecordStr != null) {
				if (!patientRecordStr.split(" ")[5].equals("#null")) {
					
					%><h3>Patient Record Found!</h3><br><h6><%=patientRecordStr%></h6><br><%
				} else {
					%><h3>No Records Found!</h3><br><%
				}
			}
			%>
  </div>

</body>
</html>