<%@page import="java.util.ArrayList"%>
<%@page import="dentalclinic.entities.Patient"%>
<%@page import="dentalclinic.entities.Branch"%>
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

	function calcAge(dateOfBirthVal) {
		var date = new Date(dateOfBirthVal)
		var today = new Date();
		
		var difference = Math.abs(today.getTime() - date.getTime());
		var age = Math.ceil(difference / (1000 * 3600 * 24)) / 365;
		
		return Math.floor(age);
	}

	function validateRegister() {
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
			alert("You need to fill all requiered fields");
			return false;
		} else if(patientSIN.value.length != 9){
			alert("The length of SIN needs to be 9 digits long");
			return false;
		} else if(age.value < 18){
			alert("Must be at least 18 years of age");
			return false;
		} else if(guardian.value.length > 0 && guardian.value.length != 9){
			alert("The length of the guardian's SIN needs to be 9 digits long");
			return false;
		} else if(patientPwd.value != patientPwdagain.value){
			alert("Passwords need to match!");
			return false;
		} else
			return true;
	}
	
	function validateEdit() {
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
		} else if(age.value < 18){
			alert("Must be at least 18 years of age");
			return false;
		} else if(guardian.value.length > 0 && guardian.value.length != 9){
			alert("Guardian's SIN needs to be either 9 digits long or empty");
			return false;
		} else if(patientPwd.value != patientPwdagain.value){
			alert("Passwords need to match!");
			return false;
		} else
			return true;
	}
	
	function resetEditPatient() {
		document.getElementById("editPatientSearch").style.display = "block";
		document.getElementById("editPatientForm").style.display = "none";
		if (document.getElementById("outcome") != null) {
			document.getElementById("outcome").value = "";
		}
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

	<% String outcome = (String) request.getAttribute("outcome");
	if (outcome != null) { 
	
		if (outcome.equals("updateSuccess")) {%>

			var fName = "<%=(String) request.getAttribute("updatedFirstName")%>";
			var lName = "<%=(String) request.getAttribute("updatedLastName")%>";
			alert("Patient entry for "+fName+" "+lName+" successfully updated.");
			
		<%} else if (outcome.equals("updateFailed")) {
			%>

			var fName = "<%=(String) request.getAttribute("updatedFirstName")%>";
			var lName = "<%=(String) request.getAttribute("updatedLastName")%>";
			
			alert("Patient entry for "+fName+" "+lName+" could not be updated; username chosen or guardian non-existent.");
			<%
		} else if (outcome.equals("registerSuccess")) {
			%>

			var fName = "<%=(String) request.getAttribute("firstNameNEW")%>";
			var lName = "<%=(String) request.getAttribute("lastNameNEW")%>";
			
			alert("Patient entry for "+fName+" "+lName+" added successfully.");
			<%
		} else if (outcome.equals("registerFailed")) {
			%>

			var fName = "<%=(String) request.getAttribute("firstNameNEW")%>";
			var lName = "<%=(String) request.getAttribute("lastNameNEW")%>";
			
			alert("Patient entry for "+fName+" "+lName+" could not be added; username & SIN must be unique.");
			<%
		}
		
		%>
	
	<% }; %>
	
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
		
		<%//Dentist List
    	  //Will show up only when dentists is non-empty
		Object obj = request.getAttribute("branches");
		ArrayList<Branch> branchList = null;
		if (obj instanceof ArrayList) {
			branchList = (ArrayList<Branch>) obj;
		}
		if (branchList != null) {
			if (branchList.size() == 0) {
				%>alert("No branches available at the moment.");<%
			} else {
				%><h3>Branches:</h3><br><%
			}
		}
		%>
		

		
	<% }; %>
	

	});

</script>
</head>
<body>

	<div class="container-fluid">

		<nav class="navbar text-white px-3 pt-3 mt-3">
			<h1 style="font-size: 400%">
				<img src="images/logo.png" width="80" height="80"> Welcome to Sunshine Dentist Clinic!
			</h1>
		</nav>

		<div class="content p-3 my-3 h-100 border" id="receptionists">
			<h1>Receptionist View</h1>
			<div class="p-1 my-1 border border-dark row justify-content-around"
				id="receptionistNav">
				<button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('newApppointment')">Set a New Appointment</button>
				<button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('patientRegister')">Add a New Patient</button>
				<button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('editPatient')">Edit Patient Information</button>
				<button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('listDentists')">List Branch Dentists</button>
				<button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="location.href='index.html'">Go Back</button>
			</div>

			<div class="tab p-3 my-3" style="display: none;" id="patientRegister">
				<h2>New Patient</h2>
				<!--
		post refers to the method doPost from PatientRegisterServlet.java
		patientRegister is the masked/shortened directory of PatientRegisterServlet.java
		-->
				<form method="post" action="patientRegister">
					SIN:<input type="text" class="m-1 form-control" pattern="[0-9]{9}" placeholder="123456789" id="patientSIN" name="patientSIN" required>
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
					<button type="submit" value="submit" onclick="return validateRegister();">Create Patient</button>
					<button type="reset" value="reset">Reset</button>
				</form>
			</div>


			<div class="tab p-3 my-3" style="display: none;" id="newApppointment">
				<h2>New Appointment</h2>
				<form>
					First Name:<input type="text" class="m-1 form-control" id="firstNameNA" name="firstName" required> 
					Middle Name:<input type="text" class="m-1 form-control" id="middleNameNA" name="middleName"> 
					Last Name:<input type="text" class="m-1 form-control" id="lastNameNA" name="lastName" required>
					Date:<input class="m-1 form-control" type="date" id="date">
					Start Time:<input class="m-1 form-control" type="time" id="sTime">
					End Time:<input class="m-1 form-control" type="time" id="eTime">
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

			<div class="tab p-3 my-3" style="display: none;" id="editPatient">
				<h2>Edit Patient Information</h2>
				<div class="p-3 my-3" id="editPatientSearch">
					<form method="post" action="editPatientInfoSearch">
						Social Insurance Number:<input class="m-1 form-control" type="text" id="patientSINEP" name="patientSINEP">
						<button type="submit" value="submit" onclick="return validateSIN()">Search for Patient</button>
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
						Guardian's SIN:<input class="m-1 form-control" type="text" id="guardianEP" name="guardianEP">
						<button type="submit" value="submit" onclick="return validateEdit()">Update Patient</button>
						<button type="reset" value="reset" onclick="resetEditPatient()">Reset</button>
					</form>
					<br>
				</div>
			</div>
			
			<div class="tab p-3 my-3" style="display: none;" id="listDentists">
				<h2>List Branch Dentists</h2>
				<div class="p-3 my-3" id="branchSearch">
					<form method="post" action="listBranches">
						<button type="submit" value="submit" onclick="return True">Search Available Provinces</button>
					</form>
				</div>
				
				<div class="p-3 my-3" id="branchList">
				<%//Branch List
			  	  //Will show up only when branches is non-empty
					Object obj = request.getAttribute("branches");
					ArrayList<Branch> branchList = null;
					if (obj instanceof ArrayList) {
						branchList = (ArrayList<Branch>) obj;
					}
					if (branchList != null) {
						if (branchList.size() == 0) {
							%><h3>No Branches Found</h3><br><%
						} else {
							%><h3>Branches:</h3><br><%
							for (Branch branch : branchList) {
								%>
								<li><%=branch.toString()%></li>
								<%
							}
							%><br><%
						}
					}
					%>
				</div>
				
				<div class="p-3 my-3" id="branchSearch">
					<form method="post" action="listBranchDentists">
						Branch City: <input class="m-1 form-control" type="text" id="branchCitySearchInput" name="branchCitySearchInput"> <br> 
						Branch Province: <select class="m-1 form-control" type="text" id="branchProvinceSearchInput" name="branchProvinceSearchInput">
							<option value="Alberta">Alberta</option>
							<option value="British Columbia">British Columbia</option>
							<option value="Manitoba">Manitoba</option>
							<option value="New Brunswick">New Brunswick</option>
							<option value="Newfoundland and Labrador">Newfoundland and Labrador</option>
							<option value="Northwest Territories">Northwest Territories</option>
							<option value="Nova Scotia">Nova Scotia</option>
							<option value="Nunavut">Nunavut</option>
							<option value="Ontario">Ontario</option>
							<option value="Prince Edward Island">Prince Edward Island</option>
							<option value="Quebec">Quebec</option>
							<option value="Saskatchewan">Saskatchewan</option>
							<option value="Yukon">Yukon</option>
						</select>
						<button type="submit" value="submit" onclick="return True">Login</button>
					</form>
				</div>
				
				<div class="p-3 my-3" style="display: none;" id="branchSearchResults">
					<%//Dentist List
		          	  //Will show up only when dentists is non-empty
					Object obj = request.getAttribute("dentists");
					ArrayList<String> dentistList = null;
					if (obj instanceof ArrayList) {
						dentistList = (ArrayList<String>) obj;
					}
					if (dentistList != null) {
						if (dentistList.size() == 0) {
							%><h3>No Dentists Found</h3><br><%
						} else {
							%><h3>Branch Dentists:</h3><br><%
							for (String dentist : dentistList) {
								%>
								<li><%=dentist%></li>
								<%
							}
							%><br><%
						}
					}
					%>
				</div>
			</div>

		</div>
	</div>
</body>
</html>
