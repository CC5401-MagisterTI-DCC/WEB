package cl.uchile.dcc.cc5401.util.integrationtests;


import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;

/* 
 *  Ingresar al sistema como Secretaria, en la interfaz de validación de documentos, 
 *  para una postulación correctamente ingresada, seleccionar la opción de Validar 
 *  Documentos.
 * 
 */
public class CP0010 extends IntegrationTest {
	
	private int idPostulacion;
	
	@Before
	public void setUp() throws Exception {		
		
		createDBDump();
		openDBConnection();

		// ingreso de postulante
		newPostulante("Jose Tomas", "Pefaur Pumarino", "rut", "6747640-9", "152", true, false, "13/06/2014", "mimail@mail.com", "1234567",
				"Una direccion 879", "Mi grado academico", "Mi institucion", "13/06/2014", "152", "Testing.pdf");
		idPostulacion = getPostulacionId("6747640-9");
		
		// crea un usuario con sesión de secretaria
		createUser("admin", "admin1234", "secretaria", "secretaria@mail.com", "secretaria", "4");
				
		// Secretaria inicia sesión
	    WebClient webClientSecretaria = logIn("secretaria", "secretaria");
	    // Secretaria aprueba
	    pasarRevision(webClientSecretaria, idPostulacion, true);	    
	}
	
	@Test
	public void postulacionValidadaPorSecretariaTest() throws SQLException {	
		int estadoPostulacion = getEstadoPostulacion(idPostulacion);
		
		assertEquals(2, estadoPostulacion);		
	}

	@After
	public void tearDown() throws Exception {
		closeDBConnection();
		importDBDump();
	}
}