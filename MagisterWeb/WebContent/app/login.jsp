<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Admin Login</title>
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/favicon.ico" />
<!-- Bootstrap -->
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="<%=request.getContextPath()%>/css/bootstrap-responsive.css"
	rel="stylesheet" media="screen">
<link href="<%=request.getContextPath()%>/css/admin/styles.css"
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
			action="<%=request.getContextPath()%>/app/login">
			<h2 class="form-signin-heading">Login Admin</h2>
			<div class="alert alert-error fade in"
				${incorrecto!=null ? '' : 'hidden' }>

				<button class="close" data-dismiss="alert" type="button">×

				</button>
				<strong> Usuario o Contraseña Incorrecta </strong> Ingrese sus datos
				nuevamente.


			</div>
			<input type="text" name="username" id="username"
				class="input-block-level" placeholder="Nombre de usuario" required>
			<input type="password" name="password" id="password"
				class="input-block-level" placeholder="Contraseña" required>
			<button class="btn btn-large btn-block btn-primary" type="submit">Ingresar</button>
		</form>

	</div>
	<!-- /container -->
	<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
</body>
</html>