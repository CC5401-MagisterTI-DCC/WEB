<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>

<head>
<title>Editar Postulación - Magister TI DCC</title>
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
<link href="${root}/css/DT_bootstrap.css"
	rel="stylesheet" media="screen">
<link href="${root}/css/edit.css" rel="stylesheet"
	media="screen">
<link rel="stylesheet"
	href="${root}/css/datepicker.css" media="screen">

<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="vendors/flot/excanvas.min.js"></script><![endif]-->
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

</head>

<body data-spy="scroll" data-target=".postulacion-nav">
	<jsp:include page="../../header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="sidebar.jsp"></jsp:include>
			</div>
			<div class="span9" id="content">

				<jsp:include page="edit.jsp"></jsp:include>

			</div>
		</div>
	</div>
	<jsp:include page="../../footer.jsp"></jsp:include>
</body>
</html>
