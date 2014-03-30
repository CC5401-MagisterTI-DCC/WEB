<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Magister TI - DCC</title>
<link rel="shortcut icon"
	href="${root}/favicon.ico" />
<!-- CSS -->
<link rel="stylesheet" type="text/css" href="../css/DT_bootstrap.css">
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/bootstrap-responsive.css">
<link rel="stylesheet" href="../css/header.css">
<link rel="stylesheet" href="../css/prettify.css">
<link rel="stylesheet" href="../css/footer.css">


</head>
<body>

	<jsp:include page="../header.jsp"></jsp:include>


	<div class="container">
		<div class="row">
			<div class="page-header">
				<h1>
					Formulario de Reenvio de Documentos <small>Magister en TI</small>
				</h1>
			</div>



			<c:choose>
				<c:when test="${inputs.size()==0 }">
					<h1>¡Todos los documentos son válidos!</h1>
				</c:when>
				<c:otherwise>
					<form id="postulacionForm" method="post"
						action="${root}/app/reenvio"
						enctype="multipart/form-data" class="form-horizontal">
						<h3>Ingrese a continuación los elementos que han sido
							rechazados en su postulación</h3>
						<div class="well ">

							<div class="control-group">
								<div class="controls">
									<span class="label label-warning"><i
										class="icon-info-sign icon-white"></i> Nota:</span>&nbsp;Sólo se
									permiten archivos en formato PDF.
								</div>
							</div>
							<input type="hidden" value="${idPostulacion }">
							<c:forEach items="${inputs}" var="input">

								<!-- File Button -->
								<div class="control-group">
									<label class="control-label" for="cv">${input.label }</label>
									<div class="controls">
										<input id="${input.id }" name="${input.name }"
											class="input-file" type="file" required="">
									</div>
								</div>

							</c:forEach>
							<input type="hidden" name="idPostulacion"
								value="${idPostulacion}">
							<!-- Button -->
							<div class="control-group">
								<label class="control-label" for="singlebutton"></label>
								<div class="controls">
									<button type="submit" class="btn btn-large btn-primary">Enviar
										Formulario</button>
								</div>
							</div>
						</div>
					</form>
				</c:otherwise>
			</c:choose>

		</div>
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
	<!-- Scripts -->
	<script src="../js/jquery.js"></script>
	<script src="../js/jquery.validate.js"></script>
	<script src="../js/prettify.js" type="text/javascript"></script>
	<script src="../js/jquery.bootstrap.wizard.min.js"
		type="text/javascript"></script>
	<script src="../js/bootstrap.js" type="text/javascript"></script>
	<script src="../js/reenvio.js" type="text/javascript"></script>
</body>
</html>