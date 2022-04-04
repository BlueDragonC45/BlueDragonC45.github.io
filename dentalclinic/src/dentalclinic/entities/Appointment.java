package dentalclinic.entities;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

@NoArgsConstructor  
@AllArgsConstructor 

public class Appointment {

	private @Getter @Setter String appointmentID;
	private @Getter @Setter String appointmentDate;
	private @Getter @Setter String appointmentStartTime;
	private @Getter @Setter String appointmentEndTime;
	private @Getter @Setter String patientSIN;
	private @Getter @Setter String roomID;
	private @Getter @Setter String branchID;
	private @Getter @Setter String invoiceID;
	private @Getter @Setter String[] employeeSINList;
	private @Getter @Setter String appointmentType;
	private @Getter @Setter String status;
	
	public String toString() {
		String str = "A "+status+" appointment ("+appointmentType+") on "+appointmentDate+", from "+appointmentStartTime
				+" to "+appointmentEndTime+" in room "+roomID+".";
		return str;
	}
}
