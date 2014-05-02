package cl.uchile.dcc.cc5401.util.integrationtests;


import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;

/* 
 * Una postulación la cual es aprobada en la validación
 * de documentos realizada por la secretaria, aprobado por
 * el Encargado PEG y rechazado por el Director del Comité.
 * Finalmente la postulación finaliza con estado Rechazado.
 * 
 */
public class CP0019 extends IntegrationTest{
	

	@Before
	public void setUp() throws Exception {
		
		createDBDump();
		openDBConnection();
		
		createUser("admin", "admin1234", "secretaria", "secretaria@mail.com", "secretaria", "4");
		createUser("admin", "admin1234", "jefepec", "jefepec@mail.com", "jefepec", "5");
		createUser("admin", "admin1234", "coord", "coord@mail.com", "coord", "2");
		
		newPostulante("Jose Tomas", "Pefaur Pumarino", "rut", "6747640-9", "152", true, false, "13/06/2014", "mimail@mail.com", "1234567",
				"Una direccion 879", "Mi grado academico", "Mi institucion", "13/06/2014", "152", "Testing.pdf");
		int idPostulacion = getPostulacionId("6747640-9");
		
		//Secretaria aprueba
	    WebClient webClientSecretaria = logIn("secretaria", "secretaria");
	    pasarRevision(webClientSecretaria, idPostulacion, true);
	    
	    //PEC aprueba
	  	WebClient webClientPEC = logIn("jefepec", "jefepec");
	    pasarValidacion(webClientPEC, idPostulacion, true);
	  		
	  	//Coordinador rechaza
		WebClient webClientCoord = logIn("coord", "coord");
	    pasarConsideracion(webClientCoord, idPostulacion,false);
	}
	
	@Test
	public void postulacionRechazadaTest() throws SQLException {	
		int postulacionId = getPostulacionId("6747640-9");
		int estadoResolucion = getEstadoResolucion(postulacionId);
		assertEquals(2, estadoResolucion);		
	}

	@After
	public void tearDown() throws Exception {
		closeDBConnection();
		importDBDump();
	}
}