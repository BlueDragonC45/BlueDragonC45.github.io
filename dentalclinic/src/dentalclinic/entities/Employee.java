package dentalclinic.entities;

public class Employee {

	private String employeeSIN;
	private String userName;
	private String employeePwd;
	private String branchID;
	private String firstName;
	private String middleName;
	private String lastName;
	private String role;
	private String employeeType;
	private String salary;
	private String dateofBirth;
	private String age;
	private String gender;
	private String employeeEmail;
	private String employeePhoneNumber;
	private String address;
	
	public Employee() {
		
	}
	
	public Employee(String employeeSIN, String userName, String employeePwd, String branchID, String firstName, 
					String middleName, String lastName, String role, String employeeType, String salary,
					String dateofBirth, String age, String gender,
					String employeeEmail, String employeePhoneNumber, String address) {
		this.employeeSIN = employeeSIN;
		this.userName = userName;
		this.employeePwd = employeePwd;
		this.branchID = branchID;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.role = role;
		this.employeeType = employeeType;
		this.salary = salary;
		this.dateofBirth = dateofBirth;
		this.age = age;
		this.gender = gender;
		this.employeeEmail = employeeEmail;
		this.employeePhoneNumber = employeePhoneNumber;
		this.address = address;
	}
	
	public String toString() {
		String str = firstName+" "+lastName+": a "+age+" year-old "+employeeType+" "+role
				+" identifying as: "+gender+".\nContact them at: "+employeeEmail;
		return str;
	}
	
	public String getEmployeeSIN() {
		return employeeSIN;
	}
	public void setEmployeeSIN(String employeeSIN) {
		this.employeeSIN = employeeSIN;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getEmployeePwd() {
		return employeePwd;
	}
	public void setEmployeePwd(String employeePwd) {
		this.employeePwd = employeePwd;
	}

	public String getBranchID() {
		return branchID;
	}
	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getDateofBirth() {
		return dateofBirth;
	}
	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getEmployeePhoneNumber() {
		return employeePhoneNumber;
	}
	public void setEmployeePhoneNumber(String employeePhoneNumber) {
		this.employeePhoneNumber = employeePhoneNumber;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
