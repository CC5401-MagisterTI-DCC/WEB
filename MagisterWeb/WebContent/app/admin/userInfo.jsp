<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

	<h1>Datos de Usuario</h1>
	<div class="row-fluid">
		<div class="span6">
			<address>
				<Strong>Nombre:</Strong><br> ${postulante.primerNombre} ${
				postulante.segundoNombre }<br>
				${postulante.apellidoPaterno } ${postulante.apellidoMaterno }
			</address>

			<address>
				<strong>Nacionalidad:</strong><br>
				${postulante.nacionalidad.nombre}
			</address>

			<address>
				<strong>Género:</strong><br> ${postulante.genero }
			</address>

			<div id="datosEmpresa" ${datosEmpresa==null ? 'hidden' : '' }>
				<address>
					<strong>Datos de Empresa:</strong><br> Nombre:
					${datosEmpresa.nombre}<br> Cargo: ${datosEmpresa.cargo}<br>
					Dirección: ${datosEmpresa.direccion}<br> Teléfono:
					${datosEmpresa.telefono}<br>
				</address>
			</div>
		</div>
		<div class="span6">
			<address>
				<strong>Fecha de nacimiento:</strong><br>
				<fmt:formatDate type="date" pattern="dd/MM/yyyy"
					value="${postulante.nacimiento }" />
			</address>

			<address>
				<strong>Teléfonos de contacto:</strong><br>
				${postulante.telefono }<br> ${postulante.celular }
			</address>

			<address>
				<strong>Email:</strong><br> ${postulante.email }
			</address>

			<address>
				<strong>Dirección:</strong><br> ${postulante.direccion }<br>
				${postulante.paisResidencia.nombre }
			</address>
		</div>
	</div>
</body>
</html>