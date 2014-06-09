<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<div class="page-header">
	<h1>Edición de Postulación N°${postulacion.id}</h1>
</div>

<form id="postulacionForm" method="post" action="${root}/app/admin/edit"
	enctype="multipart/form-data" class="form-horizontal">
	<!-- Hidden values -->
	<input type="hidden" value="${postulacion.id}" name="idPostulacion">
	<input type="hidden" value="${financiamiento.id}" name="idFinanciamiento"> 
	<input type="hidden" value="${postulante.id}" name="idPostulante"> 
	<input type="hidden" value="${datosEmpresa.id}" name="idDatosEmpresa">
	<input type="hidden" value="${identificacion.id}" name="idIdentificacion">
	
	<div id="rootwizardEdit">
		<div class="navbar">
			<div class="navbar-inner">
				<div class="container">
					<button type="button" class="btn btn-navbar" data-toggle="collapse"
						data-target=".nav-collapse">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<div class="nav-collapse collapse">
						<ul>
							<li><a href="#tab1" data-toggle="tab"> Datos del
									Postulante</a></li>
							<li><a href="#tab2" data-toggle="tab"> Contacto</a></li>
							<li><a href="#tab3" data-toggle="tab"> Información
									Académica</a></li>
							<li><a href="#tab4" data-toggle="tab"> Empresa</a></li>
							<li><a href="#tab5" data-toggle="tab"> Financiamiento</a></li>
							<li><a href="#tab6" data-toggle="tab"> Documentación</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<!------------------------------------------------------------- TAB 1 --------------------------------------------------------------------------------- -->
		<div class="tab-content">
			<div class="tab-pane" id="tab1">

				<div class="well ">

					<!-- Text input-->
					<div class="control-group">
						<label class="control-label" for="nombre">Nombre(s):</label>
						<div class="controls">
							<c:if test="${postulante.segundoNombre == null}">
								<input id="nombre" name="nombre" type="text"
									placeholder="Ingrese su nombre" class="input-xlarge"
									value="${postulante.primerNombre}" required="">
							</c:if>
							<c:if test="${postulante.segundoNombre != null}">
								<input id="nombre" name="nombre" type="text"
									placeholder="Ingrese su nombre" class="input-xlarge"
									value="${postulante.primerNombre} ${postulante.segundoNombre}"
									required="">
							</c:if>

						</div>
					</div>

					<!-- Text input-->
					<div class="control-group">
						<label class="control-label" for="apellido">Apellido(s):</label>
						<div class="controls">
							<input id="apellido" name="apellido" type="text"
								placeholder="Ingrese sus Apellidos" class=" input-xlarge"
								value="${postulante.apellidoPaterno} ${postulante.apellidoMaterno}"
								required="">

						</div>
					</div>

					<!-- Select Basic -->
					<div class="control-group" hidden="">
						<label class="control-label" for="tipoDoc">Documento de
							Identificación:</label>
						<div class="controls">
							<select id="tipoDoc" name="tipoDoc" class="input-xlarge">
								<option>Seleccione tipo de documento...</option>
								<option value="rut"
									${identificacion.isEsRut() ? 'selected' : ''}>RUT</option>
								<option value="pass"
									${identificacion.isEsRut() ? '' : 'selected'}>Pasaporte</option>
							</select>
						</div>
					</div>


					<input id="rut" name="rut" type="hidden"
						value="${identificacion.identificacion }"> <input
						id="pasaporte" name="pasaporte" type="hidden"
						value="${identificacion.identificacion }">



					<!-- Select Basic -->
					<div class="control-group" hidden="">
						<label class="control-label" for="nacionalidadPasaporte">Pais:</label>
						<div class="controls">
							<select id="nacionalidadPasaporte" name="nacionalidadPasaporte"
								class="input-xlarge">
								<c:forEach var="pais" items="${paises}">
									<option value="${pais.id}"
										${identificacion.pais.id==pais.id ? 'selected' : ''}>${pais.nombre}</option>
								</c:forEach>
							</select>
						</div>
					</div>



					<!-- Select Basic -->
					<div class="control-group">
						<label class="control-label" for="nacionalidad">Nacionalidad:</label>
						<div class="controls">
							<select id="nacionalidad" name="nacionalidad"
								class="input-xlarge">
								<c:forEach var="pais" items="${paises}">
									<option value="${pais.id}"
										${postulante.nacionalidad.id==pais.id ? 'selected' : ''}>${pais.nombre}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<!-- Multiple Radios -->
					<div class="control-group">
						<label class="control-label" for="genero">Género:</label>
						<div class="controls">
							<label class="radio"> <input type="radio" name="genero"
								id="radioMasculino" value="Masculino"
								${postulante.genero.id==1 ? 'checked' : '' }> Masculino
							</label> <label class="radio"> <input type="radio" name="genero"
								id="radioFemenino" value="Femenino"
								${postulante.genero.id==1 ? '' : 'checked' }> Femenino
							</label>
						</div>
					</div>

					<!-- Text input-->
					<div class="control-group">
						<label class="control-label" for="fecha_nac">Fecha de
							Nacimiento:</label>
						<div class="controls">
							<input id="fecha_nac" name="fecha_nac" class="datepicker"
								type="text" value="${sdf.format(postulante.nacimiento)}"
								required="">

						</div>
					</div>

					<div style="text-align: center;">
						<button type="submit" class="btn btn-primary btn-large center">
							<i class="icon-ok icon-white"></i> Guardar Cambios
						</button>
						<a href="${root}/app/admin/postulaciones"
							class="btn btn-large btn-danger"><i
							class="icon-remove icon-white"></i>Cancelar</a>
					</div>
				</div>
			</div>

			<!------------------------------------------------------------- TAB 2 --------------------------------------------------------------------------------- -->

			<div class="tab-pane" id="tab2">

				<div class="well  ">
					<div class="control-group">
						<label class="control-label" for="emailfield">E-mail:</label>
						<div class="controls">
							<input type="text" id="emailfield" value="${postulante.email }"
								placeholder="Ej: nombre@dominio.com" name="emailfield"
								class="email" required="">
						</div>

					</div>

					<div class="control-group">
						<label class="control-label" for="telefono_p">Teléfono:</label>
						<div class="controls">
							<input type="text" id="telefono_p" placeholder="Ej: +5699988877"
								value="${postulante.telefono }" name="telefono_p"
								class="input-xlarge">
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="celular_p">Celular:</label>
						<div class="controls">
							<input type="text" id="celular_p" placeholder="Ej: +5699988877"
								value="${postulante.celular }" name="celular_p"
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
									<option value="${pais.id}"
										${postulante.paisResidencia.id==pais.id ? 'selected' : ''}>${pais.nombre}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<!-- Textarea -->
					<div class="control-group">
						<label class="control-label" for="direccion">Dirección:</label>
						<div class="controls">
							<input id="direccion" name="direccion" class="input-xlarge"
								required="" value="${postulante.direccion }" type="text"
								placeholder="Ingrese su dirección en su pais de residencia">
						</div>
					</div>

					<div style="text-align: center;">
						<button type="submit" class="btn btn-primary btn-large center">
							<i class="icon-ok icon-white"></i> Guardar Cambios
						</button>
						<a href="${root}/app/admin/postulaciones"
							class="btn btn-large btn-danger"><i
							class="icon-remove icon-white"></i>Cancelar</a>
					</div>
				</div>

			</div>
			<!------------------------------------------------------------- TAB 3 --------------------------------------------------------------------------------- -->

			<div class="tab-pane" id="tab3">
				<div class="well  ">
					<fieldset id="addEstudio">
						<c:forEach var="grado" items="${gradosAcademicos}">
							<div class="fieldwrapper well well-small" style="background: #dfdfdf">
								<input type="hidden" value="${grado.id}" name="idGrado">
								<!-- Textarea -->
								<div class="control-group">
									<label class="control-label" for="grado">Grado
										Académico:</label>
									<div class="controls">
										<input id="grado" name="grado" class="input-xlarge"
											value="${grado.nombre }" type="text"
											placeholder="Ingrese su grado académico">
									</div>
								</div>
	
								<!-- Textarea -->
								<div class="control-group">
									<label class="control-label" for="institucion">Institución:</label>
									<div class="controls">
										<input id="institucion" name="institucion"
											class="input-xlarge " value="${grado.institucion }"
											type="text"
											placeholder="Ingrese la institución de la cual obtuvo el grado académico">
									</div>
								</div>
	
								<!-- Text input-->
								<div class="control-group">
									<label class="control-label" for="fecha_ob">Fecha de
										Obtención:</label>
									<div class="controls">
										<input id="fecha_nac" name="fecha_ob" class="datepicker"
											type="text" value="${sdf.format(grado.fechaObtencion) }"
											required="">
									</div>
								</div>
	
								<!-- Select Basic -->
								<div class="control-group">
									<label class="control-label" for="pais_grado">Pais de la
										Institución:</label>
									<div class="controls">
										<select id="pais_grado" name="pais_grado" class="input-xlarge">
											<c:forEach var="pais" items="${paises}">
												<option value="${pais.id}"
													${grado.pais.id==pais.id ? 'selected' : ''}>${pais.nombre}</option>
											</c:forEach>
										</select>
									</div>
								</div>
	
								<input type="hidden" value="${grado.idCertificadoNotas}"
									name="idCertificadoNotas">
								<input type="hidden" value="${grado.idCertificadoTitulo}"
									name="idCertificadoTitulo">
	
								<div class="control-group">
									<label class="control-label" for="cert_titulo">Archivos
										Originales:</label>
									<div class="controls">
										<a href="documento?id=${grado.idCertificadoNotas}"><i
											class="icon-file"></i> Certificado de Notas </a> <a
											href="documento?id=${grado.idCertificadoTitulo}"><i
											class="icon-file"></i> Certificado de Título </a>
									</div>
								</div>
	
								<div class="control-group">
									<div class="controls">
										<span class="label label-warning">Nota:</span>&nbsp;Para
										reemplazar un archivo sólo suba ese en particular.
									</div>
								</div>
	
								<!-- File Button -->
								<div class="control-group">
									<label class="control-label" for="cert_titulo">Certificado
										de Título o Grado:</label>
									<div class="controls">
										<input id="cert_titulo" name="cert_titulo" class="  input-file"
											type="file">
									</div>
								</div>
	
								<!-- File Button -->
								<div class="control-group">
									<label class="control-label" for="cert_notas">Certificado
										de Notas:</label>
									<div class="controls">
										<input id="cert_notas" name="cert_notas" class="  input-file"
											type="file">
									</div>
								</div>
							</div>
						</c:forEach>
					</fieldset>

					<!-- Button -->
					<div class="control-group">
						<label class="control-label" for="agregar_otro"></label>
						<div class="controls">
							<a href="#" id="agregar_otro" name="agregar_otro"
								class="btn agregar_otro btn-primary"> <i
								class="icon-plus icon-white"></i> Agregar Otros Estudios
							</a>
						</div>
					</div>
					<div style="text-align: center;">
						<button type="submit" class="btn btn-primary btn-large center">
							<i class="icon-ok icon-white"></i> Guardar Cambios
						</button>
						<a href="${root}/app/admin/postulaciones"
							class="btn btn-large btn-danger"><i
							class="icon-remove icon-white"></i>Cancelar</a>
					</div>
				</div>
			</div>

			<!------------------------------------------------------------- TAB 4 --------------------------------------------------------------------------------- -->

			<div class="tab-pane" id="tab4">
				<div class="well  ">
					<!-- Text input-->
					<div class="control-group">
						<label class="control-label" for="empresa">Empresa Actual:</label>
						<div class="controls">
							<input id="empresa" name="empresa" type="text"
								value="${datosEmpresa.nombre}"
								placeholder="Ingrese su empresa actual (opcional)"
								class="input-xlarge">

						</div>
					</div>

					<!-- Text input-->
					<div class="control-group">
						<label class="control-label" for="cargo">Cargo:</label>
						<div class="controls">
							<input id="cargo" name="cargo" type="text" 
								value="${datosEmpresa.cargo}" 
								placeholder="Ingrese sus cargo en la empresa"
								class="input-xlarge">

						</div>
					</div>

					<!-- Text input-->
					<div class="control-group">
						<label class="control-label" for="dir_empr">Dirección:</label>
						<div class="controls">
							<input id="dir_empr" name="dir_empr" type="text" 
								value="${datosEmpresa.direccion}"
								placeholder="Ingrese la dirección de su empresa actual"
								class="input-xlarge">

						</div>
					</div>

					<!-- Text input-->
					<div class="control-group">
						<label class="control-label" for="fono_empr">Teléfono:</label>
						<div class="controls">
							<input id="fono_empr" name="fono_empr" type="text"
								value="${datosEmpresa.telefono}" placeholder="Ej: 99988877"
								class="input-xlarge">

						</div>
					</div>
					<div style="text-align: center;">
						<button type="submit" class="btn btn-primary btn-large center">
							<i class="icon-ok icon-white"></i> Guardar Cambios
						</button>
						<a href="${root}/app/admin/postulaciones"
							class="btn btn-large btn-danger"><i
							class="icon-remove icon-white"></i>Cancelar</a>
					</div>
				</div>
			</div>

			<!------------------------------------------------------------- TAB 5 --------------------------------------------------------------------------------- -->

			<div class="tab-pane" id="tab5">
				<div class="well  ">
					<!-- Select Basic -->
					<div class="control-group">
						<label class="control-label" for="financiamiento">Tipo de
							Financiamiento</label>
						<div class="controls">
							<select id="financiamiento" name="financiamiento"
								class="input-xlarge">
								<option ${financiamiento.tipo.id==1 ? 'selected' : '' }>Particular</option>
								<option ${financiamiento.tipo.id==2 ? 'selected' : '' }>Beca</option>
								<option ${financiamiento.tipo.id==3 ? 'selected' : '' }>Empresa</option>
							</select>
						</div>
					</div>

					<div id="beca-div">
						<!-- Multiple Radios -->
						<div class="control-group">
							<div class="controls">
								<label class="radio">
									<input type="radio"	name="tipo_beca" id="radioAsignada" value="asignada"
									checked="checked"> Asignada 
								</label> 
								<label class="radio">
									<input type="radio" name="tipo_beca" id="radioPostulacion"
									value=""> En proceso de postulación
								</label>
							</div>
						</div>

						<!-- Textarea -->
						<div class="control-group">
							<label class="control-label" for="comentario_beca">Comentario:</label>
							<div class="controls">
								<input id="comentario_beca" name="comentario_beca"
									class="input-xlarge" type="text"
									placeholder="Ingrese un comentario (opcional)" value="${financiamiento.detalle}"></input>
							</div>
						</div>


					</div>
					<div style="text-align: center;">
						<button type="submit" class="btn btn-primary btn-large center">
							<i class="icon-ok icon-white"></i> Guardar Cambios
						</button>
						<a href="${root}/app/admin/postulaciones"
							class="btn btn-large btn-danger"><i
							class="icon-remove icon-white"></i>Cancelar</a>
					</div>
				</div>
			</div>

			<!------------------------------------------------------------- TAB 6 --------------------------------------------------------------------------------- -->

			<div class="tab-pane" id="tab6">
				<div class="well  ">
					<input type="hidden" value="${postulacion.idCV}" name="idCV">
					<input type="hidden" value="${postulacion.idCartaPresentacion}"
						name="idCartaPresentacion"> <input type="hidden"
						value="${postulacion.idCarta1}" name="idCarta1"> <input
						type="hidden" value="${postulacion.idCarta2}" name="idCarta2">
					<div class="control-group">
						<label class="control-label">Archivos Originales:</label>
						<div class="controls">
							<a href="documento?id=${postulacion.idCV}"><i
								class="icon-file"></i> Currículum Vitae </a> <a
								href="documento?id=${postulacion.idCartaPresentacion}"><i
								class="icon-file"></i> Carta de Presentación </a> <a
								href="documento?id=${postulacion.idCarta1}"><i
								class="icon-file"></i> Carta de Recomendación 1 </a> <a
								href="documento?id=${postulacion.idCarta2}"><i
								class="icon-file"></i> Carta de Recomendación 2 </a>
						</div>
					</div>

					<div class="control-group">
						<div class="controls">
							<span class="label label-warning">Nota:</span>&nbsp;Para
							reemplazar un archivo sólo suba ese en particular.
						</div>
					</div>

					<!-- File Button -->
					<div class="control-group">
						<label class="control-label" for="cv">Currículum Vitae:</label>
						<div class="controls">
							<input id="cv" name="cv" class="input-file" type="file">
						</div>
					</div>

					<!-- File Button -->
					<div class="control-group">
						<label class="control-label" for="carta_pres">Carta de
							Presentación:</label>
						<div class="controls">
							<input id="carta_pres" name="carta_pres" class="input-file"
								type="file">
						</div>
					</div>

					<!-- File Button -->
					<div class="control-group">
						<label class="control-label" for="carta_rec_1">Carta de
							Recomendación 1:</label>
						<div class="controls">
							<input id="carta_rec_1" name="carta_rec_1" class="  input-file"
								type="file">
						</div>
					</div>

					<!-- File Button -->
					<div class="control-group">
						<label class="control-label" for="carta_rec_2">Carta de
							Recomendación 2:</label>
						<div class="controls">
							<input id="carta_rec_2" name="carta_rec_2" class="input-file"
								type="file">
						</div>
					</div>
					<div style="text-align: center;">
						<button type="submit" class="btn btn-primary btn-large center">
							<i class="icon-ok icon-white"></i> Guardar Cambios
						</button>
						<a href="${root}/app/admin/postulaciones"
							class="btn btn-large btn-danger"><i
							class="icon-remove icon-white"></i>Cancelar</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>

<!-- Scripts -->
<script src="${root}/js/jquery.js"></script>
<script src="${root}/js/jquery.validate.js"></script>
<script src="${root}/js/jquery.bootstrap.wizard.min.js"></script>
<script src="${root}/js/bootstrap.js"></script>
<script src="${root}/js/form.js"></script>
<script src="${root}/js/bootstrap-datepicker.js"></script>
<script src="${root}/js/locales/bootstrap-datepicker.es.js"></script>
</body>
</html>