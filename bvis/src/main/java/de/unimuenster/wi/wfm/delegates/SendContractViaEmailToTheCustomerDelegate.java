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

import de.unimuenster.wi.wfm.ejb.RentalAgreementContractServiceBean;
import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestServiceBean;
import de.unimuenster.wi.wfm.persistence.CarPool;
import de.unimuenster.wi.wfm.persistence.RentalAgreementContract;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.util.EMailSender;
import de.unimuenster.wi.wfm.util.PdfGenerator;

@Named
public class SendContractViaEmailToTheCustomerDelegate implements JavaDelegate {

	@EJB
	private RentalAgreementContractServiceBean rentalAgreementContractService;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("SendContractViaEmailToTheCustomerDelegateDelegate");

		// get business process variables
		Map<String, Object> variables = delegateExecution.getVariables();
		RentalAgreementContract rentalAgreementContract = rentalAgreementContractService
				.getRentalAgreementContract((Long) variables
						.get("contractNoBVIS"));

		// create path for pdf file
		String pdfPath = "c:/_WfM/Contract_" + rentalAgreementContract.getId()
				+ ".pdf";

		// create pdf
		createPDF(rentalAgreementContract, pdfPath);

		// send pdf via email
		sendEmail(rentalAgreementContract, pdfPath);

	}

	private void createPDF(RentalAgreementContract rentalAgreementContract,
			String pdfPath) throws InvalidParameterException, IOException {
		// set values
		Map params = new HashMap();
		params.put("rentalAgreementID", rentalAgreementContract.getId() + "");
		params.put("customerName", rentalAgreementContract.getCustomer().getName());
		params.put("customerAddress", rentalAgreementContract.getCustomer().getAddress());
		
		/* Contract Details */
		StringBuilder sbContractDetails = new StringBuilder();
		sbContractDetails.append(rentalAgreementContract.getRentalAgreementContractData());
		sbContractDetails.append("<br/>");
		sbContractDetails.append("<br/>");
		sbContractDetails.append("Cars: ");
		sbContractDetails.append("<br/>");
		for(CarPool c : rentalAgreementContract.getRentalAgreementRequest().getCarPool()) {
			sbContractDetails.append("- " + c.getCarData().getName());
			sbContractDetails.append(" ("+c.getCarData().getLicenseNumber()+")");
			sbContractDetails.append("<br/>");
		}
		params.put("contractDetails", sbContractDetails.toString());
		
		//params.put("insuranceDetails", rentalAgreementContract.getInsuranceBenefits());

		// create pdf
		PdfGenerator pdfGen = new PdfGenerator();
		pdfGen.doConversion("pdfTemplates/TemplateContract.htm", pdfPath,
				params);

	}

	private void sendEmail(RentalAgreementContract rentalAgreementContract,
			String pdfPath) throws MessagingException {

		String[] attachments = new String[1];
		attachments[0] = pdfPath;

		System.out.println("Send Email...");

		EMailSender
				.sendEmail(
						"Rental Agreement",
						"Dear "
								+ rentalAgreementContract.getCustomer().getName()
								+ ",  <br> attached you will find the rental agreement contract. Please sign it and send it back to us within the next 2 weeks."
								+ " <br> You can use our WebApp in order to upload your signed contract. <br> If you click on the following link, you will get to our upload tool:"
								+ " <br> http://capitol.jonasgerlach.de/customerWebApp/index.php?page=fileupload "
								+ " <br> Sincerly BVIS",
								rentalAgreementContract.getCustomer().getEmail(), attachments);

		System.out.println("...EMail sent");
	}

}
