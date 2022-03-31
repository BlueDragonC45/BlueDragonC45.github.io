<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="dentalclinic.entities.Appointment"%>
<%@page import="dentalclinic.entities.Branch"%>
<%@page import="dentalclinic.entities.PatientRecord"%>
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
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<title>Sunshine Dentist Clinic</title>
<script>

	function validatePatientRecord() {
		var treatmentDetails = document.getElementById("treatmentDetails");
		
		console.log(treatmentDetails.value);
		if (treatmentDetails.value.length < 20){
			alert("Type at least 20 characters.");
			return false;
		} else
			return true;
	}

	function validateSIN(SIN) {
		var numbers = /^[0-9]+$/;
		console.log(SIN);
		if(SIN.value.match(numbers) && SIN.value.length == 9) {
		      return true;
		} else {
			alert("The SIN must be a 9-digit number.");
			return false;
		}
	}
	
	function viewAppointmentsSearch() {
		document.getElementById("searchAppointments").style.display = "block";
		document.getElementById("listAppointments").style.display = "none";
	}
	function viewSearchedAppointments() {
		document.getElementById("listAppointments").style.display = "block";
		document.getElementById("searchAppointments").style.display = "none";
	}

	function viewPatientSearch() {
		document.getElementById("searchPatient").style.display = "block";
		document.getElementById("patientResult").style.display = "none";
	}
	function viewSearchedPatient() {
		document.getElementById("patientResult").style.display = "block";
		document.getElementById("searchPatient").style.display = "none";
	}

	function viewPatientRecordsSearch() {
		document.getElementById("searchPatientRecords").style.display = "block";
		document.getElementById("patientRecords").style.display = "none";
	}
	function viewPatientRecords() {
		document.getElementById("patientRecords").style.display = "block";
		document.getElementById("searchPatientRecords").style.display = "none";
	}

	function viewAppointmentSelection() {
		document.getElementById("listTreatmentAppointments").style.display = "block";
		document.getElementById("patientRecordsWrite").style.display = "none";
	}
	function viewAppointmentTreatmentsSearch() {
		document.getElementById("patientRecordsWrite").style.display = "block";
		document.getElementById("listTreatmentAppointments").style.display = "none";
	}

	function viewPatientRecordForm() {
		document.getElementById("patientRecordForm").style.display = "block";
		document.getElementById("listTreatmentAppointments").style.display = "none";
	}
	
	
	
	$(document).ready(function() {
		
		<%
		Object obj = request.getAttribute("appointments");
		ArrayList<Appointment> appointmentList = null;
		if (obj instanceof ArrayList) {
			appointmentList = (ArrayList<Appointment>) obj;
		}%>
		
		<%
		Object obj2 = request.getAttribute("branches");
		ArrayList<Branch> branchList = null;
		if (obj2 instanceof ArrayList) {
			branchList = (ArrayList<Branch>) obj2;
		}
		
		if (appointmentList != null && branchList != null) {
			%>viewSearchedAppointments();<%
		}%>
		
		<%
		String patientStr = (String) request.getAttribute("patientStr");
		if (patientStr != null) {
			%>viewSearchedPatient();<%
		}
		%>
		
		<%
		Object obj3 = request.getAttribute("patientRecords");
		ArrayList<PatientRecord> recordList = null;
		if (obj3 instanceof ArrayList) {
			recordList = (ArrayList<PatientRecord>) obj3;
		}
		if (recordList != null) {
			%>viewPatientRecords();<%
		}%>
		
		<%
		Object obj4 = request.getAttribute("appointmentTypeTreatment");
		ArrayList<Appointment> appointmentTypeTreatmentList = null;
		if (obj4 instanceof ArrayList) {
			appointmentTypeTreatmentList = (ArrayList<Appointment>) obj4;
		}%>
		
		<%
		Object obj5 = request.getAttribute("treatmentCount");
		ArrayList<Integer> treatmentCountList = null;
		if (obj5 instanceof ArrayList) {
			treatmentCountList = (ArrayList<Integer>) obj5;
		}
		Object obj6 = request.getAttribute("branches");
		ArrayList<Branch> branchList2 = null;
		if (obj6 instanceof ArrayList) {
			branchList2 = (ArrayList<Branch>) obj6;
		}
		if (treatmentCountList != null && appointmentTypeTreatmentList != null && branchList2 != null) {
			%>viewAppointmentSelection();<%
		}%>
		
		<%
		String patientSINWrite = (String) request.getAttribute("patientSINWrite");
		String appointmentIDWrite = (String) request.getAttribute("appointmentIDWrite");
		String teethCount = (String) request.getAttribute("teethCount");
		
		Object obj7 = request.getAttribute("teethInvolved");
		ArrayList<String> teethList = null;
		if (obj7 instanceof ArrayList) {
			teethList = (ArrayList<String>) obj7;
		}
		if (patientSINWrite != null && teethList != null) {
			String teethStr = "";
			if (teethList.size() > 1) {
				for (int i = 0 ; i < teethList.size()-1 ; i++) {
					teethStr += teethList.get(i)+", ";
				}
				teethStr += teethList.get(teethList.size()-1);
			} else {
				teethStr = teethList.get(0);
			}
		%>
			document.getElementById("patientSINWrite").value = <%=patientSINWrite%>;
			document.getElementById("appointmentIDWrite").value = <%=appointmentIDWrite%>;
			document.getElementById("teethInvolved").value = "<%=teethStr%>";
			viewPatientRecordForm();<%
		}
		%>

		
		<%String outcome = (String) request.getAttribute("outcome");
		if (outcome != null) { 
			
			if (outcome.equals("recordWriteSuccess")) {%>

				alert("The record was successfully submitted.");
				
			<%} else if (outcome.equals("recordAlreadyExists")) {%>
				
				alert("Could not submit the record; this appointment already has one!");
				history.back();
				<%
			}  else {
				%>
				alert("Unknown Error; SQL ERROR.");
				history.back();
				<%
			%>
			<% }%>
			
		<% }; %>
		
		console.log("Working");
	});
</script>
</head>
<body>

<% //Get employeeSIN; must always be available to search the appointments
   String employeeSIN = (String) request.getAttribute("employeeSIN");
   String role = (String) request.getAttribute("role");
   System.out.println(employeeSIN+" "+role);
%>
<div class = "container-fluid">

    <nav class="navbar text-white px-3 pt-3 mt-3">
      <h1 style="font-size: 400%">Welcome to Sunshine Dentist Clinic!</h1>
    </nav>
    
    <div class="content p-3 my-3 h-100 border" id="dentists">
      <h1> Dentist View</h1>
      <div class="p-1 my-1 border border-dark row justify-content-around" id="dentistNav">
        <button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('searchAppointments')" >List Appointments</button>
        <button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('searchPatient')" >Search Patient</button>
        <button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('searchPatientRecords')" >Search Patient Record</button>
        <button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('patientRecordsWrite')" >New Patient Record</button>
        <button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="location.href='/dentalclinic/'" >Go Back</button>
      </div>

      <div class="tab p-3 my-3" style="display: none;" id="searchAppointments">
        <h2> Appointments</h2>
        <div class="p-3 my-3" id="appointmentSearchForm">
			Search by My SIN:
			<form method="post" action="listAppointmentsEmployee">
				<input class="m-1 form-control" type="number" id="employeeSIN" name="employeeSIN" value="<%=employeeSIN%>" readonly>
				<button type="submit" value="submit">Search</button>
			</form>
        </div>
      </div>
      
      <div class="tab p-3 my-3" style="display: none;" id="listAppointments">
          	<%//Appointment List
          	  //Will show up only when appointments is non-empty
			  //i.e. when the employee has clicked on search at least once

			if (appointmentList != null) {
				if (appointmentList.size() == 0) {
					%><h3>No Appointments Found</h3><br><%
				} else {
					%><h3>Appointments Found</h3><br><%
					Integer branchID = 0;
					for (Appointment appointment : appointmentList) {
						
						branchID = Integer.parseInt(appointment.getBranchID());
						%>
						<li><%=appointment.toString()+" Branch location: "+branchList.get(branchID-1)+"."%></li>
						<%
					}
					%><br><%
			}}
			%>
			<button onclick="viewAppointmentsSearch()">Back to Search</button>
	  </div>

      <div class="tab p-3 my-3" style="display: none;" id="searchPatient">
        <h2> Patient Search</h2>
        <div class="p-3 my-3" id="patientSearch">
			Search by Patient SIN:
			<form method="post" action="patientSearch">
				<input type="hidden" id="employeeSIN" name="employeeSIN" value="<%=employeeSIN%>">
				<input class="m-1 form-control" type="number" id="patientSIN" name="patientSIN" pattern="[0-9]{9}" placeholder="123456789">
				<button type="submit" value="submit" onclick="return validateSIN(patientSIN);">Submit</button>
				<button type="reset" value="reset">Reset</button>
			</form>
        </div>
      </div>
      
      <div class="tab p-3 my-3" style="display: none;" id="patientResult">
			<%//Patient Search
			  //Won't show up if patientStr is empty
			if (patientStr != null) {
				if (!patientStr.split(" ")[0].equals("null")) {
					
					%><h3>Patient Found!</h3><br><h6><%=patientStr%></h6><br><%
				} else {
					%><h3>No Patients Found!</h3><br><%
				}
			}
			%>
			<button onclick="viewPatientSearch()">Search Another Patient</button>
	  </div>

      <div class="tab p-3 my-3" style="display: none;" id="searchPatientRecords">
        <h2> Patient Records</h2>
        <div class="p-3 my-3" id="patientRecordSearch">
			Search by Patient SIN:
			<form method="post" action="recordSearch">
				<input type="hidden" id="employeeSIN" name="employeeSIN" value="<%=employeeSIN%>">
				<input class="m-1 form-control" type="number" id="patientSIN" name="patientSIN" pattern="[0-9]{9}" placeholder="123456789">
				<button type="submit" value="submit" onclick="return validateSIN(patientSIN);">Submit</button>
				<button type="reset" value="reset">Reset</button>
			</form>
        </div>
      </div>
      
      <div class="tab p-3 my-3" style="display: none;" id="patientRecords">
      		<%//Patient Record Search
			  //Won't show up if patientRecordStr is empty
				if (recordList != null) {
					if (recordList.size() == 0) {
						%><br><h6>No records found. If you think this is an error, contact a manager.</<h6>><%
					} else {
						
						for (PatientRecord record : recordList) {
							
							%>
							<li><%=record.toString()%></li>
							<%
						}
						%><br><%
					}
				}
			%>
			<button onclick="viewPatientRecordsSearch()">Search Another Patient</button>
      </div>	



      <div class="tab p-3 my-3" style="display: none;" id="patientRecordsWrite">
        <h2> Write New Patient Record</h2>
        <div class="p-3 my-3" id="patientRecordSearchWrite">
			Search Treatments With My SIN:
			<form method="post" action="writeRecord">
				<input class="m-1 form-control" type="number" id="employeeSIN" name="employeeSIN" value="<%=employeeSIN%>" readonly>
				<button type="submit" value="submit">Search</button>
			</form>
        </div>
      </div>
      
      <div class="tab p-3 my-3" style="display: none;" id="listTreatmentAppointments">
				<form method="post" action="writeRecord">
						<h3>Treatment Appointments Found:</h3><br>
						<select class="m-1 form-control" type="text" id="appointmentIDSelected" name="appointmentIDSelected">
							<%
							if (treatmentCountList != null && appointmentTypeTreatmentList != null) {
								//appointmentTypeTreatmentList treatmentCountList
								Integer branchID = 0;
								String appointmentID = "";
								for (int i = 0 ; i < treatmentCountList.size() ; i++) {

									branchID = Integer.parseInt(appointmentTypeTreatmentList.get(i).getBranchID());
									appointmentID = appointmentTypeTreatmentList.get(i).getAppointmentID();
									%>
									<option value=<%=appointmentID%>><%=appointmentTypeTreatmentList.get(i).toString()+" Branch location: "+branchList.get(branchID-1)+"."%></option>
									<%
								}
							}
							%>
						</select>
						<input class="m-1 form-control" type="hidden" id="employeeSIN" name="employeeSIN" value="<%=employeeSIN%>" readonly>
						<button type="submit" value="submit" onclick="return true">Select</button>
						<button type="reset" value="reset" onclick="history.back();">Go Back</button>
				</form>
	  </div>

		<div class="tab p-3 my-3" style="display: none;" id="patientRecordForm">
			<form method="post" action="writeRecord">
				Patient SIN:<input type="text" class="m-1 form-control" id="patientSINWrite" name="patientSINWrite" readonly> 
				Appointment ID:<input type="text" class="m-1 form-control" id="appointmentIDWrite" name="appointmentIDWrite" readonly>
				TeethInvolved:<input type="text" class="m-1 form-control" id="teethInvolved" name="teethInvolved" readonly>
				Treatment Details: <textarea class="m-1 form-control" cols="15" rows="10" id="treatmentDetails" name="treatmentDetails"></textarea>
				<!-- <input type="text" style="height:200px;font-size:14pt;" class="m-1 form-control" id="treatmentDetails" name="treatmentDetails">
				 --> 
				<small>Note: Summarize the treatment(s).</small><br>
				<input class="m-1 form-control" type="hidden" id="employeeSIN" name="employeeSIN" value="<%=employeeSIN%>" readonly>
				<button type="submit" value="submit" onclick="return validatePatientRecord();">Submit</button>
				<button type="reset" value="reset" onclick="history.back();">Go Back</button>
			</form>
		</div>

	  </div>		

  </div>

</body>
</html>