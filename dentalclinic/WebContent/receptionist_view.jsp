<%@page import="java.util.ArrayList"%>
<%@page import="dentalclinic.entities.Branch"%>
<%@page import="dentalclinic.entities.Patient"%>
<%@page import="dentalclinic.entities.Guardian"%>
<%@page import="dentalclinic.entities.Employee"%>
<%@page import="dentalclinic.entities.Invoice"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
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
<title>Sunshine Dentist Clinic</title>
<script>

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

	function calcAge(dateOfBirthVal) {
		var date = new Date(dateOfBirthVal)
		var today = new Date();
		
		var difference = Math.abs(today.getTime() - date.getTime());
		var age = Math.ceil(difference / (1000 * 3600 * 24)) / 365;
		
		return Math.floor(age);
	}

	function validateNotWeekend(appointmentDateQuery) {
		var date = new Date(appointmentDateQuery)
		var day = date.getDay();
		
		if (day == 0 || day = 6) {
			alert("Please choose a weekday (Mon-Fri).");
			return false;
		} else {
			return true;
		}
	}

	function validatePatientRegister() {
		var patientSIN = document.getElementById("patientSIN");
		var userName = document.getElementById("userName");

		var firstName = document.getElementById("firstName");
		var lastName = document.getElementById("lastName");

		var dateOfBirth = document.getElementById("dateOfBirth");
		var age = document.getElementById("age");
		document.getElementById("age").value = calcAge(dateOfBirth.value);
		var gender = document.getElementById("gender");
		var patientEmail = document.getElementById("patientEmail");
		var patientPhoneNumber = document.getElementById("patientPhoneNumber");
		var address = document.getElementById("address");
		var guardian = document.getElementById("guardian");
		var patientPwd = document.getElementById("patientPwd");
		var patientPwdagain = document.getElementById("patientPwdagain");

		if (patientSIN.value == "" || userName.value == ""           || firstName.value == "" || lastName.value == ""
								   || patientPwd.value == ""         || patientPwdagain.value == ""
								   || dateOfBirth.value == ""        || gender.value == ""    || patientEmail.value == ""
								   || patientPhoneNumber.value == "" || address.value == ""){
			alert("You need to fill all requiered fields.");
			return false;
		} else if(!validateSIN(patientSIN)){
			return false;
		} else if(age.value < 15){
			if (guardian.value.length == 0) {
				alert("Must have a guardian if younger than 15 years of age.");
				return false;
			} else if (!validateSIN(guardian)) {
				return false;
			}
		} else if(age.value >= 15 && guardian.value.length != 0){
			alert("Patient is older than 15; a guardian is not required.");
			return false;
		} else if(patientPwd.value != patientPwdagain.value){
			console.log(age.value+" "+guardian.value.length);
			alert("Passwords need to match!");
			return false;
		} else
			return true;
	}
	
	function validatePatientEdit() {
		var userName = document.getElementById("userNameEP");

		var firstName = document.getElementById("fNameEP");
		var lastName = document.getElementById("lNameEP");

		var dateOfBirth = document.getElementById("dobEP");
		var age = document.getElementById("ageEP");
		document.getElementById("ageEP").value = calcAge(dateOfBirth.value);
		var gender = document.getElementById("genderEP");
		var patientEmail = document.getElementById("emailEP");
		var patientPhoneNumber = document.getElementById("phoneEP");
		var address = document.getElementById("addressEP");
		var guardian = document.getElementById("guardianEP");

		if (userName.value == ""           || firstName.value == "" || lastName.value == ""
		 || dateOfBirth.value == ""        || gender.value == ""    || patientEmail.value == ""
		 || patientPhoneNumber.value == "" || address.value == ""){
			alert("You need to fill all requiered fields");
			return false;
		} else if(age.value < 15){
			if (guardian.value.length == 0) {
				alert("Must have a guardian if younger than 15 years of age.");
				return false;
			}else if (!validateSIN(guardian)) {
				return false;
			}
		} else
			return true;
	}

	function validateGuardianRegister() {
		var guardianSIN = document.getElementById("guardianSIN");
		var userNameG = document.getElementById("userNameG");

		var firstNameG = document.getElementById("firstNameG");
		var lastNameG = document.getElementById("lastNameG");

		var dateOfBirthG = document.getElementById("dateOfBirthG");
		var ageG = document.getElementById("ageG");
		document.getElementById("ageG").value = calcAge(dateOfBirthG.value);
		var genderG = document.getElementById("genderG");
		var guardianEmail = document.getElementById("guardianEmail");
		var guardianPhoneNumber = document.getElementById("guardianPhoneNumber");
		var addressG = document.getElementById("addressG");
		var guardianPwd = document.getElementById("guardianPwd");
		var guardianPwdagain = document.getElementById("guardianPwdagain");

		if (guardianSIN.value == "" || userNameG.value == ""           || firstNameG.value == "" || lastNameG.value == ""
								    || guardianPwd.value == ""         || guardianPwdagain.value == ""
								    || dateOfBirthG.value == ""        || genderG.value == ""    || guardianEmail.value == ""
								    || guardianPhoneNumber.value == "" || addressG.value == ""){
			alert("You need to fill all requiered fields.");
			return false;
		} else if(!validateSIN(guardianSIN)){
			return false;
		} else if(ageG.value < 18){
			alert("Must be at least 18 years old to be a guardian!");
			return false;
		} else if(guardianPwd.value != guardianPwdagain.value){
			alert("Passwords need to match!");
			return false;
		} else
			return true;
	}
	
	function validateGuardianEdit() {
		var userNameG = document.getElementById("userNameEG");

		var firstNameG = document.getElementById("fNameEG");
		var lastNameG = document.getElementById("lNameEG");

		var dateOfBirthG = document.getElementById("dobEG");
		var ageG = document.getElementById("ageEG");
		document.getElementById("ageEG").value = calcAge(dateOfBirthG.value);
		var genderG = document.getElementById("genderEG");
		var guardianEmail = document.getElementById("emailEG");
		var guardianPhoneNumber = document.getElementById("phoneEG");
		var addressG = document.getElementById("addressEG");

		if (userNameG.value == ""           || firstNameG.value == "" || lastNameG.value == ""
		 || dateOfBirthG.value == ""        || genderG.value == ""    || guardianEmail.value == ""
		 || guardianPhoneNumber.value == "" || addressG.value == ""){
			alert("You need to fill all requiered fields.");
			return false;
		} else if (ageG.value < 18) {
			alert("Must be at least 18 years old to be a guardian!");
			return false;
		} else
			return true;
	}

	function calculateTotalBill() {
		var userAmt = parseFloat(document.getElementById("userPortionBillForm").value);
		var insuranceAmt = parseFloat(document.getElementById("insurancePortionBillForm").value);
		var employeeAmt = parseFloat(document.getElementById("employeePortionBillForm").value);

		if (Number.isNaN(userAmt)) {
			userAmt = 0;
			document.getElementById("userPortionBillForm").value = 0;
		}
		if (Number.isNaN(insuranceAmt)) {
			insuranceAmt = 0;
			document.getElementById("insurancePortionBillForm").value = 0;
		}
		if (Number.isNaN(employeeAmt)) {
			employeeAmt = 0;
			document.getElementById("employeePortionBillForm").value = 0;
		}
		
		if (userAmt > 10000) {
			userAmt = 10000;
		}
		if (insuranceAmt > 10000) {
			insuranceAmt = 10000;
		}
		if (employeeAmt > 10000) {
			employeeAmt = 10000;
		}
		
		document.getElementById("userPortionBillForm").value = userAmt;
		document.getElementById("insurancePortionBillForm").value = insuranceAmt;
		document.getElementById("employeePortionBillForm").value = employeeAmt
		
		var total = userAmt + insuranceAmt + employeeAmt;
		total = (Math.round(total * 100) / 100).toFixed(2);
		
		document.getElementById("totalBillForm").value = total;
		return;
	}
	
	
	function validateBillForm() {
		
		//in case it above function did not respond on keyup
		calculateTotalBill();
		
		//Pre-filled values
		var invoiceIDBillForm = document.getElementById("invoiceIDBillForm");
		var employeeSINBillForm = document.getElementById("employeeSINBillForm");
		var amountDue = document.getElementById("amountDue");

		//Calculated values
		var total = document.getElementById("totalBillForm");
		
		//To-fill values
		var userAmt = document.getElementById("userPortionBillForm");
		var insuranceCompany = document.getElementById("insuranceCompanyBillForm");
		var insuranceAmt = document.getElementById("insurancePortionBillForm");
		var employeeAmt = document.getElementById("employeePortionBillForm");
		var payMethodBillForm = document.getElementById("payMethodBillForm");
		
		if (employeeAmt.value != 0 && employeeSINBillForm.value.length != 9) {
			
			if (employeeSINBillForm.value.length == 0) {
				alert("Must include the employee's SIN.");
			} else {
				alert("The employee SIN must be a 9-digit number.");
			}
			return false;
			
		} else if (employeeAmt.value == 0 && employeeSINBillForm.value.length != 0) {
			
			alert("Do not need employee SIN since employee amount is 0.");
			return false;
			
		} else if (insuranceAmt.value != 0 && insuranceCompany.value.length == 0) {
			
			if (insuranceCompany.value.length == 0) {
				alert("Must include insurance company.");
			}
			
			return false;
			
		} else if (insuranceAmt.value == 0 && insuranceCompany.value.length != 0) {
			
			alert("Do not need insurance company since insurance amount is 0.");
			return false;
			
		} else if (parseFloat(amountDue.value) == parseFloat(total.value)){
			return true;
		} else {
			alert("Total amount must be the same as Total Due.");
			return false;
		}
	}

	function resetPatientBilling() {
		document.getElementById("searchInvoiceBySIN").style.display = "block";
		document.getElementById("invoicesView").style.display = "none";
	}
	
	function resetEditPatient() {
		document.getElementById("editPatientSearch").style.display = "block";
		document.getElementById("editPatientForm").style.display = "none";
		if (document.getElementById("outcome") != null) {
			document.getElementById("outcome").value = "";
		}
	}
	
	function resetEditGuardian() {
		document.getElementById("editGuardianSearch").style.display = "block";
		document.getElementById("editGuardianForm").style.display = "none";
		if (document.getElementById("outcomeG") != null) {
			document.getElementById("outcomeG").value = "";
		}
	}

	function resetDentistListing() {
		document.getElementById("branchSearch").style.display = "block";
		document.getElementById("branchList").style.display = "none";
		document.getElementById("branchSearchResults").style.display = "none";
	}
	

	$(document).ready(function() {
		
	<% Patient patientInfo = (Patient) request.getAttribute("patient");
	if (patientInfo != null) { %>
	 	openTab('editPatient');
		document.getElementById("editPatientSearch").style.display = "none";
		document.getElementById("editPatientForm").style.display = "block";
		
		document.getElementById("sinEP").value = <%=patientInfo.getPatientSIN()%>;
		document.getElementById("userNameEP").value = "<%=patientInfo.getUserName()%>";
		document.getElementById("fNameEP").value = "<%=patientInfo.getFirstName()%>";
		document.getElementById("mNameEP").value = "<%=patientInfo.getMiddleName()%>";
		document.getElementById("lNameEP").value = "<%=patientInfo.getLastName()%>";
		document.getElementById("dobEP").value = "<%=patientInfo.getDateOfBirth()%>";
		document.getElementById("genderEP").value = "<%=patientInfo.getGender()%>";
		document.getElementById("emailEP").value = "<%=patientInfo.getPatientEmail()%>";
		document.getElementById("phoneEP").value = "<%=patientInfo.getPatientPhoneNumber()%>";
		document.getElementById("addressEP").value = "<%=patientInfo.getAddress()%>";
		document.getElementById("guardianEP").value = "<%=patientInfo.getGuardianSIN()%>";
		if (document.getElementById("guardianEP").value == "null") {
			document.getElementById("guardianEP").value = "";
		}
	<% }; %>
	
	<% Guardian guardianInfo = (Guardian) request.getAttribute("guardian");
	if (guardianInfo != null) { %>
	 	openTab('editGuardian');
		document.getElementById("editGuardianSearch").style.display = "none";
		document.getElementById("editGuardianForm").style.display = "block";
		
		document.getElementById("guardianSINEdit").value = <%=guardianInfo.getGuardianSIN()%>;
		document.getElementById("userNameEG").value = "<%=guardianInfo.getUserName()%>";
		document.getElementById("fNameEG").value = "<%=guardianInfo.getFirstName()%>";
		document.getElementById("mNameEG").value = "<%=guardianInfo.getMiddleName()%>";
		document.getElementById("lNameEG").value = "<%=guardianInfo.getLastName()%>";
		document.getElementById("dobEG").value = "<%=guardianInfo.getDateOfBirth()%>";
		document.getElementById("genderEG").value = "<%=guardianInfo.getGender()%>";
		document.getElementById("emailEG").value = "<%=guardianInfo.getGuardianEmail()%>";
		document.getElementById("phoneEG").value = "<%=guardianInfo.getGuardianPhoneNumber()%>";
		document.getElementById("addressEG").value = "<%=guardianInfo.getAddress()%>";
	<% }; %>
	
	<% Invoice invoiceSelected = (Invoice) request.getAttribute("invoice");
	if (invoiceSelected != null) { %>
	 	openTab('patientBilling');
		document.getElementById("searchInvoiceBySIN").style.display = "none";
		document.getElementById("billForm").style.display = "block";
		
		document.getElementById("patientSINBillForm").value = <%=invoiceSelected.getPatientSIN()%>;
		document.getElementById("guardianSINBillForm").value = <%=invoiceSelected.getGuardianSIN()%>;
		document.getElementById("invoiceIDBillForm").value = <%=invoiceSelected.getInvoiceID()%>;
		
		var totalDue = "<%=invoiceSelected.getTotalFeeCharge()%>";
		var userAmt = "<%=invoiceSelected.getUserCharge()%>";
		var insuranceAmt = "<%=invoiceSelected.getInsuranceCharge()%>";
		var employeeAmt = "<%=invoiceSelected.getEmployeeCharge()%>";
		var penalty = "<%=invoiceSelected.getPenalty()%>";
		var discount = "<%=invoiceSelected.getDiscount()%>";
		
		totalDue = parseFloat(totalDue.replace("$", ""));
		userAmt = parseFloat(userAmt.replace("$", ""));
		insuranceAmt = parseFloat(insuranceAmt.replace("$", ""));
		employeeAmt = parseFloat(employeeAmt.replace("$", ""));
		penalty = parseFloat(penalty.replace("$", ""));
		discount = parseFloat(discount.replace("$", ""));
		
		document.getElementById("amountDue").value = (totalDue+penalty)-(userAmt+discount+insuranceAmt+employeeAmt);
	<% }; %>
	
	<% String outcomeB = (String) request.getAttribute("outcomeB");
	if (outcomeB != null) { 
		
		if (outcomeB.equals("no pending fees")) {%>

			alert("This invoice was already paid in full.");
			history.back();
			
		<%} else if (outcomeB.equals("bill success")) {%>
			var patientSIN = "<%=(String) request.getAttribute("patientSINAfterBill")%>";
			var total = "<%=(String) request.getAttribute("billTotal")%>";
			
			alert("Successfully billed "+total+" CAD, accredited to the patient with SIN: "+patientSIN);
			<%
		} else if (outcomeB.equals("employee not found")) {
			%>
			alert("Failed to bill; employee with that SIN does not exist.");
			history.back();
			<%
		} else if (outcomeB.equals("already paid in full")) {
			%>
			alert("Failed to bill; bill already processed.");
			history.back();
			<%
		%>
		<% } else {
			%>
			alert("Unknown Error; SQL ERROR.");
			history.back();
			<%
		%>
		<% }%>
		
	<% }; %>

	<% String outcome = (String) request.getAttribute("outcome");
	if (outcome != null) { 
		%>
		var fName = "<%=(String) request.getAttribute("firstNameNEW")%>";
		var lName = "<%=(String) request.getAttribute("lastNameNEW")%>";
		<%
		if (outcome.equals("updateSuccess")) {%>

			alert("Patient entry for "+fName+" "+lName+" successfully updated.");
			
		<%} else if (outcome.equals("updateFailed")) {
			%>
			alert("Patient entry for "+fName+" "+lName+" could not be updated; username already chosen or guardian non-existent.");
			<%
		} else if (outcome.equals("registerSuccess")) {
			%>
			alert("Patient entry for "+fName+" "+lName+" added successfully.");
			<%
		} else if (outcome.equals("patientsin repeated")) {
			%>
			alert("A patient with that SIN already exists.");
			history.back();
			
		<% } else if (outcome.equals("username repeated")) {
			%>
			alert("Username already in use.");
			history.back();
			<%
		%>
		<% } else if (outcome.equals("guardian does not exist")) {
			%>
			alert("The guardian with that SIN does not exist.");
			history.back();
			<%
		%>
		<% } else if (outcome.equals("unknown error")) {
			%>
			alert("Unknown Error; SQL ERROR.");
			history.back();
			<%
		
		%>
		<% }
		
	}; %>
	
	<% String outcomeG = (String) request.getAttribute("outcomeG");
	if (outcomeG != null) { 
		%>
		var fNameG = "<%=(String) request.getAttribute("firstNameNEW")%>";
		var lNameG = "<%=(String) request.getAttribute("lastNameNEW")%>";
		<%
		if (outcomeG.equals("updateSuccess")) {%>

			alert("Guardian entry for "+fNameG+" "+lNameG+" successfully updated.");
			
		<%} else if (outcomeG.equals("updateFailed")) {
			%>
			alert("Guardian entry for "+fNameG+" "+lNameG+" could not be updated; username chosen or guardian non-existent.");
			<%
		} else if (outcomeG.equals("registerSuccess")) {
			%>
			alert("Guardian entry for "+fNameG+" "+lNameG+" added successfully.");
			<%
		} else if (outcomeG.equals("registerFailed")) {
			%>
			alert("Guardian entry for "+fNameG+" "+lNameG+" could not be added; username & SIN must be unique.");
			<%
		
		%>
	<% }}; %>
	
	<% 
	Object obj = request.getAttribute("branches");
	ArrayList<Branch> branchList = null;
	if (obj instanceof ArrayList) 
		branchList = (ArrayList<Branch>) obj;
	
	if (branchList != null) {
		if (branchList.size() == 0) {
			%>alert("No branches available at the moment.");<%
		} else {
			%>
		 	openTab('listDentists');
			document.getElementById("branchSearch").style.display = "none";
			document.getElementById("branchList").style.display = "block";<%
		}
	}
	%>

	<% 
	Object obj2 = request.getAttribute("dentists");
	ArrayList<Employee> dentistList = null;
	if (obj2 instanceof ArrayList) 
		dentistList = (ArrayList<Employee>) obj2;
	
	if (dentistList != null) {
		if (dentistList.size() == 0) {
			%>alert("No dentists available in this branch.");<%
		} else {
			%>
		 	openTab('listDentists');
			document.getElementById("branchSearch").style.display = "none";
			document.getElementById("branchSearchResults").style.display = "block";<%
		}
	}
	%>
	
	<% 
	Object obj3 = request.getAttribute("invoices");
	ArrayList<Invoice> invoiceList = null;
	if (obj3 instanceof ArrayList) 
		invoiceList = (ArrayList<Invoice>) obj3;
	
	if (invoiceList != null) {
		if (invoiceList.size() == 0) {
			%>alert("No invoices found for this patient.");<%
		} else {
			%>
		 	openTab('patientBilling');
			document.getElementById("searchInvoiceBySIN").style.display = "none";
			document.getElementById("invoicesView").style.display = "block";<%
		}
	}
	%>

	});

</script>
</head>
<body>

	<div class="container-fluid">

		<nav class="navbar text-white px-3 pt-3 mt-3">
		    <h1 style="font-size: 400%">Welcome to Sunshine Dentist Clinic!</h1>
		</nav>

		<div class="content p-3 my-3 h-100 border" id="receptionists">
			<h1>Receptionist View</h1>
			<div class="p-1 my-1 border border-dark"
				id="receptionistNav">
				<div class="row justify-content-around">
				<button class="p-1 m-1 mx-auto" style="width: 20rem;" onclick="openTab('appointmentDateSearch')">Set a New Appointment</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;" onclick="openTab('patientBilling')">Bill a Patient</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;" onclick="openTab('patientRegister')">Add a New Patient</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;" onclick="openTab('editPatient')">Edit Patient Information</button>
				</div>
				
				<div class="row justify-content-around">
				<button class="p-1 m-1 mx-auto" style="width: 20rem;" onclick="openTab('guardianRegister')">Add a New Guardian</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;" onclick="openTab('editGuardian')">Edit Guardian Information</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;" onclick="openTab('listDentists')">List Branch Dentists</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;" onclick="location.href='/dentalclinic/'">Go Back</button>
				</div>
			</div>

			<div class="p-3 my-3" id="appointmentDateSearch">
				<h2>Choose Appointment Date</h2>
				<form method="post" action="appointment">
					Date:<input class="m-1 form-control" type="date" id="appointmentDateQuery" name="appointmentDateQuery">
					<button type="submit" value="submit" onclick="return validateNotWeekend(appointmentDateQuery);">Choose</button>
				</form>
			</div>

			<div class="tab p-3 my-3" style="display: none;" id="appointment">
				<h2>New Appointment</h2>
				<form>
					First Name:<input type="text" class="m-1 form-control" id="firstNameNA" name="firstName" required> 
					Middle Name:<input type="text" class="m-1 form-control" id="middleNameNA" name="middleName"> 
					Last Name:<input type="text" class="m-1 form-control" id="lastNameNA" name="lastName" required>
					Date:<input class="m-1 form-control" type="date" id="date">
					Start Time:<input class="m-1 form-control" type="time" id="sTime" step="600" min="07:00" max="23:00">
					End Time:<input class="m-1 form-control" type="time" id="eTime" step="600" min="07:00" max="23:00">
					Room Assigned:<select class="m-1 form-control" id="roomNum">
						<option value="1">Room 1</option>
						<option value="2">Room 2</option>
						<option value="3">Room 3</option>
						<option value="4">Room 4</option>
					</select> 
					Appointment Type:<select class="m-1 form-control" id="aType">
						<option value="cleaning">Cleaning</option>
						<option value="cavity">Cavity</option>
						<option value="pull">Tooth Pulled</option>
					</select>
					<!-- Only makes sense for editing an appointment
			Status:<select class="m-1 form-control" id="aStatus">
			    <option value="upcoming">Upcoming</option>
			    <option value="cancelled">Cancelled</option>
			    <option value="completed">Completed</option>
			    <option value="noshow">No-Show</option>
			  </select> -->
					<button type="submit" value="submit" onclick="??">Create Appointment</button>
					<button type="reset" value="reset">Reset</button>
				</form>
				<br>
			</div>


			<div class="tab p-3 my-3" style="display: none;" id=patientBilling>
				<h2>Patient Billing</h2>
				
				<div class="p-3 my-3" id="searchInvoiceBySIN">
					<form method="post" action="patientBilling">
						Social Insurance Number:<input class="m-1 form-control" type="number" id="patientSINBill" name="patientSINBill">
						<button type="submit" value="submit" onclick="return validateSIN(patientSINBill);">View Invoices</button>
					</form>
				</div>
				

				<%//Invoice List
			  	  //Will show up only when invoices is non-empty
					if (invoiceList != null) {
						if (invoiceList.size() != 0) {
							%>
				<div class="p-3 my-3" id="invoicesView">
					<form method="post" action="patientBilling">
							<h3>Invoices Found:</h3><br>
							<select class="m-1 form-control" type="text" id="invoiceIDSelected" name="invoiceIDSelected">
								<%
								for (Invoice invoice : invoiceList) {
									%>
									<option value=<%=invoice.getInvoiceID()%>><%=invoice.toString()%></option>
									<%
								}
								%>
							</select>
							<button type="submit" value="submit" onclick="return true">Bill Form</button>
							<button type="reset" value="reset" onclick="resetPatientBilling()">Go Back</button>
					</form>
				</div>
						<%
						}
					}
					%>

			<%if (invoiceSelected != null) {%>
				
				<div class="p-3 my-3" id="billForm">
					<form method="post" action="patientBilling">
						Invoice ID:<input type="text" class="m-1 form-control" id="invoiceIDBillForm" name="invoiceIDBillForm" readonly> 
						Patient SIN:<input type="text" class="m-1 form-control" id="patientSINBillForm" name="patientSINBillForm" readonly>
						Guardian SIN:<input type="text" class="m-1 form-control" id="guardianSINBillForm" name="guardianSINBillForm" readonly>
						User Portion:<input type="number" onkeyup="calculateTotalBill()" value = 0 min="0.00" max="10000.00" step="0.01" class="m-1 form-control" id="userPortionBillForm" name="userPortionBillForm"> 
						<small>Note: Billed to the guardian if the patient has one.</small><br>
						Employee SIN:<input type="number" class="m-1 form-control" pattern="[0-9]{9}" placeholder="123456789" id="employeeSINBillForm" name="employeeSINBillForm">
						Employee Portion:<input type="number" onkeyup="calculateTotalBill()" value = 0 min="0.00" max="10000.00" step="0.01" class="m-1 form-control" id="employeePortionBillForm" name="employeePortionBillForm"> 
						Insurance Company:<input type="text" class="m-1 form-control" id="insuranceCompanyBillForm" name="insuranceCompanyBillForm"> 
						Insurance Portion:<input type="number" onkeyup="calculateTotalBill()" value = 0 min="0.00" max="10000.00" step="0.01" class="m-1 form-control" id="insurancePortionBillForm" name="insurancePortionBillForm">
						Payment Method:<select class="m-1 form-control" id="payMethodBillForm" name="payMethodBillForm" required>
						<option value="cash">Cash</option>
						<option value="debit">Debit</option>
						<option value="amex">Amex</option>
						<option value="visa">Visa</option>
						<option value="mastercard">Mastercard</option>
						</select> 
						Total Due:<input type="number" class="m-1 form-control" id="amountDue" name="amountDue" readonly> 
						Total:<input type="number" class="m-1 form-control" value=0 id="totalBillForm" name="totalBillForm" readonly> 
						<button type="submit" value="submit" onclick="return validateBillForm();">Bill</button>
						<button type="reset" value="reset" onclick="history.back();">Go Back</button>
					</form>
				</div>
				<%
			}
			%>

			</div>

			<div class="tab p-3 my-3" style="display: none;" id="patientRegister">
				<h2>New Patient</h2>
				<!--
				post refers to the method doPost from PatientRegisterServlet.java
				patientRegister is the masked/shortened directory of PatientRegisterServlet.java
				-->
				<form method="post" action="patientRegister">
					SIN:<input type="number" class="m-1 form-control" pattern="[0-9]{9}" placeholder="123456789" id="patientSIN" name="patientSIN" required>
					Username:<input type="text" class="m-1 form-control" id="userName" name="userName" required> 
					First Name:<input type="text" class="m-1 form-control" id="firstName" name="firstName" required> 
					Middle Name:<input type="text" class="m-1 form-control" id="middleName" name="middleName"> 
					Last Name:<input type="text" class="m-1 form-control" id="lastName" name="lastName" required>
					Date of Birth:<input type="date" class="m-1 form-control" id="dateOfBirth" name="dateOfBirth" required> 
					<input type="hidden" id="age" class="m-1 form-control" name="age">
					Gender:<input type="text" class="m-1 form-control" id="gender" name="gender" required> 
					E-mail Address:<input type="email" class="m-1 form-control" id="patientEmail" name="patientEmail" required> 
					Phone Number:<input type="tel" class="m-1 form-control" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" placeholder="123-456-7890" id="patientPhoneNumber" name="patientPhoneNumber" required> 
					<small>Format: 123-456-7890</small><br>
					Address:<input class="m-1 form-control" type="text" id="address" name="address" required> 
					Guardian's SIN:<input class="m-1 form-control" type="text" id="guardian" name="guardian"> 
					Enter Password:<input type="password" class="m-1 form-control" id="patientPwd" name="patientPwd" required> 
					Re-enter Password:<input type="password" class="m-1 form-control" id="patientPwdagain" name="patientPwdagain" required>
					<button type="submit" value="submit" onclick="return validatePatientRegister();">Create Patient</button>
					<button type="reset" value="reset">Reset</button>
				</form>
			</div>


			<div class="tab p-3 my-3" style="display: none;" id="editPatient">
				<h2>Edit Patient Information</h2>
				<div class="p-3 my-3" id="editPatientSearch">
					<form method="post" action="updatePatientInfo">
						Social Insurance Number:<input class="m-1 form-control" type="number" id="patientSINEP" name="patientSINEP">
						<button type="submit" value="submit" onclick="return validateSIN(patientSINEP)">Search for Patient</button>
						<button type="reset" value="reset">Reset</button>
					</form>
				</div>

				<div class="p-3 my-3" style="display: none;" id="editPatientForm">
					<form method="post" action="updatePatientInfo">
						<%if (patientInfo != null) {%>
							<input type="hidden" id="sinEP" name="sinEP" value="<%=patientInfo.getPatientSIN()%>"><%
						}
						%>
						Username:<input type="text" class="m-1 form-control" id="userNameEP" name="userNameEP" required> 
						First Name:<input class="m-1 form-control" type="text"id="fNameEP" name="fNameEP" required> 
						Middle Name:<input class="m-1 form-control" type="text" id="mNameEP" name="mNameEP"> 
						Last Name:<input class="m-1 form-control" type="text" id="lNameEP" name="lNameEP" required> 
						Date Of Birth:<input class="m-1 form-control" type="date" id="dobEP" name="dobEP" required>
						<!-- age -->
						<input type="hidden" id="ageEP" name="ageEP">
						Gender:<input class="m-1 form-control" type="text" id="genderEP" name="genderEP" required>
						Email:<input class="m-1 form-control" type="email" id="emailEP" name="emailEP" required>
						Phone number:<input class="m-1 form-control" type="tel" id="phoneEP" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" name="phoneEP" required> 
						Address:<input class="m-1 form-control" type="text" id="addressEP" name="addressEP" required>
						Guardian's SIN:<input class="m-1 form-control" type="number" id="guardianEP" name="guardianEP">
						<button type="submit" value="submit" onclick="return validatePatientEdit()">Update Patient</button>
						<button type="reset" value="reset" onclick="window.location.reload()">Default</button>
					</form>
					<br>
				</div>
			</div>


			<div class="tab p-3 my-3" style="display: none;" id="guardianRegister">
				<h2>New Guardian</h2>
				<!--
				post refers to the method doPost from PatientRegisterServlet.java
				patientRegister is the masked/shortened directory of PatientRegisterServlet.java
				-->
				<form method="post" action="guardianRegister">										
					SIN:<input type="number" class="m-1 form-control" pattern="[0-9]{9}" placeholder="123456789" id="guardianSIN" name="guardianSIN" required>
					Username:<input type="text" class="m-1 form-control" id="userNameG" name="userNameG" required> 
					First Name:<input type="text" class="m-1 form-control" id="firstNameG" name="firstNameG" required> 
					Middle Name:<input type="text" class="m-1 form-control" id="middleNameG" name="middleNameG"> 
					Last Name:<input type="text" class="m-1 form-control" id="lastNameG" name="lastNameG" required>
					Date of Birth:<input type="date" class="m-1 form-control" id="dateOfBirthG" name="dateOfBirthG" required> 
					<input type="hidden" class="m-1 form-control" id="ageG" name="ageG">
					Gender:<input type="text" class="m-1 form-control" id="genderG" name="genderG" required> 
					E-mail Address:<input type="email" class="m-1 form-control" id="guardianEmail" name="guardianEmail" required> 
					Phone Number:<input type="tel" class="m-1 form-control" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" placeholder="123-456-7890" id="guardianPhoneNumber" name="guardianPhoneNumber" required> 
					<small>Format: 123-456-7890</small><br>
					Address:<input class="m-1 form-control" type="text" id="addressG" name="addressG" required> 
					Enter Password:<input type="password" class="m-1 form-control" id="guardianPwd" name="guardianPwd" required> 
					Re-enter Password:<input type="password" class="m-1 form-control" id="guardianPwdagain" name="guardianPwdagain" required>
					<button type="submit" value="submit" onclick="return validateGuardianRegister()">Create Guardian</button>
					<button type="reset" value="reset">Reset</button>
				</form>
			</div>


			<div class="tab p-3 my-3" style="display: none;" id="editGuardian">
				<h2>Edit Guardian Information</h2>
				<div class="p-3 my-3" id="editGuardianSearch">
					<form method="post" action="updateGuardianInfo">
						Social Insurance Number:<input class="m-1 form-control" type="number" id="guardianSINSearch" name="guardianSINSearch">
						<button type="submit" value="submit" onclick="return validateSIN(guardianSINSearch)">Search for Guardian</button>
						<button type="reset" value="reset">Reset</button>
					</form>
				</div>

				<div class="p-3 my-3" style="display: none;" id="editGuardianForm">
					<form method="post" action="updateGuardianInfo">
						<%if (guardianInfo != null) {%>
							<input type="hidden" id="guardianSINEdit" name="guardianSINEdit" value="<%=guardianInfo.getGuardianSIN()%>"><%
						}
						%>
						Username:<input type="text" class="m-1 form-control" id="userNameEG" name="userNameEG" required> 
						First Name:<input class="m-1 form-control" type="text"id="fNameEG" name="fNameEG" required> 
						Middle Name:<input class="m-1 form-control" type="text" id="mNameEG" name="mNameEG"> 
						Last Name:<input class="m-1 form-control" type="text" id="lNameEG" name="lNameEG" required> 
						Date Of Birth:<input class="m-1 form-control" type="date" id="dobEG" name="dobEG" required>
						<!-- age -->
						<input type="hidden" id="ageEG" name="ageEG">
						Gender:<input class="m-1 form-control" type="text" id="genderEG" name="genderEG" required>
						Email:<input class="m-1 form-control" type="email" id="emailEG" name="emailEG" required>
						Phone number:<input class="m-1 form-control" type="tel" id="phoneEG" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" name="phoneEG" required> 
						Address:<input class="m-1 form-control" type="text" id="addressEG" name="addressEG" required>
						<button type="submit" value="submit" onclick="return validateGuardianEdit()">Update Guardian</button>
						<button type="reset" value="reset" onclick="window.location.reload()">Default</button>
					</form>
					<br>
				</div>
			</div>

			
			<div class="tab p-3 my-3" style="display: none;" id="listDentists">
				<h2>List Branch Dentists</h2>
				<div class="p-3 my-3" id="branchSearch">
					<form method="post" action="listBranchDentists">
						<button type="submit" value="submit" onclick="return true">View All Branches</button>
					</form>
				</div>
				
				<div class="p-3 my-3" id="branchList">
					<form method="post" action="listBranchDentists">
				<%//Branch List
			  	  //Will show up only when branches is non-empty
					if (branchList != null) {
						if (branchList.size() != 0) {
							%><h3>Branches:</h3><br>
							<select class="m-1 form-control" type="text" id="branchIDSelected" name="branchIDSelected">
								<%
								for (Branch branch : branchList) {
									%>
									<option value=<%=branch.getBranchID()%>><%=branch.toString()%></option>
									<%
								}
								%>
							</select>
							<button type="submit" value="submit" onclick="return true">Select</button>
							<button type="reset" value="reset" onclick="resetDentistListing()">Go Back</button>
						<%
						}
					}
					%>
					</form>
				</div>
				
				<div class="p-3 my-3" style="display: none;" id="branchSearchResults">
					<%//Dentist List
		          	  //Will show up only when dentists is non-empty
					if (dentistList != null) {
						if (dentistList.size() != 0) {
							for (Employee dentist : dentistList) {
								%>
								<li><%=dentist.toString()%></li>
								<%
							}
							%><br><%
						}
					}
					%>
					<button type="reset" value="reset" onclick="history.back();">Go Back</button>
					
				</div>
			</div>
		</div>
	</div>
</body>
</html>
