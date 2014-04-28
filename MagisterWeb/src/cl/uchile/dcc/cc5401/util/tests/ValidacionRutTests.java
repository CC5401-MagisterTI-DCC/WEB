package cl.uchile.dcc.cc5401.util.tests;

import cl.uchile.dcc.cc5401.util.Validacion;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidacionTests {

	@Test
	public void basicRutTest() {
		assertTrue(Validacion.validateRut("18013766-1"));
		assertTrue(!Validacion.validateRut("18013766-k"));
	}
	
	@Test
	public void plainWrongRutTest(){
		assertTrue(!Validacion.validateRut("8000"));
		assertTrue(!Validacion.validateRut("holamundo"));
	}
	
	@Test
	public void badFormatTest(){
		assertTrue(!Validacion.validateRut("18.013.766-1"));
		assertTrue(!Validacion.validateRut("180137661"));
	}
	
	@Test
	public void rutWithLetterTest(){
		assertTrue(Validacion.validateRut("17698922-k"));
		assertTrue(!Validacion.validateRut("17698922-3"));
	}

}
