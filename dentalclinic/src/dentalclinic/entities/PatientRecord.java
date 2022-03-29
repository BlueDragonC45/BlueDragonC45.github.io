package dentalclinic.entities;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

@NoArgsConstructor  
@AllArgsConstructor  

public class PatientRecord {

	private @Getter @Setter String treatmentID;
	private @Getter @Setter String treatmentDetails;
	
	public String toString() {
		String str = "A patient record for treatment #"+treatmentID+": "+treatmentDetails+".";
		return str;
	}
}
