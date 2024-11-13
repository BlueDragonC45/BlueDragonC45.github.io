package dentalclinic.entities;

import lombok.AllArgsConstructor;  
import lombok.Getter;  
import lombok.NoArgsConstructor;  
import lombok.Setter;  

@NoArgsConstructor  
@AllArgsConstructor 

public class Room {

	private @Getter @Setter String roomID;
	private @Getter @Setter String branchID;
	
	public String toString() {
		String str = "Room #"+roomID;
		return str;
	}
}
