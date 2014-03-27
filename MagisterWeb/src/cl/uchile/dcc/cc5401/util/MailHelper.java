package cl.uchile.dcc.cc5401.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailHelper {

	private String username;
	private String password;
	private String host;
	private String port;
	private boolean auth;
	private Properties props;

	public MailHelper(String username, String password, String host,
			String port, boolean auth) {
		super();
		this.props = System.getProperties();
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;
		this.auth = auth;
	}


	public void sendMail(String[] to, String subject, String body) throws AddressException, MessagingException{
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", auth);

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(username));
		InternetAddress[] toAddress = new InternetAddress[to.length];

		for( int i = 0; i < to.length; i++ ) {
			toAddress[i] = new InternetAddress(to[i]);
		}

		for( int i = 0; i < toAddress.length; i++) {
			message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		}

		message.setSubject(subject);
		message.setText(body);
		Transport transport = session.getTransport("smtp");
		transport.connect(host, username, password);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
	
	public void sendMail(String to, String subject, String body) throws AddressException, MessagingException{
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(username));
		InternetAddress toAddress = new InternetAddress(to);

		message.addRecipient(Message.RecipientType.TO, toAddress);

		message.setSubject(subject);
		message.setText(body);
		Transport transport = session.getTransport("smtp");
		transport.connect(host, username, password);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

}
