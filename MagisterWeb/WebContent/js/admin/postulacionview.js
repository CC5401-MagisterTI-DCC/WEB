$(document).ready( function () {
	
	
	var countChecked1 = function() {
	      var text = "";
			$("input:checked").each(function () {
				 text = text + $(this).val()+" ";
			 });
	      $( "#id_documentos1" ).val(text);
		};
	countChecked1();
	
	$( "input[type=checkbox]" ).on( "click", countChecked1 );
	
	var countChecked2 = function() {
	      var text = "";
			$("input:checked").each(function () {
				 text = text + $(this).val()+" ";
			 });
	      $( "#id_documentos2" ).val(text);
		};
	countChecked2();
	
	$( "input[type=checkbox]" ).on( "click", countChecked2 );
	
	
});