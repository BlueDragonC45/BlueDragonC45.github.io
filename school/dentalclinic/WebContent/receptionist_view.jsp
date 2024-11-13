<%@page import="java.util.ArrayList"%>
<%@page import="dentalclinic.entities.Branch"%>
<%@page import="dentalclinic.entities.Patient"%>
<%@page import="dentalclinic.entities.Guardian"%>
<%@page import="dentalclinic.entities.Employee"%>
<%@page import="dentalclinic.entities.Invoice"%>
<%@page import="dentalclinic.entities.Room"%>
<%@page import="dentalclinic.entities.Appointment"%>
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
<%ArrayList<String> employees = new ArrayList<String>();%>

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

	function validateAppointmentDate(appointmentDateQuery) {
		if (appointmentDateQuery.value == "") {
			return false;
		}
		var date = new Date(appointmentDateQuery.value)
		
		//Use UTC to avoid getting date-1
		date = new Date(
			    date.getUTCFullYear(),
			    date.getUTCMonth(),
			    date.getUTCDate(),
			    date.getUTCHours(),
			    date.getUTCMinutes(),
			    date.getUTCSeconds(),
			    date.getUTCMilliseconds());

		var today = new Date();
		
		var dayName = date.toLocaleString("en-US", {
		    timeZone: "America/New_York",
		    weekday: 'long'
		})

		//for some reason, it is shifted by one
		if (dayName != 'Saturday' && dayName != 'Sunday') { // or some other day

			if (date > today) {
				console.log(date +" > "+today);
				return true;
			} else if (date <= today) {
				alert("Date cannot be today or earlier!");
				console.log(date +" <= "+today);
				return false;
			}
		} else {
			alert("Closed on weekends; choose another date.");
			return false;
		}
	}
	
	function validateAppointmentForm() {
		var patientSIN = document.getElementById("aFormSIN");
		var aFormDate = document.getElementById("aFormDate");
		var aFormTime = document.getElementById("aFormTime");
		var aFormRoomID = document.getElementById("aFormRoomID");
		var aFormType = document.getElementById("aFormType");
		var aFormEmployees = document.getElementById("aFormEmployees");
		
		if (aFormTime.value == "" || aFormRoomID.value == ""
					   || aFormType.value == "" || aFormEmployees.value.length == 0) {
			alert("You need to fill all required fields.");
			return false;
		} else if (Boolean(patientSIN.value.length != 9)) {
			alert("The SIN must be a 9-digit number.");
			return false;
		} else if (Boolean(!validateAppointmentDate(document.getElementById("aFormDate")))) {
			return false;
		} else {
			return true;
		}
	}
	
	function addProcedure() {
		
		var pFormType = document.getElementById("pFormType").value;
		var pFormTooth = document.getElementById("pFormTooth").value;
		var pFormAmtAndMat = document.getElementById("pFormAmtAndMat").value;
		var pFormDescription = document.getElementById("pFormDescription").value;
		
		console.log(pFormDescription);
		
		if (pFormType == ""       || pFormTooth == ""
		 || pFormAmtAndMat == ""  || pFormDescription == "") {
			alert("You need to fill all required fields.");
			return false;
		} else if (pFormDescription.length <= 21) {
			alert("Type at least 20 characters in the description.");
			return false;
		} else {
			alert("Previous procedure stashed; to submit whole appointment, "+
					"you must complete the following procedure form as well. "+
					"Beware: 'Reset Default' will rid of all procedures filled thus far.");
		}
		
		var pFormTypeList = document.getElementById("pFormTypeList").value;
		var pFormToothList = document.getElementById("pFormToothList").value;
		var pFormAmtAndMatList = document.getElementById("pFormAmtAndMatList").value;
		var pFormDescriptionList = document.getElementById("pFormDescriptionList").value;
		
		console.log(pFormTypeList);
		
		if (pFormTypeList == "") {//implies the rest are empty
			document.getElementById("pFormTypeList").value = pFormType;
			document.getElementById("pFormToothList").value = pFormTooth;
			document.getElementById("pFormAmtAndMatList").value = pFormAmtAndMat;
			document.getElementById("pFormDescriptionList").value = pFormDescription;
		} else {
			document.getElementById("pFormTypeList").value = pFormTypeList+"|"+pFormType;
			document.getElementById("pFormToothList").value = pFormToothList+"|"+pFormTooth;
			document.getElementById("pFormAmtAndMatList").value = pFormAmtAndMatList+"|"+pFormAmtAndMat;
			document.getElementById("pFormDescriptionList").value = pFormDescriptionList+"|"+pFormDescription;
		}
		
		document.getElementById("pFormType").value = "";
		document.getElementById("pFormTooth").value = "";
		document.getElementById("pFormAmtAndMat").value = "";
		document.getElementById("pFormAmt").value = "";
		document.getElementById("pFormMat").value = "";
		document.getElementById("pFormDescription").value = "";

	}
	
	function validateProcedureForm() {
		var pFormType = document.getElementById("pFormType").value;
		var pFormTooth = document.getElementById("pFormTooth").value;
		var pFormAmtAndMat = document.getElementById("pFormAmtAndMat").value;
		var pFormDescription = document.getElementById("pFormDescription").value;
		
		console.log(pFormDescription);
		
		if (pFormType == ""       || pFormTooth == ""
		 || pFormAmtAndMat == ""  || pFormDescription == "") {
			alert("You need to fill all required fields.");
			return false;
		} else if (pFormDescription.length <= 21) {
			alert("Type at least 20 characters in the description.");
			return false;
		} else {

			var pFormTypeList = document.getElementById("pFormTypeList").value;
			var pFormToothList = document.getElementById("pFormToothList").value;
			var pFormAmtAndMatList = document.getElementById("pFormAmtAndMatList").value;
			var pFormDescriptionList = document.getElementById("pFormDescriptionList").value;
			
			console.log(pFormTypeList);
			
			if (pFormTypeList == "") {//implies the rest are empty
				document.getElementById("pFormTypeList").value = pFormType;
				document.getElementById("pFormToothList").value = pFormTooth;
				document.getElementById("pFormAmtAndMatList").value = pFormAmtAndMat;
				document.getElementById("pFormDescriptionList").value = pFormDescription;
			} else {
				document.getElementById("pFormTypeList").value = pFormTypeList+"|"+pFormType;
				document.getElementById("pFormToothList").value = pFormToothList+"|"+pFormTooth;
				document.getElementById("pFormAmtAndMatList").value = pFormAmtAndMatList+"|"+pFormAmtAndMat;
				document.getElementById("pFormDescriptionList").value = pFormDescriptionList+"|"+pFormDescription;
			}
			return true;
		}
	}

	function validateTreatmentForm() {
		var tFormType = document.getElementById("tFormType").value;
		var tFormTooth = document.getElementById("tFormTooth").value;
		var tFormAmtAndMed = document.getElementById("tFormAmtAndMed").value;
		var tFormSymptoms = document.getElementById("tFormSymptoms").value;
		var tFormComments = document.getElementById("tFormComments").value;
		
		console.log(tFormComments);
		
		if (tFormType == ""       || tFormTooth == ""
			 || tFormAmtAndMed == ""  || tFormSymptoms == "" 
			 || tFormComments == "") {
			alert("You need to fill all required fields.");
			return false;
		} else if (tFormComments.length <= 21) {
			alert("Type at least 20 characters in the comments.");
			return false;
		} else {

			var tFormTypeList = document.getElementById("tFormTypeList").value;
			var tFormToothList = document.getElementById("tFormToothList").value;
			var tFormAmtAndMedList = document.getElementById("tFormAmtAndMedList").value;
			var tFormSymptomList = document.getElementById("tFormSymptomList").value;
			var tFormCommentsList = document.getElementById("tFormCommentsList").value;
			
			console.log(tFormTypeList);
			
			if (tFormTypeList == "") {//implies the rest are empty
				document.getElementById("tFormTypeList").value = tFormType;
				document.getElementById("tFormToothList").value = tFormTooth;
				document.getElementById("tFormAmtAndMedList").value = tFormAmtAndMed;
				document.getElementById("tFormSymptomList").value = tFormSymptoms;
				document.getElementById("tFormCommentsList").value = tFormComments;
			} else {
				document.getElementById("tFormTypeList").value = tFormTypeList+"|"+tFormType;
				document.getElementById("tFormToothList").value = tFormToothList+"|"+tFormTooth;
				document.getElementById("tFormAmtAndMedList").value = tFormAmtAndMedList+"|"+tFormAmtAndMed;
				document.getElementById("tFormSymptomList").value = tFormSymptomList+"|"+tFormSymptoms;
				document.getElementById("tFormCommentsList").value = tFormCommentsList+"|"+tFormComments;
			}
			return true;
		}
	}
	
	function addTreatment() {
		
		var tFormType = document.getElementById("tFormType").value;
		var tFormTooth = document.getElementById("tFormTooth").value;
		var tFormAmtAndMed = document.getElementById("tFormAmtAndMed").value;
		var tFormSymptoms = document.getElementById("tFormSymptoms").value;
		var tFormComments = document.getElementById("tFormComments").value;
		
		console.log(tFormComments);
		
		if (tFormType == ""       || tFormTooth == ""
		 || tFormAmtAndMed == ""  || tFormSymptoms == "" 
		 || tFormComments == "") {
			alert("You need to fill all required fields.");
			return false;
		} else if (tFormComments.length <= 21) {
			alert("Type at least 20 characters in the comments.");
			return false;
		} else {
			alert("Previous treatment stashed; to submit whole appointment, "+
					"you must complete the following treatment form as well. "+
					"Beware: 'Reset Default' will rid of all treatments filled thus far.");
		}
		
		var tFormTypeList = document.getElementById("tFormTypeList").value;
		var tFormToothList = document.getElementById("tFormToothList").value;
		var tFormAmtAndMedList = document.getElementById("tFormAmtAndMedList").value;
		var tFormSymptomList = document.getElementById("tFormSymptomList").value;
		var tFormCommentsList = document.getElementById("tFormCommentsList").value;
		
		console.log(tFormTypeList);
		
		if (tFormTypeList == "") {//implies the rest are empty
			document.getElementById("tFormTypeList").value = tFormType;
			document.getElementById("tFormToothList").value = tFormTooth;
			document.getElementById("tFormAmtAndMedList").value = tFormAmtAndMed;
			document.getElementById("tFormSymptomList").value = tFormSymptoms;
			document.getElementById("tFormCommentsList").value = tFormComments;
		} else {
			document.getElementById("tFormTypeList").value = tFormTypeList+"|"+tFormType;
			document.getElementById("tFormToothList").value = tFormToothList+"|"+tFormTooth;
			document.getElementById("tFormAmtAndMedList").value = tFormAmtAndMedList+"|"+tFormAmtAndMed;
			document.getElementById("tFormSymptomList").value = tFormSymptomList+"|"+tFormSymptoms;
			document.getElementById("tFormCommentsList").value = tFormCommentsList+"|"+tFormComments;
		}
		
		document.getElementById("tFormType").value = "";
		document.getElementById("tFormTooth").value = "";
		document.getElementById("tFormAmtAndMed").value = "";
		document.getElementById("tFormAmt").value = "";
		document.getElementById("tFormMed").value = "";
		document.getElementById("tFormSymptom").value = "";
		document.getElementById("tFormSymptoms").value = "";
		document.getElementById("tFormComments").value = "";
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
			alert("You need to fill all required fields.");
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
			alert("You need to fill all required fields");
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
			alert("You need to fill all required fields.");
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
			alert("You need to fill all required fields.");
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

	function addEmployeeToAppointment() {
		var employeeToAdd = document.getElementById("aFormEmployeeChoose").value;
		var prevValue = document.getElementById("aFormEmployees").value;
		
		if (prevValue.search(employeeToAdd) == -1 && employeeToAdd != "") {
			if (prevValue == "") {
				document.getElementById("aFormEmployees").value = employeeToAdd;
			} else {
				document.getElementById("aFormEmployees").value = prevValue+", "+employeeToAdd;
			}
		}
	}

	function addAmountAndMaterial() {
		
		var amount = document.getElementById("pFormAmt").value;
		var material = document.getElementById("pFormMat").value;
		var list = document.getElementById("pFormAmtAndMat").value;
		
		if (amount != "" && material != "") {
			if (list == "") {
				document.getElementById("pFormAmtAndMat").value = amount+" "+material;
			} else {
				document.getElementById("pFormAmtAndMat").value = list+", "+amount+" "+material;
			}
		} else {
			alert("Must specify the amount AND material in order to add.");
		}
	}
	
	function clearMaterialsList() {
		
		document.getElementById("pFormAmtAndMat").value = "";
	}

	function addAmountAndMedication() {
		
		var amount = document.getElementById("tFormAmt").value;
		var material = document.getElementById("tFormMed").value;
		var list = document.getElementById("tFormAmtAndMed").value;
		
		if (amount != "" && material != "") {
			if (list == "") {
				document.getElementById("tFormAmtAndMed").value = amount+" "+material;
			} else {
				document.getElementById("tFormAmtAndMed").value = list+", "+amount+" "+material;
			}
		} else {
			alert("Must specify the amount AND medicine in order to add.");
		}
	}
	
	function clearMedicineList() {
		
		document.getElementById("tFormAmtAndMed").value = "";
	}

	function addSymtomToTreatment() {
		var symptomToAdd = document.getElementById("tFormSymptom").value;
		var prevValue = document.getElementById("tFormSymptoms").value;
		
		if (prevValue.search(symptomToAdd) == -1 && symptomToAdd != "") {
			if (prevValue == "") {
				document.getElementById("tFormSymptoms").value = symptomToAdd;
			} else {
				document.getElementById("tFormSymptoms").value = prevValue+", "+symptomToAdd;
			}
		}
	}
	
	function clearSymptoms() {
		
		document.getElementById("tFormSymptoms").value = "";
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
		
		document.getElementById("amountDue").value = totalDue;
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
			alert("The employee with that SIN does not exist.");
			history.back();
			<%
		} else if (outcomeB.equals("early")) {
			%>
			alert("Can only bill within appointment start and end times!");
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
			alert("Failed to bill; request already processed.");
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
			history.back();
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
			alert("Unknown Error; make sure the SIN or username is unique.");
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
	Object obj7 = request.getAttribute("resultHours");
	ArrayList<String> resultHours = null;
	if (obj7 instanceof ArrayList) {
		resultHours = (ArrayList<String>) obj7;

	}
	Appointment newAppointmentProcedure = (Appointment) request.getAttribute("newAppointment");
	String branchLocationProcedure = (String) request.getAttribute("pFormBranchLocation");
	String pFormTime = (String) request.getAttribute("pFormTime");
	String outcomeA = (String) request.getAttribute("outcomeA");
	if (outcomeA != null) { 

		if (outcomeA.equals("patientNotFound")) {%>

			alert("The patient with that SIN does not exist!");
			history.back();
			
		<%} else if (outcomeA.equals("unavailable")) {
			%>
			alert("That day is fully booked.");
			history.back();
			<%
		} else if (outcomeA.equals("procedure")) {
			String appointmentTime = newAppointmentProcedure.getAppointmentDate();
			%>
			document.getElementById("pFormDate").value = "<%=newAppointmentProcedure.getAppointmentDate()%>";
			document.getElementById("pFormTime").value = "<%=pFormTime%>";
			document.getElementById("pFormAType").value = "<%=newAppointmentProcedure.getAppointmentType()%>";
			document.getElementById("pFormEmployees").value = "<%=(String) request.getAttribute("pFormEmployees")%>";
			document.getElementById("pFormRoomID").value = "<%=newAppointmentProcedure.getRoomID()%>";
			document.getElementById("pFormBranchID").value = "<%=newAppointmentProcedure.getBranchID()%>";
			document.getElementById("pFormSIN").value = "<%=newAppointmentProcedure.getPatientSIN()%>";
			document.getElementById("pFormBranchLocation").value = "<%=branchLocationProcedure%>";
			openTab('procedureForm')
			<%
		} else if (outcomeA.equals("treatment")) {
			String appointmentTime = newAppointmentProcedure.getAppointmentDate();
			%>
			document.getElementById("tFormDate").value = "<%=newAppointmentProcedure.getAppointmentDate()%>";
			document.getElementById("tFormTime").value = "<%=pFormTime%>";
			document.getElementById("tFormAType").value = "<%=newAppointmentProcedure.getAppointmentType()%>";
			document.getElementById("tFormEmployees").value = "<%=(String) request.getAttribute("pFormEmployees")%>";
			document.getElementById("tFormRoomID").value = "<%=newAppointmentProcedure.getRoomID()%>";
			document.getElementById("tFormBranchID").value = "<%=newAppointmentProcedure.getBranchID()%>";
			document.getElementById("tFormSIN").value = "<%=newAppointmentProcedure.getPatientSIN()%>";
			document.getElementById("tFormBranchLocation").value = "<%=branchLocationProcedure%>";
			openTab('treatmentForm')
			<%
		} else if (outcomeA.equals("not free")) {
			
			%>
			alert("That time is booked already; try a time in: "+<%=resultHours.toString()%>);
			<%
		} else if (outcomeA.equals("successAddAppointment")) {
			
			%>
			alert("Appointment of type procedure successfully added!");
			<%
		} else if (outcomeA.equals("successAddTreatment")) {
			
			%>
			alert("Appointment of type treatment successfully added!");
			<%
		}  else {
			%>
			alert("Error; request already processed.");
			<%
		}
	 }; %>
	
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
	Object obj3 = request.getAttribute("toBillInvoices");
	ArrayList<Invoice> invoiceList = null;
	if (obj3 instanceof ArrayList) 
		invoiceList = (ArrayList<Invoice>) obj3;
	Object obj31 = request.getAttribute("toBillAppointments");
	ArrayList<Appointment> invoiceAppointmentList = null;
	if (obj31 instanceof ArrayList) 
		invoiceAppointmentList = (ArrayList<Appointment>) obj31;
	
	if (invoiceList != null && invoiceAppointmentList != null) {
		if (invoiceList.size() == 0 || invoiceAppointmentList.size() == 0) {
			%>alert("No pending invoices found for this patient.");<%
		} else {
			%>
		 	openTab('patientBilling');
			document.getElementById("searchInvoiceBySIN").style.display = "none";
			document.getElementById("invoicesView").style.display = "block";<%
		}
	}%>

<%
	Object obj4 = request.getAttribute("branchesAppointment");
	ArrayList<Branch> branchList2 = null;
	if (obj4 instanceof ArrayList) {
		branchList2 = (ArrayList<Branch>) obj4;
	}
	if (branchList2 != null) {
		%>
	 	openTab('appointmentQueryLocation');
		document.getElementById("appointmentLocationBranch").style.display = "block";
		document.getElementById("appointmentQueryLocation").style.display = "none";
		<%
	}
	%>
	
	<%
	Branch branchChosen = (Branch) request.getAttribute("branch");
	Object obj5 = request.getAttribute("rooms");
	ArrayList<Room> roomList = null;
	if (obj5 instanceof ArrayList) {
		roomList = (ArrayList<Room>) obj5;
	}
	Object obj6 = request.getAttribute("employees");
	ArrayList<Employee> employeeList = null;
	if (obj6 instanceof ArrayList) {
		employeeList = (ArrayList<Employee>) obj6;
	}
	if (roomList != null && employeeList != null) {
		%>
		
	 	openTab('appointmentForm');
		document.getElementById("aFormBranchID").value = "<%=branchChosen.getBranchID()%>";
		document.getElementById("aFormBranchLocation").value = "<%=branchChosen.toString()%>";
	 	
		<%
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
				<button class="p-1 m-1 mx-auto" style="width: 20rem;" onclick="openTab('patientRegister')">Register New Patient</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;" onclick="openTab('editPatient')">Edit Patient Information</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;" onclick="openTab('guardianRegister')">Register New Guardian</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;" onclick="openTab('editGuardian')">Edit Guardian Information</button>
				</div>
				
				<div class="row justify-content-around">
				<button class="p-1 m-1 mx-auto" style="width: 20rem;" onclick="openTab('appointmentQueryLocation')">New Appointment</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;" onclick="openTab('patientBilling')">Bill Patient</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;" onclick="openTab('listDentists')">List Branch Dentists</button>
				<button class="p-1 m-1 mx-auto" style="width: 20rem;" onclick="location.href='/dentalclinic/'">Go Back</button>
				</div>
			</div>

			<div class="tab p-3 my-3" style="display: none;" id="appointmentQueryLocation">
				<h2>Query for Available Branches</h2><br>
				<form method="post" action="appointment">
					<button type="submit" value="submit" onclick="return true">View All Branches</button>
				</form>
			</div>

			<div class="tab p-3 my-3" style="display: none;" id="appointmentLocationBranch">
				<h2>Available Branches</h2><br>
					<form method="post" action="appointment">
					<%//Branch List
			  	  	//Will show up only when branches is non-empty
					if (branchList2 != null) {
						if (branchList2.size() != 0) {
							%>
							<select class="m-1 form-control" type="text" id="appointmentBranchID" name="appointmentBranchID">
								<%
								for (Branch branch : branchList2) {
									%>
									<option value=<%=branch.getBranchID()%>><%=branch.toString()%></option>
									<%
								}
								%>
							</select>
							<button type="submit" value="submit" onclick="return true">Select</button>
							<button type="reset" value="reset" onclick="openTab('appointmentQueryLocation')">Go Back</button>
						<%
						}
					}
					%>
					</form>
			</div>
			
			<div class="tab p-3 my-3" style="display: none;" id="appointmentForm">
				<h2>New Appointment</h2><br>
				<form method="post" action="appointment">
					<input type="hidden" class="m-1 form-control" id="aFormBranchID" name="aFormBranchID" required readonly> 
					Branch Location:<input type="text" class="m-1 form-control" id="aFormBranchLocation" name="aFormBranchLocation" required readonly> 
					Patient SIN:<input type="number" class="m-1 form-control" id="aFormSIN" name="aFormSIN" pattern="[0-9]{9}" placeholder="123456789" required > 
					Appointment Date:<input class="m-1 form-control" type="date" id="aFormDate" name="aFormDate" required>
					Appointment Time Slot:<select class="m-1 form-control" id="aFormTime" name="aFormTime" required>
								<option value="">Choose a time slot...</option>
						<option value="08:00:00-09:00:00">08:00:00-09:00:00</option>
						<option value="09:00:00-10:00:00">09:00:00-10:00:00</option>
						<option value="10:00:00-11:00:00">10:00:00-11:00:00</option>
						<option value="11:00:00-12:00:00">11:00:00-12:00:00</option>
						<option value="12:00:00-13:00:00">12:00:00-13:00:00</option>
						<option value="13:00:00-14:00:00">13:00:00-14:00:00</option>
						<option value="14:00:00-15:00:00">14:00:00-15:00:00</option>
						<option value="15:00:00-16:00:00">15:00:00-16:00:00</option>
						<option value="16:00:00-17:00:00">16:00:00-17:00:00</option>
					</select>	
					Room:					
					<%//Room List
			  	  	//Will show up only when roomList is non-empty
					if (roomList != null) {
						if (roomList.size() != 0) {
							%>
							<select class="m-1 form-control" type="text" id="aFormRoomID" name="aFormRoomID" required>
								<option value="">Choose room...</option>
							<%
							for (Room room : roomList) {
								%>
								<option value=<%=room.getRoomID()%>><%=room.toString()%></option>
								<%
							}
						}
					}
					%>
						</select>
					Appointment Type:<select class="m-1 form-control" id="aFormType" name="aFormType" required>
							<option value="">Choose type...</option>
						<option value="procedure">Procedure</option>
						<option value="treatment">Treatment</option>
					</select>	
					Select Employees to Add:
					<%
					if (employeeList != null) {
						if (employeeList.size() != 0) {
							%>
							<select onclick="addEmployeeToAppointment()" class="m-1 form-control" type="text" id="aFormEmployeeChoose" name="aFormEmployeeChoose" required>
								<option value="">Add employee...</option>
							<%
							
							for (Employee employee : employeeList) {
								%>
								<option value=<%=employee.getEmployeeSIN()%>><%=employee.toString()%></option>
								<%
							}
						}
					}
					%>
						</select>
					<input type="text" class="m-1 form-control" id="aFormEmployees" name="aFormEmployees" required readonly> 
					<button type="submit" value="submit" onclick="return validateAppointmentForm();">Next</button>
					<button type="reset" value="reset" onclick="window.location.reload()">Reset Default</button>
				</form>
				<br>
			</div>
			
			<div class="tab p-3 my-3" style="display: none;" id="procedureForm">
				<h2>Add Procedure To Appointment</h2><br>
				<form method="post" action="appointment">
				
					<input type="hidden" id="pFormTime" name="pFormTime"> 
					<input type="hidden" id="pFormAType" name="pFormAType"> 
					<input type="hidden" id="pFormEmployees" name="pFormEmployees"> 
					<input type="hidden" id="pFormRoomID" name="pFormRoomID"> 
					<input type="hidden" id="pFormBranchID" name="pFormBranchID"> 
				
					Patient SIN:<input type="text" class="m-1 form-control" id="pFormSIN" name="pFormSIN" pattern="[0-9]{9}" placeholder="123456789" required readonly> 
					Procedure Date:<input type="text" class="m-1 form-control" id="pFormDate" name="pFormDate" required readonly> 
					Branch Location:<input type="text" class="m-1 form-control" id="pFormBranchLocation" name="pFormBranchLocation" required readonly> 
					
					<input type="hidden" id="pFormTypeList" name="pFormTypeList"> 
					Procedure Type:<select class="m-1 form-control" id="pFormType">
							<option value="">Choose type...</option>
						<option value="D21.50 evaluation 50 21500">Evaluation</option>
						<option value="D21.77 resin 30 21770">Resin</option>
						<option value="D22.15 sealant 25 22150">Sealant</option>
						<option value="D22.33 fluoride 25 22330">Fluoride</option>
						<option value="D25.22 prophylaxis 50 25220">Prophylaxis</option>
						<option value="D27.75 varnish 25 27750">Varnish</option>
					</select>	
					
					<input type="hidden" id="pFormToothList" name="pFormToothList"> 
					Tooth Involved (FDI No.)<select class="m-1 form-control" type="text" id="pFormTooth" name="pFormTooth">
						<option value="">Choose tooth...</option>
					<%
					//adult teeth
					for (int i = 11 ; i <= 18 ; i++) {
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
					}
					for (int i = 21 ; i <= 28 ; i++) {
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
					}
					for (int i = 31 ; i <= 48 ; i++) {
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
					}
					
					//baby teeth
					for (int i = 51 ; i <= 55 ; i++) {
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
					}
					for (int i = 61 ; i <= 65 ; i++) {
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
					}
					for (int i = 71 ; i <= 75 ; i++) {
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
					}
					for (int i = 81 ; i <= 85 ; i++) {
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
					}
						
					
					%>
						</select>
					Materials Used:
					<div class="input-group">
						<select class="form-control input-sm" id="pFormAmt" name="pFormAmt">
							<option value="">Choose amount...</option>
							<option value="2g">2 grams of...</option>
							<option value="4g">4 grams of...</option>
							<option value="6g">6 grams of...</option>
							<option value="8g">8 grams of...</option>
							<option value="16g">16 grams of...</option>
							<option value="20g">20 grams of...</option>
						</select>
						<select class="form-control input-sm" id="pFormMat" name="pFormMat">
							<option value="">Choose material...</option>
							<option value="fluoride">Fluoride</option>
							<option value="putty">Putty</option>
							<option value="resin">Resin</option>
							<option value="antiseptic">Antiseptic</option>
							<option value="wax">Wax</option>
							<option value="gum">Gum</option>
						</select>	
						<input type="button" value="Add to List" onclick="addAmountAndMaterial()">
					</div>

					<input type="hidden" id="pFormAmtAndMatList" name="pFormAmtAndMatList"> 
					<div class="input-group">
						<input class="form-control input-sm" type="text" id="pFormAmtAndMat" name="pFormAmtAndMat" placeholder="Material list currently empty, try adding something..." required readonly> 
						<input type="button" value=" Empty List" onclick="clearMaterialsList()">
					</div>
					
					<input type="hidden" id="pFormDescriptionList" name="pFormDescriptionList"> 
					Description: <textarea class="m-1 form-control" cols="20" rows="20" id="pFormDescription" name="pFormDescription" required></textarea>
					
					<button type="submit" value="submit" onclick="return validateProcedureForm();">Submit Appointment</button>
					<button type="reset" value="reset" onclick="window.location.reload()">Reset Default</button>
					<input type="button" value="Add Another Procedure" onclick="addProcedure()">
				</form>
				<br>
			</div>
			
			<div class="tab p-3 my-3" style="display: none;" id="treatmentForm">
				<h2>Add Treatment To Appointment</h2><br>
				<form method="post" action="appointment">
				
					<input type="hidden" id="tFormTime" name="tFormTime"> 
					<input type="hidden" id="tFormAType" name="tFormAType"> 
					<input type="hidden" id="tFormEmployees" name="tFormEmployees"> 
					<input type="hidden" id="tFormRoomID" name="tFormRoomID"> 
					<input type="hidden" id="tFormBranchID" name="tFormBranchID"> 
				
					Patient SIN:<input type="text" class="m-1 form-control" id="tFormSIN" name="tFormSIN" pattern="[0-9]{9}" placeholder="123456789" required readonly> 
					Treatment Date:<input type="text" class="m-1 form-control" id="tFormDate" name="tFormDate" required readonly> 
					Branch Location:<input type="text" class="m-1 form-control" id="tFormBranchLocation" name="tFormBranchLocation" required readonly> 
					
					<input type="hidden" id="tFormTypeList" name="tFormTypeList"> 
					Treatment Type:<select class="m-1 form-control" id="tFormType">
							<option value="">Choose type...</option>
						<option value="D10.25 extraction 60 10250">Extraction</option>
						<option value="D11.47 scaling 40 11470">Scaling</option>
						<option value="D14.48 amalgam 80 14480">Amalgam</option>
						<option value="D15.62 crown 90 15620">Crown</option>
						<option value="D16.41 bridge 100 16410">Bridge</option>
					</select>	
					
					<input type="hidden" id="tFormToothList" name="tFormToothList"> 
					Tooth Involved (FDI No.)<select class="m-1 form-control" type="text" id="tFormTooth" name="tFormTooth">
						<option value="">Choose tooth...</option>
					<%
					//adult teeth
					for (int i = 11 ; i <= 18 ; i++) {
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
					}
					for (int i = 21 ; i <= 28 ; i++) {
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
					}
					for (int i = 31 ; i <= 48 ; i++) {
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
					}
					
					//baby teeth
					for (int i = 51 ; i <= 55 ; i++) {
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
					}
					for (int i = 61 ; i <= 65 ; i++) {
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
					}
					for (int i = 71 ; i <= 75 ; i++) {
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
					}
					for (int i = 81 ; i <= 85 ; i++) {
						%>
						<option value="<%=i%>"><%=i%></option>
						<%
					}
						
					
					%>
						</select>
					Medication:
					<div class="input-group">
						<select class="form-control input-sm" id="tFormAmt" name="tFormAmt">
							<option value="">Choose amount...</option>
							<option value="5mg">5 milligrams of...</option>
							<option value="10mg">10 milligrams of...</option>
							<option value="20mg">20 milligrams of...</option>
							<option value="50mg">50 milligrams of...</option>
							<option value="100mg">100 milligrams of...</option>
							<option value="200mg">200 milligrams of...</option>
						</select>
						<select class="form-control input-sm" id="tFormMed" name="tFormMed">
							<option value="">Choose material...</option>
							<option value="kenalog">Kenalog</option>
							<option value="ambesol">Ambesol</option>
							<option value="orajel">Orajel</option>
							<option value="peridex">Peridex</option>
							<option value="fluoride">Fluoride</option>
							<option value="pilocarpine">Pilocarpine</option>
							<option value="motrin">Motrin</option>
						</select>	
						<input type="button" value="Add to List" onclick="addAmountAndMedication()">
					</div>
					<input type="hidden" id="tFormAmtAndMedList" name="tFormAmtAndMedList"> 
				<div class="input-group">
						<input class="form-control input-sm" type="text" id="tFormAmtAndMed" name="tFormAmtAndMed" placeholder="Medication list currently empty, try adding something..." required readonly> 
						<input type="button" value=" Empty List" onclick="clearSymptoms()">
					</div>
					
					Symptoms:
						<select onclick="addSymtomToTreatment()" class="m-1 form-control" type="text" id="tFormSymptom" name="tFormSymptom" required>
							<option value="">Add a symptom...</option>
							<option value="toothache">Toothache</option>
							<option value="sensitivity">Sensitivity</option>
							<option value="bleeding">Bleeding</option>
							<option value="mouth sore">Mouth Sore</option>
							<option value="bad breath">Bad Breath</option>
							<option value="jaw pain">Jaw Pain</option>
							<option value="dry mouth">Dry Mouth</option>
							<option value="piercing infection">Piercing Infection</option>
							<option value="cracked tooth">Cracked Tooth</option>
							<option value="tooth discoloration">Tooth Discoloration</option>
						</select>

					<input type="hidden" id="tFormSymptomList" name="tFormSymptomList"> 
					<div class="input-group">
						<input class="form-control input-sm" type="text" id="tFormSymptoms" name="tFormSymptoms" placeholder="Symptom list currently empty, try adding something..." required readonly> 
						<input type="button" value=" Empty List" onclick="clearSymptoms()">
					</div>
					
					<input type="hidden" id="tFormCommentsList" name="tFormCommentsList"> 
					Comments: <textarea class="m-1 form-control" cols="20" rows="20" id="tFormComments" name="tFormComments" required></textarea>
					
					<button type="submit" value="submit" onclick="return validateTreatmentForm();">Submit Appointment</button>
					<button type="reset" value="reset" onclick="window.location.reload()">Reset Default</button>
					<input type="button" value="Add Another Treatment" onclick="addTreatment()">
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
					if (invoiceList != null && invoiceAppointmentList != null) {
						if (invoiceList.size() != 0 || invoiceAppointmentList.size() != 0) {
							%>
				<div class="p-3 my-3" id="invoicesView">
					<form method="post" action="patientBilling">
							<h3>Pending Invoices Found:</h3><br>
							<select class="m-1 form-control" type="text" id="invoiceIDSelected" name="invoiceIDSelected">
								<%
								for (int i = 0 ; i < invoiceList.size() ; i++) {
									%>
									<option value=<%=invoiceList.get(i).getInvoiceID()%>><%=invoiceList.get(i).toString()
									+" For an appointment on the day "+invoiceAppointmentList.get(i).getAppointmentDate()+", of type "
									+invoiceAppointmentList.get(i).getAppointmentType()+"."%></option>
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
						<button type="reset" value="reset" onclick="window.location.reload()">Reset Default</button>
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
						<button type="reset" value="reset" onclick="window.location.reload()">Reset Default</button>
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
