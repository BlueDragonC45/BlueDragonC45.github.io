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
	private @Getter @Setter String guardianSIN;  
	private @Getter @Setter String userCharge;  
	private @Getter @Setter String insuranceCharge;  
	private @Getter @Setter String employeeCharge; 
	private @Getter @Setter String totalFeeCharge;  
	private @Getter @Setter String discount;  
	private @Getter @Setter String penalty;  
	
	public String toString() {
		String patientOrGuardian = "";
		if (guardianSIN == null) {
			patientOrGuardian = patientSIN;
		} else if (guardianSIN.isEmpty()) {
			patientOrGuardian = patientSIN;
		} else {
			patientOrGuardian = guardianSIN;
		}
		
		String str = "["+invoiceID+"] Issued "+dateOfIssue
				  + " for user with SIN: "+patientOrGuardian+". "
				   + "Total: "+totalFeeCharge+" CAD.";
		return str;
	}
	public String toStringLonger() {
		String patientOrGuardian = "";
		if (guardianSIN == null) {
			patientOrGuardian = patientSIN;
		} else if (guardianSIN.isEmpty()) {
			patientOrGuardian = patientSIN;
		} else {
			patientOrGuardian = guardianSIN;
		}
		
		String str = "["+invoiceID+"] Issued "+dateOfIssue
				  + " for user with SIN: "+patientOrGuardian+". "
				   + "Total: "+totalFeeCharge+" | User Portion: "
				     +userCharge+" | Insurance Portion: "
				     +insuranceCharge+" | Employee Portion: "+employeeCharge
				   +" | Discount: "+discount
				  + " | Penalty: "+penalty;
		return str;
	}
}