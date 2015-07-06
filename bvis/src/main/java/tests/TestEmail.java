package tests;

import java.util.HashMap;

import javax.mail.MessagingException;

import de.unimuenster.wi.wfm.application.EMailSender;
import de.unimuenster.wi.wfm.application.PdfGenerator;

public class TestEmail {

	public static void main(String[] args) {
		
		String conctractID = "000000123";
		String pdfPath = "c:/_WfM/contract" + conctractID + ".pdf";
		
		// generate pdf
		try {
			System.out.println("Generate pdf...");
			PdfGenerator pdfGenerator = new PdfGenerator();
			HashMap params = new HashMap();
			params.put("rentalAgreementID", conctractID);
			
			pdfGenerator.doConversion("pdfTemplates\\TemplateContract.htm", pdfPath, params);
			System.out.println("...pdf generated");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			String[] attachments = new String[1];
			attachments[0] = pdfPath;
			
			System.out.println("Send Email...");
			
			EMailSender.sendEmail(
					"Rental Agreement",
					"Please sign it",
					"a_herm14@uni-muenster.de", attachments);
			
			System.out.println("...EMail sent");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
