package database;

public class Doctor {
	
	// attributes of the doctor
	private int doctorId;
	private String doctorName;
	private int specialtyID;
	
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public int getSpecialtyID() {
		return specialtyID;
	}
	public void setSpecialtyID(int specialtyID) {
		this.specialtyID = specialtyID;
	}
	
	
}
