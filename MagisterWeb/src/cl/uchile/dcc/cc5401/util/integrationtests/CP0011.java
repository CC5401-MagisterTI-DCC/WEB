package cl.uchile.dcc.cc5401.util.integrationtests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/* 
 *   Ingresar al sistema como Secretaria, en la interfaz de validación de documentos, 
 *   para una postulación correctamente ingresada, seleccionar la opción de Rechazar 
 *   Documento sin especificar los documentos faltantes.
 * 
 */
public class CP0011 extends IntegrationTest {

	private WebClient webClientSecretaria;
	private int idPostulacion;

	@Before
	public void setUp() throws Exception {

		createDBDump();
		openDBConnection();

		// ingreso de postulante
		newPostulante("Jose Tomas", "Pefaur Pumarino", "rut", "6747640-9",
				"152", true, false, "13/06/2014", "mimail@mail.com", "1234567",
				"Una direccion 879", "Mi grado academico", "Mi institucion",
				"13/06/2014", "152", "Testing.pdf");
		idPostulacion = getPostulacionId("6747640-9");

		// Crea un usuario con rol de secretaria
		createUser("admin", "admin1234", "secretaria", "secretaria@mail.com",
				"secretaria", "4");

		// Secretaria inicia sesión
		webClientSecretaria = logIn("secretaria", "secretaria");
	}

	@Test
	public void postulacionRechazadaPorDocumentoSinIndicarCualTest() throws SQLException,
			FailingHttpStatusCodeException, MalformedURLException, IOException {
		int estadoPostulacion;

		// Secretaria rechaza sin seleccionar documentación
		HtmlPage page = webClientSecretaria
				.getPage("http://localhost:8080/MagisterWeb/app/admin/rechazo?id_documentos1=&id_postulacion="
						+ idPostulacion + "&comentario=");
		// se obtiene página de error.
		assertEquals("Error - Admin SP", page.getTitleText());

		// se obtiene el estado de la postulación
		estadoPostulacion = getEstadoPostulacion(idPostulacion);
		// mantiene el estado
		assertEquals(1, estadoPostulacion);
	}

	@After
	public void tearDown() throws Exception {
		closeDBConnection();
		importDBDump();
	}
}