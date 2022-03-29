package dentalclinic.entities;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

@NoArgsConstructor  
@AllArgsConstructor 

public class Employee {

	private @Getter @Setter String employeeSIN;
	private @Getter @Setter String userName;
	private @Getter @Setter String employeePwd;
	private @Getter @Setter String branchID;
	private @Getter @Setter String firstName;
	private @Getter @Setter String middleName;
	private @Getter @Setter String lastName;
	private @Getter @Setter String role;
	private @Getter @Setter String employeeType;
	private @Getter @Setter String salary;
	private @Getter @Setter String dateofBirth;
	private @Getter @Setter String age;
	private @Getter @Setter String gender;
	private @Getter @Setter String employeeEmail;
	private @Getter @Setter String employeePhoneNumber;
	private @Getter @Setter String address;
	
	public String toString() {
		String str = firstName+" "+lastName+": a "+age+" year-old "+employeeType+" "+role
				+" identifying as: "+gender+".\nContact them at: "+employeeEmail;
		return str;
	}
}
