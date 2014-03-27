<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
									src="<%=request.getContextPath()%>/img/aceptado.png"
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
									src="<%=request.getContextPath()%>/img/rechazado.png"
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
							src="<%=request.getContextPath()%>/img/novoto.png" alt="64x64"
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