package de.unimuenster.wi.wfm.application; 

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Util {

	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;

	protected static final String _SMTP_HOSTNAME = "smtp.gmail.com";
	protected static final String _SENDER = "bvis.service@gmail.com";
	protected static final String _PASSWORD = "workflowmanagement2015";


	
	/**
	 * send Email
	 */
	public static void sendEmail(String subject, String content,
			String recipientEmailAdress) throws MessagingException {

		// Set Properties
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);

		// set receiver, subject, content
		generateMailMessage.addRecipient(Message.RecipientType.TO,
				new InternetAddress(recipientEmailAdress));
		generateMailMessage.addRecipient(Message.RecipientType.CC,
				new InternetAddress("david.jauernig@googlemail.com"));
		generateMailMessage.setSubject(subject);
		generateMailMessage.setContent(content, "text/html");

		// send message
		Transport transport = getMailSession.getTransport("smtp");
		transport.connect("smtp.gmail.com", _SENDER, _PASSWORD);
		transport.sendMessage(generateMailMessage,
				generateMailMessage.getAllRecipients());
		transport.close();
	}
}
