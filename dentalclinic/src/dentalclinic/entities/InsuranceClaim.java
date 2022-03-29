package dentalclinic.entities;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

@NoArgsConstructor  
@AllArgsConstructor 

public class InsuranceClaim {

	private @Getter @Setter String patientSIN;
	private @Getter @Setter String insuranceCompany;
	private @Getter @Setter String invoiceID;
	private @Getter @Setter String insuranceAmount;
	
	public String toString() {
		String str = "Patient with SIN: "+patientSIN+" filed an insurance claim from "
					 +insuranceCompany+" to cover "+insuranceAmount+" CAD of the invoice"
					 +"["+invoiceID+"].";
		return str;
	}
}
