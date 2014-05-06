package cl.uchile.dcc.cc5401.controllers.admin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cl.uchile.dcc.cc5401.model.dao.ComentarioDAO;
import cl.uchile.dcc.cc5401.model.dao.HistorialDAO;
import cl.uchile.dcc.cc5401.model.dao.PostulacionDAO;
import cl.uchile.dcc.cc5401.model.dao.VotoDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.ComentarioDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.HistorialDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PostulacionDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.VotoDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.ComentarioDTO;
import cl.uchile.dcc.cc5401.model.dto.HistorialDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulacionDTO;
import cl.uchile.dcc.cc5401.model.dto.UserDTO;
import cl.uchile.dcc.cc5401.model.dto.VotoDTO;
import cl.uchile.dcc.cc5401.util.TipoVoto;

@WebServlet("/app/admin/voto")
public class VotoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private VotoDAO votoDAO;
	private PostulacionDAO postulacionDAO;
	private ComentarioDAO comentarioDAO;
	private HistorialDAO historialDAO;

	private static final String ERROR_PAGE = "/error.jsp";
	private static final String SUCCESS_PAGE = "/app/operacionExitosa.jsp";

	public VotoController() {
		super();
	}

	/**
	 * Inicializa los objetos DAO para interacturar con la BD
	 * */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		votoDAO = VotoDAOFactory.getVotoDAO();
		postulacionDAO = PostulacionDAOFactory.getPostulacionDAO();
		historialDAO = HistorialDAOFactory.getHistorialDAO();
		comentarioDAO = ComentarioDAOFactory.getComentarioDAO();
	}

	/**
	 * Realiza la función de registrar el voto de un miembro de la comisión o
	 * actualizar la deadline.
	 * */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String forward = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String dl = request.getParameter("dl");
		int id = Integer.parseInt(request.getParameter("id"));

		HttpSession session = request.getSession(false);
		UserDTO user = (UserDTO) session.getAttribute("user");

		// Si no es un request de deadline de votación, seguimos
		if (dl == null) {
			PostulacionDTO postulacion = postulacionDAO.getPostulacion(id);
			String decision = request.getParameter("decision");
			String comentario = request.getParameter("comentario");

			// Agregamos el comentario al sistema si es distinto de null.
			if (comentario != null) {
				ComentarioDTO comentarioDTO = new ComentarioDTO(0, id,
						user.getId(), comentario, new Date(), false);
				comentarioDAO.agregar(comentarioDTO);
			} else
				comentario = "";

			// Si fue un voto positivo
			if (decision.equalsIgnoreCase("aceptado")) {
				VotoDTO voto = new VotoDTO(0, id, user.getId(),
						TipoVoto.POSITIVO);
				// Verificamos si existía un voto anterior del usuario en el
				// sistema. De ser así, se actualiza, si no, se crea un nuevo
				// voto
				if (votoDAO.get(id, user.getId()) != null) {
					votoDAO.actualizar(voto, id, user.getId());
				} else {
					votoDAO.agregar(voto);
				}

				// Se agrega la acción al historial
				historialDAO.agregar(new HistorialDTO(0, postulacion.getId(),
						"<strong>" + user.getUsername()
								+ "</strong>: Votación: Aceptado", new Date(),
						comentario));

				forward = SUCCESS_PAGE;
			}
			// Si fue un voto negativo
			else if (decision.equalsIgnoreCase("rechazado")) {
				VotoDTO voto = new VotoDTO(0, id, user.getId(),
						TipoVoto.NEGATIVO);
				// Verificamos si existía un voto anterior del usuario en el
				// sistema. De ser así, se actualiza, si no, se crea un nuevo
				// voto
				if (votoDAO.get(id, user.getId()) != null) {
					votoDAO.actualizar(voto, id, user.getId());
				} else {
					votoDAO.agregar(voto);
				}

				// Se agrega la acción al historial
				historialDAO.agregar(new HistorialDTO(0, postulacion.getId(),
						"<strong>" + user.getUsername()
								+ "</strong>: Votación: Rechazado", new Date(),
						comentario));

				forward = SUCCESS_PAGE;
			} else {
				forward = ERROR_PAGE;
			}
		}
		// Si es un request de deadline de votación, lo actualizamos
		else {
			try {
				Date deadline = sdf.parse(request.getParameter("deadline"));
				Date currentDate = new Date();
				
				// verificamos que el deadline contiene una fecha posterior a la
				// actual.
				if (deadline.before(currentDate)) {
					throw new ServletException(
							"La fecha ingresada es anterior a hoy "
									+ currentDate.toString());
				}

				PostulacionDTO postulacion = postulacionDAO.getPostulacion(id);
				postulacion.setDeadline(deadline);
				postulacionDAO.actualizar(postulacion);
				forward = SUCCESS_PAGE;
				historialDAO.agregar(new HistorialDTO(0, postulacion.getId(),
						"<strong>" + user.getUsername()
								+ "</strong>: Se actualiza deadline a "
								+ request.getParameter("deadline"), new Date(),
						""));
			} catch (ParseException e) {
				e.printStackTrace();
				forward = ERROR_PAGE;
			}
		}
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

}
