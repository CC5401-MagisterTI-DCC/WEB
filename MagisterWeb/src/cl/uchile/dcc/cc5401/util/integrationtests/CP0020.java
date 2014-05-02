package cl.uchile.dcc.cc5401.util.integrationtests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;

/*
 * Una postulación la cual es aprobada en la validación
 * de documentos realizada por la secretaria, aprobado
 * por el Encargado PEG y delegada por el Director del
 * Comité a Votación del Comité y finalmente rechazada
 * por el Director de Comité luego de 3 votos en contra.
 * Finalmente la postulación finaliza con estado Rechazado.
 * 
 */
public class CP0020 extends IntegrationTest{
	
	@Before
	public void setUp() throws Exception {
		
		createDBDump();
		openDBConnection();
		
		createUser("admin", "admin1234", "secretaria", "secretaria@mail.com", "secretaria", "4");
		createUser("admin", "admin1234", "jefepec", "jefepec@mail.com", "jefepec", "5");
		createUser("admin", "admin1234", "coord", "coord@mail.com", "coord", "2");
		createUser("admin", "admin1234", "comisionado1", "comisionado1@mail.com", "comisionado1", "3");
		createUser("admin", "admin1234", "comisionado2", "comisionado2@mail.com", "comisionado2", "3");
		createUser("admin", "admin1234", "comisionado3", "comisionado3@mail.com", "comisionado3", "3");
		
		newPostulante("Jose Tomas", "Pefaur Pumarino", "rut", "6747640-9", "152", true, false, "13/06/2014", "mimail@mail.com", "1234567",
				"Una direccion 879", "Mi grado academico", "Mi institucion", "13/06/2014", "152", "Testing.pdf");
		int idPostulacion = getPostulacionId("6747640-9");
		
		//Secretaria aprueba
	    WebClient webClientSecretaria = logIn("secretaria", "secretaria");
	    pasarRevision(webClientSecretaria, idPostulacion, true);
	    
	    //PEC aprueba
	  	WebClient webClientPEC = logIn("jefepec", "jefepec");
	    pasarValidacion(webClientPEC, idPostulacion, true);
	  		
	    //Coordinador pasa a evaluacion
		WebClient webClientCoord = logIn("coord", "coord");
		pasarConsideracion(webClientCoord, idPostulacion,true);
		
		//Miembro1 comite vota no
		WebClient webClientComisionado1 = logIn("comisionado1", "comisionado1");
		Votar(webClientComisionado1,idPostulacion,false);
				
		//Miembro2 comite vota no
		WebClient webClientComisionado2 = logIn("comisionado2", "comisionado2");
		Votar(webClientComisionado2,idPostulacion,false);
				
		//Miembro3 comite vota no
		WebClient webClientComisionado3 = logIn("comisionado3", "comisionado3");
		Votar(webClientComisionado3,idPostulacion,false);
		
		//Coord dice siguiente paso
		pasarEvaluacion (webClientCoord, idPostulacion);
		
		//Coord decide
		pasarDecision(webClientCoord, idPostulacion, false);	
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