package dentalclinic.entities;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

@NoArgsConstructor  
@AllArgsConstructor

public class Treatment {

	private @Getter @Setter String treatmentID;
	private @Getter @Setter String treatmentStartTime;
	private @Getter @Setter String treatmentEndTime;
	private @Getter @Setter String appointmentDate;
	private @Getter @Setter String appointmentStartTime;
	private @Getter @Setter String roomID;
	private @Getter @Setter String branchID;
	private @Getter @Setter String procedureDate;
	private @Getter @Setter String procedureStartTime;
	private @Getter @Setter String procedureRoomID;
	private @Getter @Setter String procedureBranchID;
	private @Getter @Setter String patientSIN;
	private @Getter @Setter String[] employeeSINList;
	private @Getter @Setter String treatmentType;
	private @Getter @Setter String medication;
	private @Getter @Setter String symptoms;
	private @Getter @Setter String tooth;
	private @Getter @Setter String comments;
	
	public String toString() {
		String str = "A treatment from "+appointmentDate+ " involving a "+treatmentType+
				   " of the tooth #"+tooth+" making use of: "+medication+". "+
				    "Based on a procedure from "+procedureDate+".";
		return str;
	}
}
