package cl.uchile.dcc.cc5401.controllers.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/app/admin/estadisticas")
public class EstadisticasController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String ESTADISTICAS_PAGE = "/app/admin/estadisticas.jsp";

	public EstadisticasController() {
		super();
	}

	/**
	 * Inicializa los objetos DAO para interacturar con la BD
	 * */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// bean de navegacion
		request.setAttribute("admin", "admin");
		request.setAttribute("estadisticas", true);

		RequestDispatcher view = request
				.getRequestDispatcher(ESTADISTICAS_PAGE);
		view.forward(request, response);
	}

}
