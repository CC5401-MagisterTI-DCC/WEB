<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Adminstración de Usuarios - Magister TI DCC</title>
<link rel="shortcut icon"
	href="${root}/favicon.ico" />
<!-- Bootstrap -->
<link href="${root}/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="${root}/css/bootstrap-responsive.css"
	rel="stylesheet" media="screen">
<link href="${root}/css/header.css"
	rel="stylesheet" media="screen">
<link href="${root}/css/footer.css"
	rel="stylesheet" media="screen">
<link href="${root}/css/admin/styles.css"
	rel="stylesheet" media="screen">


<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="vendors/flot/excanvas.min.js"></script><![endif]-->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

<!-- FancyBox -->
<link rel="stylesheet"
	href="${root}/fancybox/source/jquery.fancybox.css?v=2.1.5"
	type="text/css" media="screen" />

</head>
<body>
	<jsp:include page="../../../header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span3">
				<jsp:include page="../sidebar.jsp"></jsp:include>
			</div>
			<div class="span9" id="content">
				<div class="row-fluid">
					<h1>
						Administración de Usuarios <small>Magister en TI</small>
					</h1>
				</div>
				<div class="row-fluid">
					<div class="block">
						<div class="navbar navbar-inner block-header">
							<div class="muted pull-left">Usuarios activos en el sistema</div>
							<a href="userAdmin?action=new" data-fancybox-type="iframe"
								class="pull-right btn btn-primary fancy"><i
								class="icon-plus icon-white"></i> Agregar Usuario</a>
						</div>
						<div class="block-content collapse in">
							<table cellpadding="0" cellspacing="0" border="0"
								class="table table-striped table-bordered table-hover"
								style="background-color: white;">
								<thead>
									<tr>
										<th>Username</th>
										<th>Correo</th>
										<th>Rol</th>
										<th>Acciones</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${usuarios}" var="usuario">
										<c:if test="${usuario.rol ne 'ADMINISTRADOR'}">
											<tr>
												<td>${usuario.username}</td>
												<td>${usuario.email}</td>
												<td>${usuario.rol}</td>
												<td>
													<div id="acciones" class="btn-group"
														style="text-align: center;">
														<a href="userAdmin?action=edit&username=${usuario.username}"
															data-fancybox-type="iframe" class="btn fancy">
															<i class="icon-edit"></i>
														</a> 
														<c:if test="${not user.username.equals(usuario.username)}">
															<a href="userAdmin?action=delete&username=${usuario.username}"
																data-fancybox-type="iframe" class="btn btn-danger fancy">
																<i class="icon-trash icon-white"></i>
															</a>
														</c:if>
													</div>
												</td>
											</tr>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
	<jsp:include page="../../../footer.jsp"></jsp:include>
	<script src="${root}/js/jquery.js"></script>
	<script src="${root}/js/bootstrap.js"></script>
	<script type="text/javascript"
		src="${root}/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
	<script type="text/javascript"
		src="${root}/fancybox/lib/jquery.mousewheel-3.0.6.pack.js"></script>
	<script src="${root}/js/header.js"></script>
</body>
</html>