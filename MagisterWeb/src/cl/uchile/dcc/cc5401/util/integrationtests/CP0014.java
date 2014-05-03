package cl.uchile.dcc.cc5401.util.integrationtests;


import static org.junit.Assert.*;

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
 *  Ingresar al sistema como Encargado del PEG, en la interfaz de este usuario, 
 *  para una postulación con los documentos validados, seleccionar la opción de Aceptar,
 *   escribiendo un mensaje indicando las razones de esta.
 * 
 */
public class CP0014 extends IntegrationTest {
	
	private WebClient webClientJefePEC;
	private int idPostulacion;
	
	@Before
	public void setUp() throws Exception {
			
		createDBDump();
		openDBConnection();

		// Ingreso de postulante
		newPostulante("Jose Tomas", "Pefaur Pumarino", "rut", "6747640-9", "152", true, false, "13/06/2014", "mimail@mail.com", "1234567",
				"Una direccion 879", "Mi grado academico", "Mi institucion", "13/06/2014", "152", "Testing.pdf");
		idPostulacion = getPostulacionId("6747640-9");
		
		// Se crea un usuario con el rol de secretaria.
		createUser("admin", "admin1234", "secretaria", "secretaria@mail.com", "secretaria", "4");
		// Se crea un usuario con el rol de jefe pec
		createUser("admin", "admin1234", "jefepec", "jefepec@mail.com", "jefepec", "5");
		
		// Secretaria inicia sesión
	    WebClient webClientSecretaria = logIn("secretaria", "secretaria");
	    // Secretaria aprueba documentación
	    pasarRevision(webClientSecretaria, idPostulacion, true);	 
	    
		// Jefe PEC inicia sesión
	    webClientJefePEC = logIn("jefepec", "jefepec");
	}
	
	@Test
	public void postulacionRechazadaTest() throws SQLException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		String comentario = "poner+atención+en+calificaciones";
		
		//Secretaria rechaza seleccionando algun documento
		HtmlPage page = webClientJefePEC.getPage("http://localhost:8080/MagisterWeb/app/admin/estado?action=validacion&id=" + idPostulacion + "&comentario=" + comentario);
		
		// se obtiene página de éxito.
		assertEquals("Exito!", page.getTitleText());		
	}

	@After
	public void tearDown() throws Exception {
		closeDBConnection();
		importDBDump();
	}
}