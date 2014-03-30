package cl.uchile.dcc.cc5401.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cl.uchile.dcc.cc5401.model.dao.ComentarioDAO;
import cl.uchile.dcc.cc5401.model.dao.HistorialDAO;
import cl.uchile.dcc.cc5401.model.dao.PostulacionDAO;
import cl.uchile.dcc.cc5401.model.dao.PostulanteDAO;
import cl.uchile.dcc.cc5401.model.dao.ResolucionDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.ComentarioDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.HistorialDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PostulacionDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PostulanteDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.ResolucionDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.ComentarioDTO;
import cl.uchile.dcc.cc5401.model.dto.HistorialDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulacionDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulanteDTO;
import cl.uchile.dcc.cc5401.model.dto.ResolucionDTO;
import cl.uchile.dcc.cc5401.model.dto.UserDTO;
import cl.uchile.dcc.cc5401.util.Estado;
import cl.uchile.dcc.cc5401.util.MailHelper;
import cl.uchile.dcc.cc5401.util.ResultadoPostulacion;

@WebServlet("/app/admin/estado")
public class EstadoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String ERROR_PAGE = "/error.jsp";
	private static String SUCCESS_PAGE = "/app/operacionExitosa.jsp";


	private PostulacionDAO postulacionDAO;
	private PostulanteDAO postulanteDAO;
	private ComentarioDAO comentarioDAO;
	private HistorialDAO historialDAO;
	private ResolucionDAO resolucionDAO;
	private MailHelper mailHelper;

	public EstadoController() {
		super();
		postulacionDAO = PostulacionDAOFactory.getPostulacionDAO();
		comentarioDAO = ComentarioDAOFactory.getComentarioDAO();
		historialDAO = HistorialDAOFactory.getHistorialDAO();
		resolucionDAO = ResolucionDAOFactory.getResolucionDAO();
		postulanteDAO = PostulanteDAOFactory.getPostulanteDAO();
	}

	public void init(ServletConfig config) throws ServletException { 
		mailHelper = new MailHelper(
				config.getServletContext().getInitParameter("usernameMail"),
				config.getServletContext().getInitParameter("passwordMail"),
				config.getServletContext().getInitParameter("hostMail"),
				config.getServletContext().getInitParameter("portMail"), true);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward = "";
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		//Obtenemos el usuario activo en la sesión
		HttpSession session = request.getSession(true);
		UserDTO user = (UserDTO) session.getAttribute("user");

		//Si la acción es distinta de null procedemos
		if(action!=null){

			//Cambio de estado: Revisión -> Validación
			if(action.equalsIgnoreCase("revision")){
				int id = Integer.parseInt(request.getParameter("id"));
				PostulacionDTO postulacion = postulacionDAO.getPostulacion(id);
				postulacion.setEstado(Estado.EN_VALIDACION);
				postulacionDAO.actualizar(postulacion);
				historialDAO.agregar(new HistorialDTO(0,postulacion.getId(),"<strong>"+user.getUsername()+"</strong>: Cambio de Estados: En Revisión <i class='icon-arrow-right'></i> En Validación",new Date(),""));
				forward = SUCCESS_PAGE;
			}

			//Cambio de estado: Validación -> Consideración
			else if(action.equalsIgnoreCase("validacion")){
				int id = Integer.parseInt(request.getParameter("id"));
				String comentario = request.getParameter("comentario");
				ComentarioDTO comentarioDTO = new ComentarioDTO(0,id,user.getId(),comentario,new Date(),false);
				comentarioDAO.agregar(comentarioDTO);
				PostulacionDTO postulacion = postulacionDAO.getPostulacion(id);
				postulacion.setEstado(Estado.EN_CONSIDERACION);
				historialDAO.agregar(new HistorialDTO(0,postulacion.getId(),"<strong>"+user.getUsername()+"</strong>: Cambio de Estados: En Validación <i class='icon-arrow-right'></i> En Consideración",new Date(),comentarioDTO.getTexto()));
				postulacionDAO.actualizar(postulacion);
				forward = SUCCESS_PAGE;
			}

			//Cambio de estado: Consideración -> Evaluación
			else if(action.equalsIgnoreCase("consideracion")){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

				//Modificamos el deadline
				String dl = request.getParameter("deadline");
				Date deadline = null;
				try {
					deadline = sdf.parse(dl);
				} catch (ParseException e) {
					forward = ERROR_PAGE;
					e.printStackTrace();
				}
				int id = Integer.parseInt(request.getParameter("id"));

				String comentario = request.getParameter("comentario");
				ComentarioDTO comentarioDTO = new ComentarioDTO(0,id,user.getId(),comentario,new Date(),false);
				PostulacionDTO postulacion = postulacionDAO.getPostulacion(id);
				postulacion.setEstado(Estado.EN_EVALUACION);
				postulacion.setDeadline(deadline);
				historialDAO.agregar(new HistorialDTO(0,postulacion.getId(),"<strong>"+user.getUsername()+"</strong>: Cambio de Estados: En Consideración <i class='icon-arrow-right'></i> En Evaluación",new Date(),comentarioDTO.getTexto()));
				postulacionDAO.actualizar(postulacion);
				forward = SUCCESS_PAGE;

			}

			//Cambio de estado: Evaluación -> Decisión
			else if(action.equalsIgnoreCase("evaluacion")){
				int id = Integer.parseInt(request.getParameter("id"));
				PostulacionDTO postulacion = postulacionDAO.getPostulacion(id);
				postulacion.setEstado(Estado.DECISION);
				postulacionDAO.actualizar(postulacion);
				historialDAO.agregar(new HistorialDTO(0,postulacion.getId(),"<strong>"+user.getUsername()+"</strong>: Cambio de Estados: En Evaluación <i class='icon-arrow-right'></i> En Decisión",new Date(),""));
				forward = SUCCESS_PAGE;
			}

			//Cambio de estado: Decisión -> En Espera de Notificación
			else if(action.equalsIgnoreCase("decision")){
				int id = Integer.parseInt(request.getParameter("id"));
				String detalles = request.getParameter("detalles");
				String decision = request.getParameter("decision");
				PostulacionDTO postulacion = postulacionDAO.getPostulacion(id);
				postulacion.setEstado(Estado.EN_ESPERA);
				ResolucionDTO resolucion = null;
				if(decision.equalsIgnoreCase("aceptado")){
					resolucion = new ResolucionDTO(0,id,detalles,ResultadoPostulacion.ACEPTADO,new Date());
					historialDAO.agregar(new HistorialDTO(0,postulacion.getId(),"<strong>"+user.getUsername()+"</strong>: Postulación Aceptada",new Date(),detalles));
				}
				else if(decision.equalsIgnoreCase("aceptado_condicional")){
					resolucion = new ResolucionDTO(0,id,detalles,ResultadoPostulacion.ACEPTADO_CONDICIONAL,new Date());
					historialDAO.agregar(new HistorialDTO(0,postulacion.getId(),"<strong>"+user.getUsername()+"</strong>: Postulación Aceptada Condicionalmente",new Date(),detalles));
				}
				else{
					resolucion = new ResolucionDTO(0,id,detalles,ResultadoPostulacion.RECHAZADO,new Date());
					historialDAO.agregar(new HistorialDTO(0,postulacion.getId(),"<strong>"+user.getUsername()+"</strong>: Postulación Rechazada",new Date(),detalles));
				}

				historialDAO.agregar(new HistorialDTO(0,postulacion.getId(),"<strong>"+user.getUsername()+"</strong>: Cambio de Estados: En Decisión <i class='icon-arrow-right'></i> En Espera de Notificación",new Date(),""));
				postulacionDAO.actualizar(postulacion);
				resolucionDAO.agregar(resolucion);
				forward = SUCCESS_PAGE;
			}

			//Cambio de estado: En Espera de Notificación -> Resuelta
			else if(action.equalsIgnoreCase("espera_notificacion")){
				int id = Integer.parseInt(request.getParameter("id"));
				String decision = request.getParameter("decision");
				PostulacionDTO postulacion = postulacionDAO.getPostulacion(id);
				
				//Si se decidió notificar formalmente al estudiante
				if(decision.equalsIgnoreCase("notificar")){
					String contenido = request.getParameter("contenido");
					PostulanteDTO postulante = postulanteDAO.get(postulacion.getIdPostulante());
					//Enviamos un mail al postulante
					try{
						mailHelper.sendMail(postulante.getEmail(), "Postulación Resuelta - Magister T.I.", contenido);
					}
					catch(Exception e){
						e.printStackTrace();
						System.out.println("NO SE PUDO MANDAR MAIL A "+postulante.getEmail());
						forward = ERROR_PAGE;
					}
					finally{
						postulacion.setEstado(Estado.RESUELTA);
						postulacionDAO.actualizar(postulacion);
						historialDAO.agregar(new HistorialDTO(0,postulacion.getId(),"<strong>"+user.getUsername()+"</strong>: Cambio de Estados: En Espera de Notificación <i class='icon-arrow-right'></i> Resuelta",new Date(),"Se notificó formalmente al postulante"));
					}
				}
				//Si se decidió no notificar
				else{
					postulacion.setEstado(Estado.RESUELTA);
					postulacionDAO.actualizar(postulacion);
					historialDAO.agregar(new HistorialDTO(0,postulacion.getId(),"<strong>"+user.getUsername()+"</strong>: Cambio de Estados: En Espera de Notificación <i class='icon-arrow-right'></i> Resuelta",new Date(),"No se notificó formalmente al postulante"));
				}
				forward = SUCCESS_PAGE;
			}
		}	
		else{
			forward = ERROR_PAGE;
		}

		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher view = request.getRequestDispatcher(ERROR_PAGE);
		view.forward(request, response);
	}

}
