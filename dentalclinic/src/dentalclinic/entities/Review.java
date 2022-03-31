package dentalclinic.entities;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

@NoArgsConstructor  
@AllArgsConstructor 

public class Review {

	private @Getter @Setter String patientSIN;
	private @Getter @Setter String appointmentID;
	private @Getter @Setter String reviewDate;
	private @Getter @Setter String reviewTime;
	private @Getter @Setter String employeeProfessionalism;
	private @Getter @Setter String communication;
	private @Getter @Setter String cleanliness;
	private @Getter @Setter String comments;
	
	public String toString() {
		String str = reviewDate+" "+reviewTime+": For appointment ["+
					 patientSIN+", "+appointmentID+"] Professionalism: "+
					 employeeProfessionalism+" | Communication: "+
					 communication+" | Cleanliness: "+cleanliness+
					 " | Comments: "+comments;
		return str;
	}
}
