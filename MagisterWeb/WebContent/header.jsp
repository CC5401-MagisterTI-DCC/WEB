<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
<div class="navbar navbar-inverse navbar-static-top header-nav">

	<div class="navbar-inner">
		<button type="button" class="btn btn-navbar" data-toggle="collapse"
			data-target=".nav-collapse">
			<span class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
		</button>

		<a href="<%=request.getContextPath()%>/index.jsp"> <img
			class="brand" src="<%=request.getContextPath()%>/img/logo_dcc.png"></a>
		<div class="nav-collapse collapse">
			<ul class="nav">
				<li ${form==null&&admin==null ? 'class="active"' : '' }><a
					href="<%=request.getContextPath()%>/index.jsp">Inicio</a></li>
				<li ${form!=null ? 'class="active"' : '' }><a
					href="<%=request.getContextPath()%>/app/form">Formulario de
						Postulación</a></li>
				<li ${tracking!=null ? 'class="active"' : ''}><a
					href="<%=request.getContextPath()%>/app/track">Tracking</a>
				<li ${admin!=null ? 'class="active"' : ''}><a
					href="<%=request.getContextPath()%>/app/login">${admin!=null ?
						'Administración' : 'Ingresar'}</a></li>

			</ul>
			<div id="user" ${user !=null ? '' : 'hidden'}>
				<ul class="nav pull-right">
					<li class="dropdown"><a href="#" role="button"
						class="dropdown-toggle" data-toggle="dropdown"> <i
							class="icon-user icon-white"></i> ${user.username } (${user.rol})<i
							class="caret"></i>

					</a>
						<ul class="dropdown-menu">
							<li><a tabindex="-1"
								href="<%=request.getContextPath()%>/app/admin/passwordChange"><i
									class="icon-pencil"></i>&nbsp; Cambiar Contraseña</a></li>
							<li ${user.hasPermisos("ADMIN_USERS") ? '' : 'hidden'}><a
								href="<%=request.getContextPath()%>/app/admin/userAdmin"><i
									class="icon-user"></i>&nbsp; Administrar Usuarios</a></li>
							<li class="divider"></li>
							<li><a tabindex="-1"
								href="<%=request.getContextPath()%>/app/logout">Cerrar
									Sesión</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</div>
</div>