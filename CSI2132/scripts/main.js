
function openPane(paneName) {
	validated = validate(paneName, true);
	switch(paneName) {
		case 'receptionists':
			if (validated) {
				document.getElementById("receptionistNav").style.display = "flex";
				document.getElementById("validateRec").style.display = "none";
			} else {
				document.getElementById("validateRec").style.display = "block";
			}
			break;
		case 'dentists':
			if (validated) {
				document.getElementById("dentistNav").style.display = "flex";
				document.getElementById("validateDen").style.display = "none";
			} else {
				document.getElementById("validateDen").style.display = "block";
			}
			break;
		case 'patients':
			if (validated) {
				document.getElementById("patientNav").style.display = "flex";
				document.getElementById("validatePat").style.display = "none";
			} else {
				document.getElementById("validatePat").style.display = "block";
			}
			break;
	}

	// Get all elements with class="content" and hide them
	panecontent = document.getElementsByClassName("content");
	for (i = 0; i < panecontent.length; i++) {
		panecontent[i].style.display = "none";
	}

	document.getElementById(paneName).style.display = "block";
};

function openTab(tabName) {

	// Get all elements with class="tab" and hide them
	tabcontent = document.getElementsByClassName("tab");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}

	document.getElementById(tabName).style.display = "block";

};

function validate(paneName, testing) {
	cookieString = document.cookie;
	switch(paneName) {
		case 'receptionists':
			if (cookieString.includes("receptionist=true") || testing) {
				return true;
			}
		case 'dentists':
			if (cookieString.includes("dentist=true") || testing) {
				return true;
			}
		case 'patients':
			if (cookieString.includes("patient=true") || testing) {
				return true;
			}
	}
	return false;

};

function validateReceptionist() {
	//todo connect to the DB, determine if the user has permissions
	document.cookie = "receptionist=true; max-age=3600";
	openPane("receptionists");
};

function validateDentist() {
	//todo connect to the DB, determine if the user has permissions
	document.cookie = "dentist=true; max-age=3600";
	openPane("dentists");
};

function validatePatient() {
	//todo connect to the DB, determine if the user has permissions,
	//find the users name for later use
	name= ""
	document.cookie = "patient=true; max-age=3600";
	document.cookie = "name="+ name +"; max-age=3600";
	openPane("patients");
};

function updateDynamicList(divId, list) {
	listDiv = document.getElementById(divId);

	while (listDiv.hasChildNodes()) {
		listDiv.removeChild(listDiv.lastChild);
	}

	for (var i = 0; i < list.length; i++) {
		var newDiv = document.createElement("div");
		newDiv.innerHTML = list[i];
		// newDiv.className += " ";
		listDiv.appendChild(newDiv);
	}

}

function createPatient() {
	//todo connect to the DB, create the Patient and User

};

function patientSearchReception() {
	//todo connect to the DB, fill in the info
	document.getElementById("editPatientSearch").style.display = "none";
	document.getElementById("editPatientForm").style.display = "block";
};

function updatePatient() {
	//todo update the patient using connection to the DB
	document.getElementById("editPatientSearch").style.display = "block";
	document.getElementById("editPatientForm").style.display = "none";
};

function cancelUpdatePatient() {
	document.getElementById("editPatientSearch").style.display = "block";
	document.getElementById("editPatientForm").style.display = "none";
};

function branchSearch() {
	//todo connect to the DB, display all dentists

};

function createEmployee() {
	//todo connect to the DB, create the Employee and User

};

function patientSearchRecord() {
	//todo connect to the DB, retrieve the records

};
