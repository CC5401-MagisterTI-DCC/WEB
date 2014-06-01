package cl.uchile.dcc.cc5401.util;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface MailHelper {
	public void sendMail(String[] to, String subject, String body) throws AddressException, MessagingException;	
	public void sendMail(String to, String subject, String body) throws AddressException, MessagingException;
}
