package cl.uchile.dcc.cc5401.controllers.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cl.uchile.dcc.cc5401.model.dao.HistorialDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.HistorialDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.HistorialDTO;
import cl.uchile.dcc.cc5401.model.dto.UserDTO;
import cl.uchile.dcc.cc5401.util.RolUsuario;

@WebServlet("/app/admin/historial")
public class HistorialController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HistorialDAO historialDAO;
 
	private static final String HISTORIAL_PARTICULAR = "/app/admin/historialPostulacion.jsp";
	private static final String HISTORIAL_GENERAL = "/app/admin/historial.jsp";

	public HistorialController() {
		super();
	}

	/**
	 * Inicializa los objetos DAO para interacturar con la BD
	 * */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		historialDAO = HistorialDAOFactory.getHistorialDAO();
	}

	/**
	 * Carga los datos necesarios para mostrar el historial respectivo
	 * (dependiendo de si es de una postulación particular o de todo el
	 * historial).
	 * */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String forward;
		String idPostulacion = request.getParameter("idPostulacion");

		// Obtenemos el usuario activo en la sesión
		HttpSession session = request.getSession(true);
		UserDTO user = (UserDTO) session.getAttribute("user");
			
		// Caso historial general
		if (idPostulacion == null) {
			List<HistorialDTO> hts = historialDAO.getAll();
			if (hts != null) {
				hts = quitarComentarios(user, hts);
				Collections.reverse(hts);				
			}
			request.setAttribute("historial", "yes");
			request.setAttribute("admin", "admin");
			request.setAttribute("hts", hts);
			forward = HISTORIAL_GENERAL;
		}
		// Caso historial particular
		else {
			int id = Integer.parseInt(idPostulacion);
			List<HistorialDTO> ht = historialDAO.get(id);
			ht = quitarComentarios(user, ht);
			Collections.reverse(ht);
			request.setAttribute("listaHistorial", ht);
			request.setAttribute("idPostulacion", idPostulacion);
			forward = HISTORIAL_PARTICULAR;
		}
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}
	
	/**
	 * Quita los comentarios del comisionado, administrador y coordinador cuando
	 * quien consulta es una asistente o un jefe de PEC
	 * 
	 * @param user usuario que realizar la consulta de historial
	 * @param hts lista con los hitos (historial)
	 * */
	private List<HistorialDTO> quitarComentarios(UserDTO user, List<HistorialDTO> hts) {
		
		List<HistorialDTO> nuevaLista = new ArrayList<HistorialDTO>();
		RolUsuario rolUsuario = RolUsuario.getValue(user.getIdRol());
		RolUsuario rolHito;
		
		if (rolUsuario == RolUsuario.ASISTENTE || 
			rolUsuario == RolUsuario.JEFE_PEC) {
			
			for (HistorialDTO h: hts) {	
				rolHito = h.getRolAutor();
				
				if (rolHito != RolUsuario.ADMINISTRADOR && 
					rolHito != RolUsuario.COORDINADOR && 
					rolHito != RolUsuario.COMISIONADO) {
					nuevaLista.add(h);					
				}
			}
			
			return nuevaLista;
		}
		
		return hts;
	}
}
