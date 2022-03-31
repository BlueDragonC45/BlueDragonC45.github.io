package dentalclinic.entities;

import java.util.Arrays;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

@NoArgsConstructor  
@AllArgsConstructor  

public class PatientRecord {

	private @Getter @Setter String patientSIN;
	private @Getter @Setter String appointmentID;
	private @Getter @Setter Integer[] teethInvolved;
	private @Getter @Setter String treatmentDetails;
	
	public String toString() {
		String str = "A treatment involving the teeth: "
			         +Arrays.toString(teethInvolved)+" (FDI Notation). Details: "+treatmentDetails;
		return str;
	}
}
