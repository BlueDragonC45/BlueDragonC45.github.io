package dentalclinic.entities;

public class Patient {

	private String patientSIN;
	private String userName;
	private String firstName;
	private String middleName;
	private String lastName;
	private String dateOfBirth;
	private String age;
	private String gender;
	private String patientEmail;
	private String patientPhoneNumber;
	private String address;
	private String guardian;
	
	public Patient() {
		
	}
	
	public Patient(String patientSIN, String userName, String firstName, String middleName, String lastName,
					   String dateOfBirth, String age, String gender, String patientEmail,
					   String patientPhoneNumber, String address, String guardian) {
		this.patientSIN = patientSIN;
		this.userName = userName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.age = age;
		this.gender = gender;
		this.patientEmail = patientEmail;
		this.patientPhoneNumber = patientPhoneNumber;
		this.address = address;
		this.guardian = guardian;
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

	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public String getGuardian() {
		return guardian;
	}
	public void setGuardian(String guardian) {
		this.guardian = guardian;
	}
}
