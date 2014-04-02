<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Eliminar Usuario</title>

<link rel="stylesheet" href="${root}/css/bootstrap.min.css">

<script src="${root}/js/jquery.js"></script>
<script src="${root}/js/bootstrap.js"></script>
</head>
<body>
	<form class="form-horizontal" id="delete" method="post"
		action="${root}/app/track/delete">
		<fieldset>

			<h2>Eliminar Postulación</h2>

			<p>¿Desea realmente eliminar su postulación?</p>
			<input id="track_n" name="track_n" type="hidden"
				value="${param.track}" />


			<div class="form-actions">
				<button type="submit" class="btn btn-primary">Sí</button>
				<button type="button" class="btn btn-danger"
					onclick="parent.$.fancybox.close()">Cancelar</button>
			</div>
		</fieldset>
	</form>
</body>
</html>