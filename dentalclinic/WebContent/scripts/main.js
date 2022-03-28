
	function validateLogin(username, password) {
		var uname = document.getElementById(username);
		var upass = document.getElementById(password);
		if(uname.value == ""){
			alert("username can't be null");
			return false;
		}
		
		else if(upass.value ==""){
			alert("password can't be null");
			return false;
		}
		else
			return true;
	}
	
	function validateSIN(SIN) {
		var numbers = /^[0-9]+$/;
		if(SIN.value.match(numbers) && SIN.value.length == 9) {
		      return true;
		} else {
			return false
		}
	}


function openTab(tabName) {

	// Get all elements with class="tab" and hide them
	tabcontent = document.getElementsByClassName("tab");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}

	document.getElementById(tabName).style.display = "block";

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
