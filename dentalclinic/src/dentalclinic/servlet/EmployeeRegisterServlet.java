package dentalclinic.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Employee;

public class EmployeeRegisterServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		//16 columns total
		String employeeSIN = req.getParameter("employeeSIN");
		String userName = req.getParameter("username");
		String employeePwd = req.getParameter("employeePwd");
		String branchID = req.getParameter("branchID");
		String firstName = req.getParameter("fName");
		String middleName = req.getParameter("mName");
		String lastName = req.getParameter("lName");
		String role = req.getParameter("role");
		String employeeType = req.getParameter("employeeType");
		String salary = req.getParameter("salary");
		String dateOfBirth = req.getParameter("dateOfBirth");
		String age = req.getParameter("age");
		System.out.println(age+" years");
		String gender = req.getParameter("gender");
		String employeeEmail = req.getParameter("email");
		String employeePhoneNumber = req.getParameter("phone");
		String address = req.getParameter("address");
		
		
		Employee employee = new Employee();
		employee.setEmployeeSIN(employeeSIN);
		employee.setUserName(userName);
		employee.setEmployeePwd(employeePwd);
		employee.setBranchID(branchID);
		employee.setFirstName(firstName);
		employee.setMiddleName(middleName);
		employee.setLastName(lastName);
		employee.setRole(role);
		employee.setEmployeeType(employeeType);
		employee.setSalary(salary);
		employee.setDateofBirth(dateOfBirth);
		employee.setAge(age);
		employee.setGender(gender);
		employee.setEmployeeEmail(employeeEmail);
		employee.setEmployeePhoneNumber(employeePhoneNumber);
		employee.setAddress(address);
		
		
		PostgreSqlConn con = new PostgreSqlConn();
		
		boolean isInserted = con.insertNewEmployee(employee, employeeSIN);
		if (isInserted) {			
				
				//firstName and lastName might have been used already
				//...at receptionist_view.jsp
				req.setAttribute("firstNameNEW", firstName);
				req.setAttribute("lastNameNEW", lastName);

				req.setAttribute("outcome", "registerSuccess");

				req.getRequestDispatcher("manager_view.jsp").forward(req, resp);
				return;			
		} else {

			req.setAttribute("firstNameNEW", firstName);
			req.setAttribute("lastNameNEW", lastName);
			
			req.setAttribute("outcome", "registerFailed");

			req.getRequestDispatcher("manager_view.jsp").forward(req, resp);
			return;
		}
		
	}
	

}
