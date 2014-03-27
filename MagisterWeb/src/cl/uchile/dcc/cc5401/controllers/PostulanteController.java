package cl.uchile.dcc.cc5401.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.uchile.dcc.cc5401.model.dao.DatosEmpresaDAO;
import cl.uchile.dcc.cc5401.model.dao.PostulanteDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.DatosEmpresaDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PostulanteDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.DatosEmpresaDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulanteDTO;

@WebServlet("/app/admin/postulante")
public class PostulanteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String ERROR_PAGE = "/error.jsp";
	private static final String POSTULANTE_VIEW = "/app/admin/userInfo.jsp";
	private PostulanteDAO postulanteDAO;
	private DatosEmpresaDAO datosEmpresaDAO;

	public PostulanteController() {
		super();
		postulanteDAO = PostulanteDAOFactory.getPostulanteDAO();
		datosEmpresaDAO = DatosEmpresaDAOFactory.getDatosEmpresaDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String forward = "";
		
		try{
			int id = Integer.parseInt(request.getParameter("id"));
			PostulanteDTO postulante = null;
			postulante = postulanteDAO.get(id);
			DatosEmpresaDTO datosEmpresa = datosEmpresaDAO.get(id);
			request.setAttribute("postulante", postulante);
			request.setAttribute("datosEmpresa", datosEmpresa);
			forward=POSTULANTE_VIEW;
		}
		catch(Exception e){
			e.printStackTrace();
			forward=ERROR_PAGE;
		}

		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

}
