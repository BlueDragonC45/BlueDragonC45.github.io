package dentalclinic.entities;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

@NoArgsConstructor  
@AllArgsConstructor  

public class Invoice  {  
	
	private @Getter @Setter String invoiceID;  
	private @Getter @Setter String dateOfIssue;  
	private @Getter @Setter String patientSIN;  
	private @Getter @Setter String patientCharge;  
	private @Getter @Setter String insuranceCharge;  
	private @Getter @Setter String totalFeeCharge;  
	private @Getter @Setter String discount;  
	private @Getter @Setter String penalty;  
	
	public String toString() {
		String str = "["+invoiceID+"] Issued "+dateOfIssue
				  + " for patient with SIN: "+patientSIN+". "
				   + "Total: "+totalFeeCharge+".";
		return str;
	}
	public String toStringLonger() {
		String str = "["+invoiceID+"] Issued "+dateOfIssue
				  + " for patient with SIN: "+patientSIN+". "
				   + "Total: "+totalFeeCharge+" | Patient Portion: "
				     +patientCharge+" | Insurance Portion"
				     +insuranceCharge+" | Discount: "+discount
				  + " | Penalty: "+penalty;
		return str;
	}
}  