package cl.uchile.dcc.cc5401.util.integrationtests;

import cl.uchile.dcc.cc5401.util.MailHelper;
import cl.uchile.dcc.cc5401.util.MailHelperFactory;

public class MailHelperFactoryMock implements MailHelperFactory {

	private MailHelperMock mailHelperMock = new MailHelperMock();
	
	public MailHelperMock getMailHelperMock() {
		return mailHelperMock;
	}

	@Override
	public MailHelper makeMailHelper(String username, String password,
			String host, String port, boolean auth) {
		return mailHelperMock;
	}

}
