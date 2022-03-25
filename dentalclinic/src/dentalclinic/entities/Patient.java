package dentalclinic.entities;

public class Patient {

	private String patientSIN;
	private String userName;
	private String firstName;
	private String middleName;
	private String lastName;
	private String dateofBirth;
	private String age;
	private String gender;
	private String patientEmail;
	private String patientPhoneNumber;
	private String address;
	
	public Patient() {
		
	}
	
	public Patient(String patientSIN, String userName, String firstName, String middleName, String lastName,
					   String dateofBirth, String age, String gender,
					   String patientEmail, String patientPhoneNumber, String address) {
		this.patientSIN = patientSIN;
		this.userName = userName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.dateofBirth = dateofBirth;
		this.age = age;
		this.gender = gender;
		this.patientEmail = patientEmail;
		this.patientPhoneNumber = patientPhoneNumber;
		this.address = address;
	}
	
	public String toString() {
		String str = firstName+" "+lastName+": a "+age+" year-old identifying as: "+gender
				+".\nContact them at: "+patientEmail;
		return str;
	}
	
	public String getPatientSIN() {
		return patientSIN;
	}
	public void setPatientSIN(String patientSIN) {
		this.patientSIN = patientSIN;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getPatientEmail() {
		return patientEmail;
	}
	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	public String getPatientPhoneNumber() {
		return patientPhoneNumber;
	}
	public void setPatientPhoneNumber(String patientPhoneNumber) {
		this.patientPhoneNumber = patientPhoneNumber;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
