<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="nav nav-list bs-docs-sidenav affix">

	<li ${postulaciones!=null ? 'class="active"' : '' }><a
		href="postulaciones"><i class="icon-chevron-right"></i>Postulaciones
			<c:if test="${nPendientes>0 }">
				<span class="badge badge-warning pull-right">${nPendientes }</span>
			</c:if></a></li>
	<li ${historial!=null ? 'class="active"' : '' }><a
		href="historial"><i class="icon-chevron-right"></i> Historial</a></li>
	<li ${estadisticas!=null ? 'class="active"' : '' }><a
		href="estadisticas"><i class="icon-chevron-right"></i>
			Estadísticas</a></li>
</ul>