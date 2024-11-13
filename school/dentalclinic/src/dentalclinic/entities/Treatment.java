package dentalclinic.entities;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

import java.util.Arrays;

@NoArgsConstructor  
@AllArgsConstructor

public class Treatment {

	private @Getter @Setter String appointmentID;
	private @Getter @Setter String toothInvolved;
	private @Getter @Setter String treatmentCode;
	private @Getter @Setter String treatmentType;
	private @Getter @Setter String[] medication;
	private @Getter @Setter String[] symptoms;
	private @Getter @Setter String comments;
	private @Getter @Setter String treatmentDate;
	
	public String toString() {
		String str = "A treatment from "+treatmentDate+" where "+treatmentType+
				    " was performed"+" on tooth "+toothInvolved+".";
		return str;
	}
	
	public String toStringLonger() {
		String str = "A treatment from "+treatmentDate+" where "+treatmentType+
				    " was performed"+" on tooth "+toothInvolved+" making use of: "+
				      Arrays.toString(medication)+". Where originally, the patient "
				   + "showed symptoms for: "+Arrays.toString(symptoms)+".";
		return str;
	}
}
