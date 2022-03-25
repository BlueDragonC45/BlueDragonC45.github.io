<%@page import="java.util.ArrayList"%>
<%@page import="dentalclinic.entities.Room"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Booking Page</title>
</head>
<body>

	<%
		String firstName = (String) request.getAttribute("firstName");
		//String middleName = (String) request.getAttribute("middleName");
		//String lastName = (String) request.getAttribute("lastName");
	%>
	<form method="post" action="roomBooking">
		<h4> Welcome, <%=firstName%></h4>
				<h4>Here are the room(s) you booked</h4>
				<ul>
					<%
						Object obj1 = request.getAttribute("bookedRooms");
						ArrayList<Room> broomList = null;
						if (obj1 instanceof ArrayList) {
							broomList = (ArrayList<Room>) obj1;
						}
						if (broomList != null) {
							for (Room room : broomList) {
								String roomInfo = room.getRoomID() + "---" + room.getRoomStatus();
					%>
					<li><%=roomInfo%></li>
					<%
							}
						}
					%>
				</ul>
				<input type="hidden" name="firstName" value="<%=firstName%>" />
				<h4>Here are the Available Rooms</h4>

				<h4>At Branch </h4><select name = "branchid">
					<%
						Object obj = request.getAttribute("allRooms");
						ArrayList<Room> roomList = null;
						if (obj instanceof ArrayList) {
							roomList = (ArrayList<Room>) obj;
						}
						ArrayList<String> branchList = new ArrayList<String>();
						if (roomList != null) {
							String id = "";
							for (Room room : roomList) {
								if (!id.equals(room.getBranchID())) {
									branchList.add(room.getBranchID());
								}
								id = room.getBranchID();
								
								//String roomInfo = room.getRoomID() + "---" + room.getRoomStatus();
					%>					
							<!-- <option%=room.getBranchID()%/option> -->

					<%
							}
							for (String bid : branchList) {
								%><option value = <%=bid%>><%=bid%></option><%
							}
						}
					%>
				</select>

				<h4>Room </h4><select name = "roomid">
					<%
						Object obj2 = request.getAttribute("allRooms");
						ArrayList<Room> roomList2 = null;
						if (obj instanceof ArrayList) {
							roomList = (ArrayList<Room>) obj2;
						}
						ArrayList<String> branchList2 = new ArrayList<String>();
						if (roomList != null) {
							String id = "";
							for (Room room : roomList) {
								
								
								//String roomInfo = room.getRoomID() + "---" + room.getRoomStatus();
								
									
								
					%>					
							 	<option><%=room.getRoomID()%></option>
								
					<%
								
							}
						}
					%>
				</select>

				<button type="submit" onclick="return confirm('Confirm Booking?');">Book</button>
	</form>


</body>
</html>