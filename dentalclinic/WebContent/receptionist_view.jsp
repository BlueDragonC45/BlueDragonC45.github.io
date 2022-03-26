<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<% String[] dentists = (String[]) request.getAttribute("dentists");%>
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
<script src="./scripts/main.js"></script>
<title>Sunshine Dentist Clinic</title>
<script>

	$(document).ready(function() {
		if (<%dentists%> === null || <%=dentists%>.length === 0) {
			return;
		} else {
			document.getElementById(branchSearch).style.display = "none";
			document.getElementById(branchSearchResults).style.display = "block";
			
			updateDynamicList(branchSearchResults, <%dentists%>);
		}
	});

	function validateRegister() {
		var patientSSN = document.getElementById("patientSSN");
		var userName = document.getElementById("userName");

		var firstName = document.getElementById("firstName");
		var middleName = document.getElementById("middleName");
		var lastName = document.getElementById("lastName");

		var dateOfBirth = document.getElementById("dateOfBirth");
		document.getElementById("age").value = calcAge("dateOfBirth");
		var gender = document.getElementById("gender");
		var patientEmail = document.getElementById("patientEmail");
		var patientPhoneNumber = document.getElementById("patientPhoneNumber");
		var address = document.getElementById("address");
		var patientPwd = document.getElementById("patientPwd");
		var patientPwdagain = document.getElementById("patientPwdagain");

		if (patientSIN.value == "" || userName.value == "" || firstName.value == "" || lastName.value == "" || patientPwd.value == "" || patientPwdagain.value == "" || dateOfBirth == "" || gender == "" || patientEmail == "" || patientPhoneNumber == "" || address == ""){
			alert("You need to fill all requiered fields");
			return false;
		}

		else if(patientSIN.value.length != 9){
			alert("The length of SIN needs to be 9 digits long");
			return false;
		}

		else if(age < 18){
			alert("Must be at least 18 years of age");
			return false;
		}

		else if(patientPwd.value != patientPwdagain.value){
			alert("Passwords need to match!");
			return false;
		}
		else
			return true;
	}

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
				<button class="p-1 m-1 mx-auto" style="width: 17rem;"
					onclick="openTab('newApppointment')">Set a New
					Appointment</button>
				<button class="p-1 m-1 mx-auto" style="width: 17rem;"
					onclick="openTab('patientRegister')">Add a New Patient</button>
				<button class="p-1 m-1 mx-auto" style="width: 17rem;"
					onclick="openTab('editPatient')">Edit Patient Information</button>
				<button class="p-1 m-1 mx-auto" style="width: 17rem;"
					onclick="openTab('listDentists')">List Branch Dentists</button>
				<button class="p-1 m-1 mx-auto" style="width: 17rem;"
					onclick="openTab('searchPatientRecord')">Search Patient
					Record</button>
				<button class="p-1 m-1 mx-auto" style="width: 17rem;"
					onclick="location.href='index.html'">Go Back</button>
			</div>

			<div class="tab p-3 my-3" style="display: none;" id="patientRegister">
				<h2>New Patient</h2>
				<!--
		post refers to the method dePost from PatientRegisterServlet.java
		patientRegister is the masked/shortened directory of PatientRegisterServlet.java
		-->
				<form method="post" action="patientRegister">
					SSN:<input type="text" class="m-1 form-control" pattern="[0-9]{9}"
						placeholder="123456789" id="patientSSN" name="patientSSN" required>
					<small>Format: 123456789</small><br> Username:<input
						type="text" class="m-1 form-control" id="userName" name="userName"
						required> <small>Note: Used to login</small><br>
					First Name:<input type="text" class="m-1 form-control"
						id="firstName" name="firstName" required> Middle Name:<input
						type="text" class="m-1 form-control" id="middleName"
						name="middleName"> Last Name:<input type="text"
						class="m-1 form-control" id="lastName" name="lastName" required>
					Date of Birth:<input type="date" class="m-1 form-control"
						id="dateOfBirth" name="dateOfBirth" required> <input
						type="hidden" id="age" class="m-1 form-control" name="age">
					Gender:<input type="text" class="m-1 form-control" id="gender"
						name="gender" required> E-mail Address:<input type="email"
						class="m-1 form-control" id="patientEmail" name="patientEmail"
						required> Phone Number:<input type="tel"
						class="m-1 form-control" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"
						placeholder="123-456-7890" id="patientPhoneNumber"
						name="patientPhoneNumber" required> <small>Format:
						123-456-7890</small><br> Address:<input class="m-1 form-control"
						type="text" id="address"> Enter Password:<input
						type="password" class="m-1 form-control" id="patientPwd"
						name="patientPwd" required> Re-enter Password:<input
						type="password" class="m-1 form-control" id="patientPwdagain"
						name="patientPwdagain" required>
					<button type="submit" value="submit" onclick="return validate();">Create
						Patient</button>
					<button type="reset" value="reset">Reset</button>
				</form>
			</div>


			<div class="tab p-3 my-3" style="display: none;" id="newApppointment">
				<h2>New Appointment</h2>
				<form>
					First Name:<input type="text" class="m-1 form-control"
						id="firstNameNA" name="firstName" required> Middle Name:<input
						type="text" class="m-1 form-control" id="middleNameNA"
						name="middleName"> Last Name:<input type="text"
						class="m-1 form-control" id="lastNameNA" name="lastName" required>
					Date:<input class="m-1 form-control" type="date" id="date">
					Start Time:<input class="m-1 form-control" type="time" id="sTime">
					End Time:<input class="m-1 form-control" type="time" id="eTime">
					Room Assigned:<select class="m-1 form-control" id="roomNum">
						<option value="1">Room 1</option>
						<option value="2">Room 2</option>
						<option value="3">Room 3</option>
						<option value="4">Room 4</option>
					</select> Appointment Type:<select class="m-1 form-control" id="aType">
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
					<button type="submit" value="submit" onclick="??">Create
						Appointment</button>
					<button type="reset" value="reset">Reset</button>
				</form>
				<br>
			</div>

			<div class="tab p-3 my-3" style="display: none;" id="editPatient">
				<h2>Update Patient Information</h2>
				<div class="p-3 my-3" id="editPatientSearch">
					Social Insurance Number:<input class="m-1 form-control" type="text"
						id="patientSINEP">
					<button type="submit" value="submit" onclick="??">Search
						for Patient</button>
					<button type="reset" value="reset">Reset</button>
				</div>

				<div class="p-3 my-3" style="display: none;" id="editPatientForm">
					<form>
						First Name:<input class="m-1 form-control" type="text"id="fNameEP"> 
						Middle Name:<input class="m-1 form-control" type="text" id="mNameEP"> 
						Last Name:<input class="m-1 form-control" type="text" id="lNameEP"> 
						Date Of Birth:<input class="m-1 form-control" type="date" id="dobEP">
						Gender:
						<div class="p-2 d-inline" id="genderEP">
							<form id="genderRadioEP">
								<input type="radio" class="m-1" name="gender" id="maleEP"
									value="Male">Male <input type="radio" class="m-1"
									name="gender" id="femaleEP" value="Female">Female <input
									type="radio" class="m-1" name="gender" id="otherEP"
									value="Other">Other
							</form>
						</div>
						Email:<input class="m-1 form-control" type="email" id="emailEP">
						Phone number:<input class="m-1 form-control" type="tel" id="phoneEP" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"> 
						Social Security Number:<input class="m-1 form-control" type="text" id="ssnEP"> Address:<input class="m-1 form-control" type="text" id="addressEP">
						<button type="submit" value="submit" onclick="??">Update Patient</button>
						<button type="reset" value="reset">Reset</button>
					</form>
					<br>

					<div class="tab p-3 my-3" style="display: none;" id="listDentists">
						<h2>List Branch Dentists</h2>
						<div class="p-3 my-3" id="branchSearch">
							<form method="post" action="">
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
						
						</div>
					</div>


				</div>
			</div>
		</div>
	</div>
</body>
</html>
