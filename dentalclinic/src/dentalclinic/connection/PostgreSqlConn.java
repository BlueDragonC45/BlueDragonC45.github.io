package dentalclinic.connection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import java.sql.Date; 
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import dentalclinic.entities.*;
import lombok.Getter;
import lombok.Setter; 

import java.time.*; 


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
				db = DriverManager.getConnection("jdbc:postgresql://192.168.0.8:5432/postgres",
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
	        	} else if (entity.equals("guardian")) {
		            ps = db.prepareStatement("SELECT * from dentalclinic.guardian "
			                +"WHERE username=? "
							+"AND guardianpwd = crypt(?, guardianpwd)");	
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
	            
	            System.out.println(ps.toString());
	            
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
												  guardianSIN, address);
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return patient;       
	    }

		//Searches an patient by their username (unique)
		public Guardian getUserInfoByGuardianUsername(String userName){
			getConn();
			
			Guardian guardian = new Guardian();
			
	        try{
	            ps = db.prepareStatement("SELECT * from dentalclinic.guardian "
	            		               + "WHERE username=?");
	            
	            ps.setString(1, userName);	               
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					String guardianSIN = rs.getString("guardianSIN");
					//userName already have
					String firstName = rs.getString("firstName");
					String middleName = rs.getString("middleName");
					String lastName = rs.getString("lastName");
					String dateofBirth = rs.getString("dateofBirth");
					String age = rs.getString("age");
					String gender = rs.getString("gender");
					String guardianEmail = rs.getString("guardianEmail");
					String guardianPhoneNumber = rs.getString("guardianPhoneNumber");
					String address = rs.getString("address");
					
					guardian = new Guardian(guardianSIN, userName, firstName, middleName,
							 		      lastName, dateofBirth, age, gender,
							 		     guardianEmail, guardianPhoneNumber, address);
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return guardian;       
	    }

		//Searches and returns an array list of minors that a
		//figure guardians over
		public ArrayList<Patient> getMinors(String guardianSIN){
			getConn();
			
			ArrayList<Patient> minors = new ArrayList<Patient>();
			
	        try{
	            ps = db.prepareStatement("SELECT * from dentalclinic.patient "
	            					   + "WHERE guardianSIN=?");
	            ps.setString(1, guardianSIN);	
	                           
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					String patientSIN = rs.getString("patientSIN");
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
					//col12: guardianSIN already have
					
					Patient minor = new Patient(patientSIN, userName, firstName, middleName,
							 					  lastName, dateofBirth, age, gender,
												  patientEmail, patientPhoneNumber,
												  guardianSIN, address);
					minors.add(minor);				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return minors;       
	    }

		//Searches and returns a list of PatientRecord by patient SIN
		public PatientRecord getPatientRecordByKey(String patientSIN, String appointmentID){
			
			getConn();
			
			PatientRecord patientRecord = new PatientRecord();
			
	        try{
	            ps = db.prepareStatement("SELECT * from dentalclinic.patientrecord "
	            					   + "WHERE patientSIN=?"
	            					   + "AND appointmentID=?");
	            ps.setString(1, patientSIN);	
	            ps.setInt(2, Integer.parseInt(appointmentID));	
	                           
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					//col1: patientSIN already have
					//col2: appointmentID already have
					String[] teethInvolved = (String[]) rs.getArray("teethInvolved").getArray();
					String treatmentDetails = rs.getString("treatmentDetails");
					
					patientRecord = new PatientRecord(patientSIN, appointmentID, teethInvolved, treatmentDetails);
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return patientRecord;       
	    }

		//Searches and returns a list of PatientRecord by patient SIN
		public ArrayList<PatientRecord> getPatientRecordsByPatientSIN(String patientSIN){
			
			getConn();
			
			ArrayList<PatientRecord> patientRecords = new ArrayList<PatientRecord>();
			PatientRecord patientRecord = new PatientRecord();
			
	        try{
	            ps = db.prepareStatement("SELECT * from dentalclinic.patientrecord "
	            					   + "WHERE patientSIN=? "
	            					   + "ORDER BY appointmentID");
	            ps.setString(1, patientSIN);	
	                           
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					//col1: patientSIN already have
					String appointmentID = rs.getString("appointmentID");
					String[] teethInvolved = (String[]) rs.getArray("teethInvolved").getArray();
					String treatmentDetails = rs.getString("treatmentDetails");
					
					patientRecord = new PatientRecord(patientSIN, appointmentID, teethInvolved, treatmentDetails);
					patientRecords.add(patientRecord);
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return patientRecords;       
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
		public int insertPatient(Patient patient, String pwd){

			String patientSIN = patient.getPatientSIN();
			if (getUserInfoByPatientSIN(patientSIN).getAge() != null) {
				System.out.println("Patient with SIN: "+patientSIN+
						          " already exists!");
				return 1;
			}
			
			String patientUsername = patient.getUserName();
			ArrayList<String> usernames = getAllUsernamesByEntity("patient");
			if (usernames.size() != 0) {
				for (int i = 0 ; i < usernames.size(); i++) {
					if (patientUsername.equals(usernames.get(i))) {
						System.out.println("Username "+patientUsername+
								          " already in use!");
			            return 2;
					}
				}
			}
			
			String guardianSIN = patient.getGuardianSIN();
			if (!guardianSIN.isEmpty()) {
				if (getUserInfoByGuardianSIN(guardianSIN).getAge() == null) {
					System.out.println("Guardian with SIN: "+guardianSIN+
							          " does not exist!");
					return 3;
				}
			}

			getConn();//must be below above statement since getAllUsernamesByEntity
			          //closed the connection

	        try{

				ps = db.prepareStatement("INSERT into dentalclinic.patient "
									   + "values(?, ?, crypt(?, gen_salt('bf')), ?, "
									   + "?, ?, ?, ?, ?, ?, ?, ?, ?)");
				
	            ps.setString(1, patientSIN);//set right below method def	
	            ps.setString(2, patientUsername);//set when checking usernames
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
	            
	            return 0;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return 4;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		//For receptionist only; inserts a new patient
		public boolean updatePatientInfo(Patient newPatientInfo, String PatientSIN){
			
			//if username is not equal to previous, check list of usernames
			//if unique, continue
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
			
			String guardianSIN = newPatientInfo.getGuardianSIN();
			if (!guardianSIN.isEmpty()) {
				if (getUserInfoByGuardianSIN(guardianSIN).getAge() == null) {
					System.out.println("Guardian with SIN: "+guardianSIN+
							          " does not exist.");
					return false;
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
		public boolean insertGuardian(Guardian guardian, String pwd){

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
	            
	            System.out.println("Inserted new guardian "+ pwd);
	            
	            return true;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return false;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		public int insertEmployee(Employee employee, String pwd){


			String employeeSIN = employee.getEmployeeSIN();
			if (getUserInfoByEmployeeSIN(employeeSIN).getAge() != null) {
				System.out.println("Employee with SIN: "+employeeSIN+
						          " already exists!");
				return 1;
			}
			
			ArrayList<String> usernames = getAllUsernamesByEntity("employee");
			if (usernames.size() != 0) {
				for (int i = 0 ; i < usernames.size(); i++) {
					if (employee.getUserName().equals(usernames.get(i))) {
			            return 2;
					}
				}
			}
			
			boolean branchExists = false;
			ArrayList<Branch> branches = getAllBranches();
			if (branches.size() != 0) {
				for (int i = 0 ; i < branches.size(); i++) {
					if (branches.get(i).getBranchID().equals(employee.getBranchID())) {
						branchExists = true;
					}
				}
			} else {
				return 3;
			}
			
			if (!branchExists) {
				return 4;
			}

			if (employee.getRole().equals("receptionist")) {
				ArrayList<Employee> employeesSameBranch = getEmployeesByBranchID(employee.getBranchID());
				if (employeesSameBranch.size() != 0) {
					int receptionistsFound = 0;
					for (int i = 0 ; i < employeesSameBranch.size() ; i++) {
						if (employeesSameBranch.get(i).getRole().equals("receptionist")) {
							receptionistsFound++;
						}
					}
					if (receptionistsFound == 2) {
						return 5;//there are already 2 receptionists
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
	            
	            System.out.println(ps.toString());
	            
	            ps.executeUpdate();
	            
	            System.out.println("Inserted new employee");
	            
	            return 0;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return 6;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		public Integer insertPatientRecord(PatientRecord patientRecord){

			PatientRecord foundRecord = getPatientRecordByKey(patientRecord.getPatientSIN(), patientRecord.getAppointmentID());
			if (foundRecord.getPatientSIN() != null) {
				return 1;//already written one
			}

			getConn();//must be below above statement since getPatientRecordByKey
			          //closed the connection

	        try{

				ps = db.prepareStatement("INSERT into dentalclinic.patientrecord "
									   + "values(?, ?, ?, ?)");
				
	            ps.setString(1, patientRecord.getPatientSIN());	
	            ps.setInt(2, Integer.parseInt(patientRecord.getAppointmentID()));	
	            ps.setObject(3, patientRecord.getTeethInvolved(), Types.ARRAY);
	            ps.setString(4, patientRecord.getTreatmentDetails());	
	            
	            ps.executeUpdate();
	            
	            System.out.println("Inserted new employee");
	            
	            return 0;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return 2;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		public Integer insertInvoice(Invoice invoice){

			Invoice foundInvoice = getInvoiceByID(invoice.getInvoiceID());
			if (foundInvoice.getPatientSIN() != null) {
				return 1;//already exists
			}

			getConn();

	        try{

				ps = db.prepareStatement("INSERT into dentalclinic.Invoice "
									   + "values(?, CURRENT_DATE, ?, null,"
									   + "0, 0, 0, 0, 0, 0)");
				
	            ps.setInt(1, Integer.parseInt(invoice.getInvoiceID()));	
	            ps.setString(2, invoice.getPatientSIN());
	            
	            ps.executeUpdate();
	            
	            System.out.println("Inserted new invoice with ID: "+invoice.getInvoiceID());
	            
	            return 0;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return 2;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		public Integer insertAppointment(Appointment appointment){

			//appointmentDate appointmentStartTime roomID branchID
			Appointment foundAppointment = 
					getAppointmentByLocationAndTime(
							appointment.getAppointmentDate(),
							appointment.getAppointmentStartTime(), 
							appointment.getRoomID(),
							appointment.getBranchID());
			
			if (foundAppointment.getPatientSIN() != null) {
				return 1;//already inserted
			}

			getConn();

	        try{
	        	
	        	//concatenating date since could not get setDate nor setTime to work
	        	//am aware of the vulnerabilities
				ps = db.prepareStatement("INSERT into dentalclinic.appointment "
									   + "values(?, '"+appointment.getAppointmentDate()+"', "
									   + "'"+appointment.getAppointmentStartTime()+"', "
									   + "'"+appointment.getAppointmentEndTime()+"', ?, ?, ?, ?, ?, ?, ?)");

	            ps.setInt(1, Integer.parseInt(appointment.getAppointmentID()));	
	            ps.setString(2, appointment.getPatientSIN());	
	            ps.setInt(3, Integer.parseInt(appointment.getRoomID()));	
	            ps.setInt(4, Integer.parseInt(appointment.getBranchID()));	
	            ps.setInt(5, Integer.parseInt(appointment.getInvoiceID()));	
	            ps.setObject(6, appointment.getEmployeeSINList(), Types.ARRAY);
	            ps.setString(7, appointment.getAppointmentType());	
	            ps.setString(8, appointment.getStatus());	
	            
	            ps.executeUpdate();
	            
	            System.out.println(ps.toString());
	            
	            System.out.println("Inserted new appointment with ID: "+appointment.getAppointmentID());
	            
	            return 0;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return 2;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		public Integer insertAppointmentProcedure(AppointmentProcedure appointmentP){

			AppointmentProcedure foundAppointmentP = getAppointmentProcedureByKey(
														appointmentP.getAppointmentID(), 
														appointmentP.getToothInvolved(), 
														appointmentP.getProcedureCode());
			if (foundAppointmentP.getProcedureType() != null) {
				return 1;//already inserted
			}

			getConn();

	        try{
	        	
	        	//concatenating date since could not get setDate nor setTime to work
	        	//am aware of the vulnerabilities
				ps = db.prepareStatement("INSERT into dentalclinic.appointmentprocedure "
									   + "values(?, ?, ?, ?, ?, ?, '"+appointmentP.getProcedureDate()+"')");

	            ps.setInt(1, Integer.parseInt(appointmentP.getAppointmentID()));	
	            ps.setString(2, appointmentP.getToothInvolved());	
	            ps.setString(3, appointmentP.getProcedureCode());	
	            ps.setString(4, appointmentP.getProcedureType());	
	            ps.setObject(5, appointmentP.getMaterialsAndAmountUsed(), Types.ARRAY);
	            ps.setString(6, appointmentP.getDescription());
	            
	            ps.executeUpdate();
	            
	            System.out.println("Inserted new appointment procedure for appointment ID: "+appointmentP.getAppointmentID());
	            
	            return 0;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return 2;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		public Integer insertTreatment(Treatment treatment){

			Treatment foundTreatment = getTreatmentByKey(
										treatment.getAppointmentID(), 
										treatment.getToothInvolved(), 
										treatment.getTreatmentCode());
			if (foundTreatment.getTreatmentType() != null) {
				return 1;//already inserted
			}

			getConn();

	        try{
	        	
	        	//concatenating date since could not get setDate nor setTime to work
	        	//am aware of the vulnerabilities
				ps = db.prepareStatement("INSERT into dentalclinic.treatment "
									   + "values(?, ?, ?, ?, ?, ?, ?, '"+treatment.getTreatmentDate()+"')");

	            ps.setInt(1, Integer.parseInt(treatment.getAppointmentID()));	
	            ps.setString(2, treatment.getToothInvolved());	
	            ps.setString(3, treatment.getTreatmentCode());	
	            ps.setString(4, treatment.getTreatmentType());	
	            ps.setObject(5, treatment.getMedication(), Types.ARRAY);
	            ps.setObject(6, treatment.getSymptoms(), Types.ARRAY);
	            ps.setString(7, treatment.getComments());
	            
	            ps.executeUpdate();
	            
	            System.out.println("Inserted new treatment for appointment ID: "+treatment.getAppointmentID());
	            
	            return 0;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return 2;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		public Integer insertFeeCharge(FeeCharge feeCharge){

			FeeCharge foundFeeCharge = getFeeCharge(feeCharge.getFeeID());
			if (foundFeeCharge.getInvoiceID() != null) {
				return 1;//already inserted
			}

			getConn();

	        try{
	        	
	        	//concatenating date since could not get setDate nor setTime to work
	        	//am aware of the vulnerabilities
				ps = db.prepareStatement("INSERT into dentalclinic.feecharge "
									   + "values(?, ?, ?, ?)");
	            ps.setInt(1, Integer.parseInt(feeCharge.getFeeID()));
	            ps.setInt(2, Integer.parseInt(feeCharge.getInvoiceID()));
	            ps.setInt(3, Integer.parseInt(feeCharge.getFeeCode()));
				ps.setBigDecimal(4, new BigDecimal(feeCharge.getCharge()));
	            
	            ps.executeUpdate();
	            
	            System.out.println("Inserted new FeeCharge with Code: "+feeCharge.getFeeCode());
	            
	            return 0;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return 2;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		public boolean isAppointmentNoShow(String invoiceID){

			//no need to check errors since invoices are created before an appointment
			Appointment appointment = getAppointmentByInvoiceID(invoiceID);
			
			boolean isNoShow = false;

			getConn();

	        try{
	        	
	        	//concatenating date since could not get setDate nor setTime to work
	        	//am aware of the vulnerabilities
				ps = db.prepareStatement("SELECT (LOCALTIMESTAMP - '"+appointment.getAppointmentDate()+
										"' > interval '24 hours') AS IsNoShow");
	            rs = ps.executeQuery();
	            
	            System.out.println(ps.toString());
	            
				while(rs.next()) {
					
					isNoShow = rs.getBoolean("IsNoShow");
					
				}
	            return isNoShow;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return false;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		//Checks if the date is after appointment start time
		public boolean isAfterAppointmentStart(Appointment appointment){

			boolean isAfterAppointmentStart = false;

			getConn();

	        try{
	        	
	        	//concatenating date since could not get setDate nor setTime to work
	        	//am aware of the vulnerabilities
				ps = db.prepareStatement("SELECT (LOCALTIMESTAMP > timestamp '"
										  +appointment.getAppointmentDate()
										  +" "
										  +appointment.getAppointmentStartTime()
										 +"') AS isAfterAppointmentStart");
	            rs = ps.executeQuery();
	            
	            System.out.println(ps.toString());
	            
				while(rs.next()) {
					
					isAfterAppointmentStart = rs.getBoolean("isAfterAppointmentStart");
					
				}
	            return isAfterAppointmentStart;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return false;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		//Checks if the date is after appointment start time
		public boolean isAfterAppointmentEnd(Appointment appointment){

			boolean isAfterAppointmentEnd = false;

			getConn();

	        try{
	        	
	        	//concatenating date since could not get setDate nor setTime to work
	        	//am aware of the vulnerabilities
				ps = db.prepareStatement("SELECT (LOCALTIMESTAMP > timestamp '"
										  +appointment.getAppointmentDate()
										  +" "
										  +appointment.getAppointmentEndTime()
										 +"') AS isAfterAppointmentEnd");
	            rs = ps.executeQuery();
	            
	            System.out.println(ps.toString());
	            
				while(rs.next()) {
					
					isAfterAppointmentEnd = rs.getBoolean("isAfterAppointmentEnd");
					
				}
	            return isAfterAppointmentEnd;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return false;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		//Checks if the date is within 24 hours of appointment
		public boolean isWithinADayFromAppointmentStart(Appointment appointment){

			boolean withinADay = false;

			getConn();

	        try{
	        	
	        	//concatenating date since could not get setDate nor setTime to work
	        	//am aware of the vulnerabilities
				ps = db.prepareStatement("SELECT (timestamp '"
									      +appointment.getAppointmentDate()
				                      +" "+appointment.getAppointmentStartTime()+"' - "
									   + "LOCALTIMESTAMP <= interval '1 day') AS withinADay");
	            rs = ps.executeQuery();
	            
	            System.out.println(ps.toString());
	            
				while(rs.next()) {
					
					withinADay = rs.getBoolean("withinADay");
					
				}
	            return withinADay;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return false;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		//Updates the invoice based on existing fees
		public boolean updateInvoiceTotal(Appointment appointment){
			
			ArrayList<FeeCharge> fees = getAllFeeChargesByInvoiceID(appointment.getInvoiceID());
			Double total = 0.0;
			for (FeeCharge fee : fees) {
				total += Double.parseDouble(fee.getCharge());
			}
			
			getConn();

	        try{

				ps = db.prepareStatement("UPDATE dentalclinic.invoice "
									   + "SET totalfeecharge=? "
						               + "WHERE invoiceID=?");
					
	            ps.setBigDecimal(1, new BigDecimal(total));
	            ps.setInt(2, Integer.parseInt(appointment.getInvoiceID()));	
	            
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
		public int updateEmployeeInfo(Employee newEmployeeInfo, String employeeSIN) {
			
			Employee oldEmployeeInfo = getUserInfoByEmployeeSIN(employeeSIN);
			
			if (!oldEmployeeInfo.getUserName()
					.equals(newEmployeeInfo.getUserName())) {
				ArrayList<String> usernames = getAllUsernamesByEntity("employee");
				for (int i = 0 ; i < usernames.size(); i++) {
					if (newEmployeeInfo.getUserName().equals(usernames.get(i))) {
						System.out.println("Username "+newEmployeeInfo.getUserName()+" already exists!");
			            return 1;//username taken
					}
				}
			}
			
			boolean branchExists = false;
			ArrayList<Branch> branches = getAllBranches();
			if (branches.size() != 0) {
				for (int i = 0 ; i < branches.size(); i++) {
					if (branches.get(i).getBranchID().equals(newEmployeeInfo.getBranchID())) {
						branchExists = true;
					}
				}
			} else {
				return 2;//0 branches found in DB
			}
			
			if (!branchExists) {
				return 3;//branch entered does not exist
			}
			
			//Employee previously not receptionist, but now become receptionist
			//this means we must check that there are no more than 2 receptionists
			//in this particular branch
			if (newEmployeeInfo.getRole().equals("receptionist")) {
				
				if (oldEmployeeInfo.getRole().equals("receptionist")
				 && oldEmployeeInfo.getBranchID().equals(newEmployeeInfo.getBranchID())) {
					
					//no need to check since prev. role == current role and working at same branch
					
				} else {
					ArrayList<Employee> employeesSameBranch = getEmployeesByBranchID(newEmployeeInfo.getBranchID());
					if (employeesSameBranch.size() != 0) {
						int receptionistsFound = 0;
						for (int i = 0 ; i < employeesSameBranch.size() ; i++) {
							if (employeesSameBranch.get(i).getRole().equals("receptionist")) {
								receptionistsFound++;
							}
						}
						if (receptionistsFound == 2) {
							return 4;//there are already 2 receptionists
						}
					}
				}
			} else {//Changed to a role other than receptionist
				
				//If the employee was a receptionist previously
			    //we need to make sure the previous branch is not
			    //left with 0 receptionists; it would not be able to operate!
				if (oldEmployeeInfo.getRole().equals("receptionist")) {
					ArrayList<Employee> employeesSameBranch = getEmployeesByBranchID(oldEmployeeInfo.getBranchID());
					if (employeesSameBranch.size() != 0) {
						int receptionistsFound = 0;
						for (int i = 0 ; i < employeesSameBranch.size() ; i++) {
							if (employeesSameBranch.get(i).getRole().equals("receptionist")) {
								receptionistsFound++;
							}
						}
						if (receptionistsFound == 1) {
							return 5;//previously, there would have been 1 receptionist; the one
							         //currently to be updated. But since they will have another role
							         //we will not have any receptionists in the previous branch
						}
					}
				}
				
			}
			
			
			
			getConn();

	        try{

				ps = db.prepareStatement("UPDATE dentalclinic.employee "
									   + "SET employeesin=?, BranchID=?, username=?, role=?, employeeType=?, salary=?, "
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
	            
	            ps.setInt(11, Integer.parseInt(newEmployeeInfo.getAge()));	
	            ps.setString(12, newEmployeeInfo.getGender());	
	            ps.setString(13, newEmployeeInfo.getEmployeeEmail());	
	            ps.setString(14, newEmployeeInfo.getEmployeePhoneNumber());	
	            ps.setString(15, newEmployeeInfo.getAddress());

	            
	            ps.setString(16, employeeSIN);	
	            
	            ps.executeUpdate();

	            System.out.println(ps.toString());
	            
	            return 0;//updated just fine

	        }catch(SQLException e){
	            e.printStackTrace();
	            return 5;//psql error
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		//Returns an appointment based on the ID
		public Appointment getAppointmentByAppointmentID(String appointmentID){
			
			getConn();
			
			Appointment appointment = new Appointment();
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.appointment "
						               + "WHERE appointmentID=? "
						               + "ORDER BY appointmentdate");
	            ps.setInt(1, Integer.parseInt(appointmentID));	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					//col1: appointmentID already have
					String appointmentDate = rs.getString("appointmentDate");
					String startTime = rs.getString("appointmentstartTime");
					String endTime = rs.getString("appointmentendTime");
					String patientSIN = rs.getString("patientSIN");
					String roomID = rs.getString("roomID");
					String branchID = rs.getString("branchID");
					String invoiceID = rs.getString("invoiceID");
					String[] employeeSINList = (String[]) rs.getArray("employeeSINList").getArray();
					String appointmentType = rs.getString("appointmentType");
					String status = rs.getString("status");
					appointment = new Appointment(appointmentID, appointmentDate, startTime,
													endTime, patientSIN, roomID, branchID, invoiceID,
													employeeSINList, appointmentType, status);

				 System.out.println(Arrays.toString(appointment.getEmployeeSINList()));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return appointment;
		}
		
		//Returns an appointment based on other uniquely identifying parameters
		//Namely, appointmentDate, appointmentStartTime, roomID and branchID
		public Appointment getAppointmentByLocationAndTime(
						String appointmentDate, String appointmentStartTime, String roomID, String branchID){
			
			getConn();
			
			Appointment appointment = new Appointment();
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.appointment "
						               + "WHERE appointmentDate='"+appointmentDate+"' "
						               + "AND appointmentStartTime='"+appointmentStartTime+"' "
						               + "AND roomID=? AND branchID=?");
	            ps.setInt(1, Integer.parseInt(roomID));	
	            ps.setInt(2, Integer.parseInt(branchID));	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					String appointmentID = rs.getString("appointmentID");
					//col2: appointmentDate
					//col3 appointmentStartTime
					String endTime = rs.getString("appointmentendTime");
					String patientSIN = rs.getString("patientSIN");
					//col6 roomID
					//col7 branchID
					String invoiceID = rs.getString("invoiceID");
					String[] employeeSINList = (String[]) rs.getArray("employeeSINList").getArray();
					String appointmentType = rs.getString("appointmentType");
					String status = rs.getString("status");
					appointment = new Appointment(appointmentID, appointmentDate, appointmentStartTime,
													endTime, patientSIN, roomID, branchID, invoiceID,
													employeeSINList, appointmentType, status);

				 System.out.println(Arrays.toString(appointment.getEmployeeSINList()));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return appointment;
		}
		
		//Returns an appointment based on the ID
		public Appointment getAppointmentByInvoiceID(String invoiceID){
			
			getConn();
			
			Appointment appointment = new Appointment();
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.appointment "
						               + "WHERE invoiceID=? "
						               + "ORDER BY appointmentdate");
	            ps.setInt(1, Integer.parseInt(invoiceID));	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					String appointmentID = rs.getString("appointmentID");
					String appointmentDate = rs.getString("appointmentDate");
					String startTime = rs.getString("appointmentstartTime");
					String endTime = rs.getString("appointmentendTime");
					String patientSIN = rs.getString("patientSIN");
					String roomID = rs.getString("roomID");
					String branchID = rs.getString("branchID");
					//col8: invoiceID already have
					String[] employeeSINList = (String[]) rs.getArray("employeeSINList").getArray();
					String appointmentType = rs.getString("appointmentType");
					String status = rs.getString("status");
					appointment = new Appointment(appointmentID, appointmentDate, startTime,
													endTime, patientSIN, roomID, branchID, invoiceID,
													employeeSINList, appointmentType, status);

				 System.out.println(Arrays.toString(appointment.getEmployeeSINList()));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return appointment;
		}
		
		//Returns an appointmentprocedure based on its key
		public AppointmentProcedure getAppointmentProcedureByKey(String appointmentID, String tooth, String pCode){
			
			getConn();
			
			AppointmentProcedure appointmentP = new AppointmentProcedure();
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.appointmentprocedure "
						               + "WHERE appointmentID=? "
						               + "AND toothinvolved=? "
						               + "AND procedurecode=?");
	            ps.setInt(1, Integer.parseInt(appointmentID));	
	            ps.setString(2, tooth);	
	            ps.setString(3, pCode);	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					String procedureType = rs.getString("procedureType");
					String[] materialsAndAmountUsed = (String[]) rs.getArray("materialsAndAmountUsed").getArray();
					String description = rs.getString("description");
					String procedureDate = rs.getString("procedureDate");
					
					appointmentP = new AppointmentProcedure(appointmentID, tooth, pCode,
															procedureType, materialsAndAmountUsed,
															description, procedureDate);

				 System.out.println("Fetched procedure with code: "+appointmentP.getProcedureCode());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return appointmentP;
		}
		
		//Returns an appointmentprocedure based on its key
		public Treatment getTreatmentByKey(String appointmentID, String tooth, String tCode){
			
			getConn();
			
			Treatment treatment = new Treatment();
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.treatment "
						               + "WHERE appointmentID=? "
						               + "AND toothinvolved=? "
						               + "AND treatmentcode=?");
	            ps.setInt(1, Integer.parseInt(appointmentID));	
	            ps.setString(2, tooth);	
	            ps.setString(3, tCode);	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					String treatmentType = rs.getString("treatmentType");
					String[] medication = (String[]) rs.getArray("medication").getArray();
					String[] symptoms = (String[]) rs.getArray("symptoms").getArray();
					String comments = rs.getString("comments");
					String treatmentDate = rs.getString("treatmentDate");
					
					treatment = new Treatment(appointmentID, tooth, tCode,
							treatmentType, medication, symptoms,
							comments, treatmentDate);

				 System.out.println("Fetched treatment with code: "+treatment.getTreatmentCode());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return treatment;
		}
		
		//Returns a feecharge based on its id
		public FeeCharge getFeeCharge(String feeID){
			
			getConn();
			
			FeeCharge fee = new FeeCharge();
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.feecharge "
						               + "WHERE feeID=?");
	            ps.setInt(1, Integer.parseInt(feeID));	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					//col1: feeID already have
					String invoiceID = rs.getString("invoiceID");
					String feeCode = rs.getString("feeCode");
					String charge = rs.getString("charge");
					
					fee = new FeeCharge(feeID, invoiceID, feeCode, charge);
				 System.out.println("Fetched FeeCharge with fee code: "+feeCode);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return fee;
		}
		
		//Returns a feecharges based on invoiceID
		public ArrayList<FeeCharge> getAllFeeChargesByInvoiceID(String invoiceID){
			
			getConn();
			
			ArrayList<FeeCharge> fees = new ArrayList<FeeCharge>();
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.feecharge "
						               + "WHERE invoiceID=?");
	            ps.setInt(1, Integer.parseInt(invoiceID));	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()) {
					String feeID = rs.getString("feeID");
					//col2: invoiceID already have
					String feeCode = rs.getString("feeCode");
					String charge = rs.getString("charge");
					
					FeeCharge fee = new FeeCharge(feeID, invoiceID, feeCode, charge);
					
					fees.add(fee);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return fees;
		}
		
		//Returns an InsuranceClaim based on its key, SIN and invoiceID
		public InsuranceClaim getInsuranceClaim(String patientSIN, String invoiceID){
			
			getConn();
			
			InsuranceClaim claim = new InsuranceClaim();
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.insuranceclaim "
						               + "WHERE patientSIN=? "
						               + "AND invoiceID=?");
	            ps.setString(1, patientSIN);	
	            ps.setInt(2, Integer.parseInt(invoiceID));	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					//col1: patientSIN already have
					String insuranceCompany = rs.getString("insuranceCompany");
					//col3: invoiceID already have
					String insuranceAmount = rs.getBigDecimal("insuranceAmount").toString();
					
					claim = new InsuranceClaim(patientSIN, insuranceCompany, invoiceID, insuranceAmount);
					/*
					private @Getter @Setter String patientSIN;
					private @Getter @Setter String insuranceCompany;
					private @Getter @Setter String invoiceID;
					private @Getter @Setter String insuranceAmount;*/
				 System.out.println("Insurance: "+insuranceCompany+" "+insuranceAmount+" CAD");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return claim;
		}
		
		//Returns appointments that involve a certain employee using their SIN
		public ArrayList<Appointment> getAllAppointmentsByPatientSIN(String patientSIN){
			
			getConn();
			
			ArrayList<Appointment> appointments = new ArrayList<Appointment>();
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.appointment "
						               + "WHERE patientsin=? "
						               + "ORDER BY appointmentdate");
	            ps.setString(1, patientSIN);	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					String appointmentID = rs.getString("appointmentID");
					String appointmentDate = rs.getString("appointmentDate");
					String startTime = rs.getString("appointmentstartTime");
					String endTime = rs.getString("appointmentendTime");
					//col5: patientSIN already have
					String roomID = rs.getString("roomID");
					String branchID = rs.getString("branchID");
					String invoiceID = rs.getString("invoiceID");
					String[] employeeSINList = (String[]) rs.getArray("employeeSINList").getArray();
					String appointmentType = rs.getString("appointmentType");
					String status = rs.getString("status");
					Appointment appointment = new Appointment(appointmentID, appointmentDate, startTime,
													endTime, patientSIN, roomID, branchID, invoiceID,
													employeeSINList, appointmentType, status);
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
		
		//Returns appointments that involve a certain employee using their SIN
		public ArrayList<Appointment> getAllAppointmentsByPatientSINOrderByInvoiceID(String patientSIN){
			
			getConn();
			
			ArrayList<Appointment> appointments = new ArrayList<Appointment>();
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.appointment "
						               + "WHERE patientsin=? "
						               + "ORDER BY invoiceid");
	            ps.setString(1, patientSIN);	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					String appointmentID = rs.getString("appointmentID");
					String appointmentDate = rs.getString("appointmentDate");
					String startTime = rs.getString("appointmentstartTime");
					String endTime = rs.getString("appointmentendTime");
					//col5: patientSIN already have
					String roomID = rs.getString("roomID");
					String branchID = rs.getString("branchID");
					String invoiceID = rs.getString("invoiceID");
					String[] employeeSINList = (String[]) rs.getArray("employeeSINList").getArray();
					String appointmentType = rs.getString("appointmentType");
					String status = rs.getString("status");
					Appointment appointment = new Appointment(appointmentID, appointmentDate, startTime,
													endTime, patientSIN, roomID, branchID, invoiceID,
													employeeSINList, appointmentType, status);
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
		
		//Returns appointments that involve a certain employee using their SIN
		public ArrayList<Appointment> getUnfinishedAppointmentsByPatientSIN(String patientSIN){
			
			getConn();
			
			ArrayList<Appointment> appointments = new ArrayList<Appointment>();
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.appointment "
						               + "WHERE patientsin=? "
						               + "AND status != 'finished' "
						               + "ORDER BY appointmentdate");
	            ps.setString(1, patientSIN);	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					String appointmentID = rs.getString("appointmentID");
					String appointmentDate = rs.getString("appointmentDate");
					String startTime = rs.getString("appointmentstartTime");
					String endTime = rs.getString("appointmentendTime");
					//col5: patientSIN already have
					String roomID = rs.getString("roomID");
					String branchID = rs.getString("branchID");
					String invoiceID = rs.getString("invoiceID");
					String[] employeeSINList = (String[]) rs.getArray("employeeSINList").getArray();
					String appointmentType = rs.getString("appointmentType");
					String status = rs.getString("status");
					Appointment appointment = new Appointment(appointmentID, appointmentDate, startTime,
													endTime, patientSIN, roomID, branchID, invoiceID,
													employeeSINList, appointmentType, status);
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
		
		public ArrayList<Appointment> getAppointmentsByPatientSINAndStatus(String patientSIN, String status){
			
			getConn();
			
			ArrayList<Appointment> appointments = new ArrayList<Appointment>();
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.appointment "
						               + "WHERE patientsin=? "
						               + "AND status = ? "
						               + "ORDER BY appointmentdate");
	            ps.setString(1, patientSIN);
	            ps.setString(2, status);
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					String appointmentID = rs.getString("appointmentID");
					String appointmentDate = rs.getString("appointmentDate");
					String startTime = rs.getString("appointmentstartTime");
					String endTime = rs.getString("appointmentendTime");
					//col5: patientSIN already have
					String roomID = rs.getString("roomID");
					String branchID = rs.getString("branchID");
					String invoiceID = rs.getString("invoiceID");
					String[] employeeSINList = (String[]) rs.getArray("employeeSINList").getArray();
					String appointmentType = rs.getString("appointmentType");
					//col11: status aleardy have
					Appointment appointment = new Appointment(appointmentID, appointmentDate, startTime,
													endTime, patientSIN, roomID, branchID, invoiceID,
													employeeSINList, appointmentType, status);
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
		
		//Returns appointments that involve a certain employee using their SIN
		public ArrayList<Appointment> getAppointmentsByEmployeeSIN(String employeeSIN){
			
			getConn();
			
			ArrayList<Appointment> appointments = new ArrayList<Appointment>();
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.appointment "
						               + "WHERE ? = ANY(employeesinlist) "
						               + "ORDER BY appointmentdate");
	            ps.setString(1, employeeSIN);	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					String appointmentID = rs.getString("appointmentID");
					String appointmentDate = rs.getString("appointmentDate");
					String startTime = rs.getString("appointmentstartTime");
					String endTime = rs.getString("appointmentendTime");
					String patientSIN = rs.getString("patientSIN");
					String roomID = rs.getString("roomID");
					String branchID = rs.getString("branchID");
					String invoiceID = rs.getString("invoiceID");
					String[] employeeSINList = (String[]) rs.getArray("employeeSINList").getArray();
					String appointmentType = rs.getString("appointmentType");
					String status = rs.getString("status");
					Appointment appointment = new Appointment(appointmentID, appointmentDate, startTime,
													endTime, patientSIN, roomID, branchID, invoiceID,
													employeeSINList, appointmentType, status);
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
		
		//Returns appointments that match this date; including branchID and roomID
		public ArrayList<Appointment> getAppointmentsByDateAndLocation(String date, String branchID, String roomID){
			
			getConn();
			
			ArrayList<Appointment> appointments = new ArrayList<Appointment>();
			
			try {
				//Will have to make an exception to this since cannot
				//figure out setDate
				ps = db.prepareStatement("SELECT * from dentalclinic.appointment "
						               + "WHERE appointmentdate=timestamp '"+date+"' "
						               + "AND branchID=? AND roomID=? "
						               + "ORDER BY appointmentdate");
	            ps.setInt(1, Integer.parseInt(branchID));	
	            ps.setInt(2, Integer.parseInt(roomID));	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					String patientSIN = rs.getString("patientSIN");
					String appointmentID = rs.getString("appointmentID");
					String appointmentDate = rs.getString("appointmentDate");
					String startTime = rs.getString("appointmentStartTime");
					String endTime = rs.getString("appointmentendTime");
					//col6: roomID already have
					//col7: branchID already have
					String invoiceID = rs.getString("invoiceID");
					String[] employeeSINList = (String[]) rs.getArray("employeeSINList").getArray();
					String appointmentType = rs.getString("appointmentType");
					String status = rs.getString("status");
					Appointment appointment = new Appointment(appointmentID, appointmentDate, startTime,
													endTime, patientSIN, roomID, branchID, invoiceID,
													employeeSINList, appointmentType, status);
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
		
		//Returns appointments that involve a certain employee using their SIN
		public Integer getTreatmentCountByAppointmentID(String appointmentID){
			
			getConn();
			
			Integer treatmentCount = 0;
			
			try {
				ps = db.prepareStatement("SELECT COUNT(*) "
						               + "FROM dentalclinic.treatment "
						               + "WHERE appointmentid=?");
				ps.setInt(1, Integer.parseInt(appointmentID));	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					treatmentCount = rs.getInt("count");
				 System.out.println(treatmentCount);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return treatmentCount;
			
		}
		
		//Returns appointments that involve a certain employee using their SIN
		public ArrayList<Treatment> getTreatmentsByAppointmentID(String appointmentID){
			
			getConn();
			
			ArrayList<Treatment> treatments = new ArrayList<Treatment>();
			
			try {
				ps = db.prepareStatement("SELECT * "
						               + "FROM dentalclinic.treatment "
						               + "WHERE appointmentid=?");
				ps.setInt(1, Integer.parseInt(appointmentID));	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					//col1 appointmentID already have
					String toothInvolved = rs.getString("toothInvolved");
					String treatmentCode = rs.getString("treatmentCode");
					String treatmentType = rs.getString("treatmentType");
					String[] medication = (String[]) rs.getArray("medication").getArray();
					String[] symptoms = (String[]) rs.getArray("symptoms").getArray();
					String comments = rs.getString("comments");
					String treatmentDate = rs.getString("treatmentDate");
					
					Treatment treatment = new Treatment(appointmentID, toothInvolved, treatmentCode,
													    treatmentType, medication, symptoms,
													    comments, treatmentDate);

					System.out.println(treatment.getTreatmentDate());
					
					treatments.add(treatment);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return treatments;
			
		}
		
		//Returns a review by its key
		public Review getReviewByKey(String patientSIN, String appointmentID){
			
			getConn();
			
			Review review = new Review();
			
			try {
				ps = db.prepareStatement("SELECT * "
						               + "FROM dentalclinic.review "
						               + "WHERE patientSIN=? "
						               + "AND appointmentID=?");
				ps.setString(1, patientSIN);	
				ps.setInt(2, Integer.parseInt(appointmentID));	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					String reviewDate = rs.getString("reviewDate");
					String reviewTime = rs.getString("reviewTime");
					String professionalism = rs.getString("employeeprofessionalism");
					String communication = rs.getString("communication");
					String cleanliness = rs.getString("cleanliness");
					String value = rs.getString("value");
					String comments = rs.getString("comments");
					
					review = new Review(patientSIN, appointmentID, reviewDate,
							reviewTime, professionalism, communication,
							            cleanliness, value, comments);
					
				 System.out.println(patientSIN+", "+appointmentID);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return review;
			
		}
		
		//From https://stackoverflow.com/questions/17102988/java-sql-date-formatting
	    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
	        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
	        return sDate;
	    }
		
		//For patient view; inserts a review
		public int insertReview(Review review){

			ArrayList<Appointment> finishedAppointments = getAppointmentsByPatientSINAndStatus(review.getPatientSIN(), "finished");
			if (finishedAppointments.size() == 0) {
				System.out.println("no finished appointments; cannot review");
				return 1;
			}
			
			getConn();

	        try{
	            
				ps = db.prepareStatement("INSERT INTO dentalclinic.review "
									   + "VALUES (?, CURRENT_DATE, LOCALTIME, ?, ?, ?, ?, ?, ?)");
		
				ps.setString(1, review.getPatientSIN());	
				//set date in sql
				//set time in sql
	            ps.setInt(2, Integer.parseInt(review.getEmployeeProfessionalism()));
	            ps.setInt(3, Integer.parseInt(review.getCommunication()));
	            ps.setInt(4, Integer.parseInt(review.getCleanliness()));
	            ps.setInt(5, Integer.parseInt(review.getValue()));
	            ps.setInt(6, Integer.parseInt(review.getAppointmentID()));
				ps.setString(7, review.getComments());
				
				System.out.println(ps.toString());
				
				ps.executeUpdate();
				
				System.out.println(ps.toString());
	            
	            return 0;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return 2;//SQL error
	        }finally {
	        	closeDB();
	        }	       
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
		
		//Returns an ArrayList containing all rooms from this branch
		public ArrayList<Room> getAllRoomsByBranchID(String branchID){
			
			getConn();
			
			ArrayList<Room> rooms = new ArrayList<Room>();
			
			try {
				ps = db.prepareStatement("SELECT * "
									   + "FROM dentalclinic.room "
									   + "WHERE branchID=?");
				ps.setInt(1, Integer.parseInt(branchID));	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					String roomID = rs.getString("roomID");
					Room room = new Room(roomID, branchID);
					
					rooms.add(room);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return rooms;
			
		}
		
		//Returns an ArrayList containing all invoices
		public ArrayList<Invoice> getAllInvoicesByPatientSIN(String patientSIN){
			
			getConn();
			
			ArrayList<Invoice> invoices = new ArrayList<Invoice>();
			
			try {
				ps = db.prepareStatement("SELECT * FROM dentalclinic.invoice "
									   + "WHERE patientSIN=?");
	            ps.setString(1, patientSIN);	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					String invoiceID = rs.getString("invoiceID");
					String dateOfIssue = rs.getString("dateOfIssue");
					//col3: patientSIN already have
					String guardianSIN = rs.getString("guardianSIN");
					String userCharge = rs.getString("userCharge");
					String insuranceCharge = rs.getString("insuranceCharge");
					String employeeCharge = rs.getString("employeeCharge");
					String totalFeeCharge = rs.getString("totalFeeCharge");
					String discount = rs.getString("discount");
					String penalty = rs.getString("penalty");
					Invoice invoice = new Invoice(invoiceID, dateOfIssue, patientSIN, guardianSIN,
										          userCharge, insuranceCharge, employeeCharge,
										          totalFeeCharge, discount, penalty);
					invoices.add(invoice);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return invoices;
			
		}
		
		//Returns an invoice by its ID
		public PatientBilling getPatientBillingByKey(String patientSIN, String invoiceID){
			
			getConn();
			
			PatientBilling patientBilling = new PatientBilling();
			
			try {
				ps = db.prepareStatement("SELECT * FROM dentalclinic.patientBilling "
									   + "WHERE invoiceID=? "
									   + "AND patientSIN=?");
	            ps.setInt(1, Integer.parseInt(invoiceID));	
	            ps.setString(2, patientSIN);	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					//col1: patientSIN already have
					//col2: invoiceID already have
					String guardianSIN = rs.getString("guardianSIN");
					String employeeSIN = rs.getString("employeeSIN");
					String userPortion = rs.getString("userPortion");
					String employeePortion = rs.getString("employeePortion");
					String insurancePortion = rs.getString("insurancePortion");
					String totalAmount = rs.getString("totalAmount");
					String paymentType = rs.getString("paymentType");
					patientBilling = new PatientBilling(patientSIN, invoiceID, guardianSIN, employeeSIN,
													    userPortion, employeePortion,
													    insurancePortion, totalAmount, paymentType);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return patientBilling;
			
		}
		
		//Returns an ArrayList containing all Branches
		public Invoice getInvoiceByID(String invoiceID){
			
			getConn();
			
			Invoice invoice = new Invoice();
			
			try {
				ps = db.prepareStatement("SELECT * FROM dentalclinic.invoice "
									   + "WHERE invoiceID=?");
	            ps.setInt(1, Integer.parseInt(invoiceID));	
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					//col1: invoiceID already have
					String dateOfIssue = rs.getString("dateOfIssue");
					String patientSIN = rs.getString("patientSIN");
					String guardianSIN = rs.getString("guardianSIN");
					String userCharge = rs.getString("userCharge");
					String insuranceCharge = rs.getString("insuranceCharge");
					String employeeCharge = rs.getString("employeeCharge");
					String totalFeeCharge = rs.getString("totalFeeCharge");
					String discount = rs.getString("discount");
					String penalty = rs.getString("penalty");
					invoice = new Invoice(invoiceID, dateOfIssue, patientSIN, guardianSIN,
											userCharge, insuranceCharge, employeeCharge,
										          totalFeeCharge, discount, penalty);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return invoice;
			
		}
		
		//Returns most recent id
		public Integer getMostRecentAppointmentID(){
			
			getConn();
			
			Integer recentID = 0;
			
			try {
				ps = db.prepareStatement("SELECT appointmentID "
					            	   + "FROM dentalclinic.appointment "
									   + "ORDER BY appointmentID DESC "
									   + "LIMIT 1");
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					recentID = rs.getInt("appointmentID");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return recentID;
			
		}

		//Returns most recent id
		public Integer getMostRecentInvoiceID(){
			
			getConn();
			
			Integer recentID = 0;
			
			try {
				ps = db.prepareStatement("SELECT invoiceID "
					            	   + "FROM dentalclinic.invoice "
									   + "ORDER BY invoiceID DESC "
									   + "LIMIT 1");
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					recentID = rs.getInt("invoiceID");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return recentID;
			
		}

		//Returns most recent id
		public Integer getMostRecentFeeID(){
			
			getConn();
			
			Integer recentID = 0;
			
			try {
				ps = db.prepareStatement("SELECT feeID "
					            	   + "FROM dentalclinic.feecharge "
									   + "ORDER BY feeID DESC "
									   + "LIMIT 1");
	            
	            System.out.println(ps.toString());   
	            
	            rs = ps.executeQuery();
				while(rs.next()){
					recentID = rs.getInt("feeID");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
						
			return recentID;
			
		}
		
		public boolean updateAppointmentStatusByInvoiceID(String invoiceID, String status){
			
			getConn();

	        try{

				ps = db.prepareStatement("UPDATE dentalclinic.appointment "
									   + "SET status=? "
						               + "WHERE invoiceID=?");
					
	            ps.setString(1, status);	
	            ps.setInt(2, Integer.parseInt(invoiceID));	
	            
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
		
		public boolean updateAppointmentStatusByAppointmentID(String appointmentID, String status){
			
			getConn();

	        try{

				ps = db.prepareStatement("UPDATE dentalclinic.appointment "
									   + "SET status=? "
						               + "WHERE appointmentID=?");
					
	            ps.setString(1, status);	
	            ps.setInt(2, Integer.parseInt(appointmentID));	
	            
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
		
		public boolean removeAllFees(String invoiceID){
			
			getConn();

	        try{

				ps = db.prepareStatement("DELETE FROM dentalclinic.feecharge "
						               + "WHERE invoiceID=?");
					
	            ps.setInt(1, Integer.parseInt(invoiceID));	
	            
	            System.out.println(ps.toString());
	            
	            ps.executeUpdate();
	            
	            return true;

	        }catch(SQLException e){
	            e.printStackTrace();
	            return false;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		//For receptionist only; bills a patient based on an invoice
		//If a guardian is found, will bill to them instead
		public int billUser(PatientBilling newBill, InsuranceClaim claim){
			
			
			String employeeSIN = newBill.getEmployeeSIN();
			if (!employeeSIN.isEmpty()) {
				if (getUserInfoByEmployeeSIN(employeeSIN).getUserName() == null) {
					System.out.println("Employee not found!");
					return 1;
				}
			}
			
			PatientBilling searchedBill = getPatientBillingByKey(newBill.getPatientSIN(), newBill.getInvoiceID());
			if (searchedBill.getTotalAmount() != null) {
				System.out.println("Already billed to this invoice!");
				return 2;
			}

			if (!claim.getInsuranceCompany().isEmpty()) {
				InsuranceClaim foundClaim = getInsuranceClaim(claim.getPatientSIN(), claim.getInvoiceID());
				if (foundClaim.getPatientSIN() == null) {
					insertClaim(claim);
				} else {
					return 5;
				}
			}
			
			getConn();

	        try{

	            String guardian = newBill.getGuardianSIN();
	        	
				ps = db.prepareStatement("UPDATE dentalclinic.invoice "
									   + "SET userCharge=?, insurancecharge=?, employeecharge=?, guardiansin=? "
									   + "WHERE invoiceid=?");
					
	            ps.setFloat(1, Float.parseFloat(newBill.getUserPortion()));	
	            ps.setFloat(2, Float.parseFloat(newBill.getInsurancePortion()));	
	            ps.setFloat(3, Float.parseFloat(newBill.getEmployeePortion()));

	            if (guardian.isEmpty()) {
	            	ps.setNull(4, Types.VARCHAR);//since patient can have no guardian
	            } else {
					ps.setString(4, newBill.getGuardianSIN());	
	            }

	            ps.setInt(5, Integer.parseInt(newBill.getInvoiceID()));
	            
	            ps.executeUpdate();

	            System.out.println(ps.toString());
	            
	            try {
					ps = db.prepareStatement("INSERT INTO dentalclinic.patientbilling "
							   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

					ps.setString(1, newBill.getPatientSIN());	
				    ps.setInt(2, Integer.parseInt(newBill.getInvoiceID()));
				     
				    if (guardian.isEmpty()) {
				     	ps.setNull(3, Types.VARCHAR);//since patient can have no guardian
				    } else {
							ps.setString(3, newBill.getGuardianSIN());	
				    }
				 	
				    if (employeeSIN.isEmpty()) {
				     	ps.setNull(4, Types.VARCHAR);//since patient can have no employee portion
				    } else {
				     	ps.setString(4, employeeSIN);	
				    }
						ps.setFloat(5, Float.parseFloat(newBill.getUserPortion()));
						ps.setFloat(6, Float.parseFloat(newBill.getEmployeePortion()));
						ps.setFloat(7, Float.parseFloat(newBill.getInsurancePortion()));
						ps.setFloat(8, Float.parseFloat(newBill.getTotalAmount()));
						ps.setString(9, newBill.getPaymentType());
						 
						ps.executeUpdate();
						
						System.out.println(ps.toString());
			     
						return 0;
	            } catch (SQLException e){
		            e.printStackTrace();
		            return 3;
		        }


	        }catch(SQLException e){
	            e.printStackTrace();
	            return 4;
	        }finally {
	        	closeDB();
	        }	       
	    }
		
		//For receptionist only; inserts an insurance claim
		private boolean insertClaim(InsuranceClaim claim){
			
			getConn();

	        try{
	            
				ps = db.prepareStatement("INSERT INTO dentalclinic.insuranceclaim "
									   + "VALUES (?, ?, ?, ?)");
		
				ps.setString(1, claim.getPatientSIN());	
				ps.setString(2, claim.getInsuranceCompany());	
	            ps.setInt(3, Integer.parseInt(claim.getInvoiceID()));
				ps.setFloat(4, Float.parseFloat(claim.getInsuranceAmount()));
				 
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
		
		//Returns the branch by its ID
		public Branch getBranchByBranchID(String branchID) {
			
			getConn();

			Branch branch = new Branch();
			
			try {
				ps = db.prepareStatement("SELECT * from dentalclinic.branch "
						               + "WHERE branchID=?");
				
				ps.setInt(1, Integer.parseInt(branchID));
				
				rs = ps.executeQuery();
				
				while(rs.next()){
					//col1: branchID already have
					String city = rs.getString("city");
					String province = rs.getString("province");
					String managerID = rs.getString("managerID");
					branch = new Branch(branchID, city, province, managerID);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	        	closeDB();
	        }
			return branch;
		}
		
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

