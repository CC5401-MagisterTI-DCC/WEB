package cl.uchile.dcc.cc5401.controllers;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/app/track/delete")
public class DeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String DELETE_CONFIRM = "/app/tracking/confirmDelete.jsp";
	private static String ERROR_PAGE = "/error.jsp";
	private static String SUCCESS = "/app/tracking/eliminacionExitosa.jsp";
	private PostulacionDAO postulacionDAO;
	private HistorialDAO historialDAO;
	
       
    public DeleteController() {
        super();
        postulacionDAO = PostulacionDAOFactory.getPostulacionDAO();
        historialDAO = HistorialDAOFactory.getHistorialDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher(DELETE_CONFIRM);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String forward="";
		try{
			//Obtenemos el numero de track para obtener la postulación
			String track = request.getParameter("track_n");
			PostulacionDTO postulacion = postulacionDAO.getPostulacion(track);
			
			//"Eliminamos" la postulación (La dejamos en estado ELIMINADA)
			postulacionDAO.eliminar(postulacion.getId());
			//Se agrega el historial
			historialDAO.agregar(new HistorialDTO(0,postulacion.getId(),"<i class='icon-remove'></i> Se eliminó la postulación",new Date(),""));
			forward=SUCCESS;
		}
		catch(Exception e){
			e.printStackTrace();
			forward=ERROR_PAGE;
		}
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

}
