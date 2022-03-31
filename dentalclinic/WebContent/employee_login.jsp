<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Employee Login</title>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
<link href="./styles/styles.css" rel="stylesheet" type="text/css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="./scripts/main.js"></script>
<title>Sunshine Dentist Clinic</title>
<script>
$(document).ready(function() {
	
	<% String outcome = (String) request.getAttribute("outcome");
	if (outcome != null) { 
		
		if (outcome.equals("loginFailed")) {%>

			alert("Login failed: Wrong username or password!");
			
		<%}}; %>
	console.log("Not empty");
});
</script>
</head>
<body>
	<div class = "container-fluid">
	
	    <nav class="navbar text-white px-3 pt-3 mt-3">
	      <h1 style="font-size: 400%">Welcome to Sunshine Dentist Clinic!</h1>
	    </nav>
	    
	    <div class="content p-3 my-3 h-100 border" id="main">
	      <h1> Employee Login</h1>
			<form method="post" action="employeeLogin">
				Username:<input type="text" id="userName" class="m-1 form-control" name="userName"><br>
				Password:<input type="password" id="pwd" class="m-1 form-control" name="pwd"><br>
				<button type="submit" value="submit" onclick="return validateLogin('userName', 'pwd');">Login</button>
				<button type="reset" value="reset" onclick="location.href='index.html'">Back</button>
			</form>
		</div>
	</div>
</body>
</html>
