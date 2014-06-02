package cl.uchile.dcc.cc5401.controllers.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.uchile.dcc.cc5401.model.dao.EstadisticasDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.EstadisticasDAOFactory;

@WebServlet("/app/admin/estadisticas")
public class EstadisticasController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EstadisticasDAO estadisticasDAO;
	
	private static final String ESTADISTICAS_PAGE = "/app/admin/estadisticas.jsp";
	private static final String ERROR_PAGE = "/error.jsp";

	public EstadisticasController() {
		super();
	}

	/**
	 * Inicializa los objetos DAO para interacturar con la BD
	 * */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		estadisticasDAO = EstadisticasDAOFactory.getEstadisticasDAO();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String forward = "";

		try {
			// Seteamos los atributos en el request
			request.setAttribute("listaPostulaciones",
					estadisticasDAO.getListaPostulaciones());
			
			forward = ESTADISTICAS_PAGE;
		} catch (Exception e) {
			e.printStackTrace();
			forward = ERROR_PAGE;
		}

		// bean de navegacion
		request.setAttribute("admin", "admin");
		request.setAttribute("estadisticas", true);
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

}
