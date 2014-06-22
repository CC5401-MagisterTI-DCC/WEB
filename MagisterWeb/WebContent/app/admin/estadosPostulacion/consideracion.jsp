<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h3>Comentarios</h3>
<p>
	<c:forEach var="comentario" items="${comentarios}" varStatus="status">
		<strong>${usuarios.get(status.index).username } </strong> ${comentario.texto}
	</c:forEach>
</p>
<c:if test='${user.hasPermisos("SET_DEADLINE")}'>
	<hr>
	<form method="get" action="estado" onsubmit="return confirm('¿Está seguro de pasar la postulación a la etapa de evaluación?');">
		<input type="hidden" id="action" name="action" value="consideracion">
		<input type="hidden" id="id" name="id" value="${postulacion.id}">

		<textarea id="comentario" name="comentario" rows="2"
			placeholder="Ingrese un comentario para la comision"></textarea>
			
		<input type="hidden" id="deadline" name="deadline">		
		<h4>Seleccione Deadline</h4>
		<div class="well well-small" style="width:220px;background:white;margin-left:auto;margin-right:auto" id="calendar-embedded"></div>
		
		<button type="submit" class="btn btn-large btn-primary">
			<i class="icon-eye-open icon-white"></i> Pasar a evaluación
		</button>

	</form>
</c:if>
<c:if test='${user.hasPermisos("DECIDIR")}'>
	<hr>
	<h3>Tomar decisión</h3>
	<p>
		<span class="label label-warning">Nota:</span>&nbsp;Al utilizar esta
		opción se saltará el criterio de la comisión. Esto quedará registrado.
	</p>
	<form action="estado" method="get" 
		onsubmit="return (function(){ if($('#detalles').val()===''){alert('Debe dejar un comentario.');return false;}; return confirm('Tenga en cuenta que esta decisión elimina toda participación del comité. \n\n -------------------------------------------------------- \n Significado \n -------------------------------------------------------- \n\n Aceptar: finaliza proceso con postulante aceptado al magister. \n\n Rechazar: finaliza proceso con postulante rechazado. \n\n Aceptar Condicionalmente: finaliza proceso con postulante aceptado bajo las condiciones indicadas en el cuadro de texto. \n\n ¿Desea continuar?')})()">
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
