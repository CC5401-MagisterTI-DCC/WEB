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
 *  Ingresar al sistema como Director del Comité, en la 
 *  interfaz de este usuario, para una postulación aceptada 
 *  por el Encargado del PEG, seleccionar la opción de 
 *  Rechazar, escribiendo un mensaje indicando las razones de esta.
 * 
 */
public class CP0016 extends IntegrationTest {

	private WebClient webClientCoord;
	private int idPostulacion;

	@Before
	public void setUp() throws Exception {

		createDBDump();
		openDBConnection();

		// Se crea un usuario con el rol de secretaria.
		createUser("admin", "admin1234", "secretaria", "secretaria@mail.com",
				"secretaria", "4");

		// Se crea un usuario con el rol de jefe pec.
		createUser("admin", "admin1234", "jefepec", "jefepec@mail.com",
				"jefepec", "5");

		// Se crea un usuario con el rol de coordinador.
		createUser("admin", "admin1234", "coord", "coord@mail.com", "coord",
				"2");

		// Se crea una postulación
		newPostulante("Jose Tomas", "Pefaur Pumarino", "rut", "6747640-9",
				"152", true, false, "13/06/2014", "mimail@mail.com", "1234567",
				"Una direccion 879", "Mi grado academico", "Mi institucion",
				"13/06/2014", "152", "Testing.pdf");

		// se guarda el identificador asignado a la postulación
		idPostulacion = getPostulacionId("6747640-9");

		// Secretaria aprueba
		WebClient webClientSecretaria = logIn("secretaria", "secretaria");
		pasarRevision(webClientSecretaria, idPostulacion, true);

		// PEC aprueba
		WebClient webClientPEC = logIn("jefepec", "jefepec");
		pasarValidacion(webClientPEC, idPostulacion, true);

		// Coordinador inicia sesión
		webClientCoord = logIn("coord", "coord");
		webClientCoord.getOptions().setJavaScriptEnabled(false);
	}

	@Test
	public void postulacionRechazadaPorCoordinadorTest() throws SQLException,
			FailingHttpStatusCodeException, MalformedURLException, IOException {

		HtmlPage page;
		String comentario = "este+es+un+mensaje";

		// se rechaza la postulación indicando un comentario.
		page = webClientCoord.getPage("http://localhost:8080/MagisterWeb/"
				+ "app/admin/estado?id=" + idPostulacion
				+ "&action=decision&detalles=" + comentario
				+ "&decision=rechazado");

		// se obtiene página de exito.
		assertEquals("Exito!", page.getTitleText());

		int estadoResolucion = getEstadoResolucion(idPostulacion);
		// La postulación queda rechazada.
		assertEquals(2, estadoResolucion);
	}

	@After
	public void tearDown() throws Exception {
		closeDBConnection();
		importDBDump();
	}
}