package de.unimuenster.wi.wfm.sharedLib.rest;

import de.unimuenster.wi.wfm.sharedLib.constants.Capitol;

public class CapitolREST {

	/*
	 * Example:
	 * String[] images = {
	 * "http://capitol.jonasgerlach.de/testImg/TEST_IMG.JPG",
	 * "http://www.winfridia-breslau.de/_img/content/D20_3861b-1.jpg" };
	 * System.out.println(CapitolREST.NewLiabilityCase(2L, "Details...", 2000.0,
	 * images));
	 */
	public static String NewLiabilityCase(Long contractNo, String claimDetails,
			Double estimateOfCosts, String[] images) {

		CamundaMessage msg = new CamundaMessage(
				Capitol.REST_MSG_NEW_LIABILITY_CASE);

		msg.addProcessVariables("contractNo", new CamundaMessageVariable(
				contractNo.toString(), CamundaVariableType.LONG));
		msg.addProcessVariables("estimateOfCosts", new CamundaMessageVariable(
				estimateOfCosts.toString(), CamundaVariableType.DOUBLE));
		msg.addProcessVariables("claimDetails", new CamundaMessageVariable(
				claimDetails, CamundaVariableType.STRING));

		if (images.length > 0) {
			msg.addProcessVariables("imageCount", new CamundaMessageVariable(
					String.valueOf(images.length), CamundaVariableType.INTEGER));
			for (int i = 0; i < images.length; i++)
				msg.addProcessVariables("image_" + (i + 1),
						new CamundaMessageVariable(images[i],
								CamundaVariableType.STRING));
		}

		return msg.toString();
	}

}
