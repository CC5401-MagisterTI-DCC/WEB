package cl.uchile.dcc.cc5401.controllers.password;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

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
import cl.uchile.dcc.cc5401.util.MailHelper;
import cl.uchile.dcc.cc5401.util.MailHelperFactory;
import cl.uchile.dcc.cc5401.util.MailHelperFactoryImpl;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/app/olvideMiPassword")
public class OlvideMiPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDAO userDAO;
	private CambioPasswordDAO cambioPasswordDAO;
	private MailHelper mailHelper;
	private String passwordResetSubject;
	private String passwordResetBody;
	private String paginaNuevaPassword;
	private SecureRandom secureRandom;

	private static MailHelperFactory mailHelperFactory = new MailHelperFactoryImpl();

	private static final String FORGOT_PASSWORD_PAGE = "/app/olvideMiPassword.jsp";

	public OlvideMiPasswordController() {
		super();
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		userDAO = UserDAOFactory.getUserDAO();
		cambioPasswordDAO = CambioPasswordDAOFactory.getCambioPasswordDAO();
		mailHelper = mailHelperFactory.makeMailHelper(config.getServletContext()
				.getInitParameter("usernameMail"), config.getServletContext()
				.getInitParameter("passwordMail"), config.getServletContext()
				.getInitParameter("hostMail"), config.getServletContext()
				.getInitParameter("portMail"), true);
		passwordResetSubject = config.getServletContext()
				.getInitParameter("passwordResetSubject");
		passwordResetBody = config.getServletContext()
				.getInitParameter("passwordResetBody");
		paginaNuevaPassword = config.getServletContext()
				.getInitParameter("paginaNuevaPassword");
		secureRandom = new SecureRandom();
	}

	/**
	 * Muestra la ventana para reestablecer contraseña
	 * */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher view = request.getRequestDispatcher(FORGOT_PASSWORD_PAGE);
		view.forward(request, response);
	}

	/**
	 * Verifica si el email y password coinciden. De ser así, se envía un mail con
	 * link para resetear password.
	 * */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher view = request.getRequestDispatcher(FORGOT_PASSWORD_PAGE);
		
		// Obtenemos los datos
		String username = request.getParameter("username");
		String email = request.getParameter("email").trim();
		String codigo = new BigInteger(130, secureRandom).toString(32);

		UserDTO usuario = userDAO.getUser(username);

		// Si no existe el usuario, de vuelta al formulario de OlvideMiPassword
		if (usuario == null) {
			request.setAttribute("incorrecto", true);
			view.forward(request, response);
		} else {
			// Si el email coincide, se envia correo para reestablecer password
			if (usuario.getEmail().equalsIgnoreCase(email)) {
				String passwordResetBodyP = passwordResetBody.replaceAll("@usuario",
						usuario.getUsername());
				passwordResetBodyP = passwordResetBodyP.replaceAll("@codigo",
						codigo);
				passwordResetBodyP = passwordResetBodyP.replaceAll("@paginaNuevaPassword",
						paginaNuevaPassword);
				String ip = request.getHeader("X-FORWARDED-FOR");  
				if (ip == null) {  
					ip = request.getRemoteAddr();
				}
				passwordResetBodyP = passwordResetBodyP.replaceAll("@ip", ip);
				CambioPasswordDTO cambioPassword = new CambioPasswordDTO();
				cambioPassword.setIdUsuario(usuario.getId());
				cambioPassword.setCodigo(codigo);
				cambioPasswordDAO.agregar(cambioPassword);
				try {
					mailHelper.sendMail(email, passwordResetSubject,
							passwordResetBodyP);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Ocurrió un error al enviar mail a "
							+ email);
				}
				request.setAttribute("exito", true);
				view.forward(request, response);
			}
			// Si no, de vuelta al formulario...
			else {
				request.setAttribute("incorrecto", true);
				view.forward(request, response);
			}
		}

	}

}
