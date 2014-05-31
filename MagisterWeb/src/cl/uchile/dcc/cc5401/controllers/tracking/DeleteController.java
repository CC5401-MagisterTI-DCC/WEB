package cl.uchile.dcc.cc5401.controllers.tracking;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.uchile.dcc.cc5401.model.dao.HistorialDAO;
import cl.uchile.dcc.cc5401.model.dao.PostulacionDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.HistorialDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PostulacionDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.HistorialDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulacionDTO;
import cl.uchile.dcc.cc5401.util.RolUsuario;

@WebServlet("/app/track/delete")
public class DeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private PostulacionDAO postulacionDAO;
	private HistorialDAO historialDAO;

	private static final String DELETE_CONFIRM = "/app/tracking/confirmDelete.jsp";
	private static final String SUCCESS = "/app/tracking/eliminacionExitosa.jsp";
	private static final String ERROR_PAGE = "/error.jsp";

	public DeleteController() {
		super();
	}

	/**
	 * Inicializa los objetos DAO para interacturar con la BD
	 * */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		postulacionDAO = PostulacionDAOFactory.getPostulacionDAO();
		historialDAO = HistorialDAOFactory.getHistorialDAO();
	}
	
	/**
	 * Una llamada por este medio muestra una ventana donde el usuario debe
	 * confirmar la eliminación de la postulación.
	 * */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher(DELETE_CONFIRM);
		view.forward(request, response);
	}

	/**
	 * Una llamada por este medio elimina la postulación de usuario de forma
	 * permanente.
	 * */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String forward = "";
		// Connection conn;
		
		try {
			// Obtenemos el numero de track para recuperar la postulación
			String track = request.getParameter("track_n");
			PostulacionDTO postulacion = postulacionDAO.getPostulacion(track);

			// Se activa la transacción
			//TODO: terminar esto
			//conn = ConnectionFactory.getInstance().getConnection();
			//conn.setAutoCommit(false);

			// "Eliminamos" la postulación (La dejamos en estado ELIMINADA)
			postulacionDAO.eliminar(postulacion.getId());
			// Se agrega el historial
			// Usuario cero significa que fue el postulante quien realizó la acción.
			historialDAO.agregar(new HistorialDTO(0, postulacion.getId(),
					"<i class='icon-remove'></i> Se eliminó la postulación.",
					new Date(), "", RolUsuario.POSTULANTE));

			// se confirma la transacción.
			// conn.commit();
			// conn.setAutoCommit(true);
			
			forward = SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			forward = ERROR_PAGE;
		} 

		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

}
