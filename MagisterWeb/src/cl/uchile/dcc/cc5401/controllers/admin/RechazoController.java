package cl.uchile.dcc.cc5401.controllers.admin;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cl.uchile.dcc.cc5401.model.dao.DocumentoDAO;
import cl.uchile.dcc.cc5401.model.dao.HistorialDAO;
import cl.uchile.dcc.cc5401.model.dao.PostulacionDAO;
import cl.uchile.dcc.cc5401.model.dao.PostulanteDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.DocumentoDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.HistorialDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PostulacionDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PostulanteDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.DocumentoDTO;
import cl.uchile.dcc.cc5401.model.dto.HistorialDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulacionDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulanteDTO;
import cl.uchile.dcc.cc5401.model.dto.UserDTO;
import cl.uchile.dcc.cc5401.util.Algoritmo;
import cl.uchile.dcc.cc5401.util.Estado;
import cl.uchile.dcc.cc5401.util.HashHelper;
import cl.uchile.dcc.cc5401.util.MailHelper;
import cl.uchile.dcc.cc5401.util.MailHelperFactory;
import cl.uchile.dcc.cc5401.util.MailHelperFactoryImpl;

@WebServlet("/app/admin/rechazo")
public class RechazoController extends HttpServlet {
	private static MailHelperFactory mailHelperFactory = new MailHelperFactoryImpl();
	private static final long serialVersionUID = 1L;

	private PostulacionDAO postulacionDAO;
	private PostulanteDAO postulanteDAO;
	private DocumentoDAO documentoDAO;
	private HistorialDAO historialDAO;

	private MailHelper mailHelper;
	private String rechazoSubject;
	private String rechazoBody;
	private String paginaRechazo;

	private static final String ERROR_PAGE = "/error.jsp";
	private static final String SUCCESS_PAGE = "/app/operacionExitosa.jsp";

	public static void setMailHelperFactory(MailHelperFactory mailHelperFactory) {
		RechazoController.mailHelperFactory = mailHelperFactory;
	}

	public RechazoController() {
		super();
	}

	/**
	 * Inicializamos las variables
	 * */
	public void init(ServletConfig config) throws ServletException {

		postulacionDAO = PostulacionDAOFactory.getPostulacionDAO();
		documentoDAO = DocumentoDAOFactory.getDocumentoDAO();
		historialDAO = HistorialDAOFactory.getHistorialDAO();
		postulanteDAO = PostulanteDAOFactory.getPostulanteDAO();

		mailHelper = mailHelperFactory.makeMailHelper(config.getServletContext()
				.getInitParameter("usernameMail"), config.getServletContext()
				.getInitParameter("passwordMail"), config.getServletContext()
				.getInitParameter("hostMail"), config.getServletContext()
				.getInitParameter("portMail"), true);
		rechazoSubject = config.getServletContext().getInitParameter(
				"rechazoSubject");
		rechazoBody = config.getServletContext()
				.getInitParameter("rechazoBody");
		paginaRechazo = config.getServletContext().getInitParameter(
				"paginaRechazo");
	}

	/**
	 * Rechaza algún documento enviado por el postulante.
	 * */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String forward = "";
		String splitter = " ";
		String rechazoBodyP = "";

		HttpSession session = request.getSession(false);
		UserDTO user = (UserDTO) session.getAttribute("user");

		try {
			String idPostulacion = request.getParameter("id_postulacion");
			String documentos1 = request.getParameter("id_documentos1");
			String documentos2 = request.getParameter("id_documentos2");
			String razon = request.getParameter("comentario");
			String docs[] = null;

			// Obtenemos los id de documentos
			if (documentos1 != null) {
				docs = documentos1.trim().split(splitter);
			} else if (documentos2 != null) {
				docs = documentos2.trim().split(splitter);
			} else {
				throw new Exception("No hay documentos a rechazar");
			}

			int[] idDocumentos = new int[docs.length];

			for (int i = 0; i < idDocumentos.length; i++) {
				idDocumentos[i] = Integer.parseInt(docs[i]);
			}

			PostulacionDTO postulacion = postulacionDAO.getPostulacion(Integer
					.parseInt(idPostulacion));
			PostulanteDTO postulante = postulanteDAO.get(postulacion
					.getIdPostulante());

			// Por cada documento rechazado, seteamos su comentario en la BD
			for (int i = 0; i < idDocumentos.length; i++) {
				DocumentoDTO documento = documentoDAO.get(idDocumentos[i]);
				documento.setComentario("rechazado");
				documentoDAO.actualizar(documento);
			}

			postulacion.setEstado(Estado.REVISION);

			// Agregamos el historial
			historialDAO
					.agregar(new HistorialDTO(
							0,
							postulacion.getId(),
							"<strong>"
									+ user.getUsername()
									+ "</strong>: <i class='icon-exclamation-sign'></i>  Rechazo de documentos",
							new Date(), "", user.getRol()));
			postulacionDAO.actualizar(postulacion);

			// Enviamos el mail al postulante
			rechazoBodyP = rechazoBody.replaceAll("@nombre",
					postulacion.getNombrePostulante());
			rechazoBodyP = rechazoBodyP.replaceAll("@track", HashHelper.toHash(
					String.valueOf(postulacion.getId() * 10
							+ postulacion.getIdPostulante()), Algoritmo.MD5));
			rechazoBodyP = rechazoBodyP.replaceAll("@paginaRechazo",
					paginaRechazo);
			rechazoBodyP = rechazoBodyP.replaceAll("@razon", razon);
			try {
				mailHelper.sendMail(postulante.getEmail(), rechazoSubject,
						rechazoBodyP);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("NO SE PUDO MANDAR MAIL A "
						+ postulante.getEmail());
			}
			forward = SUCCESS_PAGE;
		} catch (Exception e) {
			e.printStackTrace();
			forward = ERROR_PAGE;
		}

		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

}
