package cl.uchile.dcc.cc5401.util.integrationtests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/*
 * Se simula un postulante que ingresa correctamente una 
 * postulación al sistema. Luego de que este confirma el envío 
 * esta debe tener el estado de En revisión de documentos y estar 
 * disponible para las secretarias.
 * 
 */

public class CP0022 extends IntegrationTest{
	
	@Before
	public void setUp() throws Exception {
		
		createDBDump();
		openDBConnection();
		
		newPostulante("Jose Tomas", "Pefaur Pumarino", "rut", "6747640-9", "152", true, false, "13/06/2014", "mimail@mail.com", "1234567",
				"Una direccion 879", "Mi grado academico", "Mi institucion", "13/06/2014", "152", "Testing.pdf");

	}
	
	@Test
	public void postulacionRechazadaTest() throws SQLException {
		int postulacionId = getPostulacionId("6747640-9");
		int estadoPostulacion = getEstadoPostulacion(postulacionId);
		assertEquals(1, estadoPostulacion);	
	}

	@After
	public void tearDown() throws Exception {
		closeDBConnection();
		importDBDump();
	}
}