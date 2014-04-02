<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="${root}/favicon.ico" />
<link rel="stylesheet" href="${root}/css/bootstrap.min.css">

<script src="${root}/js/jquery.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Exito!</title>
</head>
<body>

	<div class="thumbnail center well well-small"
		style="text-align: center;">
		<h2>Éxito!</h2>

		<p>La operación ha sido concluida de manera exitosa.</p>

		<button type="button" class="btn btn-large btn-block btn-success"
			onclick="parent.$.fancybox.close();parent.location.reload()">
			<i class="icon-circle-arrow-left icon-white"></i> Volver Atrás
		</button>
	</div>

</body>
</html>