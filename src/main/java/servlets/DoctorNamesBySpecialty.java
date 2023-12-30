package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.DoctorService;

import java.io.IOException;
import java.util.ArrayList;

import database.Doctor;


public class DoctorNamesBySpecialty extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoctorNamesBySpecialty() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	// When we select the specialty in the jsp it comes to this request as a POST request
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// get the specialty id from the parameter
		int specialtyId = Integer.parseInt(request.getParameter("specialty"));
		
		// get all the doctors by specialty id
		ArrayList<Doctor> doctors = DoctorService.getAllDoctorsBySpecialty(specialtyId);
		
		// get the specialty name by the specialty id
		// this is a additional step for the UI
		String specialtyName = DoctorService.getSpecialtyById(specialtyId);
		
		// add doctors to the request attribute as doctors
		request.setAttribute("doctors", doctors);
		
		// add specialtyName to the request as specialty
		// this helps to show the selected specialty of a patient in the select dropdown menu
		// you can check that by commenting this line 
		request.setAttribute("specialty", specialtyName);
		request.getRequestDispatcher("HomeServlet").forward(request, response);
	}

}
