package dentalclinic.connection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.sql.Date; 

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
					String employeePwd = rs.getString("EmployeePwd");
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

					employee = new Employee(employeeSIN, userName, employeePwd, branchID, firstName, middleName, 
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
		
		public Employee getUserInfoByEmployeeSIN(String sin){
			getConn();

			Employee employee = new Employee();
			
	        try{
	            ps = db.prepareStatement("SELECT * from dentalclinic.employee "
	            		               + "WHERE EmployeeSIN=?");
	            
	            ps.setString(1, sin);	               
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					String userName = rs.getString("Username");
					String employeePwd = rs.getString("EmployeePwd");
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

					employee = new Employee(sin, userName, employeePwd, branchID, firstName, middleName, 
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

		//Searches and returns a Patient by their SIN
		public Guardian getUserInfoByGuardianSIN(String guardianSIN){
			getConn();
			
			Guardian guardian = new Guardian();
			
	        try{
	            ps = db.prepareStatement("SELECT * from dentalclinic.guardian "
	            					   + "WHERE guardianSIN=?");
	            ps.setString(1, guardianSIN);	
	                           
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					String userName = rs.getString("userName");
					String firstName = rs.getString("firstName");
					String middleName = rs.getString("middleName");
					String lastName = rs.getString("lastName");
					String dateofBirth = rs.getString("dateofBirth");
					String age = rs.getString("age");
					String gender = rs.getString("gender");
					String patientEmail = rs.getString("guardianEmail");
					String patientPhoneNumber = rs.getString("guardianPhoneNumber");
					String address = rs.getString("address");
					
					guardian = new Guardian(guardianSIN, userName, firstName, middleName,
							 					  lastName, dateofBirth, age, gender,
												  patientEmail, patientPhoneNumber,
												  address);
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return guardian;       
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
					String guardianSIN = rs.getString("guardianSIN");
					
					patient = new Patient(patientSIN, userName, firstName, middleName,
							 		      lastName, dateofBirth, age, gender,
										  patientEmail, patientPhoneNumber,
										  guardianSIN, address);
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
					String guardianSIN = rs.getString("guardianSIN");
					
					patient = new Patient(patientSIN, userName, firstName, middleName,
							 					  lastName, dateofBirth, age, gender,
												  patientEmail, patientPhoneNumber,
												  address, guardianSIN);
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return patient;       
	    }

		//Searches and returns a PatientRecord by their SIN
		public PatientRecord getPatientRecordByPatientSIN(String patientSIN){
			getConn();
			
			PatientRecord patientRecord = new PatientRecord();
			
	        try{
	            ps = db.prepareStatement("SELECT * from dentalclinic.patientrecord "
	            					   + "WHERE treatmentID IN " 
	            		               + 	"(SELECT treatmentID from dentalclinic.treatment "
	            					   + 	"WHERE patientSIN=?)");
	            ps.setString(1, patientSIN);	
	                           
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					String treatmentID = rs.getString("treatmentID");
					String treatmentDetails = rs.getString("treatmentDetails");
					
					patientRecord = new PatientRecord(treatmentID, treatmentDetails);
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return patientRecord;       
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

			ArrayList<String> usernames = getAllUsernamesByEntity("patient");
			if (usernames.size() != 0) {
				for (int i = 0 ; i < usernames.size(); i++) {
					if (patient.getUserName().equals(usernames.get(i))) {
			            return false;
					}
				}
			}

			getConn();//must be below above statement since getAllUsernamesByEntity
			          //closed the connection

	        try{

				ps = db.prepareStatement("INSERT into dentalclinic.patient "
									   + "values(?, ?, crypt(?, gen_salt('bf')), ?, "
									   + "?, ?, ?, ?, ?, ?, ?, ?, ?)");
				
	            ps.setString(1, patient.getPatientSIN());	
	            ps.setString(2, patient.getUserName());	
	            ps.setString(3, pwd);	
	            ps.setString(4, patient.getFirstName());	
	            ps.setString(5, patient.getMiddleName());	
	            ps.setString(6, patient.getLastName());	

	            String str = patient.getDateOfBirth();  
	            Date date = Date.valueOf(str);
	            ps.setDate(7, date);
	            
	            ps.setInt(8, Integer.parseInt(patient.getAge()));	
	            ps.setString(9, patient.getGender());	
	            ps.setString(10, patient.getPatientEmail());	
	            ps.setString(11, patient.getPatientPhoneNumber());	
	            ps.setString(12, patient.getAddress());	
	            ps.setString(13, patient.getGuardianSIN());	
	            
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
		
		//For receptionist only; inserts a new patient
		public boolean insertNewGuardian(Guardian guardian, String pwd){

			ArrayList<String> usernames = getAllUsernamesByEntity("guardian");
			if (usernames.size() != 0) {
				for (int i = 0 ; i < usernames.size(); i++) {
					if (guardian.getUserName().equals(usernames.get(i))) {
			            return false;
					}
				}
			}

			getConn();//must be below above statement since getAllUsernamesByEntity
			          //closed the connection

	        try{

				ps = db.prepareStatement("INSERT into dentalclinic.guardian "
									   + "values(?, ?, crypt(?, gen_salt('bf')), ?, "
									   + "?, ?, ?, ?, ?, ?, ?, ?)");
				
	            ps.setString(1, guardian.getGuardianSIN());	
	            ps.setString(2, guardian.getUserName());	
	            ps.setString(3, pwd);	
	            ps.setString(4, guardian.getFirstName());	
	            ps.setString(5, guardian.getMiddleName());	
	            ps.setString(6, guardian.getLastName());	

	            String str = guardian.getDateOfBirth();  
	            Date date = Date.valueOf(str);
	            ps.setDate(7, date);
	            
	            ps.setInt(8, Integer.parseInt(guardian.getAge()));	
	            ps.setString(9, guardian.getGender());	
	            ps.setString(10, guardian.getGuardianEmail());	
	            ps.setString(11, guardian.getGuardianPhoneNumber());	
	            ps.setString(12, guardian.getAddress());	
	            
	            ps.executeUpdate();
	            
	            System.out.println("Inserted new guardian");
	            
	            return true;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return false;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		public boolean insertNewEmployee(Employee employee, String pwd){

			ArrayList<String> usernames = getAllUsernamesByEntity("employee");
			if (usernames.size() != 0) {
				for (int i = 0 ; i < usernames.size(); i++) {
					if (employee.getUserName().equals(usernames.get(i))) {
			            return false;
					}
				}
			}

			getConn();//must be below above statement since getAllUsernamesByEntity
			          //closed the connection

	        try{

				ps = db.prepareStatement("INSERT into dentalclinic.employee "
									   + "values(?, ?, ?, crypt(?, gen_salt('bf')), ?, "
									   + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				
	            ps.setString(1, employee.getEmployeeSIN());	
	            ps.setInt(2, Integer.parseInt(employee.getBranchID()));	
	            ps.setString(3, employee.getUserName());	
	            ps.setString(4, pwd);
	            ps.setString(5, employee.getRole());	
	            ps.setString(6, employee.getEmployeeType());	
	            ps.setBigDecimal(7, new BigDecimal(employee.getSalary()));
	            ps.setString(8, employee.getFirstName());	
	            ps.setString(9, employee.getMiddleName());	
	            ps.setString(10, employee.getLastName());


	            String str = employee.getDateofBirth();  
	            Date date = Date.valueOf(str);
	            ps.setDate(11, date);
	            
	            ps.setInt(12, Integer.parseInt(employee.getAge()));	
	            ps.setString(13, employee.getGender());	
	            ps.setString(14, employee.getEmployeeEmail());	
	            ps.setString(15, employee.getEmployeePhoneNumber());	
	            ps.setString(16, employee.getAddress());	
	            
	            ps.executeUpdate();
	            
	            System.out.println("Inserted new employee");
	            
	            return true;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return false;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		//For receptionist only; inserts a new patient
		public boolean updatePatientInfo(Patient newPatientInfo, String PatientSIN){
			
			if (!getUserInfoByPatientSIN(PatientSIN).getUserName()
					.equals(newPatientInfo.getUserName())) {
				ArrayList<String> usernames = getAllUsernamesByEntity("patient");
				for (int i = 0 ; i < usernames.size(); i++) {
					if (newPatientInfo.getUserName().equals(usernames.get(i))) {
						System.out.println("Username "+newPatientInfo.getUserName()+" already exists!");
			            return false;
					}
				}
			}
			
			getConn();//must be below above statement since getAllUsernamesByEntity
	          		  //closed the connection

	        try{

				ps = db.prepareStatement("UPDATE dentalclinic.patient "
									   + "SET username=?, firstname=?, middlename=?, lastname=?, "
									   +     "dateofbirth=?, age=?, gender=?, patientemail=?, "
									   +     "patientphonenumber=?, address=?, guardianSIN=? "
						               + "WHERE patientsin=?");
					
	            ps.setString(1, newPatientInfo.getUserName());	
	            ps.setString(2, newPatientInfo.getFirstName());	
	            ps.setString(3, newPatientInfo.getMiddleName());	
	            ps.setString(4, newPatientInfo.getLastName());
	            
	            String str = newPatientInfo.getDateOfBirth();  
	            Date date = Date.valueOf(str);
	            ps.setDate(5, date);
	            
	            ps.setInt(6, Integer.parseInt(newPatientInfo.getAge()));	
	            ps.setString(7, newPatientInfo.getGender());	
	            ps.setString(8, newPatientInfo.getPatientEmail());	
	            ps.setString(9, newPatientInfo.getPatientPhoneNumber());	
	            ps.setString(10, newPatientInfo.getAddress());	
	            
	            String guardianSIN = newPatientInfo.getGuardianSIN();
	            if (guardianSIN.isEmpty()) {
	            	ps.setNull(11, Types.VARCHAR);
	            } else {
		            ps.setString(11, guardianSIN);	
	            }
	            
	            ps.setString(12, PatientSIN);	
	            
	            ps.executeUpdate();

	            System.out.println(ps.toString());
	            
	            return true;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return false;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		//For receptionist only; inserts a new patient
		public boolean updateGuardianInfo(Guardian newGuardianInfo, String GuardianSIN){
			
			if (!getUserInfoByGuardianSIN(GuardianSIN).getUserName()
					.equals(newGuardianInfo.getUserName())) {
				ArrayList<String> usernames = getAllUsernamesByEntity("guardian");
				for (int i = 0 ; i < usernames.size(); i++) {
					if (newGuardianInfo.getUserName().equals(usernames.get(i))) {
						System.out.println("Username "+newGuardianInfo.getUserName()+" already exists!");
			            return false;
					}
				}
			}
			
			getConn();//must be below above statement since getAllUsernamesByEntity
	          		  //closed the connection

	        try{

				ps = db.prepareStatement("UPDATE dentalclinic.guardian "
									   + "SET username=?, firstname=?, middlename=?, lastname=?, "
									   +     "dateofbirth=?, age=?, gender=?, guardianemail=?, "
									   +     "guardianphonenumber=?, address=? "
						               + "WHERE guardiansin=?");
					
	            ps.setString(1, newGuardianInfo.getUserName());	
	            ps.setString(2, newGuardianInfo.getFirstName());	
	            ps.setString(3, newGuardianInfo.getMiddleName());	
	            ps.setString(4, newGuardianInfo.getLastName());
	            
	            String str = newGuardianInfo.getDateOfBirth();  
	            Date date = Date.valueOf(str);
	            ps.setDate(5, date);
	            
	            ps.setInt(6, Integer.parseInt(newGuardianInfo.getAge()));	
	            ps.setString(7, newGuardianInfo.getGender());	
	            ps.setString(8, newGuardianInfo.getGuardianEmail());	
	            ps.setString(9, newGuardianInfo.getGuardianPhoneNumber());	
	            ps.setString(10, newGuardianInfo.getAddress());	
	            
	            ps.setString(11, GuardianSIN);	
	            
	            ps.executeUpdate();

	            System.out.println(ps.toString());
	            
	            return true;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return false;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		//For manager only; updates an employee
		public boolean updateEmployeeInfo(Employee newEmployeeInfo, String employeeSIN){
			
			if (!getUserInfoByEmployeeSIN(employeeSIN).getUserName()
					.equals(newEmployeeInfo.getUserName())) {
				ArrayList<String> usernames = getAllUsernamesByEntity("employee");
				for (int i = 0 ; i < usernames.size(); i++) {
					if (newEmployeeInfo.getUserName().equals(usernames.get(i))) {
						System.out.println("Username "+newEmployeeInfo.getUserName()+" already exists!");
			            return false;
					}
				}
			}
			
			getConn();//must be below above statement since getAllUsernamesByEntity
	          		  //closed the connection

	        try{

				ps = db.prepareStatement("UPDATE dentalclinic.employee "
									   + "SET employeesin=?, BranchID=?, username=?, role=?, employeeType=?, salary=? "
									   + "firstname=?, middlename=?, lastname=?, "
									   + "dateofbirth=?, age=?, gender=?, EmployeeEmail=?, "
									   + "EmployeePhoneNumber=?, address=? "
									   + "WHERE employeeSIN=?");
				
				System.out.println(employeeSIN);
				ps.setString(1, employeeSIN);
				ps.setInt(2, Integer.parseInt(newEmployeeInfo.getBranchID()));	
	            ps.setString(3, newEmployeeInfo.getUserName());
	            ps.setString(4, newEmployeeInfo.getRole());
	            ps.setString(5, newEmployeeInfo.getEmployeeType());
	            ps.setBigDecimal(6, new BigDecimal(newEmployeeInfo.getSalary()));	
	            ps.setString(7, newEmployeeInfo.getFirstName());	
	            ps.setString(8, newEmployeeInfo.getMiddleName());	
	            ps.setString(9, newEmployeeInfo.getLastName());
	            
	            String str = newEmployeeInfo.getDateofBirth();  
	            Date date = Date.valueOf(str);
	            ps.setDate(10, date);
	            
	            ps.setInt(10, Integer.parseInt(newEmployeeInfo.getAge()));	
	            ps.setString(12, newEmployeeInfo.getGender());	
	            ps.setString(13, newEmployeeInfo.getEmployeeEmail());	
	            ps.setString(14, newEmployeeInfo.getEmployeePhoneNumber());	
	            ps.setString(15, newEmployeeInfo.getAddress());

	            
	            ps.setString(16, employeeSIN);	
	            
	            ps.executeUpdate();

	            System.out.println(ps.toString());
	            
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
						               + "GROUP BY appointmentdate, appointmentstarttime, roomid, branchid");
	            ps.setString(1, employeeSIN);	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					String appointmentDate = rs.getString("appointmentDate");
					String startTime = rs.getString("appointmentstartTime");
					String roomID = rs.getString("roomID");
					String branchID = rs.getString("branchID");
					String[] employeeSINList = (String[]) rs.getArray("employeeSINList").getArray();
					String invoiceID = rs.getString("invoiceID");
					String endTime = rs.getString("appointmentendTime");
					String appointmentType = rs.getString("appointmentType");
					String status = rs.getString("status");
					Appointment appointment = new Appointment(appointmentDate, startTime, roomID,
															  branchID, employeeSINList, invoiceID,
															  endTime, appointmentType, status);
					appointments.add(appointment);
				 System.out.println(Arrays.toString(appointment.getEmployeeSINList()));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return appointments;
			
		}
		
		//Returns an ArrayList containing all Branches
		public ArrayList<Branch> getAllBranches(){
			
			getConn();
			
			ArrayList<Branch> branches = new ArrayList<Branch>();
			
			try {
				ps = db.prepareStatement("SELECT branchid, city, province, managerid "
									   + "FROM dentalclinic.branch "
									   + "ORDER BY province, city");
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					String branchID = rs.getString("branchID");
					String city = rs.getString("city");
					String province = rs.getString("province");
					String managerID = rs.getString("managerID");
					Branch branch = new Branch(branchID, city,
											   province, managerID);
					branches.add(branch);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return branches;
			
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
		public ArrayList<Employee> getDentistsByBranchID(String branchID) {
			getConn();
			
			ArrayList<Employee> dentists = new ArrayList<Employee>();
			
			try { 
				Integer.parseInt(branchID); 
				try {

					Employee dentist = new Employee();
					
					ps = db.prepareStatement("SELECT * from dentalclinic.employee "
							               + "WHERE BranchId=? "
							               + "AND role='dentist'");
					ps.setInt(1, Integer.valueOf(branchID));	
					
					rs = ps.executeQuery();
					
					while(rs.next()){
						String employeeSIN = rs.getString("employeeSIN");
						String userName = rs.getString("userName");
						String employeePwd = rs.getString("EmployeePwd");
						//4th col: already have branchID
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

						dentist = new Employee(employeeSIN, userName, employeePwd, branchID, firstName, middleName, 
						        lastName, role, employeeType, salary, dateofBirth, age, gender,
								employeeEmail, employeePhoneNumber, address); 
						
						dentists.add(dentist);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Branch #"+branchID+" might not exist."); 
					e.printStackTrace();
				} finally {
		        	closeDB();
		        }
			}  
			catch (NumberFormatException e) { 
				System.out.println(branchID + " is not a valid integer."); 
			}
			
			return dentists;
		}
		
		public ArrayList<Employee> getEmployeesByBranchID(String branchID) {
			getConn();
			
			ArrayList<Employee> employees = new ArrayList<Employee>();
			
			try { 
				Integer.parseInt(branchID); 
				try {

					Employee employee = new Employee();
					
					ps = db.prepareStatement("SELECT * from dentalclinic.employee "
							               + "WHERE BranchId=? ");
					ps.setInt(1, Integer.valueOf(branchID));	
					
					rs = ps.executeQuery();
					
					while(rs.next()){
						String employeeSIN = rs.getString("employeeSIN");
						String userName = rs.getString("userName");
						String employeePwd = rs.getString("EmployeePwd");
						//4th col: already have branchID
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

						employee = new Employee(employeeSIN, userName, employeePwd, branchID, firstName, middleName, 
						        lastName, role, employeeType, salary, dateofBirth, age, gender,
								employeeEmail, employeePhoneNumber, address); 
						
						employees.add(employee);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Branch #"+branchID+" might not exist."); 
					e.printStackTrace();
				} finally {
		        	closeDB();
		        }
			}  
			catch (NumberFormatException e) { 
				System.out.println(branchID + " is not a valid integer."); 
			}
			
			return employees;
		}
		
		/*
		public static void main(String []args) {
			PostgreSqlConn con = new PostgreSqlConn();
			con.getConn();
				
		}*/

		
	}

