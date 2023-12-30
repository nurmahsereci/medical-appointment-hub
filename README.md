# medical-appointment-hub
MedAppointmentHub
-----------------

In this application there are 3 java packages
1. database
2. services
3. servlets

1. Database package

In this package there are 4 java files.

* Appointment.java
* Doctor.java
* Specialty.java
     Appointment.java, Doctor.java, Specialty.java are simple POJO Classes (Plain Old Java Object) that contains attributes required to store Appointments, Doctors, Specialty values in the database.
* DbConnection.java
     DbConnection.java is java class that returns JDBC connection string.

2. Services package

In this package there are 2 main services:

* DoctorService.java
In this class there are 3 static methods:
     getAllDoctorsBySpecialty()
     getAllSpecialties()
     getSpecialtyById()


* PatientService.java
In this class there are 3 methods: 
     checkDoctorAvailability()
     checkAlreadyReserved()
     makeAnAppointment()

3. Servlets package

In this package there are 3 servlet classes:
* HomeServlet.java
     This is the entry point of the entire application. When we run the application it runs this servlet first and check the java class to see more details.

* DoctorNamesBySpecialty.java
     This servlet helps to get doctor names by the specialty.

*MakeAnAppointment.java
     This servlet helps to make a new appointment.


How the application works
--------------------------

in the home.jsp there is a select menu to select the specialty in the first form.
when select the specialty the specialty id goes to DoctorNamesBySpecialty servlet and get all the doctors with that selected specialty
all the doctors are added to the request and in the jsp we can access the request by calling requestScope.doctors and render all the doctor names in the another select menu of the second form.
after we click the make an appointment button it calls the click event in the app.js file. 

see the app.js for more details. src/main/webapp/static/js/app.js





