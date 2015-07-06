package de.unimuenster.wi.wfm.sharedLib.rest;

import de.unimuenster.wi.wfm.sharedLib.constants.Capitol;

public class CapitolREST {

	public static String NewLiabilityCase(Long contractNo,
			String claimDetails, Double estimateOfCosts, String[] images) {
		
		CamundaMessage msg = new CamundaMessage(Capitol.REST_MSG_NEW_LIABILITY_CASE);
		
		msg.addProcessVariables("contractNo", new CamundaMessageVariable(contractNo.toString(), CamundaVariableType.LONG));
		msg.addProcessVariables("estimateOfCosts", new CamundaMessageVariable(estimateOfCosts.toString(), CamundaVariableType.DOUBLE));
		msg.addProcessVariables("claimDetails", new CamundaMessageVariable(claimDetails, CamundaVariableType.STRING));
				
		if(images.length > 0) {
			msg.addProcessVariables("imageCount", new CamundaMessageVariable(String.valueOf(images.length), CamundaVariableType.INTEGER));
			for(int i = 0; i < images.length; i++)
				msg.addProcessVariables("image_"+(i+1), new CamundaMessageVariable(images[i], CamundaVariableType.STRING));
		}
	
		return msg.toString();
	}

}
