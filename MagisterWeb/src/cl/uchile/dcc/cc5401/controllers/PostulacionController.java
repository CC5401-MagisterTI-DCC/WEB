package cl.uchile.dcc.cc5401.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cl.uchile.dcc.cc5401.model.dao.ComentarioDAO;
import cl.uchile.dcc.cc5401.model.dao.DatosEmpresaDAO;
import cl.uchile.dcc.cc5401.model.dao.DocumentoDAO;
import cl.uchile.dcc.cc5401.model.dao.FinanciamientoDAO;
import cl.uchile.dcc.cc5401.model.dao.GradoAcademicoDAO;
import cl.uchile.dcc.cc5401.model.dao.PostulacionDAO;
import cl.uchile.dcc.cc5401.model.dao.PostulanteDAO;
import cl.uchile.dcc.cc5401.model.dao.ResolucionDAO;
import cl.uchile.dcc.cc5401.model.dao.UserDAO;
import cl.uchile.dcc.cc5401.model.dao.VotoDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.ComentarioDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.DatosEmpresaDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.DocumentoDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.FinanciamientoDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.GradoAcademicoDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PostulacionDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PostulanteDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.ResolucionDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.UserDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.VotoDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.ComentarioDTO;
import cl.uchile.dcc.cc5401.model.dto.DatosEmpresaDTO;
import cl.uchile.dcc.cc5401.model.dto.DocumentoDTO;
import cl.uchile.dcc.cc5401.model.dto.FinanciamientoDTO;
import cl.uchile.dcc.cc5401.model.dto.GradoAcademicoDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulacionDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulanteDTO;
import cl.uchile.dcc.cc5401.model.dto.ResolucionDTO;
import cl.uchile.dcc.cc5401.model.dto.UserDTO;
import cl.uchile.dcc.cc5401.model.dto.VotoDTO;
import cl.uchile.dcc.cc5401.util.PostulacionPostulante;

@WebServlet("/app/admin/postulaciones")
public class PostulacionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String LIST_POSTULACIONES ="/app/admin/postulaciones.jsp";
	private static String ERROR_PAGE = "/error.jsp";
	private static String POSTULACION_VIEW = "/app/admin/postulacionview.jsp";
	private static String POSTULACION_GENERAL_VIEW = "/app/admin/postulacionInfoGeneral.jsp";

	private PostulacionDAO postulacionDAO;
	private PostulanteDAO postulanteDAO;
	private ResolucionDAO resolucionDAO;
	private DatosEmpresaDAO datosEmpresaDAO;
	private FinanciamientoDAO financiamientoDAO;
	private GradoAcademicoDAO gradoAcademicoDAO;
	private ComentarioDAO comentarioDAO;
	private UserDAO userDAO;
	private DocumentoDAO documentoDAO;
	private VotoDAO votoDAO;

	public PostulacionController() {
		super();
		postulacionDAO = PostulacionDAOFactory.getPostulacionDAO();
		postulanteDAO = PostulanteDAOFactory.getPostulanteDAO();
		resolucionDAO = ResolucionDAOFactory.getResolucionDAO();
		datosEmpresaDAO = DatosEmpresaDAOFactory.getDatosEmpresaDAO();
		financiamientoDAO = FinanciamientoDAOFactory.getFinanciamientoDAO();
		gradoAcademicoDAO = GradoAcademicoDAOFactory.getGradoAcademicoDAO();
		comentarioDAO = ComentarioDAOFactory.getComentarioDAO();
		userDAO = UserDAOFactory.getUserDAO();
		documentoDAO = DocumentoDAOFactory.getDocumentoDAO();
		votoDAO = VotoDAOFactory.getVotoDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String forward = "";
		String action = request.getParameter("action");
		List<PostulacionDTO> postulaciones = new ArrayList<PostulacionDTO>();
		HttpSession session = request.getSession(true);
		UserDTO user = (UserDTO) session.getAttribute("user");

		//Vista General
		if(action==null){

			try{
				postulaciones = postulacionDAO.getAll();
			}
			catch (Exception e){
				e.printStackTrace();
				forward=ERROR_PAGE;
			}

			List<PostulacionPostulante> revision = new ArrayList<PostulacionPostulante>();
			List<PostulacionPostulante> validacion = new ArrayList<PostulacionPostulante>();
			List<PostulacionPostulante> consideracion = new ArrayList<PostulacionPostulante>();
			List<PostulacionPostulante> evaluacion = new ArrayList<PostulacionPostulante>();
			List<PostulacionPostulante> decision = new ArrayList<PostulacionPostulante>();
			List<PostulacionPostulante> esperaNotificacion = new ArrayList<PostulacionPostulante>();
			List<PostulacionPostulante> resolucion = new ArrayList<PostulacionPostulante>();
			List<ResolucionDTO> resoluciones = new ArrayList<ResolucionDTO>();
			List<ResolucionDTO> resolucionesEnEspera = new ArrayList<ResolucionDTO>();
			List<Boolean> votos = new ArrayList<Boolean>();


			if(postulaciones!=null){
				//Recorremos las postulaciones para ir rellenando las listas de argumentos de la vista
				for(PostulacionDTO p : postulaciones){
					PostulacionPostulante pp = new PostulacionPostulante(p, postulanteDAO.get(p.getIdPostulante()));
					switch (p.getEstado()){
					case REVISION:
						revision.add(pp);
						break;
					case EN_VALIDACION:
						validacion.add(pp);
						break;
					case EN_CONSIDERACION:
						consideracion.add(pp);
						break;
					case EN_EVALUACION:
						evaluacion.add(pp);
						if(votoDAO.get(p.getId(), user.getId())!=null){
							votos.add(new Boolean(true));
						}
						else{
							votos.add(new Boolean(false));
						}
						break;
					case DECISION:
						decision.add(pp);
						break;
					case EN_ESPERA:
						esperaNotificacion.add(pp);
						ResolucionDTO res = resolucionDAO.get(p.getId());
						resolucionesEnEspera.add(res);
						break;
					case RESUELTA:
						resolucion.add(pp);
						ResolucionDTO r = resolucionDAO.get(p.getId());
						resoluciones.add(r);
						break;
					default:
						break;
					}
				}			

				//Seteamos los parametros en el request y seteamos la redirección
				request.setAttribute("postulacionesRevision", revision);
				request.setAttribute("postulacionesValidacion", validacion);
				request.setAttribute("postulacionesConsideracion", consideracion);
				request.setAttribute("postulacionesDecision", decision);
				request.setAttribute("postulacionesEvaluacion", evaluacion);
				request.setAttribute("postulacionesEspera", esperaNotificacion);
				request.setAttribute("postulacionesResolucion", resolucion);
				request.setAttribute("resolucionesEspera", resolucionesEnEspera);
				request.setAttribute("resoluciones", resoluciones);
				request.setAttribute("votos", votos);


				//Manejo de numeros de pendientes
				int nCoordinador = 0;
				int nAsistente = 0;
				int nComision = 0;
				int nJefePEC = 0;

				// Revisamos si las listas contienen algo, si es así aumentamos el contador de postulaciones pendientes para las entidades
				// Una vez contados todos los pendientes, seteamos la variable en la sesión para que todas las vistas tengan acceso al conteo.
				if(revision.size()>0){
					nAsistente +=revision.size();
				}
				if(validacion.size()>0){
					nJefePEC += validacion.size();
				}
				if(consideracion.size()>0){
					nCoordinador+=consideracion.size();
				}
				if(evaluacion.size()>0){
					nComision+=evaluacion.size();
					nCoordinador+=evaluacion.size();
				}
				if(decision.size()>0){
					nCoordinador+=decision.size();
				}
				if(esperaNotificacion.size()>0){
					nAsistente +=esperaNotificacion.size();
				}

				if(user.getRol().equals("Coordinador")){
					int nPendientes = nCoordinador;
					int nPendientesVoto = nComision;
					//Caso en que ya haya votado
					for(PostulacionPostulante pp : evaluacion){
						PostulacionDTO p = pp.getPostulacion(); 
						if(votoDAO.get(p.getId(), user.getId())!=null){
							nPendientes--;
							nPendientesVoto--;
						}
					}
					session.setAttribute("nPendientes", nPendientes);
					session.setAttribute("nPendientesVoto", nPendientesVoto);
				}
				if(user.getRol().equals("Jefe del PEC")){
					session.setAttribute("nPendientes", nJefePEC);
				}
				if(user.getRol().equals("Asistente")){
					session.setAttribute("nPendientes", nAsistente);
				}
				if(user.getRol().equals("Comisionado")){
					int nPendientes = nComision;
					//Caso en que ya haya votado
					for(PostulacionPostulante pp : evaluacion){
						PostulacionDTO p = pp.getPostulacion();
						if(votoDAO.get(p.getId(), user.getId())!=null){
							nPendientes--;
						}
					}
					session.setAttribute("nPendientes", nPendientes);;
				}
			}
			//Parámetros de navegación
			request.setAttribute("postulaciones", "yes");
			request.setAttribute("admin", "admin");
			forward=LIST_POSTULACIONES;

		}
		else{
			//Caso de vista particular de postulación
			if(action.equalsIgnoreCase("revisar")){

				int id = Integer.parseInt(request.getParameter("id"));
				PostulacionDTO postulacion = postulacionDAO.getPostulacion(id);
				PostulanteDTO postulante = postulanteDAO.get(postulacion.getIdPostulante());
				DatosEmpresaDTO datosEmpresa = datosEmpresaDAO.get(postulante.getId());
				FinanciamientoDTO financiamiento = financiamientoDAO.get(postulacion.getIdFinanciamiento());
				List<GradoAcademicoDTO> gradosAcademicos = gradoAcademicoDAO.get(postulante.getId());
				List<ComentarioDTO> comentarios = comentarioDAO.get(id);
				List<UserDTO> usuarios = new ArrayList<UserDTO>();


				//Caso en el que se involucran votos
				if(request.getParameter("evaluacion")!=null || request.getParameter("decision")!=null || request.getParameter("espera_notificacion")!=null || request.getParameter("resuelta")!=null){

					List<Integer> comIds = userDAO.getComisionIds();
					List<ComentarioDTO> comentariosVotos = new ArrayList<ComentarioDTO>();
					List<VotoDTO> votos = new ArrayList<VotoDTO>();
					List<UserDTO> usuariosVotos = new ArrayList<UserDTO>();
					for(Integer i : comIds){
						VotoDTO voto = votoDAO.get(id, i);
						votos.add(voto);
						List<ComentarioDTO> cs = comentarioDAO.get(id, i);
						if(cs!=null)
							comentariosVotos.add(cs.get(cs.size()-1));
						else
							comentariosVotos.add(null);
						usuariosVotos.add(userDAO.getUser(i));

					}
					if(votoDAO.get(id, user.getId())!=null){
						request.setAttribute("userVoto", true);
					}
					request.setAttribute("votos", votos);
					request.setAttribute("usuariosVotos", usuariosVotos);
					request.setAttribute("comentariosVotos", comentariosVotos);
				}
				if(comentarios!=null){
					for(ComentarioDTO comentario : comentarios){
						usuarios.add(userDAO.getUser(comentario.getIdUsuario()));
					}
				}

				if(request.getParameter("resuelta")!=null || request.getParameter("espera_notificacion")!=null ){
					ResolucionDTO resolucion = resolucionDAO.get(id);
					request.setAttribute("resolucion", resolucion);
				}

				request.setAttribute("docExtras", documentoDAO.getExtras(id));
				request.setAttribute("CV", documentoDAO.get(postulacion.getIdCV()));
				request.setAttribute("CartaPresentacion", documentoDAO.get(postulacion.getIdCartaPresentacion()));
				request.setAttribute("Carta1", documentoDAO.get(postulacion.getIdCarta1()));
				request.setAttribute("Carta2", documentoDAO.get(postulacion.getIdCarta2()));
				List<DocumentoDTO> docGrados = new ArrayList<DocumentoDTO>();
				for(int i=0;i<gradosAcademicos.size();i++){
					docGrados.add(documentoDAO.get(gradosAcademicos.get(i).getIdCertificadoNotas()));
					docGrados.add(documentoDAO.get(gradosAcademicos.get(i).getIdCertificadoTitulo()));
				}

				request.setAttribute("docGrados", docGrados);

				request.setAttribute("postulacion", postulacion);
				request.setAttribute("postulante", postulante);
				request.setAttribute("datosEmpresa", datosEmpresa);
				request.setAttribute("financiamiento", financiamiento);
				request.setAttribute("gradosAcademicos", gradosAcademicos);
				request.setAttribute("comentarios", comentarios);
				request.setAttribute("usuarios", usuarios);
				
				if(request.getParameter("general")!=null)
					forward= POSTULACION_GENERAL_VIEW;
				else
					forward = POSTULACION_VIEW;
			}
		}
		
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher(ERROR_PAGE);
		view.forward(request, response);
	}

}

