<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<h4>Resolución Tomada</h4>
<h3
	${resolucion.resultado.id==1 ? 'class="text-success"' : resolucion.resultado.id==2 ? 'class="text-error"' : 'class="text-warning"'}>${resolucion.resultado }</h3>
<h4>Detalles de la decisión:</h4>
<p>${resolucion.comentario }</p>
<c:if test='${user.hasPermisos("VOTAR")}'>
	<hr>
	<h3>Resultados de la votación:</h3>
	<div class="container-fluid" style="text-align: left;">
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
									votó.</span>
							</p>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</c:forEach>
	</div>
</c:if>
<c:if test='${user.hasPermisos("NOTIFICAR")}'>
	<hr>
	<form action="estado" method="get">
		<input type="hidden" id="id" name="id" value="${postulacion.id}">
		<input type="hidden" id="action" name="action"
			value="espera_notificacion">
		<p>
			<span class="label label-warning">Nota:</span>&nbsp;Revise la
			plantilla antes de enviar el correo.
		</p>
		<c:choose>
			<c:when test="${resolucion.resultado.id==1}">
				<textarea id="contenido" name="contenido" rows="15"
					class="field span12"
					placeholder="Redacte aquí el correo formal de notificación al postulante.">
Estimado (a) ${postulante.primerNombre} ${ postulante.segundoNombre } ${postulante.apellidoPaterno } ${postulante.apellidoMaterno } :

Junto con saludarle, nos agrada informarle que su postulación al Programa de
Magíster en Tecnologías de la Información, que imparte
el Departamento de Ciencias de la Computación de la
Universidad de Chile ha sido ACEPTADA.

Atte.,

Programa de Educación Continua
</textarea>
			</c:when>
			<c:when test="${resolucion.resultado.id==2}">
				<textarea id="contenido" name="contenido" rows="4"
					class="field span12"
					placeholder="Redacte aquí el correo formal de notificación al postulante.">
Estimado (a) ${postulante.primerNombre} ${ postulante.segundoNombre } ${postulante.apellidoPaterno } ${postulante.apellidoMaterno } :

Junto con saludarle, lamentamos informarle que su postulación al Programa de
Magíster en Tecnologías de la Información, que imparte
el Departamento de Ciencias de la Computación de la
Universidad de Chile ha sido RECHAZADA.

Atte.,

Programa de Educación Continua
</textarea>
			</c:when>
			<c:otherwise>
				<textarea id="contenido" name="contenido" rows="4"
					class="field span12"
					placeholder="Redacte aquí el correo formal de notificación al postulante.">
Estimado (a) ${postulante.primerNombre} ${ postulante.segundoNombre } ${postulante.apellidoPaterno } ${postulante.apellidoMaterno } :

Junto con saludarle, nos agrada informarle que su postulación al Programa de
Magíster en Tecnologías de la Información, que imparte
el Departamento de Ciencias de la Computación de la
Universidad de Chile ha sido ACEPTADA CONDICIONALMENTE.

Deberá rendir los siguientes cursos de nivelación:
- curso 1
- curso 2

Atte.,

Programa de Educación Continua
</textarea>
			</c:otherwise>
		</c:choose>

		<br>

		<button class="btn btn-large btn-block btn-success" name="decision"
			value="notificar" type="submit">
			<i class="icon-envelope icon-white"></i> Enviar Correo Formal al
			Postulante
		</button>
		<br>
		<button class="btn btn-small btn-danger" name="decision"
			value="no_notificar" type="submit">
			<i class="icon-exclamation-sign icon-white"></i> No Notificar
		</button>
	</form>
</c:if>