<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Editar Usuario</title>
<link rel="stylesheet"
	href="${root}/css/bootstrap.min.css">

<script src="${root}/js/jquery.js"></script>
<script src="${root}/js/bootstrap.js"></script>
<style>
body {
	background-color: #f9f9f9;
}
</style>
</head>
<body>
	<form class="form-horizontal" method="post" action="userAdmin">
		<fieldset>

			<!-- Form Name -->
			<legend>Nuevo Usuario</legend>
			<input type="hidden" id="new" name="new" value="true">
			<!-- Text input-->
			<div class="control-group">
				<label class="control-label" for="username">Username:</label>
				<div class="controls">
					<input id="username" name="username"
						placeholder="Ingrese un nombre de usuario" class="input-xlarge"
						required="" type="text">

				</div>
			</div>

			<!-- Text input-->
			<div class="control-group">
				<label class="control-label" for="email">Correo:</label>
				<div class="controls">
					<input id="email" name="email"
						placeholder="Ingrese un correo válido" class="input-xlarge"
						required="" type="text">

				</div>
			</div>

			<!-- Password input-->
			<div class="control-group">
				<label class="control-label" for="password">Contraseña:</label>
				<div class="controls">
					<input id="password" name="password"
						placeholder="Ingrese una contraseña" class="input-xlarge"
						required="" type="password">

				</div>
			</div>

			<!-- Select Basic -->
			<div class="control-group">
				<label class="control-label" for="rol">Rol:</label>
				<div class="controls">
					<select id="rol" name="rol" class="input-xlarge">
						<option value="2">Coordinador</option>
						<option value="3">Comisionado</option>
						<option value="4">Asistente</option>
						<option value="5">Jefe del PEC</option>
					</select>
				</div>
			</div>

			<!-- Button (Double) -->
			<div class="control-group">
				<label class="control-label" for="aceptar"></label>
				<div class="controls">
					<button id="aceptar" name="aceptar" class="btn btn-primary">Crear</button>
					<button id="cancelar" name="cancelar" class="btn btn-danger">Cancelar</button>
				</div>
			</div>

		</fieldset>
	</form>

</body>
</html>