package dentalclinic.entities;

public class Treatment {

	private String treatmentID;
	private String treatmentStartTime;
	private String treatmentEndTime;
	private String appointmentDate;
	private String appointmentStartTime;
	private String roomID;
	private String branchID;
	private String procedureDate;
	private String procedureStartTime;
	private String procedureRoomID;
	private String procedureBranchID;
	private String patientSIN;
	private String[] employeeSINList;
	private String treatmentType;
	private String medication;
	private String symptoms;
	private String tooth;
	private String comments;
	
	public Treatment() {
		
	}
	
	public Treatment(String treatmentID, String treatmentStartTime, String treatmentEndTime,
					 String appointmentDate, String appointmentStartTime, String roomID, 
					 String branchID, String procedureDate, String procedureStartTime,
					 String procedureRoomID, String procedureBranchID,  String patientSIN,
					 String[] employeeSINList, String treatmentType, String medication,
					 String symptoms, String tooth, String comments) {
		this.treatmentID = treatmentID;
		this.treatmentStartTime = treatmentStartTime;
		this.treatmentEndTime = treatmentEndTime;
		this.appointmentDate = appointmentDate;
		this.appointmentStartTime = appointmentStartTime;
		this.roomID = roomID;
		this.branchID = branchID;
		this.procedureDate = procedureDate;
		this.procedureStartTime = procedureStartTime;
		this.procedureRoomID = procedureRoomID;
		this.procedureBranchID = procedureBranchID;
		this.patientSIN = patientSIN;
		this.employeeSINList = employeeSINList;
		this.treatmentType = treatmentType;
		this.medication = medication;
		this.symptoms = symptoms;
		this.tooth = tooth;
		this.comments = comments;
	}
	
	public String toString() {
		String str = "A treatment from "+appointmentDate+ " involving a "+treatmentType+
				   " of the tooth #"+tooth+" making use of: "+medication+". "+
				    "Based on a procedure from "+procedureDate+".";
		return str;
	}

	public String getTreatmentID() {
		return treatmentID;
	}

	public void setTreatmentID(String treatmentID) {
		this.treatmentID = treatmentID;
	}

	public String getTreatmentStartTime() {
		return treatmentStartTime;
	}

	public void setTreatmentStartTime(String treatmentStartTime) {
		this.treatmentStartTime = treatmentStartTime;
	}

	public String getTreatmentEndTime() {
		return treatmentEndTime;
	}

	public void setTreatmentEndTime(String treatmentEndTime) {
		this.treatmentEndTime = treatmentEndTime;
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

	public String getProcedureDate() {
		return procedureDate;
	}

	public void setProcedureDate(String procedureDate) {
		this.procedureDate = procedureDate;
	}

	public String getProcedureStartTime() {
		return procedureStartTime;
	}

	public void setProcedureStartTime(String procedureStartTime) {
		this.procedureStartTime = procedureStartTime;
	}

	public String getProcedureRoomID() {
		return procedureRoomID;
	}

	public void setProcedureRoomID(String procedureRoomID) {
		this.procedureRoomID = procedureRoomID;
	}

	public String getProcedureBranchID() {
		return procedureBranchID;
	}

	public void setProcedureBranchID(String procedureBranchID) {
		this.procedureBranchID = procedureBranchID;
	}
	
	public String getPatientSIN() {
		return patientSIN;
	}

	public void setPatientSIN(String patientSIN) {
		this.patientSIN = patientSIN;
	}
	
	public String[] getProcedureCode() {
		return employeeSINList;
	}

	public void setProcedureCode(String[] employeeSINList) {
		this.employeeSINList = employeeSINList;
	}
	
	public String getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(String treatmentType) {
		this.treatmentType = treatmentType;
	}
	
	public String getMedication() {
		return medication;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}
	
	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}
	
	public String getTooth() {
		return symptoms;
	}

	public void setTooth(String tooth) {
		this.tooth = tooth;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


}
