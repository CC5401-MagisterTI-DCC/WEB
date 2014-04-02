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

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/app/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static String POSTULACIONES_PAGE = "/app/admin/postulaciones";
	private static String LOGIN_PAGE = "/app/login.jsp";

	private UserDAO userDAO;

	public LoginController() {
		super();
		userDAO = UserDAOFactory.getUserDAO();

	}

	/**
	 * Muestra la ventana para inicio de sesión
	 * */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher(LOGIN_PAGE);
		view.forward(request, response);
	}

	/**
	 * Verifica si el usuario puede iniciar sesión.
	 * */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session;
		
		// Obtenemos los datos y aplicamos MD5 a la contraseña
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		password = HashHelper.toHash(password, Algoritmo.MD5);

		UserDTO usuario = userDAO.getUser(username);

		// Si no existe el usuario, de vuelta al login
		if (usuario == null) {
			request.setAttribute("incorrecto", true);
			RequestDispatcher view = request.getRequestDispatcher(LOGIN_PAGE);
			view.forward(request, response);
		} else {
			// Si la contraseña coincide, puede entrar
			if (usuario.getPassword().equalsIgnoreCase(password)) {
				session = request.getSession(true);
				session.setAttribute("user", usuario);
				response.sendRedirect(request.getContextPath()
						+ POSTULACIONES_PAGE);
			}
			// Si no, de vuelta al login
			else {
				request.setAttribute("incorrecto", true);
				RequestDispatcher view = request
						.getRequestDispatcher(LOGIN_PAGE);
				view.forward(request, response);
			}
		}

	}

}
