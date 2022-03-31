package dentalclinic.entities;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

import java.util.Arrays;

@NoArgsConstructor  
@AllArgsConstructor 

public class AppointmentProcedure {

	private @Getter @Setter String appointmentID;
	private @Getter @Setter String toothInvolved;
	private @Getter @Setter String procedureCode;
	private @Getter @Setter String procedureType;
	private @Getter @Setter String[] materialsAndAmountUsed;
	private @Getter @Setter String description;
	
	public String toString() {
		String str = "["+appointmentID+", "+toothInvolved+ ", "+procedureCode+"] "
				   + "A procedure where "+procedureType+" was performed on tooth "
				   + toothInvolved+". Used: "+Arrays.toString(materialsAndAmountUsed)+".";
		return str;
	}
}
