package dentalclinic.entities;

public class Guardian {

	private String guardianSIN;
	private String userName;
	private String firstName;
	private String middleName;
	private String lastName;
	private String dateOfBirth;
	private String age;
	private String gender;
	private String guardianEmail;
	private String guardianPhoneNumber;
	private String address;
	
	public Guardian() {
		
	}
	
	public Guardian(String guardianSIN, String userName, String firstName, String middleName, String lastName,
					   String dateOfBirth, String age, String gender, String guardianEmail,
					   String guardianPhoneNumber, String address) {
		this.guardianSIN = guardianSIN;
		this.userName = userName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.age = age;
		this.gender = gender;
		this.guardianEmail = guardianEmail;
		this.guardianPhoneNumber = guardianPhoneNumber;
		this.address = address;
	}
	
	public String toString() {
		String str = firstName+" "+lastName+": a "+age+" year-old identifying as: "+gender
				+".\nContact them at: "+guardianEmail;
		return str;
	}
	
	public String getGuardianSIN() {
		return guardianSIN;
	}
	public void setGuardianSIN(String guardianSIN) {
		this.guardianSIN = guardianSIN;
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

	public String getGuardianEmail() {
		return guardianEmail;
	}
	public void setGuardianEmail(String guardianEmail) {
		this.guardianEmail = guardianEmail;
	}

	public String getGuardianPhoneNumber() {
		return guardianPhoneNumber;
	}
	public void setGuardianPhoneNumber(String guardianPhoneNumber) {
		this.guardianPhoneNumber = guardianPhoneNumber;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
