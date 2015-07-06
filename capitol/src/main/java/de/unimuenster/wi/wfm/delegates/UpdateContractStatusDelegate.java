package de.unimuenster.wi.wfm.delegates;

import static org.camunda.spin.Spin.JSON;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.InsuranceContractServiceBean;
import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.entitiy.InsuranceStatus;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;

@Named
public class UpdateContractStatusDelegate implements JavaDelegate {
	
	@EJB
	private InsuranceContractServiceBean insuranceContractService;

	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		RentalAgreementMessage rentalAgreementMsg = JSON((String)delegateExecution.getVariable("agreementConditions"))
				.mapTo(RentalAgreementMessage.class);
		
		//update RentalAgreementMessage
		delegateExecution.setVariable("rentalAgreementRequestIdBVIS", rentalAgreementMsg.getRentalAgreementRequestId());
		
		int contractId = Integer.parseInt(delegateExecution.getVariable("contractId").toString());
		System.out.println("Contract id " + contractId);
		boolean approvedByBVIS = Boolean.parseBoolean(delegateExecution.getVariable("conditionsApproved").toString());
		//update contract status
		InsuranceContract contract = insuranceContractService.getInsuranceContract(contractId);
		if (!approvedByBVIS) {
			contract.setStatus(InsuranceStatus.REVISED);
			insuranceContractService.mergeInsuranceContract(contract);
		}
	}

}
