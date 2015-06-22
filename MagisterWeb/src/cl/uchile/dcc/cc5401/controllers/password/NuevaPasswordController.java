package cl.uchile.dcc.cc5401.controllers.password;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.uchile.dcc.cc5401.model.dao.CambioPasswordDAO;
import cl.uchile.dcc.cc5401.model.dao.UserDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.CambioPasswordDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.UserDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.CambioPasswordDTO;
import cl.uchile.dcc.cc5401.model.dto.UserDTO;
import cl.uchile.dcc.cc5401.util.Algoritmo;
import cl.uchile.dcc.cc5401.util.HashHelper;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/app/nuevaPassword")
public class NuevaPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDAO userDAO;
	private CambioPasswordDAO cambioPasswordDAO;

	private static final String NUEVA_PASSWORD_PAGE = "/app/nuevaPassword.jsp";

	public NuevaPasswordController() {
		super();
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		userDAO = UserDAOFactory.getUserDAO();
		cambioPasswordDAO = CambioPasswordDAOFactory.getCambioPasswordDAO();
	}
	
	private class UserAndCambioPassword {
		UserDTO user;
		CambioPasswordDTO cambioPassword;
	}

	private UserAndCambioPassword getUserAndCambioPassword(String username, String codigo){
		UserDTO user = userDAO.getUser(username);
		if(user == null){
			return null;
		}
		CambioPasswordDTO cambioPassword = cambioPasswordDAO.get(user.getId(), codigo);
		if(cambioPassword == null){
			return null;
		}
		UserAndCambioPassword result = new UserAndCambioPassword();
		result.cambioPassword = cambioPassword;
		result.user = user;
		return result;
	}

	/**
	 * Muestra la ventana para reestablecer contraseña
	 * */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserAndCambioPassword userAndCambioPassword = getUserAndCambioPassword(
				request.getParameter("usuario"),
				request.getParameter("codigo"));
		if(userAndCambioPassword==null){
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		request.setAttribute("usuario", request.getParameter("usuario"));
		request.setAttribute("codigo", request.getParameter("codigo"));
		RequestDispatcher view = request.getRequestDispatcher(NUEVA_PASSWORD_PAGE);
		view.forward(request, response);
	}

	/**
	 * Verifica si existe la solicitud de cambio de password. Si existe y las contraseñas enviadas
	 * coinciden se cambia la password y se elimina la solicitud. En otro caso se muestra el mensaje
	 * de error correspondiente.
	 * */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserAndCambioPassword userAndCambioPassword = getUserAndCambioPassword(
				request.getParameter("usuario"),
				request.getParameter("codigo"));
		if(userAndCambioPassword==null){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		request.setAttribute("usuario", request.getParameter("usuario"));
		request.setAttribute("codigo", request.getParameter("codigo"));
		String password = request.getParameter("password");
		if(password == null || !password.equals(request.getParameter("password_confirmation"))){
			request.setAttribute("incorrecto", true);
			RequestDispatcher view = request.getRequestDispatcher(NUEVA_PASSWORD_PAGE);
			view.forward(request, response);
			return;
		}
		UserDTO user = userAndCambioPassword.user;
		CambioPasswordDTO cambioPassword = userAndCambioPassword.cambioPassword;
		user.setPassword(HashHelper.toHash(password, Algoritmo.MD5));
		userDAO.actualizar(user);
		cambioPasswordDAO.quitar(cambioPassword);
		request.setAttribute("exito", true);
		RequestDispatcher view = request.getRequestDispatcher(NUEVA_PASSWORD_PAGE);
		view.forward(request, response);
	}

}
