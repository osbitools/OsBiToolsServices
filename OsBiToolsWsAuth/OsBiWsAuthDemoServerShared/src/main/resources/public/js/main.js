function authenticate() {
	$.post({
		url: "./login",
		data: {
			usr: $("#usr").val(),
			pwd: $("#pwd").val()
		},
		success: function() {
			$("#auth_res").html("SUCCESS").css("color", "green");
		},
		error: function(jqXHR) {
			$("#auth_res").html("ERROR #" + jqXHR.status).css("color", "red");
		},
	});
}