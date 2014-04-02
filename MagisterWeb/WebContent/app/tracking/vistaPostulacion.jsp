<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.io.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="${root}/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Tracking postulación ${track} - Magister TI DCC</title>
<link rel="stylesheet" href="${root}/css/bootstrap.min.css">
<link rel="stylesheet" href="${root}/css/bootstrap-responsive.css">
<link rel="stylesheet" href="${root}/css/header.css">
<link rel="stylesheet" href="${root}/css/footer.css">
<link rel="stylesheet"
	href="${root}/fancybox/source/jquery.fancybox.css?v=2.1.5"
	type="text/css" media="screen" />


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
	<jsp:include page="../../header.jsp"></jsp:include>
	<div class="container">

		<h1>
			Tracking de Postulación <small>${track}</small>
		</h1>
		<strong>Progreso de proceso de postulación:</strong> <span
			class="pull-right">${validacion ? '25%' : evaluacion ? '60%' :
			'100%'}</span>
		<div class="progress progress-striped active">
			<div class="bar"
				style="width: ${validacion ? '25%' : evaluacion ? '60%' : '100%'};"></div>
		</div>



		<ul class="thumbnails">

			<li class="span4">
				<div class="thumbnail valid-thumb">
					<img src="${root}/img/business-success.jpg" style="height: 180px;"
						alt="ALT NAME">
					<div class="caption">
						<h3>
							En Validación &nbsp;
							<c:choose>
								<c:when test="${validacion}">
									<span class="label label-warning">En progreso ...</span>
								</c:when>
								<c:when test="${evaluacion}">
									<span class="label label-success">Listo</span>
								</c:when>
								<c:otherwise>
									<span class="label label-success">Listo</span>
								</c:otherwise>
							</c:choose>
						</h3>
						<p>Durante esta etapa se realiza la validación de documentos,
							es decir, se verifica que éstos fueron ingresados correctamente y
							que corresponden a los solicitados. Si algún documento es
							incorrecto se le enviará un correo electrónico con las
							instrucciones para el reenvío.</p>

					</div>
				</div>
			</li>
			<li class="span4">
				<div class="thumbnail progress-thumb">
					<img src="${root}/img/report_icon.gif" style="height: 180px;"
						alt="ALT NAME">
					<div class="caption">
						<h3>
							En Evaluación &nbsp;
							<c:choose>
								<c:when test="${validacion}">
								</c:when>
								<c:when test="${evaluacion}">
									<span class="label label-warning">En progreso ...</span>
								</c:when>
								<c:otherwise>
									<span class="label label-success">Listo</span>
								</c:otherwise>
							</c:choose>
						</h3>
						<p>Durante esta etapa, los miembros de la comisión evaluadora,
							deliberan respecto a la resolución de su postulación.</p>

					</div>
				</div>
			</li>
			<li class="span4">
				<div class="thumbnail">
					<c:choose>
						<c:when test="${resuelta}">
							<c:if test="${resolucion.resultado.id==1}">
								<img src="${root}/img/right.png" style="height: 180px;"
									alt="ALT NAME">
								<div class="caption">
									<h3>Resolución Tomada</h3>
									<p>
										Su postulación se encuentra <strong><span
											class="text-success">aceptada</span></strong>. Por favor revise su
										correo.
									</p>
								</div>
							</c:if>
							<c:if test="${resolucion.resultado.id==2}">
								<img src="${root}/img/wrong.png" style="height: 180px;"
									alt="ALT NAME">
								<div class="caption">
									<h3>Resolución Tomada</h3>
									<p>
										Su postulación se encuentra <strong><span
											class="text-error">rechazada</span></strong>. Por favor revise su
										correo electrónico.
									</p>
								</div>
							</c:if>
							<c:if test="${resolucion.resultado.id==3}">
								<img src="${root}/img/right.png" style="height: 180px;"
									alt="ALT NAME">
								<div class="caption">
									<h3>Resolución Tomada</h3>
									<p>
										Su postulación se encuentra <strong><span
											class="text-warning">aceptada condicionalemente</span></strong>. Por
										favor revise su correo electrónico.
									</p>
								</div>
							</c:if>
						</c:when>
						<c:otherwise>
							<img src="${root}/img/question.png" style="height: 180px;"
								alt="ALT NAME">
							<div class="caption">
								<h3>Resolución Tomada</h3>
								<p>Esta etapa corresponde a la finalización del proceso.
									Usted recibirá un correo electrónico donde se le informa la
									resolución de su postulación junto con la información de como
									proceder.</p>
							</div>
						</c:otherwise>
					</c:choose>

				</div>
			</li>

		</ul>
		<div style="text-align: center;">
			<h3>Acciones</h3>

			<a class="btn btn-success btn-large" href="${root}/index.jsp"> <i
				class="icon-home icon-white"></i> Ir Al Inicio
			</a>
			<c:if test="${!resuelta}">
				<a href="track/delete?track=${track}"
					class="fancyDelete btn btn-danger btn-large"
					data-fancybox-type="iframe"> <i class="icon-remove icon-white"></i>
					Eliminar postulación
				</a>
			</c:if>
		</div>

	</div>

	<jsp:include page="../../footer.jsp"></jsp:include>
	<script src="${root}/js/jquery.js"></script>
	<script type="text/javascript"
		src="${root}/fancybox/lib/jquery.mousewheel-3.0.6.pack.js"></script>
	<script type="text/javascript"
		src="${root}/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
	<script src="${root}/js/delete.js"></script>
	<script src="${root}/js/bootstrap.js"></script>

</body>
</html>