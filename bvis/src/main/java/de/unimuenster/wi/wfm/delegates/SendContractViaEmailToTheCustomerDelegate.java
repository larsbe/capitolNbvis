package de.unimuenster.wi.wfm.delegates;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.application.PdfGenerator;
import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestServiceBean;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;

@Named
public class SendContractViaEmailToTheCustomerDelegate implements JavaDelegate {

	@EJB
	private RentalAgreementRequestServiceBean rentalAgreementRequestService;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("SendContractViaEmailToTheCustomerDelegateDelegate");
		
		Map<String, Object> variables = delegateExecution.getVariables();
		RentalAgreementRequest rentalAgreementRequest = rentalAgreementRequestService.getRentalAgreementRequest((Long) variables.get("rentalAgreementRequestId"));
		createPDF(rentalAgreementRequest);
		
	}
	
	private void createPDF(RentalAgreementRequest rentalAgreement){
		// set values
		Map params = new HashMap();
		params.put("rentalAgreementID", rentalAgreement.getId() + "");
		
		// create pdf
		
		try{
			PdfGenerator pdfGen = new PdfGenerator();
			pdfGen.doConversion("pdfTemplates/TemplateContract.htm", "c:/_WfM/pd4ml.pdf", params);
		} catch(IOException e){
			//ToDo handle Exception
			e.printStackTrace();
		}
	}

}
