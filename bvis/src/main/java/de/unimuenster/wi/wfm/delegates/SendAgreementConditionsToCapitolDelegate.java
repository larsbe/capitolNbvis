package de.unimuenster.wi.wfm.delegates;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Named
public class SendAgreementConditionsToCapitolDelegate implements JavaDelegate {

	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("SendAgreementConditionsToCapitolDelegate");
		
		System.out.println("------------------------------------");
		System.out.println("ProcessInstanceId: " + delegateExecution.getProcessInstanceId());
		
		
		
	}

}
