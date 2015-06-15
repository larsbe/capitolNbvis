package de.unimuenster.wi.wfm.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

//import de.unimuenster.wi.wfm.util.EmailHelper;

public class AddMetaDataDelegate implements JavaDelegate {

	public void execute(DelegateExecution arg0) throws Exception {

		System.out.println("Meta data added ...");
		
		//EmailHelper.SendMail("backoffice@camunda.capitol.de", "mail@jonasgerlach.de", "Test-Mail", "MetaData added");

	}

}
