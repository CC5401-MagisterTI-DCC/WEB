<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.io.*" pageEncoding="UTF-8" isErrorPage="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Postulación Duplicada - Magister TI DCC</title>
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/favicon.ico" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/footer.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap-responsive.css">
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>

<style>
.center {
	text-align: center;
	margin-left: auto;
	margin-right: auto;
	margin-bottom: auto;
	margin-top: auto;
}
</style>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="hero-unit center">
		<h1>Al parecer ya habias enviado una postulación...</h1>
		<br />
		<p>Existe ya una postulción en trámite bajo su nombre en el
			sistema por lo que los datos no han sido procesados</p>
		<p>
			Recuerde puede hacer un seguimiento de su postulación en curso en <strong>www.página.dcc.uchile.cl</strong>
		</p>
		<p>Si cree que existe algún error, no dude en comunicarse con
			nosotros</p>
		<br /> <a class="btn btn-success btn-large"
			href="<%=request.getContextPath()%>/index.jsp"><i
			class="icon-home icon-white"></i> Ir Al Inicio</a>
	</div>


	<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>