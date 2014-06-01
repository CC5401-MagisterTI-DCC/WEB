package cl.uchile.dcc.cc5401.controllers.admin;

import java.io.IOException;
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
import cl.uchile.dcc.cc5401.model.dao.HistorialDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.DocumentoDAOFactory;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.HistorialDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.DocumentoDTO;
import cl.uchile.dcc.cc5401.model.dto.HistorialDTO;
import cl.uchile.dcc.cc5401.model.dto.UserDTO;
import cl.uchile.dcc.cc5401.util.RolUsuario;

@WebServlet("/app/admin/docExtra")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 10 * 10)
public class DocumentoExtraController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String ruta = "";
	private DocumentoDAO documentoDAO;
	private HistorialDAO historialDAO;

	private static final String SUCCESS = "/app/operacionExitosa.jsp";

	public DocumentoExtraController() {
		super();
	}

	/**
	 * Inicializa los objetos DAO para interacturar con la BD y carga
	 * información de la configuración inicial.
	 * */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		documentoDAO = DocumentoDAOFactory.getDocumentoDAO();
		historialDAO = HistorialDAOFactory.getHistorialDAO();
		ruta = config.getServletContext().getInitParameter("archivos.dir");
	}

	/**
	 * Agrega un documento a la postulación
	 * */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String forward = "";
		int count = 0;

		// Obtenemos los parámetros del formulario junto con el usuario de la
		// sesión actual
		int idPostulacion = Integer.parseInt(request
				.getParameter("id_postulacion"));
		String comentario = request.getParameter("comentario");
		HttpSession session = request.getSession(false);
		UserDTO user = (UserDTO) session.getAttribute("user");

		// Obtenemos el archivo del documento
		Part part = request.getPart("doc_extra");

		// Vemos si no habia otro con el mismo numero para no sobreescribirlo
		List<DocumentoDTO> extras = documentoDAO.getExtras(idPostulacion);

		if (extras != null)
			count = extras.size();

		DocumentoDTO docExtra = new DocumentoDTO(0,
				ruta + "/" + "DocumentoExtra" + (count + 1) + ":"
						+ idPostulacion + ".pdf", "DocumentoExtra"
						+ (count + 1) + ":" + idPostulacion + ".pdf",
				comentario);

		// Subimos el archivo al servidor
		part.write(docExtra.getDireccion());

		// Agregamos el registro a la base de datos, junto con el historial
		documentoDAO.agregarExtra(idPostulacion, docExtra);
		historialDAO
				.agregar(new HistorialDTO(
						0,
						idPostulacion,
						"<strong>"
								+ user.getUsername()
								+ ":</strong> <i class='icon-plus'></i> Se agregó un documento extra",
						new Date(), comentario, RolUsuario.getValue(user.getIdRol())));

		forward = SUCCESS;
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

}
