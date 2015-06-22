<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>Reestablecer Contraseña</title>
<link rel="shortcut icon" href="${root}/favicon.ico" />
<!-- Bootstrap -->
<link href="${root}/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="${root}/css/bootstrap-responsive.css" rel="stylesheet"
	media="screen">
<link href="${root}/css/admin/styles.css" rel="stylesheet"
	media="screen">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
</head>
<body id="login">
	<div class="container">
		<form class="form-signin big-form" method="post" action="${root}/app/olvideMiPassword">
			<h2 class="form-signin-heading">Reestablecer Contraseña</h2>
				
			<div ${exito!=null ? '' : 'hidden'}>
				<p>El correo fue enviado exitosamente. Por motivos de seguridad su contenido será válido sólo las siguientes 12 horas.</p>
	
				<a type="button" class="btn btn-large btn-block btn-success"
					href="${root}">
					<span class="icon-circle-arrow-left icon-white"></span> Volver
				</a>			
			</div>
			
			<div ${exito==null ? '' : 'hidden' }>
				<p>Ingrese su nombre de usuario y su email. Se le enviará un correo con el resto de los pasos a seguir.</p>
				<div class="alert alert-error fade in"
					${incorrecto!=null ? '' : 'hidden' }>
	
					<button class="close" data-dismiss="alert" type="button">×
	
					</button>
					<strong> El nombre de usuario y el email ingresado no coinciden. </strong>
	
	
				</div>
				<input type="text" name="username" id="username"
					class="input-block-level" placeholder="Nombre de usuario" required>
				<input type="email" name="email" id="email"
					class="input-block-level" placeholder="Email" required>
				<input class="btn btn-large btn-block btn-primary" type="submit" id="submit_button" value="Enviar correo">
			</div>
		</form>		
	</div>
		

	</div>
	<!-- /container -->
	<script src="${root}/js/jquery.js"></script>
	<script src="${root}/js/bootstrap.js"></script>
</body>
</html>