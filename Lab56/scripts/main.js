
$(document).ready(function(){
	$('[data-toggle="tooltip"]').tooltip();

	$("input[name='animal-type']").on("change", function() {
		$("#booking2").show();
		var radioValue = $("input[name='animal-type']:checked").val();
		$(".smhousevets").hide();
		$(".mdhousevets").hide();
		$(".lghousevets").hide();
		$(".reptilesvets").hide();
		$(".farmvets").hide();
		switch (radioValue) {
			case "smhouse":
				$(".smhousevets").show();
				break;
			case "mdhouse":
			$(".mdhousevets").show();
				break;
			case "lghouse":
			$(".lghousevets").show();
				break;
			case "reptiles":
			$(".reptilesvets").show();
				break;
			case "farm":
			$(".farmvets").show();
				break;
			default:
			$("#booking3").show('slow');
		}
		$("#vets").val("");
	});

	$("#vets").on("change", function() {
		$("#booking3").show();
		$('#dateInput').val("");
	});

	$("#phone").on("change", function(){
		if (!validatePhone()){
			$("#incorrect-phone-alert").removeClass("collapse");
			$("#phone").addClass("error");
		}
		else {
			$("#incorrect-phone-alert").addClass("collapse");
			$("#phone").removeClass("error");
		}
	});

	$("#creditCard").on("change", function(){
		if (!validateCreditCard()){
			$("#incorrect-creditCard-alert").removeClass("collapse");
			$("#creditCard").addClass("error");
		}
		else {
			$("#incorrect-creditCard-alert").addClass("collapse");
			$("#creditCard").removeClass("error");
		}
	});

	$("#book-button").on("click", function(){
		var vet = document.getElementById("vets").value;
		var date = validateDate();
		var service = validateService();
		if (validateCreditCard() && validatePhone() && vet != "" && date && service) {
			alert("Booking was successful!");
			$('#appointment-booking').modal('toggle');
		} else {
			alert("You need to complete the booking!");
		}
	});

	$("input[type='text']").hover(function() {
  	$(this).css("background-color", "rgb(232, 252, 253)");
	}, function() {
		$(this).css("background-color", "white");
	});


});

window.onscroll = function() {scrollFunction()};

function scrollFunction() {
  if (document.body.scrollTop > 220 || document.documentElement.scrollTop > 220) {
		$("#sticky-nav").css({"width" : "60%"});
		$("#sticky-nav").addClass("rounded-3");
  } else {
		$("#sticky-nav").css({"width" : "100%"});
		$("#sticky-nav").removeClass("rounded-3");
  }
}

const setDateFormat = "mm/dd/yy";

$(function() {
	$( "#dateInput" ).datepicker({
		dateFormat: setDateFormat,
		minDate: '+0d',
		maxDate: '+4m',
		beforeShowDay: disableDates
	});

});

function disableDates(date) {
    // Sunday is Day 0, disable all Sundays
    if (date.getDay() === 0 || date.getDay() === 6) {
        return [false];
		}
		var val = document.getElementById("vets").value;
		switch (val) {
			case "samuel":
				if (date.getDay() === 5) {
					return [false];
				}
			break;

			case "judy":
			case "clark":
				if (date.getDay() === 4 || date.getDay() === 5) {
						return [false];
				}
			break;

			case "robert":
			case "rowan":
				if (date.getDay() === 2 || date.getDay() === 4) {
						return [false];
				}
			break;
		}
    return [ true ]
}

function validateDate() {
  var a = $("#dateInput").val();
	var dateFormat = /^\(?([0-9]{2})\)?[/]?([0-9]{2})[/]?([0-9]{4})$/;
  if(a.match(dateFormat)) {
     return true;
  } else {
    return false;
  }
}

function validatePhone() {
  var a = $("#phone").val();
  var phoneFormat = /^\(?([0-9]{3})\)?[- ]?([0-9]{3})[- ]?([0-9]{4})$/;
  if(a.match(phoneFormat)) {
     return true;
  } else {
    return false;
  }
}

function validateCreditCard() {
  var a = $("#creditCard").val();
  var cardFormat = /^\(?([0-9]{4})\)?[-. ]?([0-9]{4})[-. ]?([0-9]{4})[-. ]?([0-9]{4})$/;
  if(a.match(cardFormat)) {
     return true;
  } else {
    return false;
  }
}

function validateService() {
	if ($("#checkup").is(":checked") || $("#vaccines").is(":checked") ||  $("#chipping").is(":checked") || $("#neuter").is(":checked")) {
		return true;
	}
	return false;
}
