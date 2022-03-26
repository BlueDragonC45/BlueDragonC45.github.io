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

	    //Attempts to establish a connection to the database
		public void getConn(){
			
			try {
				
				Class.forName("org.postgresql.Driver"); 
																					//DB name
				db = DriverManager.getConnection("jdbc:postgresql://192.168.0.6:5432/postgres",
						//username   password
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
		

		//Returns true if the pwd entered matches the encrypted pwd in the DB
		public boolean isCorrectPwd(String entity, String userName, String Pwd){
			
			getConn();

			boolean isCorrect = false;
			
	        try{
	        	
	        	if (entity.equals("employee")) {
		            ps = db.prepareStatement("SELECT * from dentalclinic.employee "
			                +"WHERE username=? "
							+"AND employeepwd = crypt(?, employeepwd)");	
	        	} else if (entity.equals("patient")) {
		            ps = db.prepareStatement("SELECT * from dentalclinic.patient "
			                +"WHERE username=? "
							+"AND patientpwd = crypt(?, patientpwd)");	
	        	} else {
	        		return isCorrect;//false
	        	}

	            System.out.println(ps.toString());   
	            
	            ps.setString(1, userName);	  
	            ps.setString(2, Pwd);
	            
	            rs = ps.executeQuery();

	            System.out.println(entity);
	            System.out.println(userName);
	            System.out.println(Pwd);
	            System.out.println(ps.toString());   

				String foundUser = "";
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
		
		//Searches an employee by their username (unique)
		public Employee getUserInfoByEmployeeUsername(String userName){
			getConn();

			Employee employee = new Employee();
			
	        try{
	            ps = db.prepareStatement("SELECT * from dentalclinic.employee "
	            		               + "WHERE username=?");
	            
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

		//Searches an patient by their username (unique)
		public Patient getUserInfoByPatientUsername(String userName){
			getConn();
			
			Patient patient = new Patient();
			
	        try{
	            ps = db.prepareStatement("SELECT * from dentalclinic.patient "
	            		               + "WHERE username=?");
	            
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

		//Searches and returns a Patient by their SIN
		public Patient getUserInfoByPatientSIN(String patientSIN){
			getConn();
			
			Patient patient = new Patient();
			
	        try{
	            ps = db.prepareStatement("SELECT * from dentalclinic.patient "
	            					   + "WHERE patientSIN=?");
	            ps.setString(1, patientSIN);	
	                           
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
		
		//Returns all usernames stored in either Patient or Employee
		public ArrayList<String> getAllUsernamesByEntity(String entity){
			getConn();

			ArrayList<String> usernames = new ArrayList<String>();
			
	        try{

	        	if (entity.equals("employee")) {
	        		ps = db.prepareStatement("SELECT * from dentalclinic.employee");	
	        	} else if (entity.equals("patient")) {
	        		ps = db.prepareStatement("SELECT * from dentalclinic.patient");
	        	} else {
	        		return usernames;//empty
	        	}
	        	
	            
	            ps.setString(1, entity);	
	                          
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					usernames.add(rs.getString("username"));
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return usernames;       
	    }
		
		//For receptionist only; inserts a new patient
		public boolean insertNewPatient(Patient patient, String pwd){
			getConn();

			ArrayList<String> usernames = getAllUsernamesByEntity("patient");
			for (int i = 0 ; i < usernames.size(); i++) {
				if (patient.getUserName().equals(usernames.get(i))) {
		            return false;
				}
			}

	        try{

				ps = db.prepareStatement("INSERT into dentalclinic.patient "
									   + "values(?, ?, crypt(?, gen_salt('bf')), ?, "
									   + "?, ?, ?, ?, ?, ?, ?,?)");
				
	            ps.setString(1, patient.getPatientSIN());	
	            ps.setString(2, patient.getUserName());	
	            ps.setString(3, pwd);	
	            ps.setString(4, patient.getFirstName());	
	            ps.setString(5, patient.getMiddleName());	
	            ps.setString(7, patient.getLastName());	
	            ps.setString(8, patient.getDateofBirth());	
	            ps.setString(9, patient.getAge());	
	            ps.setString(10, patient.getGender());	
	            ps.setString(11, patient.getPatientEmail());	
	            ps.setString(12, patient.getPatientPhoneNumber());	
	            ps.setString(13, patient.getAddress());	
	            
	            ps.executeUpdate();
	            
	            System.out.println("Inserted new patient");
	            
	            return true;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return false;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		//Returns appointments that involve a certain employee using their SIN
		public ArrayList<Appointment> getAppointmentsByEmployeeSIN(String employeeSIN){
			
			getConn();
			
			ArrayList<Appointment> appointments = new ArrayList<Appointment>();
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.appointment "
						               + "WHERE ? = ANY(employeesinlist) "
						               + "GROUP BY appointmentdate, appointmentstarttime");
	            ps.setString(1, employeeSIN);	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					String appointmentDate = rs.getString("appointmentDate");
					String appointmentType = rs.getString("appointmentType");
					String startTime = rs.getString("appointmentstartTime");
					String endTime = rs.getString("appointmentendTime");
					String roomID = rs.getString("roomID");
					String status = rs.getString("status");
					Appointment appointment = new Appointment(appointmentDate, appointmentType,
														      startTime, endTime, roomID, status);
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

		//Returns appointments that involve a certain patient using their SIN
//		public ArrayList<Appointment> getAppointmentsByPatientSIN(String patientSIN){
//			
//			getConn();
//			
//			ArrayList<Appointment> appointments = new ArrayList<Appointment>();
//			
//			try {
//				ps = db.prepareStatement("select * from dentalclinic.appointment where '"+patientSIN+"' ??? GROUP BY appointmentdate, starttime");
//				rs = ps.executeQuery();
//				while(rs.next()){
//					String appointmentDate = rs.getString("appointmentDate");
//					String appointmentType = rs.getString("appointmentType");
//					String startTime = rs.getString("startTime");
//					String endTime = rs.getString("endTime");
//					String roomID = rs.getString("roomID");
//					String status = rs.getString("status");
//					Appointment appointment = new Appointment(appointmentDate, appointmentType, startTime,
//															  endTime, roomID, status);
//					appointments.add(appointment);
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} finally {
//	        	closeDB();
//	        }
//						
//			return appointments;
//			
//		}
		
		//Returns the branch by the location and city
		public String getBranchByLocation(String province, String city) {
			getConn();
			
			String branchId = "";
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.branches "
						               + "WHERE dentalclinic.Branch.Province=? "
						               + "AND dentalclinic.Branch.City=?");
				
				ps.setString(1, province);
				ps.setString(2, city);	
				
				rs = ps.executeQuery();
				
				while(rs.next()){
					branchId = rs.getString(branchId);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
			return branchId;
		}
		
		//Returns all dentists in a certain branch
		public String[] getDentistsByBranchId(String branchId) {
			getConn();
			
			String[] dentists = new String[]{};
			int i = 0;
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.employee "
						               + "WHERE dentalclinic.employee.BranchId=? "
						               + "AND dentalclinic.employee.role='dentist'");
				ps.setString(1, branchId);	
				
				rs = ps.executeQuery();
				
				while(rs.next()){
					String fName = rs.getString("firstName");
					String lName = rs.getString("lastName");
					dentists[i++] = String.format("%s, %s", fName, lName);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
			return dentists;
		}
		
		/*
		public static void main(String []args) {
			PostgreSqlConn con = new PostgreSqlConn();
			con.getConn();
				
		}*/

		
	}

