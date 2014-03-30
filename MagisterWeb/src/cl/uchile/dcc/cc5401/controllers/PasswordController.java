package cl.uchile.dcc.cc5401.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cl.uchile.dcc.cc5401.model.dao.UserDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.UserDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.UserDTO;
import cl.uchile.dcc.cc5401.util.Algoritmo;
import cl.uchile.dcc.cc5401.util.HashHelper;

@WebServlet("/app/admin/passwordChange")
public class PasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String POSTULACIONES_PAGE = "/app/admin/postulaciones";
	private static String PASS_CHANGE = "/app/admin/passchange.jsp";
	private UserDAO userDAO;

	public PasswordController() {
		super();
		userDAO = UserDAOFactory.getUserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher(PASS_CHANGE);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(true);
		UserDTO user = (UserDTO) session.getAttribute("user");

		//Obtenemos los par�metros del formulario.
		String old = request.getParameter("old");
		old = HashHelper.toHash(old,Algoritmo.MD5);

		//Si su contrase�a anterior coincide, seguimos.
		if(old.equals(user.getPassword())){
			String nueva = request.getParameter("new");
			//Si las contrase�as nuevas coinciden, cambiamos la contrase�a.
			if(request.getParameter("new2").equals(nueva)){
				nueva = HashHelper.toHash(nueva,Algoritmo.MD5);
				user.setPassword(nueva);
				userDAO.actualizarPassword(user);
				session.setAttribute("user", user);
				response.sendRedirect(request.getContextPath() + POSTULACIONES_PAGE +"?nuevaC=true");

			}
			//De lo contrario lo enviamos de vuelta con el error de que ambas contrase�as no son iguales.
			else{
				request.setAttribute("noigual", true);
				RequestDispatcher view = request.getRequestDispatcher(PASS_CHANGE);
				view.forward(request, response);
			}
		}
		//Si su contrase�a anterior no coincide, lo enviamos de vuelta con un error.
		else{
			request.setAttribute("incorrecto", true);
			RequestDispatcher view = request.getRequestDispatcher(PASS_CHANGE);
			view.forward(request, response);
		}
		
	}

}
