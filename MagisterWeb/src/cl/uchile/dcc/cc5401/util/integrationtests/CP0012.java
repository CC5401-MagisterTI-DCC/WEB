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
 *  Ingresar al sistema como Secretaria, en la interfaz de validación de documentos, 
 *  para una postulación correctamente ingresada, seleccionar la opción de Rechazar 
 *  Documento especificando algún documento como erróneo, con un mensaje indicando las razones.
 * 
 */
public class CP0012 extends IntegrationTest {
	
	private WebClient webClientSecretaria;
	private int idPostulacion;
	
	@Before
	public void setUp() throws Exception {
			
		createDBDump();
		openDBConnection();

		// Ingreso de postulante
		newPostulante("Jose Tomas", "Pefaur Pumarino", "rut", "6747640-9", "152", true, false, "13/06/2014", "mimail@mail.com", "1234567",
				"Una direccion 879", "Mi grado academico", "Mi institucion", "13/06/2014", "152", "Testing.pdf");
		idPostulacion = getPostulacionId("6747640-9");
		
		// Se crea un usuario con el rol de secretaria
		createUser("admin", "admin1234", "secretaria", "secretaria@mail.com", "secretaria", "4");
				
		// Secretaria inicia sesión
	    webClientSecretaria = logIn("secretaria", "secretaria");
	}
	
	@Test
	public void postulacionRechazadaTest() throws SQLException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		String comentario = "se+encontraron+errores.+Corregir+por+favor";
		String documentos = "3+4+";
		
		//Secretaria rechaza seleccionando algun documento
		HtmlPage page = webClientSecretaria.getPage("http://localhost:8080/MagisterWeb/app/admin/rechazo?id_documentos1=" + documentos + "&id_postulacion=" + idPostulacion + "&comentario=" + comentario);
		
		// se obtiene página de éxito.
		assertEquals("Exito!", page.getTitleText());
		
		//TODO: validar la llamada para enviar mails.
	}

	@After
	public void tearDown() throws Exception {
		closeDBConnection();
		importDBDump();
	}
}