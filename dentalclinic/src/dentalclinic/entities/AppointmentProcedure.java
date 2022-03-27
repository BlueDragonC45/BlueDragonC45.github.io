package dentalclinic.entities;

public class AppointmentProcedure {
	
	private String procedureStartTime;
	private String procedureEndTime;
	private String appointmentDate;
	private String appointmentStartTime;
	private String roomID;
	private String branchID;
	private String procedureCode;
	private String procedureType;
	private String description;
	private String toothInvolved;
	private String amountOfProcedure;
	
	public AppointmentProcedure() {
		
	}
	
	public AppointmentProcedure(String procedureStartTime, String procedureEndTime,
								String appointmentDate, String appointmentStartTime, String roomID, 
								String branchID, String procedureCode,  String procedureType, 
								String description, String toothInvolved, String amountOfProcedure) {
		this.procedureStartTime = procedureStartTime;
		this.procedureEndTime = procedureEndTime;
		this.appointmentDate = appointmentDate;
		this.appointmentStartTime = appointmentStartTime;
		this.roomID = roomID;
		this.branchID = branchID;
		this.procedureCode = procedureCode;
		this.procedureType = procedureType;
		this.description = description;
		this.toothInvolved = toothInvolved;
		this.amountOfProcedure = amountOfProcedure;
	}
	
	public String toString() {
		String str = "A procedure from "+appointmentDate+ " involving a "+procedureType+
				    " of the tooth #"+toothInvolved+" using: "+amountOfProcedure+".";
		return str;
	}

	public String getProcedureStartTime() {
		return procedureStartTime;
	}

	public void setProcedureStartTime(String procedureStartTime) {
		this.procedureStartTime = procedureStartTime;
	}

	public String getProcedureEndTime() {
		return procedureEndTime;
	}

	public void setProcedureEndTime(String procedureEndTime) {
		this.procedureEndTime = procedureEndTime;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getAppointmentStartTime() {
		return appointmentStartTime;
	}

	public void setAppointmentStartTime(String appointmentStartTime) {
		this.appointmentStartTime = appointmentStartTime;
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
	
	public String getProcedureType() {
		return procedureType;
	}

	public void setProcedureType(String procedureType) {
		this.procedureType = procedureType;
	}
	
	public String getProcedureCode() {
		return procedureCode;
	}

	public void setProcedureCode(String procedureCode) {
		this.procedureCode = procedureCode;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getToothInvolved() {
		return description;
	}

	public void setToothInvolved(String toothInvolved) {
		this.toothInvolved = toothInvolved;
	}
	
	public String getAmountOfProcedure() {
		return description;
	}

	public void setAmountOfProcedure(String amountOfProcedure) {
		this.amountOfProcedure = amountOfProcedure;
	}


}
