package dentalclinic.entities;

public class Appointment {
	
	private String appointmentDate;
	private String appointmentStartTime;
	private String appointmentEndTime;
	private String appointmentType;
	private String roomID;
	private String branchID;
	private String invoiceID;
	private String status;
	private String[] employeeSINList;
	
	public Appointment() {
		
	}
	
	public Appointment(String appointmentDate, String appointmentStartTime, String roomID,
					   String branchID, String[] employeeSINList, String invoiceID,
					   String appointmentEndTime, String appointmentType, String status) {
		this.appointmentDate = appointmentDate;
		this.appointmentStartTime = appointmentStartTime;
		this.appointmentEndTime = appointmentEndTime;
		this.appointmentType = appointmentType;
		this.roomID = roomID;
		this.branchID = branchID;
		this.invoiceID = invoiceID;
		this.status = status;
		this.employeeSINList = employeeSINList;
	}
	
	public String toString() {
		String str = "A "+status+" "+appointmentType+" from "+appointmentDate+" at "+appointmentStartTime
				+" to "+appointmentEndTime+" at room "+roomID+".";
		return str;
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

	public String getAppointmentEndTime() {
		return appointmentEndTime;
	}

	public void setAppointmentEndTime(String appointmentEndTime) {
		this.appointmentEndTime = appointmentEndTime;
	}
	
	public String getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(String appointmentType) {
		this.appointmentType = appointmentType;
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

	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}

	public String getInvoiceID() {
		return invoiceID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[] getEmployeeSINList() {
		return employeeSINList;
	}

	public void setEmployeeSINList(String[] employeeSINList) {
		this.employeeSINList = employeeSINList;
	}
}
