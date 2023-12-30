package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import database.Appointment;
import database.DbConnection;

public class PatientService {
	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;

	
	// This method checks doctor availability
	// This method gets doctor id and date and time of the appointment provided by the patient
	public static boolean checkDoctorAvailability(int doctorId, String dateAndTime) {

		// translate the date and time to LocalDateTime object
		LocalDateTime appointmentDateTime = LocalDateTime.parse(dateAndTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		boolean result = false;

		try {
			connection = DbConnection.getDbConnection();
			
			// The query that checks whether there is a doctor according to the doctorID and the available time in the DoctorAvailability Table
			// This query should be returns 1 , That means there is at least one doctor present the given condition
			String query = "SELECT COUNT(*) FROM DoctorAvailability WHERE DoctorID = ? AND AvailableDateTime = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(2,
					appointmentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			resultSet = preparedStatement.executeQuery();
			result = resultSet.next() && resultSet.getInt(1) > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	// This method checks the appointment already booked or not by the doctor id and date time
	public static boolean checkAlreadyReserved(int doctorId, String dateAndTime) {
		
		// convert the date and time given by patient to the LocalDateTime object
		LocalDateTime appointmentDateTime = LocalDateTime.parse(dateAndTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		boolean result = false;

		try {
			connection = DbConnection.getDbConnection();
			
			// The query that checks whether there is a doctor according to the doctorID and the available time in the Appointments table
			String query = "SELECT COUNT(*) FROM Appointments WHERE DoctorID = ? AND AppointmentDateTime = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(2,
					appointmentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			resultSet = preparedStatement.executeQuery();
			result = resultSet.next() && resultSet.getInt(1) > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		// return the result as a boolean
		return result;
	}

	// This method makes new appointment in the appointment table
	// This method takes Appointment object as the parameter
	public static String makeAnAppointment(Appointment appointment) {
		String result = null;
		
		// Check the doctor availability by doctor id and date time
		// This must return True to make a appointment by a patient
		boolean isAvailable = PatientService.checkDoctorAvailability(appointment.getDoctorId(),
				appointment.getDateAndTime());
		
		// check the time slot already reserved or not
		// This must return False to make a appointment by a patient
		boolean isReserved = PatientService.checkAlreadyReserved(appointment.getDoctorId(),
				appointment.getDateAndTime());

		// According to the given time and according to the doctor id, checking whether the relevant doctor is there and whether that doctor has any appointment at that time or not.
		// then patient can make an appointment
		if (isAvailable == true && isReserved == false) {
			try {
				connection = DbConnection.getDbConnection();
				
				// query to enter patient details to the Appointment table
				String query = "INSERT INTO Appointments (PatientName,PatientEmail,PatientPhone, AppointmentDateTime, DoctorID) VALUES(?,?,?,?,?)";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, appointment.getPatientName());
				preparedStatement.setString(2, appointment.getPatientEmail());
				preparedStatement.setString(3, appointment.getPatientPhone());
				preparedStatement.setString(4, appointment.getDateAndTime());
				preparedStatement.setInt(5, appointment.getDoctorId());
				preparedStatement.executeUpdate();
				
				// result should be OK
				result = "OK";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(isAvailable == false) {
			
			// if isAvailable == false means doctor is not available in the given time slot
			// result should be Unavailable
			result = "Unavailable";
		}else if(isReserved == true) {
			
			// if isReserved == true means the time slot already reserved by a patient
			// result should be Reserved
			result = "Reserved";
		}

		// returns the result as a string
		return result;
	}
}
