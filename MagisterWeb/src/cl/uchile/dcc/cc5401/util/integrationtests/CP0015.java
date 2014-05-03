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
 * Ingresar al sistema como Director del Comité, en la 
 * interfaz de este usuario, para una postulación aceptada 
 * por el Encargado del PEG, ingresar un deadline anterior a 
 * la fecha actual, y presionar el botón para Pasar a Evaluación.
 * 
 */
public class CP0015 extends IntegrationTest {

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
	}

	@Test
	public void postulacionEnviadaAVotacionPorCoordinadorTest() throws SQLException,
			FailingHttpStatusCodeException, MalformedURLException, IOException {

		HtmlPage page;
		// %2F = / -> para envío por get
		String datePassed = "01%2F01%2F2000", dateFuture = "01%2F01%2F2099";

		// se envía la postulación al comité con una fecha anterior a la actual.
		page = webClientCoord.getPage("http://localhost:8080/MagisterWeb/"
				+ "app/admin/estado?action=consideracion&id=" + idPostulacion
				+ "&comentario=&deadline=" + datePassed);

		// se obtiene página de error.
		assertEquals("Error - Admin SP", page.getTitleText());

		// se envía la postulación al comité con una fecha posterior a la
		// actual.
		page = webClientCoord.getPage("http://localhost:8080/MagisterWeb/"
				+ "app/admin/estado?action=consideracion&id=" + idPostulacion
				+ "&comentario=&deadline=" + dateFuture);

		// se obtiene página de exito.
		assertEquals("Exito!", page.getTitleText());

		int estadoPostulacion = getEstadoPostulacion(idPostulacion);
		// está en evaluación del comité.
		assertEquals(4, estadoPostulacion);
	}

	@After
	public void tearDown() throws Exception {
		closeDBConnection();
		importDBDump();
	}
}