<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<c:if test='${user.hasPermisos("VOTAR")}'>
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
<c:if test='${user.hasPermisos("DECIDIR")}'>
	<hr>
	<form action="estado" method="get">
		<input type="hidden" id="id" name="id" value="${postulacion.id}">
		<input type="hidden" id="action" name="action" value="decision">

		<textarea id="detalles" name="detalles" rows="4" class="field span8"
			placeholder="Ingrese los detalles de la decisión (Comentario interno)"></textarea>
		<br>

		<button class="btn btn-large btn-success" name="decision"
			value="aceptado" type="submit">
			<i class="icon-ok-sign icon-white"></i> Aceptar
		</button>
		&nbsp;
		<button class="btn btn-large btn-danger" name="decision"
			value="rechazado" type="submit">
			<i class="icon-remove-sign icon-white"></i> Rechazar
		</button>
		<br>
		<br>
		<button class="btn btn-large btn-warning" name="decision"
			value="aceptado_condicional" type="submit">
			<i class="icon-warning-sign icon-white"></i> Aceptar Condicionalmente
		</button>
	</form>
</c:if>