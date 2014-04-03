package cl.uchile.dcc.cc5401.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.uchile.dcc.cc5401.model.dao.DocumentoDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.DocumentoDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.DocumentoDTO;

/**
 * Servlet implementation class DocumentoController
 */
@WebServlet("/app/admin/documento")
public class DocumentoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DocumentoDAO documentoDAO;

	public DocumentoController() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		documentoDAO = DocumentoDAOFactory.getDocumentoDAO();
	}

	/**
	 * Encargado de la lógica de descargar archivos
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Obtenemos el id del request y extraemos el documento
		int id = Integer.parseInt(request.getParameter("id"));
		DocumentoDTO documento = documentoDAO.get(id);
		File file = new File(documento.getDireccion());

		System.out
				.println("File location on server:: " + file.getAbsolutePath());

		if (!file.exists()) {
			throw new ServletException("Archivo no existe en el servidor.");
		}

		// Extraemos el documento del sistema
		ServletContext ctx = getServletContext();
		InputStream fis = new FileInputStream(file);
		String mimeType = ctx.getMimeType(file.getAbsolutePath());
		response.setContentType(mimeType != null ? mimeType
				: "application/octet-stream");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ documento.getNombre() + "\"");

		// Enviamos el stream de datos al cliente
		ServletOutputStream os = response.getOutputStream();
		byte[] bufferData = new byte[1024];
		int read = 0;
		while ((read = fis.read(bufferData)) != -1) {
			os.write(bufferData, 0, read);
		}
		os.flush();
		os.close();
		fis.close();
		System.out.println("Archivo descargado correctamente por el usuario");

	}
}
