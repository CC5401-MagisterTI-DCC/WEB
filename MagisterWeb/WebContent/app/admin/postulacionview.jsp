<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Usuario</title>
<link rel="stylesheet" href="${root}/css/bootstrap.min.css">
<link rel="stylesheet" href="${root}/css/datepicker.css">

<style>
body {
	background-color: #f9f9f9;
}
</style>

<script src="${root}/js/jquery.js"></script>
<script src="${root}/js/bootstrap.js"></script>
<script src="${root}/js/jquery.validate.js"></script>
<script src="${root}/js/admin/postulacionview.js"></script>
<script src="${root}/js/bootstrap-datepicker.js"></script>
<script src="${root}/js/locales/bootstrap-datepicker.es.js"></script>

</head>
<body>

	<div class="container-fluid">

		<h1>Postulación de ${postulante.primerNombre} ${ postulante.segundoNombre }
			${postulante.apellidoPaterno } ${postulante.apellidoMaterno }</h1>
		<div class="row-fluid">
			<div class="span6">
				<div class="accordion" id="accordion1">
					<div class="accordion-group">
						<div class="accordion-heading">
							<a class="accordion-toggle" data-toggle="collapse"
								data-parent="#accordion1" href="#collapseOne"> Documentos
								Adjuntos </a>
						</div>
						<div id="collapseOne" class="accordion-body collapse in">
							<div class="accordion-inner">
								<label class="checkbox"> <input type="checkbox"
									value="${postulacion.idCV}" ${not empty mostrarCheck ? '':'hidden'}><a
									href="documento?id=${postulacion.idCV}"><i
										class="icon-file"></i> Currículum Vitae </a>
									${CV.comentario!=null&&CV.comentario.equalsIgnoreCase("rechazado") ? '<span class="label label-important"><i class="icon-info-sign icon-white"></i> Rechazado</span>' :''}
									${CV.comentario!=null&&CV.comentario.equalsIgnoreCase("nuevo") ? '<span class="label label-info"><i class="icon-info-sign icon-white"></i> Nuevo</span>'  :''}
								</label> <label class="checkbox"> <input type="checkbox"
									value="${postulacion.idCartaPresentacion}" ${not empty mostrarCheck ? '':'hidden'}><a
									href="documento?id=${postulacion.idCartaPresentacion}"><i
										class="icon-file"></i> Carta de Presentación </a>
									${CartaPresentacion.comentario!=null&&CartaPresentacion.comentario.equalsIgnoreCase("rechazado") ? '<span class="label label-important"><i class="icon-info-sign icon-white"></i> Rechazado</span>' :''}
									${CartaPresentacion.comentario!=null&&CartaPresentacion.comentario.equalsIgnoreCase("nuevo") ? '<span class="label label-info"><i class="icon-info-sign icon-white"></i> Nuevo</span>'  :''}
								</label> <label class="checkbox"> <input type="checkbox"
									value="${postulacion.idCarta1}" ${not empty mostrarCheck ? '':'hidden'}><a
									href="documento?id=${postulacion.idCarta1}"><i
										class="icon-file"></i> Carta de Recomendación 1 </a>
									${Carta1.comentario!=null&&Carta1.comentario.equalsIgnoreCase("rechazado") ? '<span class="label label-important"><i class="icon-info-sign icon-white"></i> Rechazado</span>' :''}
									${Carta1.comentario!=null&&Carta1.comentario.equalsIgnoreCase("nuevo") ? '<span class="label label-info"><i class="icon-info-sign icon-white"></i> Nuevo</span>'  :''}
								</label> <label class="checkbox"> <input type="checkbox"
									value="${postulacion.idCarta2}" ${not empty mostrarCheck ? '':'hidden'}><a
									href="documento?id=${postulacion.idCarta2}"><i
										class="icon-file"></i> Carta de Recomendación 2 </a>
									${Carta2.comentario!=null&&Carta2.comentario.equalsIgnoreCase("rechazado") ? '<span class="label label-important"><i class="icon-info-sign icon-white"></i> Rechazado</span>' :''}
									${Carta2.comentario!=null&&Carta2.comentario.equalsIgnoreCase("nuevo") ? '<span class="label label-info"><i class="icon-info-sign icon-white"></i> Nuevo</span>'  :''}
								</label>
							</div>
						</div>
					</div>
					<div class="accordion-group">
						<div class="accordion-heading">
							<a class="accordion-toggle" data-toggle="collapse"
								data-parent="#accordion1" href="#collapseTwo"> Datos
								Personales </a>
						</div>
						<div id="collapseTwo" class="accordion-body collapse">
							<div class="accordion-inner">
								<div class="row-fluid">
									<div class="span6">
										<address>
											<Strong>Nombre:</Strong><br> ${postulante.primerNombre}
											${ postulante.segundoNombre }<br>
											${postulante.apellidoPaterno } ${postulante.apellidoMaterno }
										</address>

										<address>
											<strong>Nacionalidad:</strong><br>
											${postulante.nacionalidad.nombre}
										</address>

										<address>
											<strong>Género:</strong><br> ${postulante.genero }
										</address>
										<c:if test="${not empty datosEmpresa}">						
											<div id="datosEmpresa">
												<address>
													<strong>Datos de Empresa:</strong><br> Nombre:
													${datosEmpresa.nombre}<br> Cargo: ${datosEmpresa.cargo}<br>
													Dirección: ${datosEmpresa.direccion}<br> Teléfono:
													${datosEmpresa.telefono}<br>
												</address>
											</div>
										</c:if>	
									</div>
									<div class="span6">
										<address>
											<strong>Fecha de nacimiento:</strong><br>
											<fmt:formatDate type="date" pattern="dd/MM/yyyy"
												value="${postulante.nacimiento }" />
										</address>

										<address>
											<strong>Teléfonos de contacto:</strong><br>
											${postulante.telefono }<br> ${postulante.celular }
										</address>

										<address>
											<strong>Email:</strong><br> ${postulante.email }
										</address>

										<address>
											<strong>Dirección:</strong><br> ${postulante.direccion }<br>
											${postulante.paisResidencia.nombre }
										</address>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="accordion-group">
						<div class="accordion-heading">
							<a class="accordion-toggle" data-toggle="collapse"
								data-parent="#accordion1" href="#collapseThree"> Grados
								Académicos </a>
						</div>
						<div id="collapseThree" class="accordion-body collapse">
							<div class="accordion-inner">
								<c:forEach var="grado" items="${gradosAcademicos}"
									varStatus="status">
									<address>
										<strong>${grado.nombre}:</strong><br> Institución:
										${grado.institucion } , ${grado.pais.nombre }<br> Fecha
										de obtención: ${grado.fechaObtencion }<br> <label
											class="checkbox"> <input type="checkbox"
											value="${grado.idCertificadoNotas}" ${not empty mostrarCheck ? '':'hidden'}><a
											href="documento?id=${grado.idCertificadoNotas}"><i
												class="icon-file"></i> Certificado de Notas </a>
											${docGrados.get(status.index*2).comentario!=null&&docGrados.get(status.index*2).comentario.equalsIgnoreCase("rechazado") ? '<span class="label label-important"><i class="icon-info-sign icon-white"></i> Rechazado</span>' :''}
											${docGrados.get(status.index*2).comentario!=null&&docGrados.get(status.index*2).comentario.equalsIgnoreCase("nuevo") ? '<span class="label label-info"><i class="icon-info-sign icon-white"></i> Nuevo</span>'  :''}
										</label> <label class="checkbox"> <input type="checkbox"
											value="${grado.idCertificadoTitulo}" ${not empty mostrarCheck ? '':'hidden'}><a
											href="documento?id=${grado.idCertificadoTitulo}"><i
												class="icon-file"></i> Certificado de Título </a>
											${docGrados.get(status.index*2+1).comentario!=null&&docGrados.get(status.index*2+1).comentario.equalsIgnoreCase("rechazado") ? '<span class="label label-important"><i class="icon-info-sign icon-white"></i> Rechazado</span>' :''}
											${docGrados.get(status.index*2+1).comentario!=null&&docGrados.get(status.index*2+1).comentario.equalsIgnoreCase("nuevo") ? '<span class="label label-info"><i class="icon-info-sign icon-white"></i> Nuevo</span>'  :''}
										</label>
									</address>
								</c:forEach>
							</div>
						</div>
					</div>
					<div class="accordion-group">
						<div class="accordion-heading">
							<a class="accordion-toggle" data-toggle="collapse"
								data-parent="#accordion1" href="#collapseFour"> Datos de
								Postulación </a>
						</div>
						<div id="collapseFour" class="accordion-body collapse">
							<div class="accordion-inner">
								<address>
									<Strong>Fecha de Ingreso:</Strong><br>
									<fmt:formatDate type="date" pattern="dd/MM/yyyy"
										value="${postulacion.fechaIngreso }" />
								</address>
								<c:if test="${not empty resolucion}">
									<address>
										<Strong>Fecha de Resolución:</Strong><br>
										<fmt:formatDate type="date" pattern="dd/MM/yyyy"
											value="${resolucion.fecha}" />
									</address>
								</c:if>
								<c:if test="${not empty postulacion.deadline}">
									<address>
										<Strong>Deadline:</Strong><br> ${postulacion.deadline }
									</address>
								</c:if>
								<address>
									<Strong>Financiamiento:</Strong><br> Tipo:
									${financiamiento.tipo} <br> Detalle:
									${financiamiento.detalle }
								</address>
							</div>
						</div>
					</div>
					<c:if test="${not empty docExtras}">
						<div class="accordion-group">
							<div class="accordion-heading">
								<a class="accordion-toggle" data-toggle="collapse"
									data-parent="#accordion1" href="#collapseFive"> Documentos
									Extra </a>
							</div>
							<div id="collapseFive" class="accordion-body collapse">
								<div class="accordion-inner">
									<c:forEach items="${docExtras}" var="doc" varStatus="status">
										<a href="documento?id=${doc.id}"><i class="icon-file"></i>Documento
											Extra N°${status.count}</a>
										<p>
											<strong>Descripción: </strong>${doc.comentario}</p>
										<br>
									</c:forEach>
								</div>
							</div>
						</div>
					</c:if>
					<div class="accordion-group">
						<div class="accordion-heading">
							<a class="accordion-toggle" data-toggle="collapse"
								data-parent="#accordion1" href="#collapseSix"> Resolución postulación anterior </a>
						</div>
						<div id="collapseSix" class="accordion-body collapse">
							<div class="accordion-inner">
							<c:if test="${not empty historialPostulacionesResueltas}">
								<address>
									<p><Strong><fmt:formatDate type="date" pattern="dd/MM/yyyy"
													value="${historialPostulacionesResueltas.get(0).fecha}" /> 
													${historialPostulacionesResueltas.get(0).accion.substring(24)}</Strong></p>
									<c:forEach items="${historialPostulacionesResueltas}" var="hist" begin="1" varStatus="status">
									<div class="well well-small" style="width:320px;background:white">
										${hist.accion.replaceFirst(":", "<br>")}<br>
										Comentario: <i>${hist.comentario}</i><br>
									</div>
									</c:forEach>
								</address>
							</c:if>
							<c:if test="${empty historialPostulacionesResueltas}">
								<p><Strong>No hay postulaciones anteriores</Strong></p>
							</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="span6">				
				<div class="well" style="text-align: center;">
					<c:if test="${not empty param.revision}">
						<jsp:include page="estadosPostulacion/revision.jsp"></jsp:include>
					</c:if>
					<c:if test="${not empty param.validacion}">
						<jsp:include page="estadosPostulacion/validacion.jsp"></jsp:include>
					</c:if>
					<c:if test="${not empty param.consideracion}">
						<jsp:include page="estadosPostulacion/consideracion.jsp"></jsp:include>
					</c:if>
					<c:if test="${not empty param.evaluacion}">
						<jsp:include page="estadosPostulacion/evaluacion.jsp"></jsp:include>	
					</c:if>
					<c:if test="${not empty param.decision}">
						<jsp:include page="estadosPostulacion/decision.jsp"></jsp:include>
					</c:if>
					<c:if test="${not empty param.espera_notificacion}">
						<jsp:include page="estadosPostulacion/espera_notificacion.jsp"></jsp:include>
					</c:if>
					<c:if test="${not empty param.resuelta}">
						<jsp:include page="estadosPostulacion/resuelta.jsp"></jsp:include>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$('#calendar-embedded').datepicker({
			weekStart: 1, 
		    language: "es",
		}).on('changeDate', function(e){
		      $('#deadline').val(e.format('dd/mm/yyyy'))
	    });
	</script>
</body>
</html>