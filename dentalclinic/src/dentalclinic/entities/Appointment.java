package dentalclinic.entities;

public class Appointment {
	
	private String appointmentDate;
	private String appointmentType;
	private String startTime;
	private String endTime;
	private String roomID;
	private String status;
	
	public Appointment() {
		
	}
	
	public Appointment(String appointmentDate, String appointmentType, String startTime,
					   String endTime, String roomID, String status) {
		this.appointmentDate = appointmentDate;
		this.appointmentType = appointmentType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roomID = roomID;
		this.status = status;
	}
	
	public String toString() {
		String str = "A "+status+" "+appointmentType+" from "+appointmentDate+" at "+startTime
				+" to "+endTime+" at room "+roomID+".";
		return str;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(String appointmentType) {
		this.appointmentType = appointmentType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
