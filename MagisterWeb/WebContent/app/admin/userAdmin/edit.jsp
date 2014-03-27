<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Editar Usuario</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">

<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
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
			<legend>Editar Usuario</legend>

			<input type="hidden" id="id" name="id" value="${usuario.id }">
			<input type="hidden" id="edit" name="edit" value="true">
			<!-- Text input-->
			<div class="control-group">
				<label class="control-label" for="username">Username:</label>
				<div class="controls">
					<input id="username" name="username"
						placeholder="Ingrese un nombre de usuario" class="input-xlarge"
						value="${usuario.username }" required="" type="text">

				</div>
			</div>

			<!-- Text input-->
			<div class="control-group">
				<label class="control-label" for="email">Correo:</label>
				<div class="controls">
					<input id="email" name="email"
						placeholder="Ingrese un correo válido" class="input-xlarge"
						value="${usuario.email }" required="" type="text">

				</div>
			</div>

			<!-- Select Basic -->
			<div class="control-group">
				<label class="control-label" for="rol">Rol:</label>
				<div class="controls">
					<select id="rol" name="rol" class="input-xlarge">
						<option ${usuario.idRol==2 ? 'selected' : ''} value="2">Coordinador</option>
						<option ${usuario.idRol==3 ? 'selected' : ''} value="3">Comisionado</option>
						<option ${usuario.idRol==4 ? 'selected' : ''} value="4">Asistente</option>
						<option ${usuario.idRol==5 ? 'selected' : ''} value="5">Jefe
							del PEC</option>
					</select>
				</div>
			</div>

			<!-- Button (Double) -->
			<div class="control-group">
				<label class="control-label" for="aceptar"></label>
				<div class="controls">
					<button id="aceptar" name="aceptar" class="btn btn-success">Guardar
						Cambios</button>
					<button id="cancelar" name="cancelar" class="btn btn-danger">Cancelar</button>
				</div>
			</div>

		</fieldset>
	</form>
</body>
</html>