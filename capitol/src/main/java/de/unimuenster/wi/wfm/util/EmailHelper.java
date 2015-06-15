package de.unimuenster.wi.wfm.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.*;

public class EmailHelper {

	private static final String HOST = "cloud.webartifact.de";
	private static final String USER = "wfm.capitol@jonasgerlach.de";
	private static final String PWD = "test1234";
		
	public static Email CreateNewEmail() {
		Email email = new SimpleEmail();
	    email.setHostName(HOST);
	    email.setDebug(true);
	    email.setSmtpPort(25);
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
