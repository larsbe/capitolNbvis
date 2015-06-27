package de.unimuenster.wi.wfm.delegates;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Named
public class SendContractViaEmailToTheCustomerDelegate implements JavaDelegate {

	
	public void execute(DelegateExecution arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("SendContractViaEmailToTheCustomerDelegateDelegate");
	}

}
