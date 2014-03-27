<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Eliminar Usuario</title>

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

			<h2>Eliminar Usuario</h2>

			<p>¿Desea realmente eliminar al usuario ${usuario.username }?</p>
			<input id="delete" name="delete" type="hidden" value="true" /> <input
				id="id" name="id" type="hidden" value="${usuario.id}" />

			<div class="form-actions">
				<button type="submit" class="btn btn-primary">Sí</button>
				<button type="button" class="btn btn-danger"
					onclick="parent.$.fancybox.close()">Cancelar</button>
			</div>
		</fieldset>
	</form>
</body>
</html>