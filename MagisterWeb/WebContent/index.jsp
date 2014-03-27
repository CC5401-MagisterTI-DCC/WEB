<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Magister TI - DCC</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/favicon.ico" />
<!-- CSS -->
<link rel="stylesheet" type="text/css" href="css/DT_bootstrap.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/bootstrap-responsive.css">
<link rel="stylesheet" href="css/header.css">
<link rel="stylesheet" href="css/footer.css">

<!-- Scripts -->
<script src="js/jquery.js"></script>
<script src="js/jquery.dataTables.js" type="text/javascript"></script>
<script src="js/dataTables.bootstrap.js" type="text/javascript"></script>
<script src="js/bootstrap.js" type="text/javascript"></script>

</head>
<body>

	<jsp:include page="header.jsp"></jsp:include>

	<div class="container">


		<div class="hero-unit">
			<h1>Magister en TI</h1>
			<p>Bienvenido al sistema de postulación al Magíster en
				Tecnologías de la Información del Departamento de Ciencias de la
				Computación de la Universidad de Chile.</p>
			<p>
				<a class="btn btn-primary btn-large"
					href="<%=request.getContextPath()%>/app/form"> Ir al formulario
					de postulación </a>
			</p>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>


</html>