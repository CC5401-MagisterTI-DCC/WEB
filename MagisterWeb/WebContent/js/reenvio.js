$(document).ready(function() {

	$.validator.addMethod("validFile", function(value, element) { 
		var extension = (value.substring(value.lastIndexOf("."))).toLowerCase(); 
		return extension == ".pdf";
	}, "Ingrese un archivo PDF");


	

	
	var $validator = $("#postulacionForm").validate({
		rules: {
			
		},
		errorPlacement: function(error, element) {
			error.insertAfter(element);
		},
		highlight: function(element) {
			$(element).closest('.control-group').removeClass('success').addClass('error');
		},
		success: function(element) {
			element
			.text('OK!').addClass('valid')
			.closest('.control-group').removeClass('error').addClass('success');
		}
	});	
	
	$("input").each(function(){
		if($(this).name != ""){
			$(this).rules("add",{
				required: true,
				validFile: true
			});
		}
	});
	
});
