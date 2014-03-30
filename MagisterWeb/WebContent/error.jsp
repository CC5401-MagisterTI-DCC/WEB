<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.io.*" pageEncoding="UTF-8" isErrorPage="true"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error - Admin SP</title>
<link rel="shortcut icon"
	href="${root}/favicon.ico" />
<link rel="stylesheet"
	href="${root}/css/bootstrap.min.css">
<script src="${root}/js/jquery.js"></script>
<script src="${root}/js/bootstrap.min.js"></script>

<style>
.center {
	text-align: center;
	margin-left: auto;
	margin-right: auto;
	margin-bottom: auto;
	margin-top: auto;
}
</style>

</head>
<body>

	<div class="hero-unit center">
		<h1>¡Error!</h1>
		<br />
		<p ${param.type.equals("404") ? '' : 'hidden'}>
			La página que se solicitó no se encuentra: <b>Error 404</b>.
		</p>		
		<p ${param.type.equals("500") ? '' : 'hidden'}>
			<b>Oops! Lo sentimos</b> Ha ocurrido un error en el servidor: <b>Error
				500</b> El evento ha sido notificado a nuestro equipo técnico el cual se
			encuentra trabajando en el problema.
		</p>
		<p ${param.type==null ? '' : 'hidden' }>¡Se ha producido un error
			inesperado! El evento ha sido notificado a nuestro equipo técnico el
			cual se encuentra trabajando en el problema.</p>


		<p>
			<b>Por favor elija una de las siguientes acciones:</b>
		</p>
		<a class="btn btn-info btn-large"
			onclick="window.history.back();parent.$.fancybox.close();"><i
			class="icon-circle-arrow-left icon-white"></i> Volver Atrás</a> 
		<a class="btn btn-danger btn-large" onclick="parent.$.fancybox.close();"
			href="${root}/index.jsp"><i
			class="icon-home icon-white"></i> Ir Al Inicio</a>
	</div>


	<!-- 
	<%
	StringWriter stringWriter = new StringWriter();
	PrintWriter printWriter = new PrintWriter(stringWriter);
	
	if(exception!=null) 
		exception.printStackTrace(printWriter);
	
	out.println(stringWriter);
	printWriter.close();
	stringWriter.close();
	%>
	 -->


</body>
</html>