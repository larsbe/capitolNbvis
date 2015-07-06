package de.unimuenster.wi.wfm.delegates;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.InsuranceContractServiceBean;
import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.entitiy.InsuranceStatus;

@Named
public class ActivateContractDelegate implements JavaDelegate {

	@EJB
	private InsuranceContractServiceBean insuranceContractService;

	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		int contractId = Integer.parseInt(delegateExecution.getVariable("contractId").toString());
		//update contract status
		InsuranceContract contract = insuranceContractService.getInsuranceContract(contractId);
		contract.setStatus(InsuranceStatus.ACTIVE);
		contract = insuranceContractService.mergeInsuranceContract(contract);
		System.out.println("Activated contract: " + contract.getStatus().toString());
	}

}
