<%@page import="java.util.ArrayList"%>
<%@page import="dentalclinic.entities.Branch"%>
<%@page import="dentalclinic.entities.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
<link href="./styles/styles.css" rel="stylesheet" type="text/css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
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

function validateEmployeeRegister() {
	var employeeSIN = document.getElementById("employeeSIN");
	var userName = document.getElementById("userName");
	var branchID = document.getElementById("branchID");

	var firstName = document.getElementById("fName");
	var lastName = document.getElementById("lName");

	var dateOfBirth = document.getElementById("dateOfBirth");
	var age = document.getElementById("age");
	document.getElementById("age").value = calcAge(dateOfBirth.value);
	var gender = document.getElementById("gender");
	var email = document.getElementById("email");
	var phoneNumber = document.getElementById("phone");
	var address = document.getElementById("address");
	var role = document.getElementById("role");
	var employeeType = document.getElementById("employeeType");
	var salary = document.getElementById("salary");
	var employeePwd = document.getElementById("patientPwd");
	var employeePwdagain = document.getElementById("patientPwdagain");

	if (employeeSIN.value == "" || userName.value == ""           || firstName.value == "" || lastName.value == ""
							    || employeePwd.value == ""        || employeePwdagain.value == ""
							    || dateOfBirth.value == ""        || gender.value == ""    || email.value == ""
							    || phoneNumber.value == "" 		  || address.value == ""   || role.value == "" 
							    || employeeType.value == ""       || salary.value == ""	   || branchID.value == ""){
		alert("You need to fill all requiered fields");
		return false;
	} else if(patientSIN.value.length != 9){
		alert("The length of SIN needs to be 9 digits long");
		return false;
	} else if(age.value < 18){
		alert("Must be at least 18 years of age");
		return false;
	} else if(patientPwd.value != patientPwdagain.value){
		alert("Passwords need to match!");
		return false;
	} else
		return true;
}

function validateEmployeeEdit() {
	var employeeSIN = document.getElementById("sinEE");
	var userName = document.getElementById("userNameEE");
	var branchID = document.getElementById("branchIDEE");

	var firstName = document.getElementById("fNameEE");
	var lastName = document.getElementById("lNameEE");

	var dateOfBirth = document.getElementById("dateOfBirthEE");
	var age = document.getElementById("ageEE");
	document.getElementById("age").value = calcAge(dateOfBirth.value);
	var gender = document.getElementById("genderEE");
	var email = document.getElementById("emailEE");
	var phoneNumber = document.getElementById("phoneEE");
	var address = document.getElementById("addressEE");
	var role = document.getElementById("roleEE");
	var employeeType = document.getElementById("employeeTypeEE");
	var salary = document.getElementById("salaryEE");

	if (employeeSIN.value == "" || userName.value == ""           || firstName.value == "" || lastName.value == ""
							    || dateOfBirth.value == ""        || gender.value == ""    || email.value == ""
							    || phoneNumber.value == "" 		  || address.value == ""   || role.value == "" 
							    || employeeType.value == ""       || salary.value == ""	   || branchID.value == ""){
		alert("You need to fill all requiered fields");
		return false;
	} else if(patientSIN.value.length != 9){
		alert("The length of SIN needs to be 9 digits long");
		return false;
	} else if(age.value < 18){
		alert("Must be at least 18 years of age");
		return false;
	} else
		return true;
}

function resetEmployeeListing() {
	document.getElementById("branchSearch").style.display = "block";
	document.getElementById("branchList").style.display = "none";
	document.getElementById("branchSearchResults").style.display = "none";
}

function resetEditEmployee() {
	document.getElementById("editEmployeeSearch").style.display = "block";
	document.getElementById("editEmployeeForm").style.display = "none";
	if (document.getElementById("outcome") != null) {
		document.getElementById("outcome").value = "";
	}
}

$(document).ready(function() {
	
	<% Employee employeeInfo = (Employee) request.getAttribute("employee");
	if (employeeInfo != null) { %>
	 	openTab('updateEmployee');
		document.getElementById("editEmployeeSearch").style.display = "none";
		document.getElementById("editEmployeeForm").style.display = "block";
		document.getElementById("sinEE").value = <%=employeeInfo.getEmployeeSIN()%>;
		document.getElementById("usernameEE").value = "<%=employeeInfo.getUserName()%>";
		document.getElementById("branchIDEE").value = "<%=employeeInfo.getBranchID()%>";
		document.getElementById("fNameEE").value = "<%=employeeInfo.getFirstName()%>";
		document.getElementById("mNameEE").value = "<%=employeeInfo.getMiddleName()%>";
		document.getElementById("lNameEE").value = "<%=employeeInfo.getLastName()%>";
		document.getElementById("dobEE").value = "<%=employeeInfo.getDateofBirth()%>";
		document.getElementById("genderEE").value = "<%=employeeInfo.getGender()%>";
		document.getElementById("emailEE").value = "<%=employeeInfo.getEmployeeEmail()%>";
		document.getElementById("phoneEE").value = "<%=employeeInfo.getEmployeePhoneNumber()%>";
		document.getElementById("addressEE").value = "<%=employeeInfo.getAddress()%>";
		document.getElementById("roleEE").value = "<%=employeeInfo.getRole()%>";
		document.getElementById("employeeTypeEE").value = "<%=employeeInfo.getEmployeeType()%>";
		document.getElementById("salaryEE").value = "<%=employeeInfo.getSalary()%>";
	<% }; %>
	
	<% String outcome = (String) request.getAttribute("outcome");
	if (outcome != null) { 
		%>
		var fName = "<%=(String) request.getAttribute("firstNameNEW")%>";
		var lName = "<%=(String) request.getAttribute("lastNameNEW")%>";
		<% if (outcome.equals("updateSuccess")) { %>
			alert("Employee entry for "+fName+" "+lName+" successfully updated.");
		<% } else if (outcome.equals("updateFailed")) { %>
			alert("Employee entry for "+fName+" "+lName+" could not be updated; username already taken.");
			history.back();
		<% } else if (outcome.equals("registerSuccess")) { %>
			alert("Employee entry for "+fName+" "+lName+" successfully added.");
			history.back();
		<% } else if (outcome.equals("duplicateSIN")) { %>
			alert("Employee entry for "+fName+" "+lName+" could not be added; duplicate SIN.");
			history.back();
		<% } else if (outcome.equals("duplicateUsername")) { %>
			alert("Employee entry for "+fName+" "+lName+" could not be added; duplicate username.");
			history.back();
		<% } else if (outcome.equals("noBranchesInDB")) { %>
			alert("Employee entry for "+fName+" "+lName+" could not be added; no branches available at the moment.");
			history.back();
		<% } else if (outcome.equals("branchNotFound")) { %>
			alert("Employee entry for "+fName+" "+lName+" could not be added; branch with that ID does not exist.");
			history.back();
		<% } else { %>
			alert("Employee entry for "+fName+" "+lName+" could not be added; unknown SQL Error.");
			history.back();
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
		 	openTab('listEmployees');
			document.getElementById("branchSearch").style.display = "none";
			document.getElementById("branchList").style.display = "block";<%
		}
	}
	%>

	<% 
	Object obj2 = request.getAttribute("employees");
	ArrayList<Employee> employeeList = null;
	if (obj2 instanceof ArrayList) 
		employeeList = (ArrayList<Employee>) obj2;
	
	if (employeeList != null) {
		if (employeeList.size() == 0) {
			%>alert("No dentists available in this branch.");<%
		} else {
			%>
		 	openTab('listEmployees');
			document.getElementById("branchSearch").style.display = "none";
			document.getElementById("branchSearchResults").style.display = "block";<%
		}
	}
	%>
	
});

</script>
</head>
<body>

<div class = "container-fluid">

    <nav class="navbar text-white px-3 pt-3 mt-3">
      <h1 style="font-size: 400%">Welcome to Sunshine Dentist Clinic!</h1>
    </nav>
    
    <div class="content p-3 my-3 h-100 border" id="dentists">
      <h1> Manager View</h1>
      <div class="p-1 my-1 border border-dark row justify-content-around" id="dentistNav">
        <button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('listEmployees')" >List Employees</button>
        <button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('newEmployee')" >Add a New Employee</button>
        <button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="openTab('updateEmployee')" >Update Employee Information</button>
        <button class="p-1 m-1 mx-auto" style="width: 17rem;" onclick="location.href='/dentalclinic/'" >Go Back</button>
      </div>

      <div class="tab p-3 my-3" style="display: none;" id="listEmployees">
        <h2> Employees</h2>
		<div class="p-3 my-3" id="branchSearch">
			<form method="post" action="listBranchEmployees">
				<button type="submit" value="submit" onclick="return True">View All Branches</button>
			</form>
		</div>
				
		<div class="p-3 my-3" id="branchList">
			<form method="post" action="listBranchEmployees">
			<%//Branch List
		  	  //Will show up only when branches is non-empty
				if (branchList != null) {
					if (branchList.size() != 0) {
						%><h3>Branches:</h3><br>
						<select class="m-1 form-control" type="text" id="branchIDSelected" name="branchIDSelected">
						<%
						for (Branch branch : branchList) {	%>
							<option value=<%=branch.getBranchID()%>><%=branch.toString()%></option>
						<% } %>
						</select>
						<button type="submit" value="submit" onclick="return True">Select</button>
						<button type="reset" value="reset" onclick="resetEmployeeListing()">Go Back</button>
					<%
					}
				}
				%>
			</form>
		</div>
				
		<div class="p-3 my-3" style="display: none;" id="branchSearchResults">
			<%//Employee List
	    	  //Will show up only when dentists is non-empty
			if (employeeList != null) {
				if (employeeList.size() != 0) {
					for (Employee employee : employeeList) {	%>
						<li><%=employee.toString()%></li>
					<% } %>
					<br><%
					}
				} %>
				<button type="reset" value="reset" onclick="resetEmployeeListing()">Go Back</button>
					
		</div>
      </div>

      <div class="tab p-3 my-3" style="display: none;" id="newEmployee">
		  <h2> New Employee</h2>
		  <form method="post" action="employeeRegister">
		    Social Insurance Number:<input class="m-1 form-control" type="number" id="employeeSIN" name="employeeSIN" required>
		    Username:<input class="m-1 form-control" type="text" id="username" name="username" required>
		    BranchID:<input class="m-1 form-control" type="text" id="branchID" name="branchID" required>
		    First Name:<input class="m-1 form-control" type="text" id="fName" name="fName" required>
		    Middle Name:<input class="m-1 form-control" type="text" id="mName" name="mName">
		    Last Name:<input class="m-1 form-control" type="text" id="lName" name="lName" required>
		    Date of Birth:<input class="m-1 form-control" type="date" id="dateOfBirth" name="dateOfBirth" required>
		  	<input class="m-1 form-control" type="hidden" id="age" name="age">
		    Gender:<input type="test" class="m-1 form-control" name="gender" id="gender" name="gender" required>
		    Email:<input class="m-1 form-control" type="email" id="email" name="email" required>
		    Phone number:<input class="m-1 form-control" type="tel" id="phone" placeholder="123-456-7890" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" name="phone" required>
			<small>Format: 123-456-7890</small><br>
		    Address:<input class="m-1 form-control" type="text" id="address" name="address" required>
		    Employee Role:<select class="m-1 form-control" id="role" name="role" required>
			   		<option value="dentist">Dentist</option>
			    	<option value="hygienist">Hygienist</option>
			    	<option value="receptionist">Receptionist</option>
		    	</select>
		    Employee Type:<select class="m-1 form-control" id="employeeType" name="employeeType" required>
			        <option value="partTime">Part-time</option>
			        <option value="fullTime">Full-time</option>
			        <option value="temp">Temporary</option>
			        <option value="student">Student</option>
			    </select>
		    Salary:<input class="m-1 form-control" type="number" id="salary" name="salary" required>
		    Enter Password:<input type="password" class="m-1 form-control" id="employeePwd" name="employeePwd" required> 
			Re-enter Password:<input type="password" class="m-1 form-control" id="employeePwdagain" name="employeePwdagain" required>
			<button type="submit" value="submit" onclick="return validateEmployeeRegister();">Create Employee </button>
	        <button type="reset" value="reset">Reset</button>
	      </form>
		</div>

      <div class="tab p-3 my-3" style="display: none;" id="updateEmployee">
        <h2> Update Employee</h2>
        <div class="p-3 my-3" id="editEmployeeSearch">
			<form method="post" action="updateEmployeeInfo">
				Social Insurance Number:<input class="m-1 form-control" type="text" id="employeeSINEE" name="employeeSINEE">
				<button type="submit" value="submit" onclick="return validateSIN()">Search for Employee</button>
				<button type="reset" value="reset">Reset</button>
			</form>
		</div>
		
		<div class="p-3 my-3" style="display: none;" id="editEmployeeForm">
			<form method="post" action="updateEmployeeInfo">
				<%if (employeeInfo != null) {%>
					<input type="hidden" id="sinEE" name="sinEE" value="<%=employeeInfo.getEmployeeSIN()%>"><%
				}
				%>
				Username:<input class="m-1 form-control" type="text" id="usernameEE" name="usernameEE" required>
				branchID:<input class="m-1 form-control" type="text" id="branchIDEE" name="branchIDEE" required>
				First Name:<input class="m-1 form-control" type="text"id="fNameEE" name="fNameEE" required> 
				Middle Name:<input class="m-1 form-control" type="text" id="mNameEE" name="mNameEE"> 
				Last Name:<input class="m-1 form-control" type="text" id="lNameEE" name="lNameEE" required> 
				Date Of Birth:<input class="m-1 form-control" type="date" id="dobEE" name="dobEE" required>
				<!-- age -->
				<input type="hidden" id="ageEE" name="ageEE">
				Gender:<input class="m-1 form-control" type="text" id="genderEE" name="genderEE" required>
				Email:<input class="m-1 form-control" type="email" id="emailEE" name="emailEE" required>
				Phone number:<input class="m-1 form-control" type="tel" id="phoneEE" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" name="phoneEE" required> 
				Address:<input class="m-1 form-control" type="text" id="addressEE" name="addressEE" required>
				Employee Role:<select class="m-1 form-control" id="roleEE" name="roleEE" required>
			   		<option value="dentist">Dentist</option>
			    	<option value="hygienist">Hygienist</option>
			    	<option value="receptionist">Receptionist</option>
		    	</select>
			    Employee Type:<select class="m-1 form-control" id="employeeTypeEE" name="employeeTypeEE" required>
			        <option value="partTime">Part-time</option>
			        <option value="fullTime">Full-time</option>
			        <option value="temp">Temporary</option>
			        <option value="student">Student</option>
			    </select>
			    Salary:<input class="m-1 form-control" type="number" id="salaryEE" name="salaryEE" required>
				<button type="submit" value="submit" onclick="return validateEmployeeEdit()">Update Patient</button>
				<button type="reset" value="reset" onclick="resetEditEmployee()">Reset</button>
			</form>
			<br>
		</div>
		
      </div>

    </div>
  </div>

</body>
</html>