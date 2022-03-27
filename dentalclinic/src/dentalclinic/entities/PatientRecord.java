package dentalclinic.entities;

public class PatientRecord {

	private String treatmentID;
	private String treatmentDetails;
	
	public PatientRecord() {
		
	}
	
	public PatientRecord(String treatmentID, String treatmentDetails) {
		this.treatmentID = treatmentID;
		this.treatmentDetails = treatmentDetails;
	}
	
	public String toString() {
		String str = "A patient record for treatment #"+treatmentID+": "+treatmentDetails+".";
		return str;
	}
	
	public String getTreatmentID() {
		return treatmentID;
	}
	public void setTreatmentID(String treatmentID) {
		this.treatmentID = treatmentID;
	}

	public String getTreatmentDetails() {
		return treatmentDetails;
	}
	public void setTreatmentDetails(String treatmentDetails) {
		this.treatmentDetails = treatmentDetails;
	}
}
