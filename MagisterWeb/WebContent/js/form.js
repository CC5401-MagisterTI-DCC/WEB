$(document).ready(function() {

	//previene que se envie el formulario cuando se presiona la tecla enter.
	$(window).keydown(function(event){
		if(event.keyCode == 13) {
			event.preventDefault();
			return false;
	    }
	});
		
	$.validator.addMethod("validName", function(value, element) { 
		var exp1 = new RegExp("[\\wáéíóúÁÉÍÓÚÑñ]+[\\s[\\wáéíóúÁÉÍÓÚÑñ]+]*");
		var exec1 = exp1.exec(value);
		var exp2 = new RegExp("[\\D]+");
		var exec2 = exp2.exec(value);
		return exec1 != null && exec2 != null && exec1[0] == exec2[0] && exec1[0] == value;
	}, "Ingrese un nombre válido");

	$.validator.addMethod("validFile", function(value, element) { 
		var extension = (value.substring(value.lastIndexOf("."))).toLowerCase(); 
		return extension == ".pdf";
	}, "Ingrese un archivo PDF");

	$.validator.addMethod("validPhone", function(value, element) { 
		var exp1 = new RegExp("[+]?[\\d]+[\\s]?[\\d]+");
		var exec1 = exp1.exec(value);
		var exp2 = new RegExp("[[\\(][+]?[\\d]+[\\)]]?[\\s]?[\\d]+");
		var exec2 = exp2.exec(value);
		return  (exec1 != null && exec1.length > 0 && exec1[0] == value) 
			|| (exec2 != null && exec2.length > 0 && exec2[0] == value);
	}, "Ingrese un teléfono válido");

	$.validator.addMethod("validPhoneOpt", function(value, element) { 
		var exp1 = new RegExp("[+]?[\\d]+[\\s]?[\\d]+");
		var exec1 = exp1.exec(value);
		var exp2 = new RegExp("[[\\(][+]?[\\d]+[\\)]]?[\\s]?[\\d]+");
		var exec2 = exp2.exec(value);
		return  value == "" || (exec1 != null && exec1.length > 0 && exec1[0] == value) 
		|| (exec2 != null && exec2.length > 0 && exec2[0] == value);
	}, "Ingrese un teléfono válido");

	$.validator.addMethod("validRUT", function(value, element) { 
		var M=0,S=1;
		value = value.toLowerCase();
		var rut = value.split("-");

		for(;rut[0];rut[0]=Math.floor(rut[0]/10))
			S=(S+rut[0]%10*(9-M++%6))%11;

		if(S){
			return S-1==rut[1];
		}
		else{
			return 'k'==rut[1] || 'K'==rut[1];
		}
	}, "Ingrese un RUT válido");

	var $validator = $("#postulacionForm").validate({
		rules: {
			checkbox: {
				required: true
			},
			nombre: {
				required: true,
				validName: true,
			},
			apellido:{
				required: true,
				validName: true,
			},
			emailfield: {
				required: true,
				email: true,
				minlength: 3,
			},
			emailfield2: {
				required: true,
				minlength: 3,
				equalTo: "#emailfield"
			},
			telefono_p: {
				validPhone:true
			},
			celular_p: {
				validPhoneOpt: true
			},
			namefield: {
				required: true,
				minlength: 3
			},
			rut: {
				validRUT : true
			},
			fono_empr:{
				validPhoneOpt: true,
			},
			cert_titulo: {
				required: true,
				validFile: true,
			},
			cert_notas: {
				required: true,
				validFile: true,
			},
			cv: {
				required: true,
				validFile: true,
			},
			carta_pres: {
				required: true,
				validFile: true,
			},
			carta_rec_1: {
				required: true,
				validFile: true,
			},
			carta_rec_2: {
				required: true,
				validFile: true,
			},
			messages:{
				nombre: {
					validName: "Por favor ingrese un(os) nombre(s) válido(s)",
				},
				apellido: {
					validName: "Por favor ingrese un(os) apellido(s) válido(s)",
				},
				telefono_p:{
					validPhone: "Ingrese un teléfono válido",
				},
				celular_p:{
					validPhoneOpt: "Ingrese un celular válido", 
				},
				emailfield: {
					required: "Campo requerido",
					email: "Por favor ingrese un email válido"
				},
				namefield: {
					required: "Campo requerido",
					minlength: "Largo mínimo 3"
				}
			}

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

	var i = 1;

	$("#agregar_otro").click(function() {
		
		var j = i;
		var resumen = $("<div id=\"grado" + i + "\">");
		var gradoResumen = $("<span id=\"grado" + i + "_resumen\"></span><br>");
		var institucionResumen = $("<span id=\"institucion" + i + "_resumen\"></span><br>");
		var fechaResumen = $("<span id=\"fecha_ob" + i + "_resumen\"></span><br>");  
		var paisResumen = $("<span id=\"pais_grado" + i + "_resumen\"></span><br><br></div>");
		resumen.append(gradoResumen);
		resumen.append(institucionResumen);
		resumen.append(fechaResumen);
		resumen.append(paisResumen);
		
		$("#grados").append(resumen);
		
		var fieldWrapper = $("<div class=\"fieldwrapper well well-small\" style=\"background: #dfdfdf\" id=\"field" + i + "\"/>");
		var gradoArea = $('<!-- Textarea --><div class="control-group">	<label class="control-label">Grado Académico:</label><div class="controls">');
		var grado = $('<textarea id="grado' + j + '" name="grado' + j +'" class="" placeholder="Ingrese su grado académico" required=""></textarea>');
		grado.change(function(){
			var value = $(this).val();
			$("#grado" + j + "_resumen").text(value);
		});
		var institucionArea = $('</div></div><!-- Textarea --><div class="control-group"><label class="control-label">Institución:</label><div class="controls">');
		var institucion = $('<textarea id="institucion' + j + '" name="institucion' + j + '" class=" " placeholder="Ingrese la institución de la cual obtuvo el grado académico"	required=""></textarea></div></div>');
		institucion.change(function(){
			var value = $(this).val();
			$("#institucion" + j + "_resumen").text(value);
		});
		var fechaArea = $('<!-- Text input--><div class="control-group"><label class="control-label">Fecha de Obtención:</label><div class="controls">');
		var fecha = $('<input id="fecha_ob' + j + '" name="fecha_ob' + j + '" class="datepicker" type="text" required=""></div></div>');
		fecha.change(function(){
			var value = $(this).val();
			$("#fecha_ob" + j + "_resumen").text(value);
		});
		var paisCT =$('<!-- Select Basic --><div class="control-group"></div>');
		var paisLabel = $('<label class="control-label">Pais de la Institución:</label>');
		var paisCdiv = $('<div class="controls"></div>	');
		var paisSelect = $('<select id="pais_grado' + j + '" name="pais_grado' + j + '"	class="  input-xlarge"></select>');
		var cTitulo = $('<!-- File Button --><div class="control-group"><label class="control-label">Certificado de Título o Grado:</label><div class="controls"><input id="cert_titulo' + j + '" name="cert_titulo' + j + '" class="  input-file" type="file" required=""></div></div>');
		var cNotas = $('<!-- File Button --><div class="control-group"><label class="control-label">Certificado de Notas:</label><div class="controls"><input id="cert_notas' + j + '" name="cert_notas' + j + '" class="  input-file" type="file" required=""></div></div>');
		var removeButton = $('<div class="control-group"><label class="control-label" for=""></label> <div class="controls"><a class="remove btn btn-danger" href="#"><i class="icon-remove icon-white"></i> Eliminar Estudio</a></div></div>');
		removeButton.attr("onClick","return confirm('¿Está seguro que desea eliminar el grado académico seleccionado?');");
		
		removeButton.click(function() {
			$(this).parent().remove();
			$("#grado" + j).remove();
			i--;
		});

		// para ingreso de postulación
		$.ajax({
			type: "GET",
			url: "../DropPaises",
			success: function(html){
				paisSelect.html(html);
			}
		});
		
		// para edición de postulación
		$.ajax({
			type: "GET",
			url: "../../DropPaises",
			success: function(html){
				paisSelect.html(html);
			}
		});

		paisSelect.change(function(){
			var value = $(this).find(':selected').text();
			$("#pais_grado" + j + "_resumen").text(value);
		});
		
		gradoArea.append(grado);
		fieldWrapper.append(gradoArea);	
		institucionArea.append(institucion);
		fieldWrapper.append(institucionArea);
		fechaArea.append(fecha);
		fieldWrapper.append(fechaArea);
		paisCdiv.append(paisSelect);
		paisCT.append(paisLabel);
		paisCT.append(paisCdiv);
		fieldWrapper.append(paisCT);
		fieldWrapper.append(cTitulo);
		fieldWrapper.append(cNotas);
		fieldWrapper.append(removeButton);
		$("#addEstudio").append(fieldWrapper);

		$("#grado" + j).rules("add",{
			required: true
		});
		
		$("#institucion" + j).rules("add",{
			required: true
		});
		
		$("#fecha_ob" + j).rules("add",{
			required: true
		});
		
		$("#pais_grado" + j).rules("add",{
			required: true
		});
		
		$("#cert_titulo" + j).rules("add",{
			required: true,
			validFile: true
		});
		
		$("#cert_notas" + j).rules("add",{
			required: true,
			validFile: true
		});
		
		i++;
	});
	
	$('#rootwizard').bootstrapWizard({
		'tabClass': 'nav nav-pills',
		'nextSelector': '.button-next', 
		'previousSelector': '.button-previous',
		'onNext': function(tab, navigation, index) {
			var valid = $("#postulacionForm").valid();
			if(!valid) {
				$validator.focusInvalid();
				return false;
			}
			// pinta el numero de verde para indicar que esa etapa esta completa
			tab.children().children().attr("class", "label label-success");
		},
		'onTabClick': function(tab, navigation, index) {
			return true;
		},
		'onTabChange': function(tab, navigation, index) {
			return true;
		},
		'onTabShow' : function(tab, navigation, index) {
			
			var $total = navigation.find('li').length;
			var $current = index + 1;
			var $percent = ($current / $total) * 100;
			$('#rootwizard').find('.bar').css({
				width : $percent + '%'
			});
		}
	});	
	
	$('#rootwizardEdit').bootstrapWizard({
		'tabClass': 'nav nav-pills',
		'nextSelector': '.button-next', 
		'previousSelector': '.button-previous',
		'onNext': function(tab, navigation, index) {
			var $valid = $("#postulacionForm").valid();
			if(!$valid) {
				$validator.focusInvalid();
				return false;
			}

		},
		'onTabShow' : function(tab, navigation, index) {
			var $total = navigation.find('li').length;
			var $current = index + 1;
			var $percent = ($current / $total) * 100;
			$('#rootwizard').find('.bar').css({
				width : $percent + '%'
			});

		}
	});	

	$("#tipoDoc").change(function(){

		if($(this).val() == "rut"){
			$("#pasaporte-div").slideUp();
			$("#pasaporte-resumen-div").hide();
			$("#rut-div").slideDown();
			$("#rut-resumen-div").show();
		}
		else if($(this).val() == "pass"){
			$("#rut-div").slideUp();
			$("#rut-resumen-div").hide();
			$("#pasaporte-resumen-div").show();
			$("#pasaporte-div").slideDown();
		}
		else{
			$("#rut-div").slideUp();
			$("#rut-resumen-div").hide();
			$("#pasaporte-resumen-div").hide();
			$("#pasaporte-div").slideUp();
		}
	});

	$("#financiamiento").change(function(){

		if($(this).val() == "Beca"){
			$("#beca-div").slideDown();
		}
		else{
			$("#beca-div").slideUp();
		}

	});

	// para cuando se carga la ventana con datos ya ingresados (ej. edición).
	if ($("#tipoDoc option:selected").text()==='RUT') {
		$("#rut-div").show();
		$("#rut-resumen-div").show();		
		$("#pasaporte-div").hide();
		$("#pasaporte-resumen-div").hide();
	} else if ($("#tipoDoc option:selected").text()==='Pasaporte') {			
		$("#pasaporte-div").show();
		$("#pasaporte-resumen-div").show();
		$("#rut-div").hide();
		$("#rut-resumen-div").hide();
	} else {		
		$("#pasaporte-div").hide();
		$("#pasaporte-resumen-div").hide();
		$("#rut-div").hide();
		$("#rut-resumen-div").hide();		
	}
	
	if ($('#financiamiento option:selected').text()!='Beca') {
		$("#beca-div").hide();
	}

	$("#nombre").focusout(function () {
		var value = $(this).val();
		$("#nombre_resumen").text(value);
	}).focusout();

	$("#apellido").focusout(function () {
		var value = $(this).val();
		$("#apellido_resumen").text(value);
	}).focusout();

	$("#rut").focusout(function () {
		var value = $(this).val();
		$("#rut_resumen").text(value);
	}).focusout();

	$("#pasaporte").focusout(function () {
		var value = $(this).val();
		$("#pasaporte_resumen").text(value);
	}).focusout();

	$("#fecha_nac").change(function () {
		var value = $(this).val();
		$("#fecha_nac_resumen").text(value);
	}).focusout();

	$("#nacionalidadPasaporte").mouseup(function () {
		var value = $(this).find(':selected').text();
		$("#nacionalidad_pasaporte_resumen").text(value);
	}).mouseup();

	$("#nacionalidad").mouseup(function () {
		var value = $(this).find(':selected').text();
		$("#nacionalidad_resumen").text(value);
	}).mouseup();

	$("#radioMasculino") // select the radio by its id
	.change(function(){ // bind a function to the change event
		if( $(this).is(":checked") ){ // check if the radio is checked
			var value = $(this).val(); // retrieve the value
			$("#genero_resumen").text(value);
		}
	});
	$("#radioFemenino") // select the radio by its id
	.change(function(){ // bind a function to the change event
		if( $(this).is(":checked") ){ // check if the radio is checked
			var value = $(this).val(); // retrieve the value
			$("#genero_resumen").text(value);
		}
	});

	$("#emailfield").focusout(function () {
		var value = $(this).val();
		$("#email_resumen").text(value);
	}).focusout();

	$("#telefono_p").focusout(function () {
		var value = $(this).val();
		$("#fono_resumen").text(value);
	}).focusout();

	$("#residencia").mouseup(function () {
		var value = $(this).find(':selected').text();
		$("#residencia_resumen").text(value);
	}).mouseup();

	$("#direccion").focusout(function () {
		var value = $(this).val();
		$("#direccion_p_resumen").text(value);
	}).focusout();

	$("#empresa").focusout(function () {
		var value = $(this).val();
		$("#empresa_actual_resumen").text(value);
	}).focusout();

	$("#cargo").focusout(function () {
		var value = $(this).val();
		$("#cargo_resumen").text(value);
	}).focusout();

	$("#dir_empr").focusout(function () {
		var value = $(this).val();
		$("#direccion_empresa_resumen").text(value);
	}).focusout();

	$("#fono_empr").focusout(function () {
		var value = $(this).val();
		$("#fono_empresa_resumen").text(value);
	}).focusout();

	$("#financiamiento").mouseup(function () {
		var value = $(this).val();
		$("#financiamiento_resumen").text(value);
	}).mouseup();
		
	$("#grado").change(function(){
		var value = $(this).val();
		$("#grado_resumen").text(value);
	});
	
	$("#institucion").change(function(){
		var value = $(this).val();
		$("#institucion_resumen").text(value);
	});
	
	$("#fecha_ob").change(function(){
		var value = $(this).val();
		$("#fecha_ob_resumen").text(value);
	});
	
	$("#pais_grado").change(function(){
		var value = $(this).find(':selected').text();
		$("#pais_grado_resumen").text(value);
	});

	$('#tab1').tooltip({
		selector: "a[data-toggle=tooltip]"
	});
	$('#tab2').tooltip({
		selector: "a[data-toggle=tooltip]"
	});
	$('#tab3').tooltip({
		selector: "a[data-toggle=tooltip]"
	});
	$('#tab4').tooltip({
		selector: "a[data-toggle=tooltip]"
	});
	$('#tab5').tooltip({
		selector: "a[data-toggle=tooltip]"
	});
	$('#tab6').tooltip({
		selector: "a[data-toggle=tooltip]"
	});
	$('#tab7').tooltip({
		selector: "a[data-toggle=tooltip]"
	});

});