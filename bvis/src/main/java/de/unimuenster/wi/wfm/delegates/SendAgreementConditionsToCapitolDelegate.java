package de.unimuenster.wi.wfm.delegates;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Named
public class SendAgreementConditionsToCapitolDelegate implements JavaDelegate {

	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("SendAgreementConditionsToCapitolDelegate");
		
		
		
		
	}

}
