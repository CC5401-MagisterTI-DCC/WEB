package cl.uchile.dcc.cc5401.util.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.http.Part;

public class PartStub implements Part{

	String contentType;
	long size;
	
	public static PartStub makeValidDocumentPart(){
		PartStub partStub = new PartStub();
		partStub.contentType = "application/pdf";
		partStub.size = 6550;
		return partStub;
	}
	
	@Override
	public void delete() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public String getHeader(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> getHeaderNames() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> getHeaders(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getSize() {
		return size;
	}

	@Override
	public void write(String arg0) throws IOException {
		throw new UnsupportedOperationException();
	}
	
}
