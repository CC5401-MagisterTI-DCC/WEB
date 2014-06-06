<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<h3>Deadline:</h3>
<jsp:useBean id="now" class="java.util.Date" />

<c:choose>
	<c:when test="${postulacion.deadline lt now}">
		<h4 class="text-error">
			<fmt:formatDate type="date" pattern="dd/MM/yyyy"
				value="${postulacion.deadline }" />
			- ¡Se ha cumplido el plazo!
		</h4>
	</c:when>
	<c:otherwise>
		<h4 class="text-info">
			<fmt:formatDate type="date" pattern="dd/MM/yyyy"
				value="${postulacion.deadline }" />
		</h4>
	</c:otherwise>
</c:choose>
<c:if test='${user.hasPermisos("SET_DEADLINE")}'>
	<form method="get" action="voto">		
		<input type="hidden" id="id" name="id" value="${postulacion.id}"> 
		<input type="hidden" id="dl" name="dl" value="true"> 
		
		<input type="hidden" id="deadline" name="deadline">			
		<h4>Seleccione un nuevo Deadline</h4>
		<div class="well well-small" style="width:220px;background:white;margin-left:auto;margin-right:auto" id="calendar-embedded"></div>
		
		<button class="btn btn-primary" type="submit">Actualizar deadline</button>
	</form>
</c:if>
<hr>
<c:if test='${user.hasPermisos("DECIDIR")}'>
	<form action="estado" method="get" onsubmit="return confirm('¿Está seguro que desea poner fin a la etapa de evaluación?');">
		<input type="hidden" id="action" name="action" value="evaluacion">
		<input type="hidden" id="id" name="id" value="${postulacion.id}">

		<button class="btn btn-large btn-block btn-info" type="submit">
			Pasar a decisión</button>
	</form>
	<hr>
</c:if>
<c:if test='${user.hasPermisos("VOTAR")}'>
	<div class="container-fluid" style="text-align: left;">
		<c:choose>
			<c:when test="${userVoto}">
				<c:forEach items="${usuariosVotos}" var="user" varStatus="status">
					<div class="media span12"
						style="margin-left: 0px; margin-left-value: 0px; margin-left-ltr-source: physical; margin-left-rtl-source: physical;">
						<c:choose>
							<c:when test="${votos.get(status.index)!=null}">
								<c:choose>
									<c:when test="${votos.get(status.index).tipoVoto.id==1}">
										<img class="media-object pull-left"
											src="${root}/img/aceptado.png"
											alt="64x64" style="width: 64px; height: 64px;">
										<div class="media-body">
											<h4 class="media-heading">${user.username}</h4>
											<p ${votos.get(status.index)!=null? '' : 'hidden'}>
												<strong>Comentarios: </strong>
												${comentariosVotos.get(status.index).texto }
											</p>
										</div>
									</c:when>
									<c:otherwise>
										<img class="media-object pull-left"
											src="${root}/img/rechazado.png"
											alt="64x64" style="width: 64px; height: 64px;">
										<div class="media-body">
											<h4 class="media-heading">${user.username}</h4>
											<p ${votos.get(status.index)!=null? '' : 'hidden'}>
												<strong>Comentarios: </strong>
												${comentariosVotos.get(status.index).texto }
											</p>
										</div>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<img class="media-object pull-left"
									src="${root}/img/novoto.png" alt="64x64"
									style="width: 64px; height: 64px;">
								<div class="media-body">
									<h4 class="media-heading">${user.username}</h4>
									<p class="text-info">
										<strong>Información: </strong> <span class="text-warning">No
											ha votado aún.</span>
									</p>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</c:forEach>
				<hr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${usuariosVotos}" var="user" varStatus="status">
					<div class="media span12"
						style="margin-left: 0px; margin-left-value: 0px; margin-left-ltr-source: physical; margin-left-rtl-source: physical;">
						<c:choose>
							<c:when test="${votos.get(status.index)!=null}">
								<img class="media-object pull-left"
									src="${root}/img/novoto.png" alt="64x64"
									style="width: 64px; height: 64px;">
								<div class="media-body">
									<h4 class="media-heading">${user.username}</h4>
									<p class="text-info">
										<strong>Información: </strong><span class="text-success">
											Ya votó. </span>
									</p>
								</div>
							</c:when>
							<c:otherwise>
								<img class="media-object pull-left"
									src="${root}/img/novoto.png" alt="64x64"
									style="width: 64px; height: 64px;">
								<div class="media-body">
									<h4 class="media-heading">${user.username}</h4>
									<p class="text-info">
										<strong>Información: </strong><span class="text-warning">No
											ha votado aún.</span>
									</p>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</c:forEach>
				<hr>
			</c:otherwise>

		</c:choose>
	</div>
	<hr>
	<form action="voto" method="get" onsubmit="return confirm('¿Está seguro de su elección?');">
		<input type="hidden" id="id" name="id" value="${postulacion.id}">

		<textarea id="comentario" name="comentario" rows="4" class="span8"
			placeholder="Ingrese un comentario o observación (opcional)"></textarea>
		<br>
		<button class="btn btn-large btn-success" name="decision"
			value="aceptado" type="submit">
			<i class="icon-ok-sign icon-white"></i> Votar Si
		</button>

		<button class="btn btn-large btn-danger" name="decision"
			value="rechazado" type="submit">
			<i class="icon-remove-sign icon-white"></i> Votar No
		</button>

	</form>
</c:if>