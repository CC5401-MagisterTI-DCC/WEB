<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Magister TI - DCC</title>
<link rel="shortcut icon" href="${root}/favicon.ico" />
<!-- CSS -->
<link rel="stylesheet" type="text/css" href="../css/DT_bootstrap.css">
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/bootstrap-responsive.css">
<link rel="stylesheet" href="../css/header.css">
<link rel="stylesheet" href="../css/footer.css">
<link rel="stylesheet" href="../css/datepicker.css">
<link rel="stylesheet" href="../css/form.css">


</head>
<body>

	<jsp:include page="../header.jsp"></jsp:include>


	<div class="container">
		<div class="row">
			<div class="page-header">
				<h1>
					Formulario de Postulación <small>Magister en TI</small>
				</h1>
			</div>

			<form id="postulacionForm" method="post" action="${root}/app/form"
				enctype="multipart/form-data" class="form-horizontal">
				<div id="rootwizard">
					<div class="navbar">
						<div class="navbar-inner">
							<div class="container">
								<button type="button" class="btn btn-navbar"
									data-toggle="collapse" data-target="#submenu">
									<span class="icon-bar"></span> <span class="icon-bar"></span> <span
										class="icon-bar"></span>
								</button>
								<div class="nav-collapse collapse" id="submenu">
									<ul>
										<li><a href="#tab1" data-toggle="tab"><span
												class="label label-info">1</span> Datos del Postulante</a></li>
										<li><a href="#tab2" data-toggle="tab"><span
												class="label label-info">2</span> Contacto</a></li>
										<li><a href="#tab3" data-toggle="tab"><span
												class="label label-info">3</span> Información Académica</a></li>
										<li><a href="#tab4" data-toggle="tab"><span
												class="label label-info">4</span> Empresa</a></li>
										<li><a href="#tab5" data-toggle="tab"><span
												class="label label-info">5</span> Financiamiento</a></li>
										<li><a href="#tab6" data-toggle="tab"><span
												class="label label-info">6</span> Documentación</a></li>
										<li><a href="#tab7" data-toggle="tab"><span
												class="label label-info">7</span> Resumen</a></li>
										<li><a href="#tab8" data-toggle="tab"><span
												class="label label-info">8</span> Envío</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>

					<div id="bar" class="progress progress-striped active">
						<div class="bar"></div>
					</div>
					<div style="float: right">
						<a name='next' href="#" class="btn button-next btn-success">Siguiente
							<i class="icon-white icon-arrow-right"></i>
						</a>
					</div>
					<div style="float: left">
						<a name='previous' href="#" class="btn button-previous btn-info"><i
							class="icon-white icon-arrow-left"></i> Atrás</a>
					</div>
					<br> <br>
					<!------------------------------------------------------------- TAB 1 --------------------------------------------------------------------------------- -->
					<div class="tab-content">
						<div class="tab-pane" id="tab1">

							<div class="well ">

								<!-- Text input-->
								<div class="control-group">
									<label class="control-label" for="nombre">Nombre(s)<a
										href="#" data-toggle="tooltip" data-placement="right"
										title="(*) = Obligatorio" style="color: rgb(185, 74, 72);">*</a>:
									</label>
									<div class="controls">
										<input id="nombre" name="nombre" type="text"
											placeholder="Ingrese su(s) nombre(s)" class="input-xlarge"
											required>

									</div>
								</div>

								<!-- Text input-->
								<div class="control-group">
									<label class="control-label" for="apellido">Apellido(s)<a
										href="#" data-toggle="tooltip" data-placement="right"
										title="(*) = Obligatorio" style="color: rgb(185, 74, 72);">*</a>:
									</label>
									<div class="controls">
										<input id="apellido" name="apellido" type="text"
											placeholder="Ingrese su(s) apellido(s)" class=" input-xlarge"
											required>

									</div>
								</div>

								<!-- Select Basic -->
								<div class="control-group">
									<label class="control-label" for="tipoDoc">Documento de
										Identificación:</label>
									<div class="controls">
										<select id="tipoDoc" name="tipoDoc" class="input-xlarge">
											<option>Seleccione tipo de documento...</option>
											<option value="rut">RUT</option>
											<option value="pass">Pasaporte</option>
										</select>
									</div>
								</div>

								<div id="rut-div">
									<!-- Text input-->
									<div class="control-group">
										<label class="control-label" for="rut">N° RUT<a
											href="#" data-toggle="tooltip" data-placement="right"
											title="(*) = Obligatorio" style="color: rgb(185, 74, 72);">*</a>:
										</label>
										<div class="controls">
											<input id="rut" name="rut" type="text"
												placeholder="Ej: 12345678-K" class="input-xlarge">

										</div>
									</div>

								</div>

								<div id="pasaporte-div">

									<!-- Text input-->
									<div class="control-group">
										<label class="control-label" for="pasaporte">N° de
											Pasaporte<a href="#" data-toggle="tooltip"
											data-placement="right" title="(*) = Obligatorio"
											style="color: rgb(185, 74, 72);">*</a>:
										</label>
										<div class="controls">
											<input id="pasaporte" name="pasaporte" type="text"
												placeholder="" class="input-xlarge">

										</div>
									</div>

									<!-- Select Basic -->
									<div class="control-group">
										<label class="control-label" for="nacionalidadPasaporte">Pais:</label>
										<div class="controls">
											<select id="nacionalidadPasaporte" name="nacionalidadPasaporte" class="input-xlarge">
												<c:forEach var="pais" items="${paises}">
													<option value="${pais.id}">${pais.nombre}</option>
												</c:forEach>
											</select>
										</div>
									</div>

								</div>


								<!-- Select Basic -->
								<div class="control-group">
									<label class="control-label" for="nacionalidad">Nacionalidad:</label>
									<div class="controls">
										<select id="nacionalidad" name="nacionalidad" class="input-xlarge">
											<c:forEach var="pais" items="${paises}">
												<option value="${pais.id}" ${pais.nombre=='Chile' ? 'Selected':''}>${pais.nombre}</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<!-- Multiple Radios -->
								<div class="control-group">
									<label class="control-label" for="genero">Género:</label>
									<div class="controls">
										<label class="radio"> <input type="radio"
											name="genero" id="radioMasculino" value="Masculino">
											Masculino
										</label> <label class="radio"> <input type="radio"
											name="genero" id="radioFemenino" value="Femenino">
											Femenino
										</label>
									</div>
								</div>

								<!-- Text input-->
								<div class="control-group">
									<label class="control-label" for="fecha_nac">Fecha de
										Nacimiento<a href="#" data-toggle="tooltip"
										data-placement="right" title="(*) = Obligatorio"
										style="color: rgb(185, 74, 72);">*</a>:
									</label>
									<div class="controls">
										<input id="fecha_nac" name="fecha_nac" type="text" required placeholder="dd/mm/aaaa">
									</div>
								</div>
							</div>
						</div>

						<!------------------------------------------------------------- TAB 2 --------------------------------------------------------------------------------- -->

						<div class="tab-pane" id="tab2">

							<div class="well  ">
								<div class="control-group">
									<label class="control-label" for="emailfield">E-mail<a
										href="#" data-toggle="tooltip" data-placement="right"
										title="(*) = Obligatorio" style="color: rgb(185, 74, 72);">*</a>:
									</label>
									<div class="controls">
										<input type="text" id="emailfield"
											placeholder="Ej: nombre@dominio.com" name="emailfield"
											class="email" required>
									</div>

								</div>

								<div class="control-group">
									<label class="control-label" for="emailfield">Ingrese
										nuevamente su e-mail<a href="#" data-toggle="tooltip"
										data-placement="right" title="(*) = Obligatorio"
										style="color: rgb(185, 74, 72);">*</a>:
									</label>
									<div class="controls">
										<input type="text" id="emailfield2"
											placeholder="Ej: nombre@dominio.com" name="emailfield2"
											class="email">
									</div>

								</div>

								<div class="control-group">
									<label class="control-label" for="telefono_p">Teléfono<a
										href="#" data-toggle="tooltip" data-placement="right"
										title="(*) = Obligatorio" style="color: rgb(185, 74, 72);">*</a>:
									</label>
									<div class="controls">
										<input type="text" id="telefono_p"
											placeholder="Ej: +56999988877" name="telefono_p"
											class="input-xlarge" required>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label" for="celular_p">Celular:</label>
									<div class="controls">
										<input type="text" id="celular_p"
											placeholder="Ej: +56999988877" name="celular_p"
											class="input-xlarge">
									</div>
								</div>

								<!-- Select Basic -->
								<div class="control-group">
									<label class="control-label" for="residencia">Pais de
										Residencia:</label>
									<div class="controls">
										<select id="residencia" name="residencia" class="input-xlarge">
											<c:forEach var="pais" items="${paises}">
												<option value="${pais.id}" ${pais.nombre=='Chile' ? 'Selected':''}>${pais.nombre}</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<!-- Textarea -->
								<div class="control-group">
									<label class="control-label" for="direccion">Dirección<a
										href="#" data-toggle="tooltip" data-placement="right"
										title="(*) = Obligatorio" style="color: rgb(185, 74, 72);">*</a>:
									</label>
									<div class="controls">
										<textarea id="direccion" name="direccion"
											placeholder="Ingrese su dirección en su pais de residencia" required></textarea>
									</div>
								</div>
							</div>

						</div>
						<!------------------------------------------------------------- TAB 3 --------------------------------------------------------------------------------- -->

						<div class="tab-pane" id="tab3">
							<div class="well  ">

								<div class="control-group">
									<div class="controls">
										<span class="label label-warning"><i
											class="icon-info-sign icon-white"></i> Nota:</span>&nbsp;Sólo se
										permiten archivos en formato PDF.
									</div>
								</div>

								<fieldset id="addEstudio">
									<div class="fieldwrapper well well-small" style="background: #dfdfdf">
										<!-- Textarea -->
										<div class="control-group">
											<label class="control-label" for="grado">Grado
												Académico<a href="#" data-toggle="tooltip"
												data-placement="right" title="(*) = Obligatorio"
												style="color: rgb(185, 74, 72);">*</a>:
											</label>
											<div class="controls">
												<textarea id="grado" name="grado" class=""
													placeholder="Ingrese su grado académico" required></textarea>
											</div>
										</div>
	
										<!-- Textarea -->
										<div class="control-group">
											<label class="control-label" for="institucion">Institución<a
												href="#" data-toggle="tooltip" data-placement="right"
												title="(*) = Obligatorio" style="color: rgb(185, 74, 72);">*</a>:
											</label>
											<div class="controls">
												<textarea id="institucion" name="institucion" class=" "
													placeholder="Ingrese la institución de la cual obtuvo el grado académico"
													required></textarea>
											</div>
										</div>
	
										<!-- Text input-->
										<div class="control-group">
											<label class="control-label" for="fecha_ob">Fecha de
												Obtención<a href="#" data-toggle="tooltip"
												data-placement="right" title="(*) = Obligatorio"
												style="color: rgb(185, 74, 72);">*</a>:
											</label>
											<div class="controls">
												<input id="fecha_ob" name="fecha_ob" type="text" placeholder="dd/mm/aaaa" required>	
											</div>
										</div>
	
										<!-- Select Basic -->
										<div class="control-group">
											<label class="control-label" for="pais_grado">Pais de
												la Institución<a href="#" data-toggle="tooltip"
												data-placement="right" title="(*) = Obligatorio"
												style="color: rgb(185, 74, 72);">*</a>:
											</label>
											<div class="controls">
												<select id="pais_grado" name="pais_grado"
													class="input-xlarge">
													<c:forEach var="pais" items="${paises}">
														<option value="${pais.id}" ${pais.nombre=='Chile' ? 'Selected':''}>${pais.nombre}</option>
													</c:forEach>
												</select>
											</div>
										</div>
	
										<!-- File Button -->
										<div class="control-group">
											<label class="control-label" for="cert_titulo">Certificado
												de Título o Grado<a href="#" data-toggle="tooltip"
												data-placement="right" title="(*) = Obligatorio"
												style="color: rgb(185, 74, 72);">*</a>:
											</label>
											<div class="controls">
												<input id="cert_titulo" name="cert_titulo"
													class="  input-file" type="file" required>
											</div>
										</div>
	
										<!-- File Button -->
										<div class="control-group">
											<label class="control-label" for="cert_notas">Certificado
												de Notas<a href="#" data-toggle="tooltip"
												data-placement="right" title="(*) = Obligatorio"
												style="color: rgb(185, 74, 72);">*</a>:
											</label>
											<div class="controls">
												<input id="cert_notas" name="cert_notas" class="  input-file"
													type="file" required>
											</div>
										</div>
									</div>
								</fieldset>

								<!-- Button -->
								<div class="control-group">
									<label class="control-label" for="agregar_otro"></label>
									<div class="controls">
										<button id="agregar_otro" name="agregar_otro"
											class="btn agregar_otro btn-primary"> <i
											class="icon-plus icon-white"></i> Agregar Otros Estudios
										</button>
									</div>
								</div>

							</div>
						</div>

						<!------------------------------------------------------------- TAB 4 --------------------------------------------------------------------------------- -->

						<div class="tab-pane" id="tab4">
							<div class="well  ">
								<!-- Text input-->
								<div class="control-group">
									<label class="control-label" for="empresa">Empresa
										Actual:</label>
									<div class="controls">
										<input id="empresa" name="empresa" type="text"
											placeholder="Ingrese su empresa actual (opcional)"
											class="input-xlarge">

									</div>
								</div>

								<!-- Text input-->
								<div class="control-group">
									<label class="control-label" for="cargo">Cargo:</label>
									<div class="controls">
										<input id="cargo" name="cargo" type="text"
											placeholder="Ingrese sus cargo en la empresa"
											class="input-xlarge">

									</div>
								</div>

								<!-- Text input-->
								<div class="control-group">
									<label class="control-label" for="dir_empr">Dirección:</label>
									<div class="controls">
										<input id="dir_empr" name="dir_empr" type="text"
											placeholder="Ingrese la dirección de su empresa actual"
											class="input-xlarge">

									</div>
								</div>

								<!-- Text input-->
								<div class="control-group">
									<label class="control-label" for="fono_empr">Teléfono:</label>
									<div class="controls">
										<input id="fono_empr" name="fono_empr" type="text"
											placeholder="Ej: +56999988877" class="input-xlarge">

									</div>
								</div>
							</div>
						</div>

						<!------------------------------------------------------------- TAB 5 --------------------------------------------------------------------------------- -->

						<div class="tab-pane" id="tab5">
							<div class="well  ">
								<!-- Select Basic -->
								<div class="control-group">
									<label class="control-label" for="financiamiento">Tipo
										de Financiamiento</label>
									<div class="controls">
										<select id="financiamiento" name="financiamiento"
											class="input-xlarge">
											<option>Particular</option>
											<option>Beca</option>
											<option>Empresa</option>
										</select>
									</div>
								</div>

								<div id="beca-div">
									<!-- Multiple Radios -->
									<div class="control-group">
										<div class="controls">
											<input type="radio" name="tipo_beca" id="radioAsignada"
												value="asignada" checked="checked"> Asignada </label> <label
												class="radio"> <input type="radio" name="tipo_beca"
												id="radioPostulacion" value=""> En proceso de
												postulación
											</label>
										</div>
									</div>

									<!-- Textarea -->
									<div class="control-group">
										<label class="control-label" for="comentario_beca">Comentario:</label>
										<div class="controls">
											<textarea id="comentario_beca" name="comentario_beca"
												class=""
												placeholder="Ingrese a que beca corresponde. Ej:CONICIT, etc"></textarea>
										</div>
									</div>


								</div>

							</div>
						</div>

						<!------------------------------------------------------------- TAB 6 --------------------------------------------------------------------------------- -->

						<div class="tab-pane" id="tab6">
							<div class="well  ">

								<div class="control-group">
									<div class="controls">
										<span class="label label-warning"><i
											class="icon-info-sign icon-white"></i> Nota:</span>&nbsp;Sólo se
										permiten archivos en formato PDF.
									</div>
								</div>


								<!-- File Button -->
								<div class="control-group"">
									<label class="control-label" for="cv">Currículum Vitae<a
										href="#" data-toggle="tooltip" data-placement="right"
										title="(*) = Obligatorio" style="color: rgb(185, 74, 72);">*</a>:
									</label>
									<div class="controls">
										<input id="cv" name="cv" class="input-file" type="file"
											required>
									</div>
								</div>

								<!-- File Button -->
								<div class="control-group">
									<label class="control-label" for="carta_pres">Carta de
										Presentación<a href="#" data-toggle="tooltip"
										data-placement="right" title="(*) = Obligatorio"
										style="color: rgb(185, 74, 72);">*</a>:
									</label>
									<div class="controls">
										<input id="carta_pres" name="carta_pres" class="input-file"
											type="file" required>
									</div>
								</div>

								<!-- File Button -->
								<div class="control-group">
									<label class="control-label" for="carta_rec_1">Carta de
										Recomendación 1<a href="#" data-toggle="tooltip"
										data-placement="right" title="(*) = Obligatorio"
										style="color: rgb(185, 74, 72);">*</a>:
									</label>
									<div class="controls">
										<input id="carta_rec_1" name="carta_rec_1"
											class="  input-file" type="file" required>
									</div>
								</div>

								<!-- File Button -->
								<div class="control-group">
									<label class="control-label" for="carta_rec_2">Carta de
										Recomendación 2<a href="#" data-toggle="tooltip"
										data-placement="right" title="(*) = Obligatorio"
										style="color: rgb(185, 74, 72);">*</a>:
									</label>
									<div class="controls">
										<input id="carta_rec_2" name="carta_rec_2" class="input-file"
											type="file" required>
									</div>
								</div>
							</div>
						</div>

						<!------------------------------------------------------------- TAB 7 --------------------------------------------------------------------------------- -->

						<div class="tab-pane" id="tab7">
							<div class="well  ">

								<h2>
									Resumen<small> por favor revise su información</small>
								</h2>

								<div class="row-fluid">

									<div class="span6">
										<div id="rut-resumen-div">
											<address>
												<strong>Rut:</strong><br> 
												<span id="rut_resumen"></span>
											</address>
										</div>

										<div id="pasaporte-resumen-div">
											<address>
												<strong>Pasaporte:</strong><br> 
												<span id="pasaporte_resumen"></span><br> 
												<span id="nacionalidad_pasaporte_resumen"></span>
											</address>
										</div>

										<address>
											<strong>Nombre:</strong><br> 
											<span id="nombre_resumen"></span><br>
											<span id="apellido_resumen"></span>
										</address>
										
										<address>
											<strong>Género:</strong><br> 
											<span id="genero_resumen"></span>
										</address>
										
										<address>
											<strong>Fecha de Nacimiento:</strong><br> 
											<span id="fecha_nac_resumen"></span>
										</address>

										<address>
											<strong>Nacionalidad:</strong><br> 
											<span id="nacionalidad_resumen"></span>
										</address>

										<address>
											<strong>E-mail:</strong><br> 
											<span id="email_resumen"></span>
										</address>

										<address>
											<strong>Teléfono de Contacto:</strong><br> 
											<span id="fono_resumen"></span>
										</address>
									</div>
									<div class="span6">
										<address>
											<strong>Dirección:</strong><br> 
											<span id="direccion_p_resumen"></span><br> 
											<span id="residencia_resumen"></span>
										</address>

										<address>
											<strong>Empresa:</strong><br> 
											<span id="empresa_actual_resumen"></span><br> 
											<span id="cargo_resumen"></span> 
											<span id="direccion_empresa_resumen"></span> 
											<span id="fono_empresa_resumen"></span>
										</address>

										<address>
											<strong>Financiamiento:</strong><br> 
											<span id="financiamiento_resumen"></span>
										</address>

										<address>
											<strong>Grados Académicos:</strong><br> 
											<span id="grado_resumen"></span><br> 
											<span id="institucion_resumen"></span><br> 
											<span id="pais_grado_resumen"></span><br> 
											<span id="fecha_ob_resumen"></span><br> <br>
											<div id="grados"></div>
										</address>
									</div>
								</div>
							</div>
						</div>

						<div class="tab-pane" id="tab8">
							<div class="well">

								<pre>Para completar su postulación al curso	de Magíster en TI del Departamente de Ciencias de la Computación de la Universidad de Chile 
debe aceptar los términos y condiciones relacionadas a la entrega de información privada.</pre>
								<label class="checkbox"> 
									<input id="checkbox" name="checkbox" type="checkbox" required /> 
									Acepto los términos y condiciones
								</label>

								<div style="text-align: center;">
								<input type="submit" value="Enviar Formulario", id="submit_button" class="btn btn-primary btn-large center">
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>

		</div>
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
	<!-- Scripts -->
	<script src="../js/jquery.js"></script>
	<script src="../js/jquery.validate.js"></script>
	<script src="../js/bootstrap.js"></script>
	<script src="../js/bootstrap-tooltip.js"></script>
	<script src="../js/jquery.bootstrap.wizard.min.js"></script>
	<script src="../js/form.js"></script>

</body>
</html>