package dentalclinic.entities;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;   

@NoArgsConstructor  
@AllArgsConstructor  

public class Patient {

	private @Getter @Setter String patientSIN;
	private @Getter @Setter String userName;
	private @Getter @Setter String firstName;
	private @Getter @Setter String middleName;
	private @Getter @Setter String lastName;
	private @Getter @Setter String dateOfBirth;
	private @Getter @Setter String age;
	private @Getter @Setter String gender;
	private @Getter @Setter String patientEmail;
	private @Getter @Setter String patientPhoneNumber;
	private @Getter @Setter String address;
	private @Getter @Setter String guardianSIN;
	
	public String toString() {
		String str = firstName+" "+lastName+": a "+age+" year-old identifying as: "+gender
				+".\nContact them at: "+patientEmail;
		return str;
	}
}
