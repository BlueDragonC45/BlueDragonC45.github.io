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
	private @Getter @Setter String[] teethInvolved;
	private @Getter @Setter String treatmentDetails;
	
	public String toString() {
		String str = "A treatment for the patient with SIN: "
				     +patientSIN+" involving the teeth: "
			         +Arrays.toString(teethInvolved).replaceAll("\\]","").replaceAll("\\[","")
			       +" (FDI Notation). Details: "+treatmentDetails;
		return str;
	}
}
