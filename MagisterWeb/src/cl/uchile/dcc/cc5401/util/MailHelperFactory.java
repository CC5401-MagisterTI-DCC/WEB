package cl.uchile.dcc.cc5401.util;

public interface MailHelperFactory {
	public MailHelper makeMailHelper(String username, String password, String host,
			String port, boolean auth);
}
