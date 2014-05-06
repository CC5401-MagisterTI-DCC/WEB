package cl.uchile.dcc.cc5401.util.integrationtests;

import java.util.ArrayList;
import java.util.Collection;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import cl.uchile.dcc.cc5401.util.MailHelper;

public class MailHelperMock implements MailHelper {

	public class SentMail{
		private String to;
		private String subject;
		private String body;
		
		public SentMail(String to, String subject, String body){
			setTo(to);
			setSubject(subject);
			setBody(body);
		}
		
		public String getTo() {
			return to;
		}
		public void setTo(String to) {
			this.to = to;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		
	}
	
	private Collection<SentMail> sentMails = new ArrayList<SentMail>();
	
	@Override
	public void sendMail(String[] to, String subject, String body)
			throws AddressException, MessagingException {
		for(String s : to){
			sendMail(s, subject, body);
		}

	}

	@Override
	public void sendMail(String to, String subject, String body)
			throws AddressException, MessagingException {
		sentMails.add(new SentMail(to, subject, body));
	}

}
