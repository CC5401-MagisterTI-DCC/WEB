<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Estadísticas - Magister en TI</title>
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/favicon.ico" />
<!-- Bootstrap -->
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="<%=request.getContextPath()%>/css/bootstrap-responsive.css"
	rel="stylesheet" media="screen">
<link href="<%=request.getContextPath()%>/css/header.css"
	rel="stylesheet" media="screen">
<link href="<%=request.getContextPath()%>/css/footer.css"
	rel="stylesheet" media="screen">
<link href="<%=request.getContextPath()%>/css/admin/styles.css"
	rel="stylesheet" media="screen">
<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="vendors/flot/excanvas.min.js"></script><![endif]-->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->



<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/morris.css">

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
					<div class="span6">
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
					<div class="span6">
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
	<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/fancybox/lib/jquery.mousewheel-3.0.6.pack.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
	<script src="<%=request.getContextPath()%>/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/js/DT_bootstrap.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/jquery.dataTables.columnFilter.js"></script>
	<script src="<%=request.getContextPath()%>/js/admin/scripts.js"></script>
	<script src="<%=request.getContextPath()%>/js/raphael-min.js"></script>
	<script src="<%=request.getContextPath()%>/js/morris.min.js"></script>

	<script type="text/javascript">
	
	
	
	var mujeres = '${mapGenero.get("Mujeres")}';
	var hombres = '${mapGenero.get("Hombres")}';
	
	var totalGenero = mujeres*1+hombres*1;
	
	var aceptados = '${mapResoluciones.get("Aceptados")}';
	var rechazados = '${mapResoluciones.get("Rechazados")}';
	var aceptados_cond = '${mapResoluciones.get("Aceptados Condicional")}';
	
	var particular = '${mapFinanciamiento.get("Particular")}';
	var beca = '${mapFinanciamiento.get("Beca")}';
	var empresa = '${mapFinanciamiento.get("Empresa")}';
	var totalFinanciamiento = particular*1+beca*1+empresa*1;
	
	
	var nacionales = '${mapNacionalidad.get("Nacionales")}';
	var extranjeros = '${mapNacionalidad.get("Extranjeros")}';
	var totalNacionalidad = nacionales*1+extranjeros*1;
	
	// Morris Bar Chart
    Morris.Bar({
        element: 'resoluciones',
        data: [
            {desicion: 'Aceptadas', nPostulaciones: aceptados},
            {desicion: 'Rechazadas', nPostulaciones: rechazados},
            {desicion: 'Aceptadas Condicionalmente', nPostulaciones: aceptados_cond},
        ],
        xkey: 'desicion',
        ykeys: ['nPostulaciones'],
        barColors: ["#8ac368"],
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
    var mNames = "<c:out value="${mapPPM.keySet()}"/>";  
	var mValues = "<c:out value="${mapPPM.values()}"/>";  
	var periodNames = mNames.replace("[","").replace("]","").split(", ");
	var periodValues =  mValues.replace("[","").replace("]","").split(", ");
	var data_ppm = new Array();
	
    for (var i=0;i<periodNames.length;i++){
    	data_ppm[i] = {"periodo": periodNames[i], "postulaciones" : periodValues[i]*1};
    }
    
    var tax_data = [
        {"period": "2013-04", "visits": 2407, "signups": 660},
        {"period": "2013-03", "visits": 3351, "signups": 729},
        {"period": "2013-02", "visits": 2469, "signups": 1318},
        {"period": "2013-01", "visits": 2246, "signups": 461},
        {"period": "2012-12", "visits": 3171, "signups": 1676},
        {"period": "2012-11", "visits": 2155, "signups": 681},
        {"period": "2012-10", "visits": 1226, "signups": 620},
        {"period": "2012-09", "visits": 2245, "signups": 500}
    ];
    Morris.Line({
        element: 'ppm',
        data: data_ppm,
        xkey: 'periodo',
        xLabels: "Mes",
        ykeys: ['postulaciones'],
        labels: ['N° Postulaciones']
    });

    
	</script>

</body>
</html>