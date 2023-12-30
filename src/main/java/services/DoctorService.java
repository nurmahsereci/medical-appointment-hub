package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.DbConnection;
import database.Doctor;
import database.Specialty;

public class DoctorService {

	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;
	
	// This method returns all the doctors by their specialty
	// This method takes specialty ID as the parameter
	public static ArrayList<Doctor> getAllDoctorsBySpecialty(int id){
		
		ArrayList<Doctor> doctors = new ArrayList<Doctor>();
		
		try {
			connection = DbConnection.getDbConnection();
			String query = "SELECT * FROM Doctors WHERE SpecialtyID=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Doctor doc = new Doctor();
				doc.setDoctorId(resultSet.getInt("DoctorID"));
				doc.setDoctorName(resultSet.getString("DoctorName"));
				doctors.add(doc);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return doctors;
	}
	
	// This method returns all the specialties in the database
	public static ArrayList<Specialty> getAllSpecialties(){
		
		ArrayList<Specialty> specialties = new ArrayList<Specialty>();
		
		try {
			connection = DbConnection.getDbConnection();
			
			// query to get all the specialties 
			String query = "SELECT * FROM Specialties";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Specialty specialty = new Specialty();
				specialty.setSpecialtyID(resultSet.getInt("SpecialtyID"));
				specialty.setSpecialtyName(resultSet.getString("SpecialtyName"));
				specialties.add(specialty);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return specialties;
	}
	
	// This method returns specialty by id
	// This method takes specialty id as parameter
	public static String getSpecialtyById(int id) {
		String specialty = null;
		
		try {
			connection = DbConnection.getDbConnection();
			
			// query to get specialty by specialty id
			String query = "SELECT * FROM Specialties WHERE SpecialtyID=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				specialty = resultSet.getString("SpecialtyName");
			}
			
		}catch(Exception e) {
			
		}
		
		return specialty;
	}
	
}	
