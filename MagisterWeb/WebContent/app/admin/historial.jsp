<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Historial - Magister TI DCC</title>
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/favicon.ico" />
<!-- Bootstrap -->
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="<%=request.getContextPath()%>/css/bootstrap-responsive.css"
	rel="stylesheet" media="screen">
<link href="<%=request.getContextPath()%>/css/header.css"
	rel="stylesheet" media="screen">
<link href="<%=request.getContextPath()%>/css/footer.css"
	rel="stylesheet" media="screen">
<link href="<%=request.getContextPath()%>/css/admin/styles.css"
	rel="stylesheet" media="screen">
<link href="<%=request.getContextPath()%>/css/DT_bootstrap.css"
	rel="stylesheet" media="screen">
<link
	href="<%=request.getContextPath()%>/css/smoothness/jquery-ui-1.10.3.custom.css"
	rel="stylesheet" media="screen">
<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="vendors/flot/excanvas.min.js"></script><![endif]-->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

<!-- FancyBox -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/fancybox/source/jquery.fancybox.css?v=2.1.5"
	type="text/css" media="screen" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/fancybox/lib/jquery.mousewheel-3.0.6.pack.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
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
					<h1>
						Historial <small>Magister en TI</small>
					</h1>
				</div>
				<div class="row-fluid">
					<div class="block">
						<div class="navbar navbar-inner block-header">
							<div class="navbar-search pull-right">
								<span id="dateFilter"></span>
							</div>
						</div>

						<div class="block-content collapse in">
							<div class="tab-content">
								<table cellpadding="0" cellspacing="0" border="0"
									class="table table-striped table-hover table-bordered"
									id="historialTable"
									style="background-color: rgb(255, 255, 255);">
									<thead>
										<tr>
											<th>Fecha</th>
											<th>Acción</th>
											<th>Comentario</th>
											<th>Ver Postulación</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="ht" items="${hts}">
											<tr>
												<td>${ht.fecha }</td>

												<td>${ht.accion }</td>
												<td>${ht.comentario }</td>
												<td>
													<div style="text-align: center;">
														<a
															href="postulaciones?action=revisar&id=${ht.idPostulacion}&general=true"
															class="btn btn-primary fancy" data-fancybox-type="iframe"><i
															class="icon-eye-open icon-white"></i></a>
													</div>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>

							</div>
						</div>
					</div>


				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../../footer.jsp"></jsp:include>
	<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/fancybox/lib/jquery.mousewheel-3.0.6.pack.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
	<script src="<%=request.getContextPath()%>/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/js/DT_bootstrap.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/jquery.dataTables.columnFilter.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/jquery-ui-1.10.3.custom.js"
		type="text/javascript"></script>

	<script src="<%=request.getContextPath()%>/js/admin/scripts.js"></script>
	<script src="<%=request.getContextPath()%>/js/admin/historial.js"></script>

</body>
</html>