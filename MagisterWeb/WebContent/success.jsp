<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.io.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Postulación Completa - Magister TI DCC</title>
<link rel="stylesheet" href="${root}/css/header.css">
<link rel="stylesheet" href="${root}/css/footer.css">
<link rel="stylesheet" href="${root}/css/bootstrap.min.css">
<link rel="stylesheet" href="${root}/css/bootstrap-responsive.css">
<script src="${root}/js/jquery.js"></script>
<script src="${root}/js/bootstrap.js"></script>

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
		<h1>¡Postulación enviada con éxito!</h1>

		<br />
		<p>La postulación ha sido procesada adecuadamente en nuestro
			sistema.
		<p>
			Se ha enviado un correo electrónico a <span class="label label-info">${email}</span>.



		
		<p>Por favor revise su correo, en él se encuentra el número
			asociado a su postulación en la que podrá hacer seguimiento en
			${initParam['paginaTrack']}</p>

		<br /> <a class="btn btn-success btn-large" href="${root}/index.jsp"><i
			class="icon-home icon-white"></i> Ir Al Inicio</a>
	</div>


	<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>