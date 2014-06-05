<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Estadísticas - Magister en TI</title>
<link rel="shortcut icon" href="${root}/favicon.ico" />
<!-- Bootstrap -->
<link href="${root}/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="${root}/css/bootstrap-responsive.css" rel="stylesheet"
	media="screen">
<link href="${root}/css/header.css" rel="stylesheet" media="screen">
<link href="${root}/css/footer.css" rel="stylesheet" media="screen">
<link href="${root}/css/admin/styles.css" rel="stylesheet"
	media="screen">
<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="vendors/flot/excanvas.min.js"></script><![endif]-->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

<link rel="stylesheet" href="${root}/css/morris.css">
<link rel="stylesheet" href="${root}/css/datepicker.css">

</head>
<body>
	<jsp:include page="../../header.jsp"></jsp:include>

	<div class="container-fluid">
		<div class="row-fluid section">
			<div class="span3">
				<jsp:include page="sidebar.jsp"></jsp:include>
			</div>
			<div class="span9" id="content">

				<div class="row-fluid">
					<h1>
						Estadísticas <small>Magister en TI</small>
					</h1>
				</div>

				<div class="row-fluid">
					<div class="span12">
						<!-- block -->
						<div class="block">
							<div class="navbar navbar-inner block-header">
								<h4>Filtro : </h4> 
								<form class="form-inline">
									<label for="from">Fecha : </label>
									<input id="from" type="text" class="input-small datepicker" placeholder="Desde">
								    <input id="to" type="text" class="input-small datepicker" placeholder="Hasta">
								    
									<button id="update" type="button" class="btn" style="margin-top:0px"><i class="icon-refresh"></i> Graficar</button>
								</form>	
								<p>
									<span class="label label-warning">Nota:</span>&nbsp;Los gráficos mostrados son construidos en base a 
									las postulaciones ingresadas en el rango de fechas indicado en el filtro (incluyendo las mostradas).
								</p>
							</div>
						</div>
						<!-- /block -->
					</div>
					<div class="span7">
					
					</div>
				</div>

				<div class="row-fluid">
					<div class="span5">
						<!-- block -->
						<div class="block">
							<div class="navbar navbar-inner block-header">
								<div class="muted pull-left">Género</div>
							</div>
							<div class="block-content collapse in">
								<div class="span12">
									<div id="HvsM" style="height: 250px;"></div>
								</div>
							</div>
						</div>
						<!-- /block -->
					</div>
					<div class="span7">
						<!-- block -->
						<div class="block">
							<div class="navbar navbar-inner block-header">
								<div class="muted pull-left">Resoluciones</div>
							</div>
							<div class="block-content collapse in">
								<div class="span12">
									<div id="resoluciones" style="height: 250px;"></div>
								</div>
							</div>
						</div>
						<!-- /block -->
					</div>
				</div>

				<!-- morris graph chart -->
				<div class="row-fluid section">
					<!-- block -->
					<div class="block">
						<div class="navbar navbar-inner block-header">
							<div class="muted pull-left">Postulaciones por país</div>
						</div>
						<div class="block-content collapse in">
							<div class="span12">
								<div id="postulaciones_por_pais" style="height: 250px;"></div>
							</div>
						</div>
					</div>
					<!-- /block -->
				</div>
				
				<!-- morris graph chart -->
				<div class="row-fluid section">
					<!-- block -->
					<div class="block">
						<div class="navbar navbar-inner block-header">
							<div class="muted pull-left">Postulaciones por mes</div>
						</div>
						<div class="block-content collapse in">
							<div class="span12">
								<div id="ppm" style="height: 230px;"></div>
							</div>
						</div>
					</div>
					<!-- /block -->
				</div>

				<div class="row-fluid">
					<div class="span6">
						<!-- block -->
						<div class="block">
							<div class="navbar navbar-inner block-header">
								<div class="muted pull-left">Financiamiento</div>
							</div>
							<div class="block-content collapse in">
								<div class="span12">
									<div id="financiamiento" style="height: 250px;"></div>
								</div>
							</div>
						</div>
						<!-- /block -->
					</div>
					<div class="span6">
						<!-- block -->
						<div class="block">
							<div class="navbar navbar-inner block-header">
								<div class="muted pull-left">Nacionales v/s Extranjeros</div>
							</div>
							<div class="block-content collapse in">
								<div class="span12">
									<div id="nacionalidad" style="height: 250px;"></div>
								</div>
							</div>
						</div>
						<!-- /block -->
					</div>
				</div>


			</div>
		</div>
	</div>


	<jsp:include page="../../footer.jsp"></jsp:include>
	<script src="${root}/js/jquery.js"></script>
	<script src="${root}/js/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="${root}/fancybox/lib/jquery.mousewheel-3.0.6.pack.js"></script>
	<script type="text/javascript"
		src="${root}/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
	<script src="${root}/js/bootstrap.js"></script>
	<script src="${root}/js/header.js"></script>
	<script src="${root}/js/DT_bootstrap.js"></script>
	<script src="${root}/js/jquery.dataTables.columnFilter.js"></script>
	<script src="${root}/js/admin/scripts.js"></script>
	<script src="${root}/js/raphael-min.js"></script>
	<script src="${root}/js/morris.min.js"></script>
	<script src="${root}/js/bootstrap-datepicker.js"></script>
	<script src="${root}/js/locales/bootstrap-datepicker.es.js"></script>
	<script src="${root}/js/spin.min.js"></script>

	<script type="text/javascript">	
	
	$('.datepicker').datepicker({
		weekStart: 1,
		startView: 1,
		format: "dd/mm/yyyy",
	    language: "es",
	    orientation: "auto",
	    autoclose: true,
	    todayHighlight: true
	});
	
	// configuración de spinner
	var opts = {
	  lines: 13, // The number of lines to draw
	  length: 29, // The length of each line
	  width: 12, // The line thickness
	  radius: 30, // The radius of the inner circle
	  corners: 1, // Corner roundness (0..1)
	  rotate: 0, // The rotation offset
	  direction: 1, // 1: clockwise, -1: counterclockwise
	  color: '#000', // #rgb or #rrggbb or array of colors
	  speed: 1, // Rounds per second
	  trail: 60, // Afterglow percentage
	  shadow: false, // Whether to render a shadow
	  hwaccel: false, // Whether to use hardware acceleration
	  className: 'spinner', // The CSS class to assign to the spinner
	  zIndex: 2e9, // The z-index (defaults to 2000000000)
	  top: '50%', // Top position relative to parent
	  left: '50%' // Left position relative to parent
	};
	
	$('#update').click(function() {
		
		var fromSplit = $("#from").val().split('/');
		var toSplit = $("#to").val().split('/');
		
		var desde = new Date(fromSplit[2],fromSplit[1]-1,fromSplit[0]);
		var hasta = new Date(toSplit[2],toSplit[1]-1,toSplit[0]);

		var target1 = document.getElementById('HvsM');
		var target2 = document.getElementById('resoluciones');
		var target3 = document.getElementById('postulaciones_por_pais');
		var target4 = document.getElementById('ppm');
		var target5 = document.getElementById('financiamiento');
		var target6 = document.getElementById('nacionalidad');

		var spinner1 = new Spinner(opts).spin(target1);
		var spinner2 = new Spinner(opts).spin(target2);
		var spinner3 = new Spinner(opts).spin(target3);
		var spinner4 = new Spinner(opts).spin(target4);
		var spinner5 = new Spinner(opts).spin(target5);
		var spinner6 = new Spinner(opts).spin(target6);
		
		//llamada ajax para recuperar estadísticas
		$.ajax( "estadisticas/async" )
			.done(function(listaPostulaciones) {
				
				// parse string to json
				listaPostulaciones = JSON.parse(listaPostulaciones.replace(/\'/g,"\""));
				
				// lista filtrada por fecha
				listaFiltrada = $.grep(listaPostulaciones, function(elem){
					var fechaSplit = elem.fecha_ingreso.split("-");
					var fecha = new Date(fechaSplit[0],fechaSplit[1]-1,fechaSplit[2]);
					
					if (desde.getTime() <= fecha.getTime() && fecha.getTime() <= hasta.getTime())
						return true;
					
					return false;
				});
				
				// cantidad de postulaciones según sexo.
				var mujeres = $.grep(listaFiltrada, function(elem){ return elem.genero==='FEMENINO';}).length;
				var hombres = $.grep(listaFiltrada, function(elem){ return elem.genero==='MASCULINO';}).length;
					
				var totalGenero = mujeres+hombres;
			
				// cantidad de postulaciones aceptadas, rechazadas y aceptadas condicionalmente.
				var aceptados = $.grep(listaFiltrada, function(elem){ return elem.resolucion==='ACEPTADA';}).length;
				var rechazados = $.grep(listaFiltrada, function(elem){ return elem.resolucion==='RECHAZADA';}).length;
				var aceptados_cond = $.grep(listaFiltrada, function(elem){ return elem.resolucion==='ACEPTADA_CONDICIONAL';}).length;
			
				// cantidad de postulaciones por tipo de financiamiento (beca, empresa, particular)
				var particular = $.grep(listaFiltrada, function(elem){ return elem.financiamiento==='PARTICULAR';}).length;
				var beca = $.grep(listaFiltrada, function(elem){ return elem.resolucion==='BECA';}).length;
				var empresa = $.grep(listaFiltrada, function(elem){ return elem.resolucion==='EMPRESA';}).length;
				
				var totalFinanciamiento = particular+beca+empresa;
				
				// cantidad de postulaciones por nacionalidad.
				var nacionales = $.grep(listaFiltrada, function(elem){ return elem.resolucion==='Chile';}).length;
				var extranjeros = totalGenero - nacionales;
				var totalNacionalidad = nacionales+extranjeros;
				
				// Morris Bar Chart
			    Morris.Bar({
			        element: 'resoluciones',
			        data: [
			            {decision: 'Aceptadas', nPostulaciones: aceptados},
			            {decision: 'Aceptadas Condicionalmente', nPostulaciones: aceptados_cond},
			            {decision: 'Rechazadas', nPostulaciones: rechazados},
			        ],
			        xkey: 'decision',
			        ykeys: ['nPostulaciones'],
			        barColors: ["#8ac368", "#e5ff00" ,"#ff0000"],
			        labels: ['N° Postulaciones'],
			        barRatio: 0.8,
			        xLabelMargin: 10,
			        hideHover: 'auto'
			       
			    });
			
				// arreglo de datos
				var datos_por_pais = [];
				var numero_por_pais = 0;
				
				for(var i in listaFiltrada) {			
					
					numero_por_pais = $.grep(listaFiltrada, function(elem){ return elem.pais===listaFiltrada[i].pais;}).length;
					
					// si no existe se agrega
					if($.grep(datos_por_pais, function(elem){ return elem.pais===listaFiltrada[i].pais;}).length===0)
						datos_por_pais.push({pais: listaFiltrada[i].pais, cantidad: numero_por_pais,});
				}
				
			 	// Morris Bar Chart
			    Morris.Bar({
			        element: 'postulaciones_por_pais',
			        data: datos_por_pais,
			        xkey: 'pais',
			        ykeys: ['cantidad'],
			        barColors: ["#3ec3df","#37afc8", "#319cb2", "#2b889c", "#257585", "#1f616f", "#184e59", "#123a42"],
			        labels: ['N° Postulaciones'],
			        barRatio: 0.8,
			        xLabelMargin: 10,
			        hideHover: 'auto'	       
			    });
				
			    // Morris Donut Chart
			    Morris.Donut({
			        element: 'HvsM',
			        data: [
			            {label: 'Mujeres', value: mujeres },
			            {label: 'Hombres', value: hombres }
			        ],
			        colors: ["#EAAAED", "#76bdee"],
			        formatter: function (y) { return y +' - '+ (y/totalGenero).toFixed(2)*100+'%';}
			    });
			    
			    // Morris Donut Chart
			    Morris.Donut({
			        element: 'financiamiento',
			        data: [
			            {label: 'Particular', value: particular },
			            {label: 'Beca', value: beca },
			            {label: 'Empresa', value: empresa }
			        ],
			        colors: ["#30a1ec", "#7CED72", "#EDAAAA"],
			        formatter: function (y) { return y +' - '+ (y/totalFinanciamiento).toFixed(2)*100+'%';}
			    });
			    
			    // Morris Donut Chart
			    Morris.Donut({
			        element: 'nacionalidad',
			        data: [
			            {label: 'Nacionales', value: nacionales },
			            {label: 'Extranjeros', value: extranjeros }
			        ],
			        colors: ["#ED7272", "#EDD772"],
			        formatter: function (y) { return y +' - '+ (y/totalNacionalidad).toFixed(2)*100+'%';}
			    });
			    
			    // Morris Line Chart
			    
			    /**
			     * Recibe una string con formato yyyy-mm-dd y devuelve string con formato yyy-mm
			     */
			    function formatDate(fecha) {
			    	return fecha.substring(0, fecha.lastIndexOf('-'));
			    };
			    
				var data_ppm = [];		
				/*
				var cantidad_ppm;
				// No es necesario agrupar por mes, morris.js lo hace automaticamente a medida que la cantidad de datos es mayor.
				for(var i in listaFiltrada) {			
					
					cantidad_ppm = $.grep(listaFiltrada, function(elem){ 
						return formatDate(elem.fecha_ingreso)===formatDate(listaFiltrada[i].fecha_ingreso);}).length;
					
					// si no existe se agrega
					if($.grep(data_ppm, function(elem){ 
						return formatDate(elem.periodo)===formatDate(listaFiltrada[i].fecha_ingreso);}).length===0)
						data_ppm.push({"periodo": listaFiltrada[i].fecha_ingreso, "postulaciones": cantidad_ppm,});
				}
			    
				data_ppm = [];*/
				for(var i in listaFiltrada){
					data_ppm.push({"periodo": listaFiltrada[i].fecha_ingreso, "postulaciones": 1,});
				}
				
			    Morris.Line({
			        element: 'ppm',
			        data: data_ppm,
			        xkey: 'periodo',
			        xLabels: "Mes",
			        ykeys: ['postulaciones'],
			        labels: ['N° Postulaciones']
			    });
		    })
		    .fail(function() {
		    	alert( "Ha ocurrido un problema al cargar los datos.");
		  	})
		  	.always(function() {
		    	spinner1.stop();
		    	spinner2.stop();
		    	spinner3.stop();
		    	spinner4.stop();
		    	spinner5.stop();
		    	spinner6.stop();
		  	});	
	});
	</script>

</body>
</html>