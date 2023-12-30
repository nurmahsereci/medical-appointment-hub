window.addEventListener("DOMContentLoaded", function() {

	// Select required html elements from home.jsp
	const submitBtn = document.getElementById("makeAppointmentBtn");
	const smallFontBtn = document.querySelector(".small");
	const mediumFontBtn = document.querySelector(".medium");
	const largeFontBtn = document.querySelector(".large");
	const labels = document.querySelectorAll("label");
	const inputBoxes = document.querySelectorAll("input");
	const darkBtn = document.querySelector(".dark");
	const lightBtn = document.querySelector(".light");
	const formsContainer = document.querySelector(".forms");


	// check if localstorage has any size variable or not
	// if there is the size variable change the font sizes according to that size
	if (localStorage.getItem("size") != null) {
	
		// get the size to style variable
		let style = localStorage.getItem("size");

		// iterate all the labels and assign the specific font size to each label
		labels.forEach(function(label) {
			label.style.fontSize = style;
		})
		
		// do it for input boxes
		inputBoxes.forEach(function(inputBox) {
			inputBox.style.fontSize = style;
		})

		// change the font size of submit button
		submitBtn.style.fontSize = style;
	}
	
	// check if localstorage has any labels variable or not
	// if there is the labels variable change the font sizes according to that size
	// this is the same as above 
	if (localStorage.getItem("labels") != null) {
		
		let labelStyle = localStorage.getItem("labels");

		labels.forEach(function(label) {
			label.style.color = labelStyle;
		})

	}

	// check if localstorage has any form variable or not
	if (localStorage.getItem("forms") != null) {
		let formsStyle = localStorage.getItem("forms");
		formsContainer.style.backgroundColor = formsStyle;
	}

	
	// when click the small font button it changes the all text size as 10px
	smallFontBtn.addEventListener("click", function() {
		labels.forEach(function(label) {
			label.style.fontSize = "10px";
		})
		inputBoxes.forEach(function(inputBox) {
			inputBox.style.fontSize = "10px";
		})

		submitBtn.style.fontSize = "10px";
		
		// finally store the size as 10px in localstorage
		// This can keep changed styles unchanged even after refreshing
		// thats why the program checks the localstorage in the initally
		localStorage.setItem("size", "10px");

	})

	// this click event for the medium font size
	mediumFontBtn.addEventListener("click", function() {
		labels.forEach(function(label) {
			label.style.fontSize = "15px";
		})
		inputBoxes.forEach(function(inputBox) {
			inputBox.style.fontSize = "15px";
		})
		submitBtn.style.fontSize = "15px";

		localStorage.setItem("size", "15px");

	})

	// this click event for the large font size
	largeFontBtn.addEventListener("click", function() {
		labels.forEach(function(label) {
			label.style.fontSize = "20px";
		})
		inputBoxes.forEach(function(inputBox) {
			inputBox.style.fontSize = "20px";
		})
		submitBtn.style.fontSize = "20px";

		localStorage.setItem("size", "20px");

	})
	
	
	// this button helps to change some colors
	darkBtn.addEventListener("click", function() {
		
		// turn label colors to white
		labels.forEach(function(label) {
			label.style.color = "#fff";
		})
		
		// form container to dark gray
		formsContainer.style.backgroundColor = "#333";
		
		// store styles in the localstorage
		localStorage.setItem("labels", "#fff");
		localStorage.setItem("forms", "#333");
	})
	
	
	// this is for the light theme
	lightBtn.addEventListener("click", function() {
		labels.forEach(function(label) {
			label.style.color = "black";
		})
		formsContainer.style.backgroundColor = "#f2f2f2";
		localStorage.setItem("labels", "black");
		localStorage.setItem("forms", "#f2f2f2");
	})
	
	// this is the submit button of appointment
	submitBtn.addEventListener("click", function() {
		
		// get all neccesary elements and assign to the variables
		const specialtyElement = document.getElementById("specialty");
		const docNameElement = document.getElementById("docName");
		const dateTimeElement = document.getElementById("datetime");
		const patientNameElement = document.getElementById("patientName");
		const patientEmailElement = document.getElementById("patientEmail");
		const patientPhoneElement = document.getElementById("patientPhone");
	
		// get the values of that elements
		const speciality = specialtyElement.value;
		const doctor = docNameElement.value;
		const patientName = patientNameElement.value;
		const patientEmail = patientEmailElement.value;
		const patientPhone = patientPhoneElement.value;
		const dateAndTime = dateTimeElement.value;

		// basic validations
		if (speciality === "Select specialty") {
			alert("Please select a specialty");
			return;
		} else if (doctor === "") {
			alert("Please select a doctor");
			return;
		} else if (patientName === "") {
			alert("Please enter your name");
			return;
		} else if (patientEmail === "") {
			alert("Please enter your email");
			return;
		} else if (patientPhone === "") {
			alert("Please enter your mobile no");
			return;
		} else if (dateAndTime === "") {
			alert("Please select the date and time");
			return;
		}

		// process date and time to doctor availability
		var selectedDate = new Date(dateAndTime);
		var selectedHours = selectedDate.getHours();
		var dayOfWeek = selectedDate.getDay();
	
		// this is cheking if selected time between 8 AM and 5 PM 
		if (selectedHours >= 8 && selectedHours < 17) {
			console.log("Selected date is between 8 AM and 5 PM.");
		} else {
			alert("Doctors are not available after 5.00 PM")
			return;
		}
	
		// this is checking the selected date is weekday or not
		if (dayOfWeek >= 1 && dayOfWeek <= 5) {
			console.log("Selected date is a weekday (Monday to Friday).");
		} else {
			alert("Doctors are not available in weekend")
			return;
		}
		
		// create new form data object
		// append the values to the form object
		const data = new FormData();
		data.append("patientName", patientName);
		data.append("patientEmail", patientEmail);
		data.append("patientPhone", patientPhone);
		data.append("doctor", doctor);
		data.append("dateAndTime", dateAndTime);
		
		// POST request to the MakeAnAppointment Servlet
		fetch('http://localhost:8081/MedAppointHub/MakeAnAppointment', {
			method: 'POST',
			body: new URLSearchParams(data),
			headers: {
				"Content-Type": "application/x-www-form-urlencoded",
			},
			responseType: 'json'
		}).then(res => res.json())
			.then(data => alert(data.message))
			.catch(err => console.log(err))



	})

})