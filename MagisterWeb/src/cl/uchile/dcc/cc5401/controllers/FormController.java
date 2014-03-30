package cl.uchile.dcc.cc5401.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import cl.uchile.dcc.cc5401.util.Algoritmo;
import cl.uchile.dcc.cc5401.util.Estado;
import cl.uchile.dcc.cc5401.util.Genero;
import cl.uchile.dcc.cc5401.util.HashHelper;
import cl.uchile.dcc.cc5401.util.MailHelper;
import cl.uchile.dcc.cc5401.util.TipoFinanciamiento;

/**
 * Servlet implementation class FormController
 */
@WebServlet("/app/form")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 10 * 10)
public class FormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String FORM = "/app/form.jsp";
	private static String ERROR_PAGE = "/error.jsp";
	private static String SUCCESS = "/success.jsp";

	private PostulacionDAO postulacionDAO;
	private PaisDAO paisDAO;
	private PostulanteDAO postulanteDAO;
	private DatosEmpresaDAO datosEmpresaDAO;
	private IdentificacionDAO identificacionDAO;
	private FinanciamientoDAO financiamientoDAO;
	private GradoAcademicoDAO gradoAcademicoDAO;
	private DocumentoDAO documentoDAO;
	private HistorialDAO historialDAO;

	private MailHelper mailHelper;
	private String ruta = "";
	private String successSubject;
	private String successBody;
	private String paginaTrack;

	public FormController() {
		super();
	}

	@Override
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
		mailHelper = new MailHelper(config.getServletContext()
				.getInitParameter("usernameMail"), config.getServletContext()
				.getInitParameter("passwordMail"), config.getServletContext()
				.getInitParameter("hostMail"), config.getServletContext()
				.getInitParameter("portMail"), true);
		successSubject = config.getServletContext().getInitParameter(
				"successSubject");
		successBody = config.getServletContext()
				.getInitParameter("successBody");
		paginaTrack = config.getServletContext()
				.getInitParameter("paginaTrack");
		ruta = config.getServletContext().getInitParameter("archivos.dir");
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Managment de beans

		String forward = "";

		forward = FORM;

		request.setAttribute("paises", paisDAO.getAll());
		request.setAttribute("form", "form");
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String successBodyP;

		String forward = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		request.setCharacterEncoding("UTF-8");

		try {
			// parse
			// postulante
			String nombre = request.getParameter("nombre");
			String apellido = request.getParameter("apellido");
			String nacionalidad = request.getParameter("nacionalidad");
			PaisDTO paisNacionalidad = paisDAO.get(Integer
					.parseInt(nacionalidad));

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

			PostulanteDTO postulante = new PostulanteDTO(null, null, null,
					null, null, paisNacionalidad, gender,
					convertedFechaNacimiento, fonoPersonal, fonoCelular,
					paisResidencia, emailPersonal, direccionResidencia);

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

			String tipoDoc = request.getParameter("tipoDoc");

			// identificacion
			IdentificacionDTO identificacion = new IdentificacionDTO();
			if (tipoDoc.equalsIgnoreCase("rut")) {
				identificacion.setEsRut(true);
				identificacion.setIdentificacion(request.getParameter("rut")
						.trim().replaceAll("\\.", ""));
				identificacion.setPais(null);
			} else {
				identificacion.setEsRut(false);
				identificacion.setIdentificacion(request
						.getParameter("pasaporte"));
				identificacion.setPais(paisDAO.get(Integer.parseInt(request
						.getParameter("nacionalidadPasaporte"))));
			}

			// grados academicos
			String grado = request.getParameter("grado");
			String institucion = request.getParameter("institucion");
			String fechaObtencion = request.getParameter("fecha_ob");
			String paisGrado = request.getParameter("pais_grado");
			int j = 10;

			GradoAcademicoDTO[] gradosAcademicos = new GradoAcademicoDTO[j];

			gradosAcademicos[0] = new GradoAcademicoDTO(0, grado, institucion,
					sdf.parse(fechaObtencion), paisDAO.get(Integer
							.parseInt(paisGrado)), 0, 0);
			for (int i = 1; i < j; i++) {
				if (request.getParameterMap().containsKey("grado" + i)) {
					grado = request.getParameter("grado" + i);
					institucion = request.getParameter("institucion" + i);
					fechaObtencion = request.getParameter("fecha_ob" + i);
					paisGrado = request.getParameter("pais_grado" + i);
					gradosAcademicos[i] = new GradoAcademicoDTO(0, grado,
							institucion, sdf.parse(fechaObtencion),
							paisDAO.get(Integer.parseInt(paisGrado)), 0, 0);
				}
			}

			// financiamiento
			String financiamiento = request.getParameter("financiamiento");
			FinanciamientoDTO finance = new FinanciamientoDTO();
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

			PostulacionDTO postulacion = new PostulacionDTO();

			postulacion.setNombrePostulante(nombre + " " + apellido);
			postulacion.setEstado(Estado.getInicial());

			// persist
			identificacion.setId(identificacionDAO.agregar(identificacion));
			postulante.setIdentificacion(identificacion);
			postulante.setId(postulanteDAO.agregar(postulante));
			postulacion.setIdPostulante(postulante.getId());
			finance.setId(financiamientoDAO.agregar(finance));
			postulacion.setIdFinanciamiento(finance.getId());

			// empresa
			String empresa = request.getParameter("empresa");
			DatosEmpresaDTO datosEmpresa = null;
			if (empresa != null && !empresa.equals("")) {
				// Insertar datos empresa
				String cargo = request.getParameter("cargo");
				String direccionEmpresa = request.getParameter("dir_empr");
				String fonoEmpresa = request.getParameter("fono_empr");
				datosEmpresa = new DatosEmpresaDTO(0, postulante.getId(),
						empresa, cargo, direccionEmpresa, fonoEmpresa);
				datosEmpresaDAO.agregar(datosEmpresa);
			}

			// TODO:ver la forma de identificar indice mejor(primer archivo
			// primer grado)
			
			// no se ocupa. Revisión 2014-1 
			//Collection<Part> parts = request.getParts();
			//java.util.Iterator<Part> iter = parts.iterator();
			Part part = null;
			part = request.getPart("cert_titulo");
			DocumentoDTO CG = new DocumentoDTO();
			// TODO: definir nombres de los archivos tomando en cuenta
			// diferencia de pasaporte y rut
			// TODO: ver caso de otros archivos
			if (part.getContentType().equalsIgnoreCase("application/pdf"))
				CG.setNombre(identificacion.getIdentificacion() + "CG1.pdf");
			CG.setDireccion(ruta + "/" + CG.getNombre());
			part.write(CG.getDireccion());
			CG.setId(documentoDAO.agregar(CG));
			// certificado de notas
			DocumentoDTO CN = new DocumentoDTO();
			part = request.getPart("cert_notas");
			if (part.getContentType().equalsIgnoreCase("application/pdf"))
				CN.setNombre(identificacion.getIdentificacion() + "CN1.pdf");
			CN.setDireccion(ruta + "/" + CN.getNombre());
			part.write(ruta + "/" + CN.getNombre());
			CN.setId(documentoDAO.agregar(CN));
			gradosAcademicos[0].setIdPostulante(postulante.getId());
			gradosAcademicos[0].setIdCertificadoTitulo(CG.getId());
			gradosAcademicos[0].setIdCertificadoNotas(CN.getId());

			gradosAcademicos[0].setId(gradoAcademicoDAO
					.agregar(gradosAcademicos[0]));

			for (int i = 1; i < gradosAcademicos.length; i++) {
				// Certificado de grado
				if (request.getParameterMap().containsKey("grado" + i)) {
					part = request.getPart("cert_titulo" + i);
					CG = new DocumentoDTO();
					// TODO: definir nombres de los archivos tomando en cuenta
					// diferencia de pasaporte y rut
					// TODO: ver caso de otros archivos
					if (part.getContentType().equalsIgnoreCase(
							"application/pdf"))
						CG.setNombre(identificacion.getIdentificacion() + "CG"
								+ (i + 1) + ".pdf");
					CG.setDireccion(ruta + "/" + CG.getNombre());
					part.write(CG.getDireccion());
					CG.setId(documentoDAO.agregar(CG));
					// certificado de notas
					CN = new DocumentoDTO();
					part = request.getPart("cert_notas" + i);
					if (part.getContentType().equalsIgnoreCase(
							"application/pdf"))
						CN.setNombre(identificacion.getIdentificacion() + "CN"
								+ (i + 1) + ".pdf");
					CN.setDireccion(ruta + "/" + CN.getNombre());
					part.write(ruta + "/" + CN.getNombre());
					CN.setId(documentoDAO.agregar(CN));
					gradosAcademicos[i].setIdPostulante(postulante.getId());
					gradosAcademicos[i].setIdCertificadoTitulo(CG.getId());
					gradosAcademicos[i].setIdCertificadoNotas(CN.getId());

					gradosAcademicos[i].setId(gradoAcademicoDAO
							.agregar(gradosAcademicos[i]));
				}
			}
			// curriculum vitae
			part = request.getPart("cv");
			DocumentoDTO CV = new DocumentoDTO();
			if (part.getContentType().equalsIgnoreCase("application/pdf"))
				CV.setNombre(identificacion.getIdentificacion() + "CV" + ".pdf");
			CV.setDireccion(ruta + "/" + CV.getNombre());
			part.write(CV.getDireccion());
			CV.setId(documentoDAO.agregar(CV));

			// carta presentacion
			part = request.getPart("carta_pres");
			DocumentoDTO CP = new DocumentoDTO();

			if (part.getContentType().equalsIgnoreCase("application/pdf"))
				CP.setNombre(identificacion.getIdentificacion() + "CP" + ".pdf");
			CP.setDireccion(ruta + "/" + CP.getNombre());
			part.write(CP.getDireccion());
			CP.setId(documentoDAO.agregar(CP));

			// carta recomendacion 1
			part = request.getPart("carta_rec_1");
			DocumentoDTO CR1 = new DocumentoDTO();
			if (part.getContentType().equalsIgnoreCase("application/pdf"))
				CR1.setNombre(identificacion.getIdentificacion() + "CR1"
						+ ".pdf");
			CR1.setDireccion(ruta + "/" + CR1.getNombre());
			part.write(CR1.getDireccion());
			CR1.setId(documentoDAO.agregar(CR1));

			// carta recomendacion 2
			part = request.getPart("carta_rec_2");
			DocumentoDTO CR2 = new DocumentoDTO();
			if (part.getContentType().equalsIgnoreCase("application/pdf"))
				CR2.setNombre(identificacion.getIdentificacion() + "CR2"
						+ ".pdf");
			CR2.setDireccion(ruta + "/" + CR2.getNombre());
			part.write(CR2.getDireccion());
			CR2.setId(documentoDAO.agregar(CR2));

			postulacion.setIdCV(CV.getId());
			postulacion.setIdCartaPresentacion(CP.getId());
			postulacion.setIdCarta1(CR1.getId());
			postulacion.setIdCarta2(CR2.getId());
			Calendar calendar = Calendar.getInstance();
			Date ingreso = calendar.getTime();
			calendar.add(Calendar.DAY_OF_MONTH, 7);
			postulacion.setFechaIngreso(ingreso);
			postulacion.setDeadline(null);
			postulacion.setId(postulacionDAO.agregar(postulacion));

			request.setAttribute("email", postulante.getEmail());
			successBodyP = successBody.replaceAll("@nombre",
					postulacion.getNombrePostulante());
			successBodyP = successBodyP.replaceAll("@track", HashHelper.toHash(
					String.valueOf(postulacion.getId() * 10
							+ postulacion.getIdPostulante()), Algoritmo.MD5));
			successBodyP = successBodyP.replaceAll("@paginaTrack", paginaTrack);
			try {
				mailHelper.sendMail(postulante.getEmail(), successSubject,
						successBodyP);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Ocurrió un error al enviar mail a "
						+ postulante.getEmail());
			}
			historialDAO.agregar(new HistorialDTO(0, postulacion.getId(),
					"Se ingresó la postulación al sistema", new Date(), ""));
			forward = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			forward = ERROR_PAGE;
		}

		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);

	}

}
