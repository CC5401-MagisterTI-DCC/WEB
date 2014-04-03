package cl.uchile.dcc.cc5401.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.uchile.dcc.cc5401.model.dao.UserDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.UserDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.UserDTO;
import cl.uchile.dcc.cc5401.util.Algoritmo;
import cl.uchile.dcc.cc5401.util.HashHelper;

@WebServlet("/app/admin/userAdmin")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String USER_PAGE = "/app/admin/userAdmin/users.jsp";
	private static final String ERROR_PAGE = "/app/admin/userAdmin/users.jsp";
	private static final String DELETE_PAGE = "/app/admin/userAdmin/delete.jsp";
	private static final String NEW_PAGE = "/app/admin/userAdmin/new.jsp";
	private static final String EDIT_PAGE = "/app/admin/userAdmin/edit.jsp";
	private static final String SUCCESS = "/app/operacionExitosa.jsp";
	private UserDAO userDAO;

	public UserController() {
		super();
		userDAO = UserDAOFactory.getUserDAO();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String forward = "";
		String action = request.getParameter("action");

		if (action == null) {
			request.setAttribute("usuarios", userDAO.getAll());
			forward = USER_PAGE;
		} else if (action.equalsIgnoreCase("edit")) {
			String username = request.getParameter("username");
			request.setAttribute("usuario", userDAO.getUser(username));
			forward = EDIT_PAGE;
		} else if (action.equalsIgnoreCase("delete")) {
			String username = request.getParameter("username");
			request.setAttribute("usuario", userDAO.getUser(username));
			forward = DELETE_PAGE;
		} else if (action.equalsIgnoreCase("new")) {
			forward = NEW_PAGE;
		} else {
			forward = ERROR_PAGE;
		}

		request.setAttribute("admin", "admin");

		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String forward = "";
		String nuevo = request.getParameter("new");
		String edit = request.getParameter("edit");
		String delete = request.getParameter("delete");

		if (nuevo != null) {
			String username = request.getParameter("username");
			String mail = request.getParameter("email");
			String password = request.getParameter("password");
			int idRol = Integer.parseInt(request.getParameter("rol"));
			password = HashHelper.toHash(password, Algoritmo.MD5);

			System.out.println("idRol:" + idRol);
			UserDTO user = new UserDTO(0, username, password, mail, "", idRol,
					null);
			userDAO.agregar(user);
			forward = SUCCESS;
		} else if (edit != null) {
			int id = Integer.parseInt(request.getParameter("id"));
			String username = request.getParameter("username");
			String mail = request.getParameter("email");
			int idRol = Integer.parseInt(request.getParameter("rol"));

			UserDTO user = userDAO.getUser(id);
			user.setUsername(username);
			user.setEmail(mail);
			user.setIdRol(idRol);
			userDAO.actualizar(user);

			forward = SUCCESS;
		} else if (delete != null) {
			int id = Integer.parseInt(request.getParameter("id"));
			userDAO.eliminar(id);
			forward = SUCCESS;
		} else {
			forward = ERROR_PAGE;
		}

		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

}
