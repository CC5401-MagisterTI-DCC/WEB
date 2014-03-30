package cl.uchile.dcc.cc5401.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import cl.uchile.dcc.cc5401.model.dao.DocumentoDAO;
import cl.uchile.dcc.cc5401.model.dao.GradoAcademicoDAO;
import cl.uchile.dcc.cc5401.model.dao.HistorialDAO;
import cl.uchile.dcc.cc5401.model.dao.PostulacionDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.DocumentoDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.GradoAcademicoDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.HistorialDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PostulacionDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.DocumentoDTO;
import cl.uchile.dcc.cc5401.model.dto.GradoAcademicoDTO;
import cl.uchile.dcc.cc5401.model.dto.HistorialDTO;
import cl.uchile.dcc.cc5401.model.dto.PostulacionDTO;
import cl.uchile.dcc.cc5401.util.InputHelper;

@WebServlet("/app/reenvio")
@MultipartConfig(fileSizeThreshold=1024*1024, maxFileSize=1024*1024*10, maxRequestSize=1024*1024*10*10)
public class ReenvioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PostulacionDAO postulacionDAO;
	private GradoAcademicoDAO gradoAcademicoDAO;
	private DocumentoDAO documentoDAO;   
	private HistorialDAO historialDAO;

	private static String REENVIO_PAGE ="/app/formReenvio.jsp";
	private static String SUCCESS = "/successDocs.jsp";
	private static String NOT_FOUND = "/trackInvalido.jsp";

	public ReenvioController() {
		super();
	}

	//Inicializamos las variables
	public void init(ServletConfig config) throws ServletException { 
		super.init(config);
		postulacionDAO = PostulacionDAOFactory.getPostulacionDAO();
		gradoAcademicoDAO = GradoAcademicoDAOFactory.getGradoAcademicoDAO();
		documentoDAO = DocumentoDAOFactory.getDocumentoDAO();
		historialDAO = HistorialDAOFactory.getHistorialDAO();
	}   

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(true);
		String forward="";

		String hash = request.getParameter("track");

		//Obtenemos la postulación a través del numero de seguimiento
		PostulacionDTO postulacion = postulacionDAO.getPostulacion(hash);
		
		//Si no lo encontramos lo mandamos a una pagina de error, si no seguimos...
		if(postulacion==null){
			forward=NOT_FOUND;
		}
		else{
			List<GradoAcademicoDTO> gradosAcademicos = gradoAcademicoDAO.get(postulacion.getIdPostulante());
			List<InputHelper> inputs = new ArrayList<InputHelper>();

			//Verificamos los documentos rechazados
			int i = 0;
			for(GradoAcademicoDTO grado : gradosAcademicos){
				DocumentoDTO cn = documentoDAO.get(grado.getIdCertificadoNotas());
				DocumentoDTO ct = documentoDAO.get(grado.getIdCertificadoTitulo());
				if(cn.getComentario()!=null&&cn.getComentario().equalsIgnoreCase("rechazado"))
					inputs.add(new InputHelper("certificado_notas"+i,"Certificado de Notas de "+grado.getNombre(),"certificado_notas"+i,cn.getId()));
				if(ct.getComentario()!=null&&ct.getComentario().equalsIgnoreCase("rechazado"))
					inputs.add(new InputHelper("certificado_titulo"+i,"Certificado de Título de "+grado.getNombre(),"certificado_notas"+i,ct.getId()));
				i++;
			}

			DocumentoDTO cv = documentoDAO.get(postulacion.getIdCV());
			DocumentoDTO cp = documentoDAO.get(postulacion.getIdCartaPresentacion());
			DocumentoDTO c1 = documentoDAO.get(postulacion.getIdCarta1());
			DocumentoDTO c2 = documentoDAO.get(postulacion.getIdCarta2());
			
			//Construimos las entradas del formulario para que luego se desplieguen de manera dinámica
			if(cv.getComentario()!=null&&cv.getComentario().equalsIgnoreCase("rechazado"))
				inputs.add(new InputHelper("cv","Currículum Vitae","cv",cv.getId()));
			if(cp.getComentario()!=null&&cp.getComentario().equalsIgnoreCase("rechazado"))
				inputs.add(new InputHelper("carta_presentacion","Carta de Presentación","carta_presentacion",cp.getId()));
			if(c1.getComentario()!=null&&c1.getComentario().equalsIgnoreCase("rechazado"))
				inputs.add(new InputHelper("carta_1","Carta de Recomendación 1","carta_1",c1.getId()));
			if(c2.getComentario()!=null&&c2.getComentario().equalsIgnoreCase("rechazado"))
				inputs.add(new InputHelper("carta_2","Carta de Recomendación 2","carta_2",c2.getId()));

			session.setAttribute("inputs", inputs);
			request.setAttribute("idPostulacion", postulacion.getId());

			forward=REENVIO_PAGE;
		}
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);

	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(true);
		String forward="";
		int idPostulacion = Integer.parseInt(request.getParameter("idPostulacion"));
		List<InputHelper> inputs = (List<InputHelper>) session.getAttribute("inputs");

		//Obtenemos todos los documentos y los subimos al servidor. Actualizamos su estado en la BD.
		for(InputHelper input : inputs){
			Part part = request.getPart(input.getName());
			DocumentoDTO doc = documentoDAO.get(input.getIdDocumento());
			part.write(doc.getDireccion());
			doc.setComentario("nuevo");
			documentoDAO.actualizar(doc);
		}

		//Agregamos la acción al historial
		historialDAO.agregar(new HistorialDTO(0,idPostulacion,"<i class='icon-upload'></i> El postulante subió nuevos documentos",new Date(),""));

		session.invalidate();
		request.setAttribute("reenvio","reenvio");
		forward=SUCCESS;
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

}
