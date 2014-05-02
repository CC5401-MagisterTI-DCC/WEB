package cl.uchile.dcc.cc5401.util.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cl.uchile.dcc.cc5401.util.Validacion;

public class ValidacionDocumentosTests {

	@Test
	public void RejectNonPdfDocumentTest() {
		PartStub partStub = PartStub.makeValidDocumentPart();
		partStub.contentType = "application/doc";
		assertTrue(!Validacion.validateDocumentPart(partStub));
		partStub.contentType = "text/plain";
		assertTrue(!Validacion.validateDocumentPart(partStub));
	}

	@Test
	public void AcceptPdfDocumentTest() {
		PartStub partStub = PartStub.makeValidDocumentPart();
		assertTrue(Validacion.validateDocumentPart(partStub));
	}	

	@Test
	public void RejectEmptyDocumentTest() {
		PartStub partStub = PartStub.makeValidDocumentPart();
		partStub.size = 0;
		assertTrue(!Validacion.validateDocumentPart(partStub));
	}	

	@Test
	public void AcceptEmptyDocumentTest() {
		PartStub partStub = PartStub.makeValidDocumentPart();
		assertTrue(Validacion.validateDocumentPart(partStub));
		partStub.size = 8000;
		assertTrue(Validacion.validateDocumentPart(partStub));
	}	

}
