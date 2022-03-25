package dentalclinic.connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalclinic.entities.*; 


public class  PostgreSqlConn{
	
		Connection db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Statement st = null;
	    String sql;


		public void getConn(){
			
			try {
				
				Class.forName("org.postgresql.Driver"); 
								
				db = DriverManager.getConnection("jdbc:postgresql://192.168.0.4:5432/postgres",
						"postgres", "password");
															
			}catch(Exception e) {
				System.out.print("Error: Could not establish connection with the database.");
			}
						
		}
		
		public void closeDB() {
				try {
					if(rs != null){
						rs.close();
					}
					if(ps!=null){
						ps.close();
					}
					if(st!=null){
						st.close();
					}
					if(db!=null){
						db.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		
		public ArrayList<String> getAllUsernamesByEntity(String entity){
			getConn();

			ArrayList<String> usernames = new ArrayList<String>();
			
	        try{
	            ps = db.prepareStatement("select * from dentalclinic."+entity);
	                          
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					usernames.add(rs.getString(3));//Username at index 3
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return usernames;       
	    }

		//@entity can be employee or patient; case-insensitive
		public boolean isCorrectPwd(String entity, String userName, String Pwd){
			getConn();

			String foundUser = "";
			boolean isCorrect = false;
			
	        try{
	            ps = db.prepareStatement("select * from dentalclinic."+entity+
	            		                " where username=?"+
	            						" and "+entity+"pwd = crypt(?, "+entity+"pwd)");
	            
	            ps.setString(1, userName);	  
	            ps.setString(2, Pwd);	             
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					foundUser = rs.getString("username");
					if (!foundUser.equals(""))
						isCorrect = true;
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return isCorrect;       
	    }
		
		public Employee getUserInfoByEmployeeUsername(String userName){
			getConn();

			Employee employee = new Employee();
			
	        try{
	            ps = db.prepareStatement("select * from dentalclinic.employee where username=?");
	            
	            ps.setString(1, userName);	               
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					String employeeSIN = rs.getString("employeeSIN");
					//userName already have
					String branchID = rs.getString("branchID");
					String firstName = rs.getString("firstName");
					String middleName = rs.getString("middleName");
					String lastName = rs.getString("lastName");
					String role = rs.getString("role");
					String employeeType = rs.getString("employeeType");
					String salary = rs.getString("salary");
					String dateofBirth = rs.getString("dateofBirth");
					String age = rs.getString("age");
					String gender = rs.getString("gender");
					String employeeEmail = rs.getString("employeeEmail");
					String employeePhoneNumber = rs.getString("employeePhoneNumber");
					String address = rs.getString("address");

					employee = new Employee(employeeSIN, userName, branchID, firstName, middleName, 
					        lastName, role, employeeType, salary, dateofBirth, age, gender,
							employeeEmail, employeePhoneNumber, address); 
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return employee;       
	    }
		
		public Patient getUserInfoByPatientUsername(String userName){
			getConn();
			
			Patient patient = new Patient();
			
	        try{
	            ps = db.prepareStatement("select * from dentalclinic.patient where username=?");
	            
	            ps.setString(1, userName);	               
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					String patientSIN = rs.getString("patientSIN");
					//userName already have
					String firstName = rs.getString("firstName");
					String middleName = rs.getString("middleName");
					String lastName = rs.getString("lastName");
					String dateofBirth = rs.getString("dateofBirth");
					String age = rs.getString("age");
					String gender = rs.getString("gender");
					String patientEmail = rs.getString("patientEmail");
					String patientPhoneNumber = rs.getString("patientPhoneNumber");
					String address = rs.getString("address");
					
					patient = new Patient(patientSIN, userName, firstName, middleName,
							 					  lastName, dateofBirth, age, gender,
												  patientEmail, patientPhoneNumber, address);
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return patient;       
	    }

		public Patient getUserInfoByPatientSIN(String patientSIN){
			getConn();
			
			Patient patient = new Patient();
			
	        try{
	            ps = db.prepareStatement("select * from dentalclinic.patient where patientSIN='"+patientSIN+"'");
	                           
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					String userName = rs.getString("userName");
					String firstName = rs.getString("firstName");
					String middleName = rs.getString("middleName");
					String lastName = rs.getString("lastName");
					String dateofBirth = rs.getString("dateofBirth");
					String age = rs.getString("age");
					String gender = rs.getString("gender");
					String patientEmail = rs.getString("patientEmail");
					String patientPhoneNumber = rs.getString("patientPhoneNumber");
					String address = rs.getString("address");
					
					patient = new Patient(patientSIN, userName, firstName, middleName,
							 					  lastName, dateofBirth, age, gender,
												  patientEmail, patientPhoneNumber, address);
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return patient;       
	    }
		
		public boolean insertNewPatient(String[] data){
			getConn();

			//todo: check username not used
	        try{
	        	st = db.createStatement();
	        	sql = "insert into dentalclinic.patient values('"+data[0]+"','"+data[1]+"',crypt('"+data[2]+"', gen_salt('bf')),'"+data[3]
	        			+"','"+data[4]+"','"+data[5]+"','"+data[6]+"',"+data[7]+",'"+data[8]+"','"+data[9]+"','"+data[10]+"','"+data[11]+"')";
	        	
	        	System.out.print(sql);
	            
	            st.executeUpdate(sql);
	            
	            return true;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return false;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		public ArrayList<Appointment> getAppointmentsByEmployeeSIN(String employeeSIN){
			
			getConn();
			
			ArrayList<Appointment> appointments = new ArrayList<Appointment>();
			
			try {
				ps = db.prepareStatement("select * from dentalclinic.appointment where '"+employeeSIN+"' = ANY(employeesinlist) GROUP BY appointmentdate, starttime");
				rs = ps.executeQuery();
				while(rs.next()){
					String appointmentDate = rs.getString("appointmentDate");
					String appointmentType = rs.getString("appointmentType");
					String startTime = rs.getString("startTime");
					String endTime = rs.getString("endTime");
					String roomID = rs.getString("roomID");
					String status = rs.getString("status");
					Appointment appointment = new Appointment(appointmentDate, appointmentType, startTime,
															  endTime, roomID, status);
					appointments.add(appointment);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return appointments;
			
		}
		
		public  ArrayList<Room> getAllAvailRooms(){
			
			getConn();
			
			ArrayList<Room> Rooms = new ArrayList<Room>();
			
			try {
				ps = db.prepareStatement("select * from dentalclinic.room where roomstatus='available'");
				rs = ps.executeQuery();
				while(rs.next()){
					String roomid = rs.getString("roomid");
					String branchid = rs.getString("branchid");
					String roomstatus = rs.getString("roomstatus");
					Room room = new Room(roomid, branchid, roomstatus);
					Rooms.add(room);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return Rooms;
			
		}
		
		public  ArrayList<Room> getBookedRooms(String patientSIN){
			
			getConn();
			
			ArrayList<Room> Rooms = new ArrayList<Room>();
			
			try {
				ps = db.prepareStatement("select * from dentalclinic.room where patientsin='"+patientSIN+"'");
				rs = ps.executeQuery();
				while(rs.next()){
					String roomid = rs.getString("roomid");
					String branchid = rs.getString("branchid");
					String roomstatus = rs.getString("roomstatus");
					Room room = new Room(roomid, branchid, roomstatus);
					Rooms.add(room);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return Rooms;
			
		}
		
		public String bookRoom(String username, String roomid, String branchid){
			getConn();
			String patientSIN="";
			
	        try{
	        	
	        	ps = db.prepareStatement("select patientsin from dentalclinic.patient where username='"+username+"'");
				rs = ps.executeQuery();
				
				while(rs.next()){
					patientSIN = rs.getString("patientsin");
				}
				
				
	        	st = db.createStatement();
	        	sql = "update dentalclinic.room set patientsin='"+patientSIN+"', roomstatus='booked' where roomid='"+roomid+"' and branchid='"+branchid+"'";
	            st.executeUpdate(sql);
	            
	            
	            return patientSIN;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return "";	 
	        }finally {
	        	closeDB();
	        }
			      
	    }
		
		
		
		
		
		public static void main(String []args) {
			PostgreSqlConn con = new PostgreSqlConn();
			con.getConn();
				
		}

		
	}

