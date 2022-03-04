
function openPane(tabName) {
  console.log("Here");

	// Get all elements with class="content" and hide them
	tabcontent = document.getElementsByClassName("content");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}

	document.getElementById(tabName).style.display = "block";

  if (tabName === "main") {
    document.getElementById("backButton").style.display = "none";
  } else {
    document.getElementById("backButton").style.display = "block";
  }
};
