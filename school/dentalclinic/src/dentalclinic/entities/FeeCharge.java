package dentalclinic.entities;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

@NoArgsConstructor  
@AllArgsConstructor 

public class FeeCharge {

	private @Getter @Setter String feeID;
	private @Getter @Setter String invoiceID;
	private @Getter @Setter String feeCode;
	private @Getter @Setter String charge;
	
	public String toString() {
		String str = "["+feeCode+"] "+charge+" CAD";
		return str;
	}
}
