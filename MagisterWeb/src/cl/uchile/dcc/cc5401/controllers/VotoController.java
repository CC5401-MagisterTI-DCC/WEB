package cl.uchile.dcc.cc5401.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
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
	private static String ERROR_PAGE = "/error.jsp";
	private static String SUCCESS_PAGE = "/app/operacionExitosa.jsp";

	public VotoController() {
		super();
		votoDAO = VotoDAOFactory.getVotoDAO();
		postulacionDAO = PostulacionDAOFactory.getPostulacionDAO();
		historialDAO = HistorialDAOFactory.getHistorialDAO();
		comentarioDAO = ComentarioDAOFactory.getComentarioDAO();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String forward = "";
		HttpSession session = request.getSession(true);
		UserDTO user = (UserDTO) session.getAttribute("user");
		String dl = request.getParameter("dl");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		int id = Integer.parseInt(request.getParameter("id"));

		// Si no es un request de deadline de votación, seguimos
		if (dl == null) {
			PostulacionDTO postulacion = postulacionDAO.getPostulacion(id);
			String decision = request.getParameter("decision");
			String comentario = request.getParameter("comentario");

			// Agregamos el comentario si es distinto de null al sistema.
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
					historialDAO.agregar(new HistorialDTO(0, postulacion
							.getId(), "<strong>" + user.getUsername()
							+ "</strong>: Votación: Aceptado", new Date(),
							comentario));
				} else {
					votoDAO.agregar(voto);
					historialDAO.agregar(new HistorialDTO(0, postulacion
							.getId(), "<strong>" + user.getUsername()
							+ "</strong>: Votación: Aceptado", new Date(),
							comentario));
				}
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
					historialDAO.agregar(new HistorialDTO(0, postulacion
							.getId(), "<strong>" + user.getUsername()
							+ "</strong>: Votación: Rechazado", new Date(),
							comentario));
				} else {
					votoDAO.agregar(voto);
					historialDAO.agregar(new HistorialDTO(0, postulacion
							.getId(), "<strong>" + user.getUsername()
							+ "</strong>: Votación: Rechazado", new Date(),
							comentario));
				}
				forward = SUCCESS_PAGE;

			} else {
				forward = ERROR_PAGE;
			}
		}
		// Si es un request de deadline de votación, lo actualizamos
		else {
			try {
				Date deadline = sdf.parse(request.getParameter("deadline"));
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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher(ERROR_PAGE);
		view.forward(request, response);
	}

}
