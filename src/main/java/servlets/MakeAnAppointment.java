package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.PatientService;

import java.io.IOException;
import java.io.PrintWriter;

import database.Appointment;

/**
 * Servlet implementation class MakeAnAppointment
 */
public class MakeAnAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MakeAnAppointment() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	// when the patient clicks make appointment button the request come to this
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get the patient name
		String patientName = request.getParameter("patientName");

		// get the patient phone
		String patientPhone = request.getParameter("patientPhone");

		// get the patient email
		String patientEmail = request.getParameter("patientEmail");

		// get the date and time
		String dateAndTime = request.getParameter("dateAndTime");

		// get the doctor id and convert into integer value
		int doctor = Integer.parseInt(request.getParameter("doctor"));

		// make a new appointment object
		Appointment appointment = new Appointment();

		// calling the setter method
		appointment.setDoctorId(doctor);
		appointment.setPatientName(patientName);
		appointment.setPatientEmail(patientEmail);
		appointment.setPatientPhone(patientPhone);
		appointment.setDateAndTime(dateAndTime);

		// calling the make an appointment method by passing appointment object
		String result = PatientService.makeAnAppointment(appointment);

		// define the message to null
		String message = null;

		// define content type to application/json because this request id done by the
		// javascript
		// return should be a json response
		response.setContentType("application/json");

		// check the results
		// if result is Unavailable that means doctor is not available
		if (result.equals("Unavailable")) {

			// assign the message
			message = "{\"message\": \"Doctor is unavailable\"}";
		} else if (result.equals("Reserved")) {
			message = "{\"message\": \"This doctor has been booked by a patient\"}";
		} else if (result.equals("OK")) {
			response.setStatus(HttpServletResponse.SC_OK);
			message = "{\"message\": \"your appointment request has been successfully received\"}";
		}

		// return the message as a json
		try (PrintWriter out = response.getWriter()) {
			out.print(message);
			out.flush();
		}

	}

}
