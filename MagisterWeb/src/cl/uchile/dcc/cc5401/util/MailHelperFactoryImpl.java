package cl.uchile.dcc.cc5401.util;

public class MailHelperFactoryImpl implements MailHelperFactory {

	@Override
	public MailHelper makeMailHelper(String username, String password,
			String host, String port, boolean auth) {
		return new MailHelperImpl(username, password, host, port, auth);
	}

}
