package de.unimuenster.wi.wfm.web.beans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.*;
import javax.inject.Inject;

import org.camunda.bpm.engine.cdi.BusinessProcess;

import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestServiceBean;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.web.Misc;

@ManagedBean
public class ProposeContractToCustomer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private BusinessProcess businessProcess;
	
	private RentalAgreementRequest rentalAgreementRequest;
	
	@EJB
	private RentalAgreementRequestServiceBean rentalAgreementRequestService;
	
	private long rentalAgreementRequestId;

	public RentalAgreementRequest getRentalAgreementRequest() {
		if (rentalAgreementRequest == null){
			rentalAgreementRequest = rentalAgreementRequestService.getRentalAgreementRequest(getRentalAgreementRequestId());
		}			
		return rentalAgreementRequest;
	}
	
	public long getRentalAgreementRequestId() {
		rentalAgreementRequestId = (Long) businessProcess.getVariable("rentalAgreementRequestId");
		return rentalAgreementRequestId;
	}


	
	public void submit() {
		try {
						
			
		} catch (EJBException e) {
			// add all validation errors
			Misc.ValidateBean(getRentalAgreementRequest());
			
		} 
//			catch (IOException ex){
////			throw new RuntimeException("Cannot complete task", ex);
//		}
	}	
	
}
