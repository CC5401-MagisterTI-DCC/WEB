package cl.uchile.dcc.cc5401.web.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.uchile.dcc.cc5401.model.dao.EstadisticasDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.EstadisticasDAOFactory;

@WebServlet("/app/admin/estadisticas/async")
public class Estadisticas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EstadisticasDAO estadisticasDAO;

	public Estadisticas() {
		super();
		estadisticasDAO = EstadisticasDAOFactory.getEstadisticasDAO();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[INFO] estadísticas : Get");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		try {
			out.print(estadisticasDAO.getListaPostulaciones());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

}
