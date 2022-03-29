package dentalclinic.entities;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

@NoArgsConstructor  
@AllArgsConstructor

public class PatientBilling {

	private @Getter @Setter String patientSIN;
	private @Getter @Setter String invoiceID;
	private @Getter @Setter String guardianSIN;
	private @Getter @Setter String employeeSIN;
	private @Getter @Setter String userPortion;
	private @Getter @Setter String employeePortion;
	private @Getter @Setter String insurancePortion;
	private @Getter @Setter String totalAmount;
	private @Getter @Setter String paymentType;
	
	public String toString() {
		String str = "["+patientSIN+", "+invoiceID+"] Accounting for "
					 +totalAmount+" CAD towards invoice ["+invoiceID+"].";
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
		
		String str = "["+patientSIN+", "+invoiceID+"] Accounting for "
					 +totalAmount+" CAD towards invoice ["+invoiceID+"]."
				   +" The user with SIN: "+patientOrGuardian+" paid "+userPortion+" CAD "
				   + "in "+paymentType+" with "+insurancePortion+" CAD from insurance. "
				   + "An employee covered "+employeePortion+" CAD of the bill.";
		return str;
	}
}
