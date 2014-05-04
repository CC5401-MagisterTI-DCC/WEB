package cl.uchile.dcc.cc5401.util.integrationtests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/* 
 *   Ingresar al sistema como Miembro del Comité, 
 *   en la interfaz de este usuario, para una postulación 
 *   aceptada por el Director del Comité, votar Si agregando 
 *   algún comentario, después repetir votando No 
 *   agregando un comentario.
 * 
 */
public class CP0017 extends IntegrationTest {

	private WebClient webClientCom;
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

		// Se crea un usuario con el rol de comisionado.
		createUser("admin", "admin1234", "com", "com@mail.com", "com", "3");

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

		// Coordinador aprueba
		WebClient webClientCoord = logIn("coord", "coord");
		pasarConsideracion(webClientCoord, idPostulacion, false);

		// Comisionado inicia sesión
		webClientCom = logIn("com", "com");
	}

	/**
	 * devuelve la cantidad votos SI asociados a una postulación
	 * 
	 * @param idPostulacion
	 *            identificador de postulacion
	 * @throws SQLException
	 * */
	private int cantidadVotosSi(int idPostulacion) throws SQLException {

		Statement statement = connect.createStatement();
		ResultSet cantidadVotos = statement
				.executeQuery("SELECT COUNT(*) AS result " + "FROM votacion "
						+ "WHERE id_postulacion = " + idPostulacion
						+ " AND id_tipo_voto = 1");
		cantidadVotos.next();

		return cantidadVotos.getInt("result");
	}

	/**
	 * devuelve la cantidad votos NO asociados a una postulación
	 * 
	 * @param idPostulacion
	 *            identificador de postulacion
	 * @throws SQLException
	 * */
	private int cantidadVotosNo(int idPostulacion) throws SQLException {

		Statement statement = connect.createStatement();
		ResultSet cantidadVotos = statement
				.executeQuery("SELECT COUNT(*) AS result " + "FROM votacion "
						+ "WHERE id_postulacion = " + idPostulacion
						+ " AND id_tipo_voto = 2");
		cantidadVotos.next();

		return cantidadVotos.getInt("result");
	}

	@Test
	public void postulacionRechazadaPorCoordinadorTest() throws SQLException,
			FailingHttpStatusCodeException, MalformedURLException, IOException {

		HtmlPage page;
		String comentario = "este+es+un+mensaje";

		// comisionado vota Si agregando un comentario
		page = webClientCom.getPage("http://localhost:8080/MagisterWeb/"
				+ "app/admin/voto?id=" + idPostulacion + "&comentario="
				+ comentario + "&decision=aceptado");

		// se obtiene página de exito.
		assertEquals("Exito!", page.getTitleText());
		// cantidad de votos SI asociados a la postulación
		assertEquals(1, cantidadVotosSi(idPostulacion));

		// comisionado vota No agregando un comentario
		page = webClientCom.getPage("http://localhost:8080/MagisterWeb/"
				+ "app/admin/voto?id=" + idPostulacion + "&comentario="
				+ comentario + "&decision=rechazado");

		// se obtiene página de exito.
		assertEquals("Error - Admin SP", page.getTitleText());
		// cantidad de votos NO asociados a la postulación
		assertEquals(1, cantidadVotosNo(idPostulacion));
	}

	@After
	public void tearDown() throws Exception {
		closeDBConnection();
		importDBDump();
	}
}