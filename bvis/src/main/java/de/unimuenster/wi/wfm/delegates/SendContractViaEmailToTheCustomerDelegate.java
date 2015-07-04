package de.unimuenster.wi.wfm.delegates;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.mail.MessagingException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.application.EMailSender;
import de.unimuenster.wi.wfm.application.PdfGenerator;
import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestServiceBean;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;

@Named
public class SendContractViaEmailToTheCustomerDelegate implements JavaDelegate {

	@EJB
	private RentalAgreementRequestServiceBean rentalAgreementRequestService;

	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("SendContractViaEmailToTheCustomerDelegateDelegate");

		// get needed information
		Map<String, Object> variables = delegateExecution.getVariables();
		RentalAgreementRequest rentalAgreementRequest = rentalAgreementRequestService
				.getRentalAgreementRequest((Long) variables
						.get("rentalAgreementRequestId"));

		// create path for pdf file
		String pdfPath = "c:/_WfM/Contract_" + rentalAgreementRequest.getId()
				+ ".pdf";

		// create pdf
		createPDF(rentalAgreementRequest, pdfPath);

		// send pdf via email
		sendEmail(rentalAgreementRequest, pdfPath);

	}

	private void createPDF(RentalAgreementRequest rentalAgreement,
			String pdfPath) throws InvalidParameterException, IOException {
		// set values
		Map params = new HashMap();
		params.put("rentalAgreementID", rentalAgreement.getId() + "");
		params.put("customerName", rentalAgreement.getCustomer().getName());
		params.put("customerAddress", rentalAgreement.getCustomer().getAddress());
		params.put("contractDetails", "Muss noch im SendContractViaEmailToTheCustomerDelegateDelegate angegeben werden");
		params.put("insuranceDetails", "Muss noch im SendContractViaEmailToTheCustomerDelegateDelegate angegeben werden");

		// create pdf
		PdfGenerator pdfGen = new PdfGenerator();
		pdfGen.doConversion("pdfTemplates/TemplateContract.htm", pdfPath,
				params);

	}

	private void sendEmail(RentalAgreementRequest rentalAgreement,
			String pdfPath) throws MessagingException {

		String[] attachments = new String[1];
		attachments[0] = pdfPath;

		System.out.println("Send Email...");

		EMailSender
				.sendEmail(
						"Rental Agreement",
						"Dear "
								+ rentalAgreement.getCustomer().getName()
								+ ", \r\n\r\n attached you will find the rental agreement. Please send it back to us within the next 2 weeks. \r\n\r\nSincerly BVIS",
						rentalAgreement.getCustomer().getEmail(), attachments);

		System.out.println("...EMail sent");
	}

}
