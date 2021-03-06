<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
function verificar_sin_rechazados(){
	var msg = "No se puede pasar a la siguiente etapa con documentos rechazados.";
	if($("#collapseOne").find('*[data-documento-rechazado="1"]').length != 0){
		alert(msg);
		$('#collapseOne').collapse('show');
		$('html, body').animate({
	        scrollTop: $("#collapseOne").offset().top
	    }, 500);
		return false;
	}
	if($("#collapseThree").find('*[data-documento-rechazado="1"]').length != 0){
		alert(msg);
		$('#collapseThree').collapse('show');
		$('html, body').animate({
	        scrollTop: $("#collapseThree").offset().top
	    }, 500);
		return false;
	}
	return true;
}
</script>
<c:if test='${user.hasPermisos("SUBIR_DOC")}'>
	<form action="docExtra" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id_postulacion" id="id_postulacion"
			value="${postulacion.id}">
		<div class="control-group">
			<input id="doc_extra" name="doc_extra" class="input-file" type="file"
				required="required">
		</div>
		<textarea id="comentario" name="comentario" rows="2"
			placeholder="Ingrese una descripcion para el documento" required></textarea>
		<br>
		<div style="text-align: center;">
			<button type="submit" class="btn btn-info btn-large center">
				<i class="icon-upload icon-white"></i> Subir Documento Extra
			</button>
		</div>
	</form>
	<hr>
</c:if>
<c:if test='${user.hasPermisos("REVISAR")}'>
	<form action="estado" method="get" onsubmit="return verificar_sin_rechazados() && confirm('¿Está seguro de su decisión?');">
		<input type="hidden" name="action" value="revision">
		<input type="hidden" name="id" value="${postulacion.id}">
		<button class="btn btn-large btn-success">
			<i class="icon-ok-sign icon-white"></i> Validar Documentos
		</button>		
	</form>
</c:if>
<c:if test='${user.hasPermisos("RECHAZAR_DOC")}'>
	<hr>
	<p>
		<span class="label label-warning">Nota:</span>&nbsp;Para rechazar una
		postulación debe seleccionar los documentos faltantes o
		incorrectamente enviados
	</p>
	<!-- TODO: Rellenar los hidden con js -->

	<form action="rechazo" method="get" onsubmit="return (function(){if($('#id_documentos1').val()===''){alert('Debe seleccionar al menos un documento para rechazarlo.'); return false;} else return confirm('Al realizar esta acción se le notificará vía email al postulante,\n ¿Desea continuar?')})()">

		<input type="hidden" name="id_documentos1" id="id_documentos1"
			value=""> <input type="hidden" name="id_postulacion"
			id="id_postulacion" value="${postulacion.id}">

		<textarea id="comentario" name="comentario" rows="2"
			placeholder="Ingrese la razón del rechazo de documentos" required></textarea>
		<br>

		<button class="btn btn-large btn-danger">
			<i class="icon-remove-sign icon-white"></i> Rechazar Documentos
		</button>
	</form>
</c:if>