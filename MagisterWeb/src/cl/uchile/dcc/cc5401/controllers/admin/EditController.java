package cl.uchile.dcc.cc5401.controllers.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import cl.uchile.dcc.cc5401.model.dao.DatosEmpresaDAO;
import cl.uchile.dcc.cc5401.model.dao.DocumentoDAO;
import cl.uchile.dcc.cc5401.model.dao.FinanciamientoDAO;
import cl.uchile.dcc.cc5401.model.dao.GradoAcademicoDAO;
import cl.uchile.dcc.cc5401.model.dao.HistorialDAO;
import cl.uchile.dcc.cc5401.model.dao.IdentificacionDAO;
import cl.uchile.dcc.cc5401.model.dao.PaisDAO;
import cl.uchile.dcc.cc5401.model.dao.PostulacionDAO;
import cl.uchile.dcc.cc5401.model.dao.PostulanteDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.DatosEmpresaDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.DocumentoDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.FinanciamientoDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.GradoAcademicoDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.HistorialDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.IdentificacionDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PaisDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PostulacionDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PostulanteDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.DatosEmpresaDTO;
import cl.uchile.dcc.cc5401.model.dto.DocumentoDTO;
import cl.uchile.dcc.cc5401.model.dto.FinanciamientoDTO;
import cl.uchile.dcc.cc5401.model.dto.GradoAcademicoDTO;
import cl.uchile.dcc.cc5401.model.dto.HistorialDTO;
import cl.uchile.dcc.cc5401.model.dto.IdentificacionDTO;
import cl.uchile.dcc.cc5401.model.dto.PaisDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulacionDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulanteDTO;
import cl.uchile.dcc.cc5401.model.dto.UserDTO;
import cl.uchile.dcc.cc5401.util.Genero;
import cl.uchile.dcc.cc5401.util.TipoFinanciamiento;

/**
 * Servlet implementation class EditController
 */
@WebServlet("/app/admin/edit")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class EditController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PostulacionDAO postulacionDAO;
	private PaisDAO paisDAO;
	private PostulanteDAO postulanteDAO;
	private DatosEmpresaDAO datosEmpresaDAO;
	private IdentificacionDAO identificacionDAO;
	private FinanciamientoDAO financiamientoDAO;
	private GradoAcademicoDAO gradoAcademicoDAO;
	private DocumentoDAO documentoDAO;
	private HistorialDAO historialDAO;

	private String ruta = "";

	private static final String EDIT_FORM = "/app/admin/editPostulacion.jsp";
	private static final String ERROR_PAGE = "/error.jsp";
	private static final String SUCCESS = "/app/admin/postulaciones";

	public EditController() {
		super();
	}

	/**
	 * Inicializa los objetos DAO para interacturar con la BD
	 * */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		postulacionDAO = PostulacionDAOFactory.getPostulacionDAO();
		postulanteDAO = PostulanteDAOFactory.getPostulanteDAO();
		paisDAO = PaisDAOFactory.getPaisDAO();
		datosEmpresaDAO = DatosEmpresaDAOFactory.getDatosEmpresaDAO();
		identificacionDAO = IdentificacionDAOFactory.getIdentificacionDAO();
		financiamientoDAO = FinanciamientoDAOFactory.getFinanciamientoDAO();
		gradoAcademicoDAO = GradoAcademicoDAOFactory.getGradoAcademicoDAO();
		documentoDAO = DocumentoDAOFactory.getDocumentoDAO();
		historialDAO = HistorialDAOFactory.getHistorialDAO();
		ruta = config.getServletContext().getInitParameter("archivos.dir");
	}

	/**
	 * Carga los datos en el formulario para editar la postulación
	 **/
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String forward = "";
		try {
			int id = Integer.parseInt(request.getParameter("id"));

			// se recuperan los datos
			PostulacionDTO postulacion = postulacionDAO.getPostulacion(id);
			PostulanteDTO postulante = postulanteDAO.get(postulacion
					.getIdPostulante());
			DatosEmpresaDTO datosEmpresa = datosEmpresaDAO.get(postulante
					.getId());
			FinanciamientoDTO financiamiento = financiamientoDAO
					.get(postulacion.getIdFinanciamiento());
			List<GradoAcademicoDTO> gradosAcademicos = gradoAcademicoDAO
					.get(postulante.getId());
			IdentificacionDTO identificacion = postulante.getIdentificacion();

			// se envían los datos a la vista
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			request.setAttribute("sdf", sdf);
			request.setAttribute("postulacion", postulacion);
			request.setAttribute("postulante", postulante);
			request.setAttribute("datosEmpresa", datosEmpresa);
			request.setAttribute("financiamiento", financiamiento);
			request.setAttribute("gradosAcademicos", gradosAcademicos);
			request.setAttribute("identificacion", identificacion);

			forward = EDIT_FORM;
			request.setAttribute("admin", "admin");
			request.setAttribute("paises", paisDAO.getAll());
		} catch (Exception e) {
			e.printStackTrace();
			forward = ERROR_PAGE;
		}

		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	/**
	 * Persiste las modificaciones hechas.
	 **/
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String forward = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		UserDTO user = (UserDTO) session.getAttribute("user");

		try {			
			// identificadores de postulación
			int idPostulacion = Integer.parseInt(request.getParameter("idPostulacion"));
			int idFinanciamiento = Integer.parseInt(request.getParameter("idFinanciamiento"));
			int idPostulante = Integer.parseInt(request.getParameter("idPostulante"));
			int idIdentificacion = Integer.parseInt(request.getParameter("idIdentificacion"));
			
			String idEmpresa = request.getParameter("idDatosEmpresa");			
			int idDatosEmpresa = 0;
			
			// si no está vacío
			if (!idEmpresa.equals(""))
				idDatosEmpresa = Integer.parseInt(idEmpresa);
			
			PostulanteDTO postulante = postulanteDAO.get(idPostulante);
			
			String nombre = request.getParameter("nombre");
			String apellido = request.getParameter("apellido");
			String nacionalidad = request.getParameter("nacionalidad");
			PaisDTO paisNacionalidad = paisDAO.get(Integer.parseInt(nacionalidad));
			String genero = request.getParameter("genero");
			Genero gender = Genero.FEMENINO;

			if (genero.equalsIgnoreCase("masculino")) {
				gender = Genero.MASCULINO;
			}

			String fechaNacimiento = request.getParameter("fecha_nac");
			Date convertedFechaNacimiento = sdf.parse(fechaNacimiento);
			String emailPersonal = request.getParameter("emailfield");
			String fonoPersonal = request.getParameter("telefono_p");
			String fonoCelular = request.getParameter("celular_p");
			String residencia = request.getParameter("residencia");
			PaisDTO paisResidencia = paisDAO.get(Integer.parseInt(residencia));
			String direccionResidencia = request.getParameter("direccion");

			postulante.setId(idPostulante);
			if (nombre.indexOf(' ') == -1)
				postulante.setPrimerNombre(nombre);
			else {
				postulante.setPrimerNombre(nombre.substring(0,
						nombre.indexOf(' ')));
				postulante.setSegundoNombre(nombre.substring(nombre
						.indexOf(' ') + 1));
			}
			if (apellido.indexOf(' ') == -1)
				postulante.setApellidoPaterno(apellido);
			else {
				postulante.setApellidoPaterno(apellido.substring(0,
						apellido.indexOf(' ')));
				postulante.setApellidoMaterno(apellido.substring(apellido
						.indexOf(' ') + 1));
			}
			postulante.setNacionalidad(paisNacionalidad);
			postulante.setGenero(gender);
			postulante.setDireccion(direccionResidencia);
			postulante.setNacimiento(convertedFechaNacimiento);
			postulante.setEmail(emailPersonal);
			postulante.setTelefono(fonoPersonal);
			postulante.setCelular(fonoCelular);
			postulante.setPaisResidencia(paisResidencia);

			String tipoDoc = request.getParameter("tipoDoc");

			// identificacion
			IdentificacionDTO identificacion = identificacionDAO
					.get(idIdentificacion);
			if (tipoDoc.equalsIgnoreCase("rut")) {
				identificacion.setEsRut(true);
				identificacion.setIdentificacion(request.getParameter("rut"));
				identificacion.setPais(null);
			} else {
				identificacion.setEsRut(false);
				identificacion.setIdentificacion(request
						.getParameter("pasaporte"));
				identificacion.setPais(paisDAO.get(Integer.parseInt(request
						.getParameter("nacionalidadPasaporte"))));
			}

			// financiamiento
			String financiamiento = request.getParameter("financiamiento");
			FinanciamientoDTO finance = new FinanciamientoDTO();
			finance.setId(idFinanciamiento);
			if (financiamiento.equalsIgnoreCase("Particular")) {
				finance.setTipo(TipoFinanciamiento.PARTICULAR);
			} else if (financiamiento.equalsIgnoreCase("Empresa")) {
				finance.setTipo(TipoFinanciamiento.EMPRESA);
			} else if (financiamiento.equalsIgnoreCase("Beca")) {
				finance.setTipo(TipoFinanciamiento.BECA);
				String detalle_beca = request.getParameter("comentario_beca");
				if (detalle_beca != null && !detalle_beca.equals(""))
					finance.setDetalle(detalle_beca);
			}

			PostulacionDTO postulacion = postulacionDAO
					.getPostulacion(idPostulacion);

			postulacion.setNombrePostulante(nombre + " " + apellido);

			// persist
			identificacionDAO.actualizar(identificacion);
			postulante.setIdentificacion(identificacion);
			postulanteDAO.actualizar(postulante);
			financiamientoDAO.actualizar(finance);
			postulacion.setIdFinanciamiento(finance.getId());

			// empresa
			String empresa = request.getParameter("empresa");
			DatosEmpresaDTO datosEmpresa;
			if (empresa != null && !empresa.equals("")) {
				// Insertar datos empresa
				String cargo = request.getParameter("cargo");
				String direccionEmpresa = request.getParameter("dir_empr");
				String fonoEmpresa = request.getParameter("fono_empr");
				datosEmpresa = new DatosEmpresaDTO(idDatosEmpresa,
						postulante.getId(), empresa, cargo, direccionEmpresa,
						fonoEmpresa);
				// si no existen datos de la empresa, se agregan, de lo contrario se actualizan.
				if (datosEmpresaDAO.get(postulante.getId()) == null) {
					datosEmpresaDAO.agregar(datosEmpresa);
				} else {					
					datosEmpresaDAO.actualizar(datosEmpresa);
				}
			}

			// grados academicos
			String[] idGrado = request.getParameterValues("idGrado");
			String[] grado = request.getParameterValues("grado");
			String[] institucion = request.getParameterValues("institucion");
			String[] fechaObtencion = request.getParameterValues("fecha_ob");
			String[] paisGrado = request.getParameterValues("pais_grado");
			GradoAcademicoDTO[] gradosAcademicos = new GradoAcademicoDTO[grado.length];
			GradoAcademicoDTO gradoAcademico;
			
			for (int i = 0; i < grado.length; i++) {
				gradoAcademico = gradoAcademicoDAO.getFromId(Integer.parseInt(idGrado[i]));
				
				// se ha agregado un nuevo grado
				if (gradoAcademico == null) {
					//TODO: agregar nuevo grado académico.
					gradosAcademicos[i] = gradoAcademico;
				} else {
					gradosAcademicos[i] = gradoAcademico;
				}
			}
			
			// TODO:ver la forma de identificar indice mejor(primer archivo
			// primer grado)
			Collection<Part> parts = request.getParts();
			java.util.Iterator<Part> iter = parts.iterator();

			Part part;
			for (int i = 0; i < gradosAcademicos.length; i++) {
				// Certificado de grado
				for (;;) {
					part = iter.next();
					if (part.getName().equals("cert_titulo"))
						break;
				}

				/*------------------------------TODO: Identificar archivos!!!!!------------------------------------*/

				DocumentoDTO CG = new DocumentoDTO();
				// TODO: definir nombres de los archivos tomando en cuenta
				// diferencia de pasaporte y rut
				// TODO: ver caso de otros archivos
				if (part.getContentType() != null && part.getContentType().equalsIgnoreCase("application/pdf")) {
					// BD no se actualiza, solo archivo
					CG.setNombre(identificacion.getIdentificacion() + "CG" + (i + 1) + ".pdf");
					CG.setDireccion(ruta + "/" + CG.getNombre());
					part.write(CG.getDireccion());
					CG.setId(Integer.parseInt(request.getParameter("idCertificadoTitulo")));
					documentoDAO.actualizar(CG);
					
					gradosAcademicos[i].setIdCertificadoTitulo(CG.getId());
				}
				// certificado de notas
				DocumentoDTO CN = new DocumentoDTO();
				part = iter.next();
				if (part.getContentType() != null && part.getContentType().equalsIgnoreCase("application/pdf")) {
					CN.setNombre(identificacion.getIdentificacion() + "CN" + (i + 1) + ".pdf");
					CN.setDireccion(ruta + "/" + CN.getNombre());
					part.write(ruta + "/" + CN.getNombre());
					CN.setId(Integer.parseInt(request.getParameter("idCertificadoNotas")));
					documentoDAO.actualizar(CN);
					
					gradosAcademicos[i].setIdCertificadoNotas(CN.getId());
				}

				gradosAcademicos[i].setIdPostulante(idPostulante);
				gradosAcademicos[i].setNombre(grado[i]);
				gradosAcademicos[i].setInstitucion(institucion[i]);
				gradosAcademicos[i].setPais(paisDAO.get(Integer.parseInt(paisGrado[i])));
				gradosAcademicos[i].setFechaObtencion(sdf.parse(fechaObtencion[i]));

				gradoAcademicoDAO.actualizar(gradosAcademicos[i]);
			}
			
			for (int i = 0; i < 7; i++)
				iter.next();
			
			// curriculum vitae
			part = request.getPart("cv");
			DocumentoDTO CV = new DocumentoDTO();
			if (part.getContentType() != null && part.getContentType().equalsIgnoreCase("application/pdf")) {
				CV.setNombre(identificacion.getIdentificacion() + "CV" + ".pdf");
				CV.setDireccion(ruta + "/" + CV.getNombre());
				part.write(CV.getDireccion());
				CV.setId(Integer.parseInt(request.getParameter("idCV")));
				documentoDAO.actualizar(CV);
				postulacion.setIdCV(CV.getId());
			}

			// carta presentacion
			part = request.getPart("carta_pres");
			DocumentoDTO CP = new DocumentoDTO();
			if (part.getContentType() != null && part.getContentType().equalsIgnoreCase("application/pdf")) {
				CP.setNombre(identificacion.getIdentificacion() + "CP" + ".pdf");
				CP.setDireccion(ruta + "/" + CP.getNombre());
				part.write(CP.getDireccion());
				CP.setId(Integer.parseInt(request
						.getParameter("idCartaPresentacion")));
				documentoDAO.actualizar(CP);
				postulacion.setIdCartaPresentacion(CP.getId());
			}

			// carta recomendacion 1
			part = request.getPart("carta_rec_1");
			DocumentoDTO CR1 = new DocumentoDTO();
			if (part.getContentType() != null && part.getContentType().equalsIgnoreCase("application/pdf")) {
				CR1.setNombre(identificacion.getIdentificacion() + "CR1"
						+ ".pdf");
				CR1.setDireccion(ruta + "/" + CR1.getNombre());
				part.write(CR1.getDireccion());
				CR1.setId(Integer.parseInt(request.getParameter("idCarta1")));
				documentoDAO.actualizar(CR1);
				postulacion.setIdCarta1(CR1.getId());
			}

			// carta recomendacion 2
			part = request.getPart("carta_rec_2");
			DocumentoDTO CR2 = new DocumentoDTO();
			if (part.getContentType() != null && part.getContentType().equalsIgnoreCase("application/pdf")) {
				CR2.setNombre(identificacion.getIdentificacion() + "CR2"
						+ ".pdf");
				CR2.setDireccion(ruta + "/" + CR2.getNombre());
				part.write(CR2.getDireccion());
				CR2.setId(Integer.parseInt(request.getParameter("idCarta2")));
				documentoDAO.actualizar(CR2);
				postulacion.setIdCarta2(CR2.getId());
			}

			postulacionDAO.actualizar(postulacion);
			historialDAO
					.agregar(new HistorialDTO(
							0,
							postulacion.getId(),
							"<strong>"
									+ user.getUsername()
									+ ":<strong> <i class='icon-edit'></i> Se editó la postulación",
							new Date(), "", user.getRol()));
			request.setAttribute("email", postulante.getEmail());

			response.sendRedirect(request.getContextPath() + SUCCESS
					+ "?exito=true");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			forward = ERROR_PAGE;
		}

		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

}
