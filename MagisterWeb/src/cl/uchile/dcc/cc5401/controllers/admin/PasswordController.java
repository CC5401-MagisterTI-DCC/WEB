package cl.uchile.dcc.cc5401.controllers.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
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

	private UserDAO userDAO;

	private static final String POSTULACIONES_PAGE = "/app/admin/postulaciones";
	private static final String PASS_CHANGE = "/app/admin/passchange.jsp";

	public PasswordController() {
		super();
	}

	/**
	 * Inicializa los objetos DAO para interacturar con la BD
	 * */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		userDAO = UserDAOFactory.getUserDAO();
	}

	/**
	 * Carga la vista para que un usuario pueda cambiar su contraseña.
	 * */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher(PASS_CHANGE);
		view.forward(request, response);
	}

	/**
	 * Persiste el cambio de contraseña.
	 * */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("user");

		// Obtenemos los parámetros del formulario.
		String old = request.getParameter("old");
		old = HashHelper.toHash(old, Algoritmo.MD5);

		// Si su contraseña anterior coincide, seguimos.
		if (old.equals(user.getPassword())) {
			String nueva = request.getParameter("new");
			// Si las contraseñas nuevas coinciden, cambiamos la contraseña.
			if (request.getParameter("new2").equals(nueva)) {
				nueva = HashHelper.toHash(nueva, Algoritmo.MD5);
				user.setPassword(nueva);
				userDAO.actualizarPassword(user);
				session.setAttribute("user", user);
				response.sendRedirect(request.getContextPath()
						+ POSTULACIONES_PAGE + "?nuevaC=true");

			}
			// De lo contrario lo enviamos de vuelta con el error de que ambas
			// contraseñas no son iguales.
			else {
				request.setAttribute("noigual", true);
				RequestDispatcher view = request
						.getRequestDispatcher(PASS_CHANGE);
				view.forward(request, response);
			}
		}
		// Si su contraseña anterior no coincide, lo enviamos de vuelta con un
		// error.
		else {
			request.setAttribute("incorrecto", true);
			RequestDispatcher view = request.getRequestDispatcher(PASS_CHANGE);
			view.forward(request, response);
		}

	}

}
