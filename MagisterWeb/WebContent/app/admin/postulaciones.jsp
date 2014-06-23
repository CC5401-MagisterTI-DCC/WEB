<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="cl.uchile.dcc.cc5401.util.ResultadoPostulacion"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html>
<html>
<head>
<title>Postulaciones - Magister TI DCC</title>
<%
	pageContext.setAttribute("aceptado", ResultadoPostulacion.ACEPTADO);
	pageContext.setAttribute("rechazado", ResultadoPostulacion.RECHAZADO);
%>

<link rel="shortcut icon" href="${root}/favicon.ico" />
<!-- Bootstrap -->
<link href="${root}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${root}/css/bootstrap-responsive.css" rel="stylesheet" media="screen">
<link href="${root}/css/header.css" rel="stylesheet" media="screen">
<link href="${root}/css/footer.css" rel="stylesheet" media="screen">
<link href="${root}/css/admin/styles.css" rel="stylesheet" media="screen">
<link href="${root}/css/DT_bootstrap.css" rel="stylesheet" media="screen">

<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="vendors/flot/excanvas.min.js"></script><![endif]-->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->

<!-- FancyBox -->
<link rel="stylesheet" href="${root}/fancybox/source/jquery.fancybox.css?v=2.1.5" type="text/css" media="screen" />
</head>

<body>
	<jsp:include page="../../header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span3">
				<jsp:include page="sidebar.jsp"></jsp:include>
			</div>
			<div class="span9" id="content">
				<div class="row-fluid">
					<h1>Postulaciones <small>Magister en TI</small></h1>
				</div>
				<div class="row-fluid">
					<div class="alert alert-success fade in" ${param.exito!=null ? '' : 'hidden'}>
						<button class="close" data-dismiss="alert" type="button">×</button>
						<strong> Operacion Exitosa </strong> Los datos han sido procesados de manera correcta.
					</div>
				</div>
				<div class="row-fluid">
					<div class="alert alert-success fade in" ${param.nuevaC!=null ? '' : 'hidden' }>
						<button class="close" data-dismiss="alert" type="button">×</button>
						<strong> Operacion Exitosa </strong> La Contraseña se ha cambiado correctamente
					</div>
				</div>
				<div class="block">
					<div class="navbar navbar-inner block-header">
						<ul class="nav">
							<c:if test='${user.rol ne "COMISIONADO"}'>
								<li class="active"><a href="#revision" data-toggle="tab">Revisión
									<c:choose>
										<c:when test='${user.rol eq "ASISTENTE"}'>
											<span ${postulacionesRevision.size()>0 ? 'class="badge badge-warning"' : 'class="badge badge-success"'}>${postulacionesRevision.size()}</span>
										</c:when>
										<c:otherwise>
											<span class="badge badge-info">${postulacionesRevision.size()}</span>
										</c:otherwise>
									</c:choose>
								</a></li>
								<li><a href="#validacion" data-toggle="tab">Validación 
									<c:choose>
										<c:when test='${user.rol eq "JEFE_PEC"}'>
											<span ${postulacionesValidacion.size()>0 ? 'class="badge badge-warning"' : 'class="badge badge-success"'}>${postulacionesValidacion.size()}</span>
										</c:when>
										<c:otherwise>
											<span class="badge badge-info">${postulacionesValidacion.size()}</span>
										</c:otherwise>
									</c:choose>
								</a></li>
								<li><a href="#consideracion" data-toggle="tab">Consideración
									<c:choose>
										<c:when test='${user.rol eq "COORDINADOR"}'>
											<span ${postulacionesConsideracion.size()>0 ? 'class="badge badge-warning"' : 'class="badge badge-success"'}>${postulacionesConsideracion.size()}</span>
										</c:when>
										<c:otherwise>
											<span class="badge badge-info">${postulacionesConsideracion.size()}</span>
										</c:otherwise>
									</c:choose>
								</a></li>
							</c:if>
							<li><a href="#evaluacion" data-toggle="tab">Evaluación 
								<c:choose>
									<c:when test='${user.rol eq "COMISIONADO"}'>
										<span ${nPendientes>0 ? 'class="badge badge-warning"' : 'class="badge badge-success"'}>${nPendientes}/${postulacionesEvaluacion.size()}</span>
									</c:when>
									<c:when test='${user.rol eq "COORDINADOR"}'>
										<span ${nPendientesVoto>0 ? 'class="badge badge-warning"' : 'class="badge badge-success"'}>${nPendientesVoto}/${postulacionesEvaluacion.size()}</span>
									</c:when>
									<c:otherwise>
										<span class="badge badge-info">${postulacionesEvaluacion.size()}</span>
									</c:otherwise>
								</c:choose>
							</a></li>
							<li><a href="#decision" data-toggle="tab">Decisión 
								<c:choose>
									<c:when test='${user.rol eq "COORDINADOR"}'>
										<span ${postulacionesDecision.size()>0 ? 'class="badge badge-warning"' : 'class="badge badge-success"'}>${postulacionesDecision.size()}</span>
									</c:when>
									<c:otherwise>
										<span class="badge badge-info">${postulacionesDecision.size()}</span>
									</c:otherwise>
								</c:choose>
							</a></li>
							<li><a href="#espera_notificacion" data-toggle="tab">Espera
								<c:choose>
									<c:when test='${user.rol eq "ASISTENTE"}'>
										<span ${postulacionesEspera.size()>0 ? 'class="badge badge-warning"' : 'class="badge badge-success"'}>${postulacionesEspera.size()}</span>
									</c:when>
									<c:otherwise>
										<span class="badge badge-info">${postulacionesEspera.size()}</span>
									</c:otherwise>
								</c:choose>
							</a></li>
							<li><a href="#resolucion" data-toggle="tab">Resuelta 
								<span class="badge badge-info">${postulacionesResolucion.size()}</span>
							</a></li>
						</ul>
					</div>
					<div class="block-content collapse in">
						<div class="tab-content">
							<c:if test='${user.rol ne "COMISIONADO"}'>
								<!-- block -->
								<div class="tab-pane active" id="revision">
									<table cellpadding="0" cellspacing="0" border="0"
										class="table table-striped table-hover table-bordered"
										id="revisionTable"
										style="background-color: rgb(255, 255, 255);">
										<thead>
											<tr>
												<th>Identificación</th>
												<th>Fecha de Inicio</th>
												<th>Nombre de Postulante</th>
												<th>Acciones</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="postulacionPostulante"
												items="${postulacionesRevision}">
												<c:set var="postulacion"
													value="${postulacionPostulante.postulacion}"></c:set>
												<c:set var="postulante"
													value="${postulacionPostulante.postulante}"></c:set>
	
												<tr>
													<td>${postulante.identificacion.identificacion}</td>
													<td><fmt:formatDate type="date" pattern="dd/MM/yyyy"
															value="${postulacion.fechaIngreso }" /></td>
													<td><a
														href="postulante?id=${postulacion.idPostulante }"
														class="fancy" data-fancybox-type="iframe">${postulacion.nombrePostulante
															}</a></td>
													<td style="text-align: center;">
	
														<div id="acciones" class="btn-group">
															<a href="historial?idPostulacion=${postulacion.id}"
																class="btn fancy" data-fancybox-type="iframe"
																data-placement="bottom" data-toggle="tooltip"
																data-original-title="Historial Particular"> <i
																class="icon-time"></i>
															</a> <a
																href="${root}/app/admin/edit?id=${postulacion.id }"
																class="btn" data-placement="bottom" data-toggle="tooltip"
																data-original-title="Editar Postulación"> <i
																class="icon-edit"></i>
															</a> <a
																href="postulaciones?action=revisar&id=${postulacion.id}&revision=true"
																class="btn btn-primary fancyPostulacion"
																data-fancybox-type="iframe" data-placement="bottom"
																data-toggle="tooltip"
																data-original-title="Ver Postulación"> <i
																class="icon-eye-open icon-white"></i>
															</a>
														</div>
	
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<!-- /block -->
	
								<!-- block -->
								<div class="tab-pane" id="validacion">
									<table cellpadding="0" cellspacing="0" border="0"
										class="table table-striped table-hover table-bordered"
										id="validacionTable">
										<thead>
											<tr>
												<th>Identificación</th>
												<th>Fecha de Inicio</th>
												<th>Nombre de Postulante</th>
												<th>Acciones</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="postulacionPostulante" items="${postulacionesValidacion}">
												<c:set var="postulacion" value="${postulacionPostulante.postulacion}"></c:set>
												<c:set var="postulante" value="${postulacionPostulante.postulante}"></c:set>
	
												<tr>
													<td>${postulante.identificacion.identificacion }</td>
													<td><fmt:formatDate type="date" pattern="dd/MM/yyyy"
															value="${postulacion.fechaIngreso }" /></td>
													<td><a href="postulante?id=${postulacion.idPostulante }" class="fancy" data-fancybox-type="iframe">
													${postulacion.nombrePostulante}</a></td>
													<td style="text-align: center;">
	
														<div id="acciones" class="btn-group">
															<a href="historial?idPostulacion=${postulacion.id}"
																class="btn fancy" data-fancybox-type="iframe"
																data-placement="bottom" data-toggle="tooltip"
																data-original-title="Historial Particular"> <i
																class="icon-time"></i>
															</a> <a
																href="${root}/app/admin/edit?id=${postulacion.id }"
																class="btn" data-placement="bottom" data-toggle="tooltip"
																data-original-title="Editar Postulación"> <i
																class="icon-edit"></i>
															</a> <a
																href="postulaciones?action=revisar&id=${postulacion.id}&validacion=true"
																class="btn btn-primary fancyPostulacion"
																data-fancybox-type="iframe" data-placement="bottom"
																data-toggle="tooltip"
																data-original-title="Ver Postulación"> <i
																class="icon-eye-open icon-white"></i>
															</a>
														</div>	
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<!-- /block -->							
							
								<!-- block -->
								<div class="tab-pane" id="consideracion">
									<table cellpadding="0" cellspacing="0" border="0"
										class="table table-striped table-hover table-bordered"
										id="consideracionTable">
										<thead>
											<tr>
												<th>Identificación</th>
												<th>Fecha de Inicio</th>
												<th>Nombre de Postulante</th>
												<th>Acciones</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="postulacionPostulante"
												items="${postulacionesConsideracion}">
												<c:set var="postulacion"
													value="${postulacionPostulante.postulacion}"></c:set>
												<c:set var="postulante"
													value="${postulacionPostulante.postulante}"></c:set>
	
												<tr>
													<td>${postulante.identificacion.identificacion }</td>
													<td><fmt:formatDate type="date" pattern="dd/MM/yyyy"
															value="${postulacion.fechaIngreso }" /></td>
													<td><a
														href="postulante?id=${postulacion.idPostulante }"
														class="fancy" data-fancybox-type="iframe">${postulacion.nombrePostulante
															}</a></td>
													<td style="text-align: center;">
	
														<div id="acciones" class="btn-group">
															<a href="historial?idPostulacion=${postulacion.id}"
																class="btn fancy" data-fancybox-type="iframe"
																data-placement="bottom" data-toggle="tooltip"
																data-original-title="Historial Particular"> <i
																class="icon-time"></i>
															</a> <a
																href="${root}/app/admin/edit?id=${postulacion.id }"
																class="btn" data-placement="bottom" data-toggle="tooltip"
																data-original-title="Editar Postulación"> <i
																class="icon-edit"></i>
															</a> <a
																href="postulaciones?action=revisar&id=${postulacion.id}&consideracion=true"
																class="btn btn-primary fancyPostulacion"
																data-fancybox-type="iframe" data-placement="bottom"
																data-toggle="tooltip"
																data-original-title="Ver Postulación"> <i
																class="icon-eye-open icon-white"></i>
															</a>
														</div>
	
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<!-- /block -->
							</c:if>
							
							<!-- block -->
							<c:choose>
								<c:when test='${user.rol eq "COMISIONADO"}'>
									<div class="tab-pane active" id="evaluacion">
								</c:when>
								<c:otherwise>
									<div class="tab-pane" id="evaluacion">
								</c:otherwise>
							</c:choose>
								<table cellpadding="0" cellspacing="0" border="0"
									class="table table-striped table-hover table-bordered"
									id="evaluacionTable">
									<thead>
										<tr>
											<th>Identificación</th>
											<th>Fecha de Inicio</th>
											<th>Deadline</th>
											<th>Nombre de Postulante</th>
											<th>Acciones</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="postulacionPostulante"
											items="${postulacionesEvaluacion}" varStatus="status">
											<c:set var="postulacion"
												value="${postulacionPostulante.postulacion}"></c:set>
											<c:set var="postulante"
												value="${postulacionPostulante.postulante}"></c:set>

											<tr ${votos.get(status.index) ? 'class="success"' : '' }>
												<td>${postulante.identificacion.identificacion }</td>
												<td><fmt:formatDate type="date" pattern="dd/MM/yyyy"
														value="${postulacion.fechaIngreso }" /></td>
												<td><c:choose>
														<c:when test="${postulacion.deadline lt now}">
															<span class="text-error"><fmt:formatDate
																	type="date" pattern="dd/MM/yyyy"
																	value="${postulacion.deadline }" /></span>
														</c:when>
														<c:otherwise>
															<fmt:formatDate type="date" pattern="dd/MM/yyyy"
																value="${postulacion.deadline }" />
														</c:otherwise>
													</c:choose></td>
												<td><a
													href="postulante?id=${postulacion.idPostulante }"
													class="fancy" data-fancybox-type="iframe">${postulacion.nombrePostulante
														}</a></td>
												<td style="text-align: center;">

													<div id="acciones" class="btn-group">
														<a href="historial?idPostulacion=${postulacion.id}"
															class="btn fancy" data-fancybox-type="iframe"
															data-placement="bottom" data-toggle="tooltip"
															data-original-title="Historial Particular"> <i
															class="icon-time"></i>
														</a> 
														<c:if test='${user.rol ne "COMISIONADO"}'>
															<a href="${root}/app/admin/edit?id=${postulacion.id }"
																class="btn" data-placement="bottom" data-toggle="tooltip"
																data-original-title="Editar Postulación"> <i
																class="icon-edit"></i>
															</a> 
														</c:if>
														<a
															href="postulaciones?action=revisar&id=${postulacion.id}&evaluacion=true"
															class="btn btn-primary fancyPostulacion"
															data-fancybox-type="iframe" data-placement="bottom"
															data-toggle="tooltip"
															data-original-title="Ver Postulación"> <i
															class="icon-eye-open icon-white"></i>
														</a>
													</div>

												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!-- /block -->

							<!-- block -->
							<div class="tab-pane" id="decision">
								<table cellpadding="0" cellspacing="0" border="0"
									class="table table-striped table-hover table-bordered"
									id="decisionTable">
									<thead>
										<tr>
											<th>Identificación</th>
											<th>Fecha de Inicio</th>
											<th>Deadline</th>
											<th>Nombre de Postulante</th>
											<th>Acciones</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="postulacionPostulante"
											items="${postulacionesDecision}">
											<c:set var="postulacion"
												value="${postulacionPostulante.postulacion}"></c:set>
											<c:set var="postulante"
												value="${postulacionPostulante.postulante}"></c:set>

											<tr>
												<td>${postulante.identificacion.identificacion }</td>
												<td><fmt:formatDate type="date" pattern="dd/MM/yyyy"
														value="${postulacion.fechaIngreso }" /></td>
												<td><fmt:formatDate type="date" pattern="dd/MM/yyyy"
														value="${postulacion.deadline }" /></td>
												<td><a
													href="postulante?id=${postulacion.idPostulante }"
													class="fancy" data-fancybox-type="iframe">${postulacion.nombrePostulante
														}</a></td>
												<td style="text-align: center;">
													<div id="acciones" class="btn-group">
														<a href="historial?idPostulacion=${postulacion.id}"
															class="btn fancy" data-fancybox-type="iframe"
															data-placement="bottom" data-toggle="tooltip"
															data-original-title="Historial Particular"> <i
															class="icon-time"></i>
														</a> 
														<c:if test='${user.rol ne "COMISIONADO"}'>
															<a href="${root}/app/admin/edit?id=${postulacion.id }"
																class="btn" data-placement="bottom" data-toggle="tooltip"
																data-original-title="Editar Postulación"> <i
																class="icon-edit"></i>
															</a> 
														</c:if>
														<a
															href="postulaciones?action=revisar&id=${postulacion.id}&decision=true"
															class="btn btn-primary fancyPostulacion"
															data-fancybox-type="iframe" data-placement="bottom"
															data-toggle="tooltip"
															data-original-title="Ver Postulación"> <i
															class="icon-eye-open icon-white"></i>
														</a>
													</div>

												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!-- /block -->

							<!-- block -->
							<div class="tab-pane" id="espera_notificacion">
								<table cellpadding="0" cellspacing="0" border="0"
									class="table table-striped table-hover table-bordered"
									id="esperaTable">
									<thead>
										<tr>
											<th>Identificación</th>
											<th>Fecha de Inicio</th>
											<th>Fecha resolución</th>
											<th>Nombre de Postulante</th>
											<th>Decisión</th>
											<th>Acciones</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="postulacionPostulante"
											items="${postulacionesEspera}" varStatus="status">
											<c:set var="postulacion"
												value="${postulacionPostulante.postulacion}"></c:set>
											<c:set var="postulante"
												value="${postulacionPostulante.postulante}"></c:set>
											<tr>
												<td>${postulante.identificacion.identificacion }</td>
												<td><fmt:formatDate type="date" pattern="dd/MM/yyyy"
														value="${postulacion.fechaIngreso }" /></td>
												<td><fmt:formatDate type="date" pattern="dd/MM/yyyy"
														value="${resolucionesEspera.get(status.index).fecha }" /></td>
												<td><a
													href="postulante?id=${postulacion.idPostulante }"
													class="fancy" data-fancybox-type="iframe">${postulacion.nombrePostulante
														}</a></td>
												<td style="text-align: center;"><span
													class="label ${resolucionesEspera.get(status.index).resultado.id==1 ? 'label-success' : resolucionesEspera.get(status.index).resultado.id==2 ? 'label-important' : 'label-warning' }">${resolucionesEspera.get(status.index).resultado}</span>
												</td>
												<td style="text-align: center;">

													<div id="acciones" class="btn-group">
														<a href="historial?idPostulacion=${postulacion.id}"
															class="btn fancy" data-fancybox-type="iframe"
															data-placement="bottom" data-toggle="tooltip"
															data-original-title="Historial Particular"> <i
															class="icon-time"></i>
														</a> 
														<c:if test='${user.rol ne "COMISIONADO"}'>
															<a href="${root}/app/admin/edit?id=${postulacion.id }"
																class="btn" data-placement="bottom" data-toggle="tooltip"
																data-original-title="Editar Postulación"> <i
																class="icon-edit"></i>
															</a> 
														</c:if>
														<a
															href="postulaciones?action=revisar&id=${postulacion.id}&espera_notificacion=true"
															class="btn btn-primary fancyPostulacion"
															data-fancybox-type="iframe" data-placement="bottom"
															data-toggle="tooltip"
															data-original-title="Ver Postulación"> <i
															class="icon-eye-open icon-white"></i>
														</a>
													</div>

												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!-- /block -->

							<!-- block -->
							<div class="tab-pane" id="resolucion">
								<table cellpadding="0" cellspacing="0" border="0"
									class="table table-striped table-hover table-bordered"
									id="resueltasTable">
									<thead>
										<tr>
											<th>Identificación</th>
											<th>Fecha de Inicio</th>
											<th>Fecha resolución</th>
											<th>Nombre de Postulante</th>
											<th>Decisión</th>
											<th>Acciones</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="postulacionPostulante"
											items="${postulacionesResolucion}" varStatus="status">
											<c:set var="postulacion"
												value="${postulacionPostulante.postulacion}"></c:set>
											<c:set var="postulante"
												value="${postulacionPostulante.postulante}"></c:set>

											<tr>
												<td>${postulante.identificacion.identificacion }</td>
												<td><fmt:formatDate type="date" pattern="dd/MM/yyyy"
														value="${postulacion.fechaIngreso }" /></td>
												<td><fmt:formatDate type="date" pattern="dd/MM/yyyy"
														value="${resoluciones.get(status.index).fecha }" /></td>
												<td><a
													href="postulante?id=${postulacion.idPostulante }"
													class="fancy" data-fancybox-type="iframe">${postulacion.nombrePostulante
														}</a></td>
												<td style="text-align: center;"><span
													class="label ${resoluciones.get(status.index).resultado.id==1 ? 'label-success' : resoluciones.get(status.index).resultado.id==2 ? 'label-important' : 'label-warning'  }">${resoluciones.get(status.index).resultado}</span>
												</td>
												<td style="text-align: center;">
													<div id="acciones" class="btn-group">
														<a href="historial?idPostulacion=${postulacion.id}"
															class="btn fancy" data-fancybox-type="iframe"
															data-placement="bottom" data-toggle="tooltip"
															data-original-title="Historial Particular"> <i
															class="icon-time"></i>
														</a> 
														<c:if test='${user.rol ne "COMISIONADO"}'>
															<a href="${root}/app/admin/edit?id=${postulacion.id }"
																class="btn" data-placement="bottom" data-toggle="tooltip"
																data-original-title="Editar Postulación"> <i
																class="icon-edit"></i>
															</a> 
														</c:if>
														<a
															href="postulaciones?action=revisar&id=${postulacion.id}&resuelta=true"
															class="btn btn-primary fancyPostulacion"
															data-fancybox-type="iframe" data-placement="bottom"
															data-toggle="tooltip"
															data-original-title="Ver Postulación"> <i
															class="icon-eye-open icon-white"></i>
														</a>
													</div>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!-- /block -->
						</div>
					</div>
				</div>

			</div>
		</div>
		<hr>
		<jsp:include page="../../footer.jsp"></jsp:include>
	</div>
	<!--/.fluid-container-->

	<script src="${root}/js/jquery.js"></script>
	<script src="${root}/js/bootstrap.js"></script>
	<script src="${root}/js/jquery.dataTables.js"></script>
	<script src="${root}/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
	<script src="${root}/fancybox/lib/jquery.mousewheel-3.0.6.pack.js"></script>
	<script src="${root}/js/header.js"></script>
	<script src="${root}/js/dataTables.bootstrap.js"></script>
	<script src="${root}/js/admin/scripts.js"></script>
	<script src="${root}/js/admin/postulaciones.js"></script>

</body>

</html>