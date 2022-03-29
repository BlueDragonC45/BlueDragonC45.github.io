package dentalclinic.entities;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

@NoArgsConstructor  
@AllArgsConstructor 

public class Appointment {
	
	private @Getter @Setter String appointmentDate;
	private @Getter @Setter String appointmentStartTime;
	private @Getter @Setter String appointmentEndTime;
	private @Getter @Setter String appointmentType;
	private @Getter @Setter String roomID;
	private @Getter @Setter String branchID;
	private @Getter @Setter String invoiceID;
	private @Getter @Setter String status;
	private @Getter @Setter String[] employeeSINList;
	
	public String toString() {
		String str = "A "+status+" "+appointmentType+" from "+appointmentDate+" at "+appointmentStartTime
				+" to "+appointmentEndTime+" at room "+roomID+".";
		return str;
	}
}
