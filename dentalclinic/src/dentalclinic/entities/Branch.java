package dentalclinic.entities;

public class Branch {

	private String branchID;
	private String city;
	private String province;
	private String managerID;
	
	public Branch() {
		
	}
	
	public Branch(String branchID, String city,
			      String province, String managerID) {
		this.branchID = branchID;
		this.city = city;
		this.province = province;
		this.managerID = managerID;
	}
	
	public String toString() {
		String str = province+", "+city;
		return str;
	}
	
	public String getBranchID() {
		return branchID;
	}
	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getManagerID() {
		return managerID;
	}
	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}
}
