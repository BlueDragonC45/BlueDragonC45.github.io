package dentalclinic.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Employee;

public class UpdateEmployeeInfoServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PostgreSqlConn con = new PostgreSqlConn();
		
		String employeeSINEdit = req.getParameter("sinEE");
		if (employeeSINEdit == null) {
			
			String employeeSINEE = req.getParameter("employeeSIN");
			System.out.println(employeeSINEE);
			Employee employee = con.getUserInfoByEmployeeSIN(employeeSINEE);
			
			req.setAttribute("employee", employee);
			
			req.getRequestDispatcher("manager_view.jsp").forward(req, resp);
		} else {
			System.out.println("Employee to update: "+ employeeSINEdit);
			
			Employee newEmployeeInfo = new Employee();

			String userName = req.getParameter("usernameEE");
			newEmployeeInfo.setUserName(userName);
			
			String branchId = req.getParameter("branchIDEE");
			newEmployeeInfo.setBranchID(branchId);
			
			String firstName = req.getParameter("fNameEE");
			newEmployeeInfo.setFirstName(firstName);
			
			String middleName = req.getParameter("mNameEE");
			newEmployeeInfo.setMiddleName(middleName);
			
			String lastName = req.getParameter("lNameEE");
			newEmployeeInfo.setLastName(lastName);
			
			String dateOfBirth = req.getParameter("dobEE");
			newEmployeeInfo.setDateofBirth(dateOfBirth);
			
			String age = req.getParameter("ageEE");//TODO calculate in a function
			newEmployeeInfo.setAge(age);
			
			String gender = req.getParameter("genderEE");
			newEmployeeInfo.setGender(gender);
			
			String patientEmail = req.getParameter("emailEE");
			newEmployeeInfo.setEmployeeEmail(patientEmail);
			
			String patientPhoneNumber = req.getParameter("phoneEE");
			newEmployeeInfo.setEmployeePhoneNumber(patientPhoneNumber);
			
			String address = req.getParameter("addressEE");
			newEmployeeInfo.setAddress(address);
			
			String role = req.getParameter("roleEE");
			newEmployeeInfo.setRole(role);
			
			String employeeType = req.getParameter("employeeTypeEE");
			newEmployeeInfo.setEmployeeType(employeeType);
			
			String salary = req.getParameter("salaryEE");
			newEmployeeInfo.setSalary(salary);
			
			if (con.updateEmployeeInfo(newEmployeeInfo, employeeSINEdit)) {//successful
				
				//something happens if successful
				req.setAttribute("firstNameNEW", firstName);
				req.setAttribute("lastNameNEW", lastName);
				req.setAttribute("outcome", "updateSuccess");
				req.getRequestDispatcher("manager_view.jsp").forward(req, resp);
				
			} else {//failed; could be an SQL error
				
				//something happens if failed
				req.setAttribute("firstNameNEW", firstName);
				req.setAttribute("lastNameNEW", lastName);
				req.setAttribute("outcome", "updateFailed");
				
				req.getRequestDispatcher("manager_view.jsp").forward(req, resp);
				
			}
		}
	}
}

