package cl.uchile.dcc.cc5401.controllers.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutController
 */
@WebServlet("/app/logout")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String INDEX_PAGE = "/index.jsp";

	public LogoutController() {
		super();
	}

	/**
	 * Inválida la sesión actual y redirecciona al index.
	 * */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		session.invalidate();
		response.sendRedirect(request.getContextPath() + INDEX_PAGE);
	}

}
