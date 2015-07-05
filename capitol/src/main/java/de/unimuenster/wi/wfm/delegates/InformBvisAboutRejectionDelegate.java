package de.unimuenster.wi.wfm.delegates;

import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.CaseStatus;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;
import de.unimuenster.wi.wfm.sharedLib.constants.Capitol;
import de.unimuenster.wi.wfm.util.EmailHelper;
import de.unimuenster.wi.wfm.util.rest.REST;

@Named
public class InformBvisAboutRejectionDelegate  implements JavaDelegate {

	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		
		Map<String, Object> variables = delegateExecution.getVariables();
		LiabilityCase claim = liabilityCaseService.getLiabilityCase((Long) variables.get("caseID"));
		
		claim.setStatus(CaseStatus.REJECTED);
		liabilityCaseService.mergeLiabilityCase(claim);
		
		EmailHelper.SendMail("capitol.wfm@gmail.com", "bvis.service@gmail.com", "LiabilityCase#" + claim.getId() + " was rejected", "/EOF");
		
		
		/* REST Test */
		String msg = "{\"messageName\" : \"" + Capitol.REST_MSG_NEW_LIABILITY_CASE + "\",\r\n\"businessKey\" : \"\",\r\n\"correlationKeys\" : {\r\n},\r\n\"processVariables\" : {\r\n    \"contractNo\" : {\"value\" : \"2\", \"type\": \"Long\"},\r\n    \"imageCount\" : {\"value\" : \"2\", \"type\": \"Integer\"},\r\n    \"image_1\": {\"value\" : \"http://capitol.jonasgerlach.de/testImg/TEST_IMG.JPG\", \"type\": \"String\"},\r\n    \"image_2\": {\"value\" : \"http://www.winfridia-breslau.de/_img/content/D20_3861b-1.jpg\", \"type\": \"String\"}\r\n}\r\n}";
		REST.SendMessageToBVIS(msg);
		
		
	}

}
