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
					"Dear Andreas,"
					+" <br> attached you will find the rental agreement contract. Please sign it and send it back to us within the next 2 weeks."
					+ " <br> You can use our WebApp in order to upload your signed contract. <br> If you click on the following link, you will get to our upload tool:"
					+ " <br> http://capitol.jonasgerlach.de/customerWebApp/index.php?page=fileupload"
					+ " <br> Sincerly BVIS",
					"a_herm14@uni-muenster.de", attachments);
			
			System.out.println("...EMail sent");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
