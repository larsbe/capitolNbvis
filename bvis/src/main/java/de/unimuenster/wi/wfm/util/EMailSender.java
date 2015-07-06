package de.unimuenster.wi.wfm.util; 

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EMailSender {

	protected static final String _SMTP_HOSTNAME = "smtp.gmail.com";
	protected static final String _SENDER = "bvis.service@gmail.com";
	protected static final String _PASSWORD = "workflowmanagement2015";


	
	/**
	 * send Email
	 */
	public static void sendEmail(String subject, String content,
			String recipientEmailAdress, String[] attachments) throws MessagingException {

		Properties mailServerProperties;		
		// Set Properties
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		
		
		// crate new session
		Session session;
		session = Session.getDefaultInstance(mailServerProperties, null);
		
		// create new e-mail message
		MimeMessage message;
		message = new MimeMessage(session);
		message.addRecipient(Message.RecipientType.TO,
				new InternetAddress(recipientEmailAdress));
		message.addRecipient(Message.RecipientType.CC,
				new InternetAddress("david.jauernig@googlemail.com"));
		message.setSubject(subject);
		
		// create message multipart, which consists of several bodyparts
        Multipart multipart = new MimeMultipart();
        message.setContent(multipart);

		// create bodyPart #1 for body message
		MimeBodyPart messageBodyPart;
		messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(content, "text/html");
		multipart.addBodyPart(messageBodyPart);
		
		// create bodyPart #2..n for attachments
        if (attachments != null && attachments.length > 0) {
            for (String filePath : attachments) {
                MimeBodyPart attachPart = new MimeBodyPart();
 
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(attachPart);
            }
        } 
		
		// send message
		Transport transport = session.getTransport("smtp");
		transport.connect("smtp.gmail.com", _SENDER, _PASSWORD);
		transport.sendMessage(message,
				message.getAllRecipients());
		transport.close();
	}
}
