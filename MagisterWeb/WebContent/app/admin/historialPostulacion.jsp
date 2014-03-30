<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Usuario</title>
<link rel="stylesheet"
	href="${root}/css/bootstrap.min.css">
<style>
body {
	background-color: #f9f9f9;
}
</style>

<script src="${root}/js/jquery.js"></script>
<script src="${root}/js/jquery.validate.js"></script>

</head>
<body>

	<h1>
		Historial de Postulación <small>N° ${idPostulación}</small>
	</h1>

	<table cellpadding="0" cellspacing="0" border="0"
		class="table table-striped table-bordered table-hover"
		style="background-color: white;">
		<thead>
			<tr>
				<th>Fecha</th>
				<th>Acción</th>
				<th>Comentario</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="historial" items="${listaHistorial}">
				<tr class="odd_gradeA">
					<td>${historial.fecha}</td>
					<td>${historial.accion}</td>
					<td>${historial.comentario}</td>
				</tr>
			</c:forEach>
		</tbody>

	</table>

</body>
</html>