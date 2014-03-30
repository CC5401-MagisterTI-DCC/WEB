<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title>Cambio de Contraseña - Magister TI</title>
<!-- Bootstrap -->
<link href="${root}/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="${root}/css/bootstrap-responsive.css"
	rel="stylesheet" media="screen">
<link href="${root}/css/admin/styles.css"
	rel="stylesheet" media="screen">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
</head>
<body id="login">
	<div class="container">

		<form class="form-signin" method="post"
			action="${root}/app/admin/passwordChange">
			<h2 class="form-signin-heading">Cambio de Contraseña</h2>
			<div class="alert alert-error fade in"
				${incorrecto!=null ? '' : 'hidden' }>
				<button class="close" data-dismiss="alert" type="button">×
				</button>
				<strong> Contraseña anterior no coincide </strong> Ingrese sus datos
				nuevamente.
			</div>
			<div class="alert alert-error fade in"
				${noigual!=null ? '' : 'hidden' }>
				<button class="close" data-dismiss="alert" type="button">×
				</button>
				<strong> Contraseñas no coinciden </strong> Ingrese sus datos
				nuevamente.
			</div>

			<input type="password" name="old" id="old" class="input-block-level"
				placeholder="Contraseña Actual" required> <input
				type="password" name="new" id="new" class="input-block-level"
				placeholder="Contraseña Nueva" required> <input
				type="password" name="new2" id="new2" class="input-block-level"
				placeholder="Ingrese nuevamente la contraseña nueva" required>
			<button class="btn btn-large btn-block btn-primary" type="submit">Cambiar
				Contraseña</button>
		</form>

	</div>
	<!-- /container -->
	<script src="${root}/js/jquery.js"></script>
	<script src="${root}/js/bootstrap.js"></script>
</body>
</html>