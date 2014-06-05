<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<c:if test='${user.hasPermisos("VALIDAR")}'>
	<form method="get" action="estado" onsubmit="return confirm('¿Está seguro de su decisión?');">

		<input type="hidden" id="action" name="action" value="validacion">
		<input type="hidden" id="id" name="id" value="${postulacion.id}">
		
		<textarea id="comentario" name="comentario" rows="2"
			placeholder="Ingrese un comentario para el coordinador"></textarea>
		<br>
		<button type="submit" class="btn btn-large btn-success">
			<i class="icon-ok-sign icon-white"></i> Validar Documentos
		</button>

	</form>


	<hr>
</c:if>
<c:if test='${user.hasPermisos("RECHAZAR_DOC")}'>
	<p>
		<span class="label label-warning">Nota:</span>&nbsp;Para rechazar una
		postulación debe seleccionar los documentos faltantes o
		incorrectamente enviados
	</p>
	<!-- TODO: Rellenar los hidden con js -->
	<form action="rechazo" method="get">

		<input type="hidden" name="id_documentos2" id="id_documentos2"
			value=""> <input type="hidden" name="id_postulacion"
			id="id_postulacion" value="${postulacion.id}">

		<textarea id="comentario" name="comentario" rows="2"
			placeholder="Ingrese la razón del rechazo de documentos" required></textarea>
		<br>

		<button class="btn btn-large btn-danger" onclick="submit();">
			<i class="icon-remove-sign icon-white"></i> Rechazar Documentos
		</button>
	</form>
</c:if>