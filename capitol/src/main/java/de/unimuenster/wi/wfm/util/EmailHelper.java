package de.unimuenster.wi.wfm.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.*;

public class EmailHelper {

	private static final String HOST = "smtp.gmail.com";
	private static final String USER = "capitol.wfm@gmail.com";
	private static final String PWD = "Test1234!";
		
	public static Email CreateNewEmail() {
		Email email = new SimpleEmail();
	    email.setHostName(HOST);
	    email.setDebug(true);
	    email.setSmtpPort(465);
	    email.setSslSmtpPort("465");
	    email.setSSLOnConnect(true);
	    email.setAuthenticator(new DefaultAuthenticator(USER, PWD));
	    return email;
	}
	
	public static boolean SendMail(String from, String to, String subject, String msg) {
		Email email = CreateNewEmail();
		try {
			email.setFrom(from);
			email.setSubject(subject);
		    email.setMsg(msg);
		    email.addTo(to);
		    email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
