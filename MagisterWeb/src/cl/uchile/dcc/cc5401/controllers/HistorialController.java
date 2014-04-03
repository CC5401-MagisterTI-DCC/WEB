package cl.uchile.dcc.cc5401.controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.uchile.dcc.cc5401.model.dao.HistorialDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.HistorialDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.HistorialDTO;

@WebServlet("/app/admin/historial")
public class HistorialController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String HISTORIAL_PARTICULAR = "/app/admin/historialPostulacion.jsp";
	private static String HISTORIAL_GENERAL = "/app/admin/historial.jsp";
	
    private HistorialDAO historialDAO;
	
	
    public HistorialController() {
        super();
        historialDAO = HistorialDAOFactory.getHistorialDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward="";
		
		String idPostulacion = request.getParameter("idPostulacion");
		
		//Caso historial general
		if(idPostulacion==null){
			List<HistorialDTO> hts = historialDAO.getAll();
			Collections.reverse(hts);
			request.setAttribute("historial", "yes");
			request.setAttribute("admin", "admin");
			request.setAttribute("hts", hts);
			forward=HISTORIAL_GENERAL;
		}
		//Caso historial particular
		else{
			int id = Integer.parseInt(idPostulacion);
			List<HistorialDTO> ht = historialDAO.get(id);
			Collections.reverse(ht);
			request.setAttribute("listaHistorial",ht);
			request.setAttribute("idPostulacion", idPostulacion);
			forward=HISTORIAL_PARTICULAR;
		}
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

}
