package de.unimuenster.wi.wfm.delegates;

import static org.camunda.spin.Spin.JSON;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.CarDataServiceBean;
import de.unimuenster.wi.wfm.ejb.CustomerServiceBean;
import de.unimuenster.wi.wfm.ejb.InsuranceContractServiceBean;
import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.CarData;
import de.unimuenster.wi.wfm.entitiy.CaseStatus;
import de.unimuenster.wi.wfm.entitiy.Customer;
import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.entitiy.InsuranceStatus;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;

@Named
public class CreateNewStandardRentalAgreementDelegate implements JavaDelegate{
	
	@EJB
	private CustomerServiceBean customerService;
	@EJB
	private InsuranceContractServiceBean insuranceContractService;
	@EJB
	private CarDataServiceBean carDataService;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		RentalAgreementMessage rentalAgreementMsg = JSON((String)delegateExecution.getVariable("agreementConditions"))
				.mapTo(RentalAgreementMessage.class);
		
		//first set agreement id, as this is our correlation key in our collaboration
		delegateExecution.setVariable("rentalAgreementRequestIdBVIS", rentalAgreementMsg.getRentalAgreementRequestId());
		
		//create and store entities
		Customer customer = customerService.createCustomerFromMessage(rentalAgreementMsg);
		InsuranceContract contract = insuranceContractService.createInsuranceContractFromMessage(rentalAgreementMsg, customer);
		carDataService.createCarDataFromMessage(rentalAgreementMsg, contract);
		
		//set data according to standard solution
		//for now only arbitrary price
		contract.setInsurancePrice(new BigDecimal("500"));
		//standard contract are immediately active
		contract.setStatus(InsuranceStatus.ACTIVE);
		insuranceContractService.mergeInsuranceContract(contract);
		
		delegateExecution.setVariable("contractId", contract.getId());
		
	}
}
