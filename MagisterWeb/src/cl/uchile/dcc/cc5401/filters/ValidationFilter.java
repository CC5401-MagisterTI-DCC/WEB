package cl.uchile.dcc.cc5401.filters;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.validator.routines.EmailValidator;

import cl.uchile.dcc.cc5401.model.dao.PaisDAO;
import cl.uchile.dcc.cc5401.model.dao.PostulacionDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PaisDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PostulacionDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.IdentificacionDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulacionDTO;
import cl.uchile.dcc.cc5401.util.Validacion;

@WebFilter("/app/form")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 10 * 10)
public class ValidationFilter implements Filter {

	private static final int maxSize = 1024 * 1024 * 10;

	private static final String ERROR_PAGE = "/error.jsp";
	private static final String ERROR_DUPLICADA = "/duplicada.jsp";

	public ValidationFilter() {
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

	/**
	 * Valida los datos enviados en el formulario de postulación
	 * */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		SimpleDateFormat sdf = null;
		request.setCharacterEncoding("UTF-8");
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		EmailValidator emailValidator = EmailValidator.getInstance();
		PaisDAO paisDAO = PaisDAOFactory.getPaisDAO();
		PostulacionDAO postulacionDAO = PostulacionDAOFactory
				.getPostulacionDAO();

		// Sólo si el método es post, validamos los parámetros
		if (request.getMethod().equalsIgnoreCase("POST")) {
			// TODO: validar formulario
			try {
				String nombre = request.getParameter("nombre").trim();
				if (nombre == null || nombre.equals("")
						|| !Validacion.validateName(nombre))
					throw new Exception("Error en el nombre " + nombre);

				String apellido = request.getParameter("apellido").trim();
				if (apellido == null || apellido.equals("")
						|| !Validacion.validateName(apellido))
					throw new Exception("Error en el apellido");

				String nacionalidad = request.getParameter("nacionalidad")
						.trim();
				if (paisDAO.get(Integer.parseInt(nacionalidad)) == null)
					throw new Exception("Error en nacionalidad");

				String genero = request.getParameter("genero").trim();
				if (genero == null
						|| genero.equals("")
						|| (!genero.equalsIgnoreCase("Masculino") && !genero
								.equalsIgnoreCase("Femenino")))
					throw new Exception("Error en el genero");

				String fechaNacimiento = request.getParameter("fecha_nac")
						.trim();
				Date convertedFechaNacimiento = sdf.parse(fechaNacimiento);

				String emailPersonal = request.getParameter("emailfield")
						.trim();
				String emailPersonal2 = request.getParameter("emailfield2")
						.trim();
				if (emailPersonal == null || emailPersonal2 == null
						|| !emailPersonal.equals(emailPersonal2)
						|| emailPersonal.equals("")
						|| !emailValidator.isValid(emailPersonal))
					throw new Exception("Error en email");

				// TODO: especificar mas verificacion de telefono
				String fonoPersonal = request.getParameter("telefono_p").trim();
				if (fonoPersonal == null || fonoPersonal.equals("")
						|| !Validacion.validatePhone(fonoPersonal))
					throw new Exception("Error en telefono");

				String residencia = request.getParameter("residencia").trim();
				if (paisDAO.get(Integer.parseInt(residencia)) == null)
					throw new Exception("Error en país de residencia");

				String direccionResidencia = request.getParameter("direccion")
						.trim();
				if (direccionResidencia == null
						|| direccionResidencia.equals(""))
					throw new Exception("Error en direccion de residencia");

				String tipoDoc = request.getParameter("tipoDoc").trim();
				if (tipoDoc == null
						|| (!tipoDoc.equalsIgnoreCase("rut") && !tipoDoc
								.equalsIgnoreCase("pass")))
					throw new Exception("Error en tipo de documento");
				if (tipoDoc.equalsIgnoreCase("rut")
						&& !Validacion.validateRut(request.getParameter("rut")
								.trim()))
					throw new Exception("Error en rut");

				// identificacion
				IdentificacionDTO identificacion = new IdentificacionDTO();
				if (tipoDoc.equalsIgnoreCase("rut")) {
					identificacion.setEsRut(true);
					identificacion.setIdentificacion(request
							.getParameter("rut"));
					identificacion.setPais(null);
				} else {
					identificacion.setEsRut(false);
					identificacion.setIdentificacion(request
							.getParameter("pasaporte"));
					identificacion.setPais(paisDAO.get(Integer.parseInt(request
							.getParameter("nacionalidadPasaporte"))));
				}
				// TODO: ESPECIFICAR CASOS EN QUE UN POSTULANTE PUEDE VOLVER A
				// POSTULAR
				PostulacionDTO postulacionDB = postulacionDAO
						.getPostulacionActiva(identificacion);
				if (postulacionDB != null) {
					RequestDispatcher view = request
							.getRequestDispatcher(ERROR_DUPLICADA);
					view.forward(request, response);
					return;
				}

				// grados academicos
				String grado = request.getParameter("grado");
				String institucion = request.getParameter("institucion");
				String fechaObtencion = request.getParameter("fecha_ob");
				String paisGrado = request.getParameter("pais_grado");

				if (grado == null || grado.trim().equals(""))
					throw new Exception("Error en grado");
				if (institucion == null || institucion.trim().equals(""))
					throw new Exception("Error en institucion");
				sdf.parse(fechaObtencion);
				if (paisDAO.get(Integer.parseInt(paisGrado)) == null)
					throw new Exception("Error en país de la institucion ");

				int j = 1;

				for (int i = 1; i < 10; i++) {
					if (request.getParameterMap().containsKey("grado" + i)) {
						grado = request.getParameter("grado" + i);
						institucion = request.getParameter("institucion" + i);
						fechaObtencion = request.getParameter("fecha_ob" + i);
						paisGrado = request.getParameter("pais_grado" + i);
						if (grado == null || grado.trim().equals(""))
							throw new Exception("Error en grado " + i);
						if (institucion == null
								|| institucion.trim().equals(""))
							throw new Exception("Error en institucion " + i);
						sdf.parse(fechaObtencion);
						if (paisDAO.get(Integer.parseInt(paisGrado)) == null)
							throw new Exception(
									"Error en país de la institucion " + i);
						j++;
					}
				}

				// financiamiento
				String financiamiento = request.getParameter("financiamiento")
						.trim();
				if (financiamiento == null
						|| (!financiamiento.equalsIgnoreCase("Particular")
								&& !financiamiento.equalsIgnoreCase("Empresa") && !financiamiento
									.equalsIgnoreCase("Beca")))
					throw new Exception("Error en financiamiento");

				Part part = null;
				part = request.getPart("cert_titulo");
				if (part.getContentType() == null
						|| !part.getContentType().equalsIgnoreCase(
								"application/pdf") || part.getSize() > maxSize)
					throw new Exception("Error en Certificado de titulo 1");
				part = request.getPart("cert_notas");
				if (part.getContentType() == null
						|| !part.getContentType().equalsIgnoreCase(
								"application/pdf") || part.getSize() > maxSize)
					throw new Exception(
							"Error en Certificado de notas titulo 1");
				for (int i = 1; i < j; i++) {
					// Certificado de grado
					if (request.getParameterMap().containsKey("grado" + i)) {
						part = request.getPart("cert_titulo" + i);
						if (part.getContentType() == null
								|| !part.getContentType().equalsIgnoreCase(
										"application/pdf")
								|| part.getSize() > maxSize)
							throw new Exception(
									"Error en Certificado de título " + (i + 1));
						part = request.getPart("cert_notas" + i);
						if (part.getContentType() == null
								|| !part.getContentType().equalsIgnoreCase(
										"application/pdf")
								|| part.getSize() > maxSize)
							throw new Exception(
									"Error en Certificado de notas título "
											+ (i + 1));
					}
				}
				part = request.getPart("cv");
				if (part.getContentType() == null
						|| !part.getContentType().equalsIgnoreCase(
								"application/pdf") || part.getSize() > maxSize)
					throw new Exception("Error en Curriculum Vitae");
				part = request.getPart("carta_pres");
				if (part.getContentType() == null
						|| !part.getContentType().equalsIgnoreCase(
								"application/pdf") || part.getSize() > maxSize)
					throw new Exception("Error en Carta de Presentación");
				part = request.getPart("carta_rec_1");
				if (part.getContentType() == null
						|| !part.getContentType().equalsIgnoreCase(
								"application/pdf") || part.getSize() > maxSize)
					throw new Exception("Error en Carta Recomendación 1");
				part = request.getPart("carta_rec_2");
				if (part.getContentType() == null
						|| !part.getContentType().equalsIgnoreCase(
								"application/pdf") || part.getSize() > maxSize)
					throw new Exception("Error en Carta Recomendación 2");

			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect(request.getContextPath() + ERROR_PAGE);
				return;
			}
			chain.doFilter(req, res);
		} else {
			chain.doFilter(req, res);
		}
	}

	public void destroy() {

	}

}
