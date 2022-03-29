package dentalclinic.entities;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

@NoArgsConstructor  
@AllArgsConstructor 

public class AppointmentProcedure {
	
	private @Getter @Setter String procedureStartTime;
	private @Getter @Setter String procedureEndTime;
	private @Getter @Setter String appointmentDate;
	private @Getter @Setter String appointmentStartTime;
	private @Getter @Setter String roomID;
	private @Getter @Setter String branchID;
	private @Getter @Setter String procedureCode;
	private @Getter @Setter String procedureType;
	private @Getter @Setter String description;
	private @Getter @Setter String toothInvolved;
	private @Getter @Setter String amountOfProcedure;
	
	public String toString() {
		String str = "A procedure from "+appointmentDate+ " involving a "+procedureType+
				    " of the tooth #"+toothInvolved+" using: "+amountOfProcedure+".";
		return str;
	}
}
