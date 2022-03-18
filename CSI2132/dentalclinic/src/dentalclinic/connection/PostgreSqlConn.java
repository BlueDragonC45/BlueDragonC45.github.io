package dentalclinic.connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dentalclinic.entities.Room; 


public class  PostgreSqlConn{
	
		Connection db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Statement st = null;
	    String sql;


		public void getConn(){
			
			try {
				
				Class.forName("org.postgresql.Driver"); 
								
				db = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres",
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
		
		public String[] getUserInforByEmployeeUsername(String userName){
			getConn();

			String[] values = new String[15];
			
	        try{
	            ps = db.prepareStatement("select * from dentalclinic.employee where username=?");
	            
	            ps.setString(1, userName);	               
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					values[0] = rs.getString(1);//SIN
					values[1] = rs.getString(2);//BranchID
					values[2] = rs.getString(3);//Username
					//values[] = rs.getString(4); do not need pwd
					values[3] = rs.getString(5);//Role
					values[4] = rs.getString(6);//EmployeeType
					values[5] = rs.getString(7);//Salary
					values[6] = rs.getString(8);//FirstName
					values[7] = rs.getString(9);//MiddleName
					values[8] = rs.getString(10);//LastName
					values[9] = rs.getString(11);//DateofBirth
					values[10] = rs.getString(12);//Age
					values[11] = rs.getString(13);//Gender
					values[12] = rs.getString(14);//EmployeeEmail
					values[13] = rs.getString(15);//EmployeePhoneNumber
					values[14] = rs.getString(16);//Address
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return values;       
	    }
		
		public String[] getUserInforByPatientUsername(String userName){
			getConn();

			String[] values = new String[11];
			
	        try{
	            ps = db.prepareStatement("select * from dentalclinic.patient where username=?");
	            
	            ps.setString(1, userName);	               
	            rs = ps.executeQuery();
	
				while(rs.next()) {
					values[0] = rs.getString(1);//SIN
					values[1] = rs.getString(2);//Username
					//values[] = rs.getString(3);//do not need pwd
					values[2] = rs.getString(4);//FirstName
					values[3] = rs.getString(5);//MiddleName
					values[4] = rs.getString(6);//LastName
					values[5] = rs.getString(7);//DateOfBirth
					values[6] = rs.getString(8);//Age
					values[7] = rs.getString(9);//Gender
					values[8] = rs.getString(10);//PatientEmail
					values[9] = rs.getString(11);//PatientPhoneNumber
					values[10] = rs.getString(12);//Address
				}
	            
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally {
	        	closeDB();
	        }
			return values;       
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
		
		
		
		
		
//		public static void main(String []args) {
//			PostgreSqlConn con = new PostgreSqlConn();
//			con.getConn();
//			String values = con.getpwdbyUname("8366341");
//			
//			System.out.println(values);
//				
//			
//			
//		}

		
	}

