package cl.uchile.dcc.cc5401.controllers.tracking;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.uchile.dcc.cc5401.model.dao.PostulacionDAO;
import cl.uchile.dcc.cc5401.model.dao.ResolucionDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PostulacionDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.ResolucionDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.PostulacionDTO;
import cl.uchile.dcc.cc5401.util.Estado;

@WebServlet("/app/track")
public class TrackingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PostulacionDAO postulacionDAO;
	private ResolucionDAO resolucionDAO;

	private static final String TRACKING_PAGE = "/app/tracking/vistaPostulacion.jsp";
	private static final String LOGIN_PAGE = "/app/loginTracking.jsp";

	public TrackingController() {
		super();
	}

	/**
	 * Inicializa los objetos DAO para interacturar con la BD
	 * */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		postulacionDAO = PostulacionDAOFactory.getPostulacionDAO();
		resolucionDAO = ResolucionDAOFactory.getResolucionDAO();
	}

	/**
	 * Muestra la página para ingresar el número de track y mail del postulante
	 * */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher(LOGIN_PAGE);
		view.forward(request, response);
	}

	/**
	 * Muestra la postulación según el número de track y mail ingresados por el
	 * usuario.
	 * */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String forward = "";
		String email = request.getParameter("email").trim();
		String trackNumber = request.getParameter("track_n").trim();

		PostulacionDTO postulacion = postulacionDAO.getPostulacion(trackNumber,
				email);

		if (postulacion == null) {
			request.setAttribute("incorrecto", true);
			forward = LOGIN_PAGE;
		} else {
			request.setAttribute("postulacion", postulacion);
			request.setAttribute("track", trackNumber);

			if (postulacion.getEstado().getId() < 3) {
				request.setAttribute("validacion", true);
			} else if (postulacion.getEstado().getId() < 7) {
				request.setAttribute("evaluacion", true);
			} else if (postulacion.getEstado().equals(Estado.ELIMINADA)) {
				request.setAttribute("eliminada", true);
			} else {
				request.setAttribute("resolucion",
						resolucionDAO.get(postulacion.getId()));
				request.setAttribute("resuelta", true);
			}
			request.setAttribute("tracking", true);
			forward = TRACKING_PAGE;
		}

		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

}
