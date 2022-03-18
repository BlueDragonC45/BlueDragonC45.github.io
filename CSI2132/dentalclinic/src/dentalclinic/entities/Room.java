package dentalclinic.entities;

public class Room {
	
	private String roomID;
	private String branchID;
	private String roomStatus;
	private String patientSIN;
	
	public Room() {
		
	}
	
	public Room(String roomID, String branchID, String roomStatus) {
		this.roomID = roomID;
		this.branchID = branchID;
		this.roomStatus = roomStatus;
	}

	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	public String getBranchID() {
		return branchID;
	}

	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}
	
	public String getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}

	public String getPatientSIN() {
		return patientSIN;
	}

	public void setPatientSIN(String patientSIN) {
		this.patientSIN = patientSIN;
	}
	

}
