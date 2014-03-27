<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.io.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Usuario Inválido - Magister TI DCC</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap-responsive.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/footer.css">
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
	<jsp:include page="../header.jsp"></jsp:include>
	<div class="hero-unit center">
		<h1>Acceso Denegado</h1>
		<br>
		<c:choose>
			<c:when test="${user!=null }">
				<p>
					El usuario <strong>${user.username }</strong> no tiene permisos
					para ver esta página.
				</p>
			</c:when>
			<c:otherwise>
				<p>¡No tienes permisos para ver esta página! Por favor inicia
					sesión con un usuario con los permisos correspondientes</p>
			</c:otherwise>
		</c:choose>


		<br> <br> <a class="btn btn-primary btn-large"
			href="<%=request.getContextPath()%>/app/login"><i
			class=" icon-user icon-white"></i> Ingresar</a> <a
			class="btn btn-success btn-large"
			href="<%=request.getContextPath()%>/index.jsp"><i
			class="icon-home icon-white"></i> Ir Al Inicio</a>
	</div>


	<jsp:include page="../footer.jsp"></jsp:include>

</body>
</html>