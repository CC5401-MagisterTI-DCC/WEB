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
		<form class="form-signin medium-form" method="post" action="${root}/app/nuevaPassword">
			<h2 class="form-signin-heading">Reestablecer Contraseña</h2>
				
			<div ${exito!=null ? '' : 'hidden'}>
				<p>Se reestableció la contraseña de manera exitosa.</p>
	
				<a type="button" class="btn btn-large btn-block btn-success"
					href="${root}/app/login">
					<span class="icon-circle-arrow-left icon-white"></span> Volver al login
				</a>			
			</div>
			
			<div ${exito==null ? '' : 'hidden' }>
				<p>Escriba dos veces su nueva contraseña.</p>
				<div class="alert alert-error fade in"
					${incorrecto!=null ? '' : 'hidden' }>
	
					<button class="close" data-dismiss="alert" type="button">×
	
					</button>
					<strong> Las contraseñas no coinciden.</strong>
	
	
				</div>
				<div class="input-prepend">
  					<span class="add-on"><i class="icon-user"></i></span>
  					<div style="overflow: hidden">
  						<input class="uneditable-input" type="text" value="${usuario}">
  					</div>
  					
				</div>
				<input type="hidden" name="usuario" id="usuario" value="${usuario}">
				<input type="hidden" name="codigo" id="codigo" value="${codigo}">
				<input type="password" name="password" id="password"
					class="input-block-level" placeholder="Contraseña nueva" required>
				<input type="password" name="password_confirmation" id="password_confirmation"
					class="input-block-level" placeholder="La misma contraseña" required>
				<input class="btn btn-large btn-block btn-primary" type="submit" id="submit_button" value="Reestablecer contraseña">
			</div>
		</form>		
	</div>
		

	</div>
	<!-- /container -->
	<script src="${root}/js/jquery.js"></script>
	<script src="${root}/js/bootstrap.js"></script>
</body>
</html>