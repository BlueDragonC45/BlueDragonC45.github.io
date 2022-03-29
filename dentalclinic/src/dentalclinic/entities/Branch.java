package dentalclinic.entities;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

@NoArgsConstructor  
@AllArgsConstructor 

public class Branch {

	private @Getter @Setter String branchID;
	private @Getter @Setter String city;
	private @Getter @Setter String province;
	private @Getter @Setter String managerID;
	
	public String toString() {
		String str = province+", "+city;
		return str;
	}
}
