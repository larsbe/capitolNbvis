package de.unimuenster.wi.wfm.util.rest;

import de.unimuenster.wi.wfm.entitiy.LiabilityCase;
import de.unimuenster.wi.wfm.sharedLib.rest.BvisREST;
import de.unimuenster.wi.wfm.sharedLib.rest.RestHelper;
import de.unimuenster.wi.wfm.util.Constants;

public class REST extends RestHelper {

	public static void SendLiabilityCaseRejectionInformation(LiabilityCase claim) {
		String msg = BvisREST
				.LiabilityCaseRejectionInformation("correlationKey",
						claim.getDecisionNote(), claim.getReportUrl());
		SendMessageToBVIS(msg);
	}

	public static void SendLiabilityCasePaymentInformation(LiabilityCase claim) {
		String msg = BvisREST.LiabilityCasePaymentInformation("correlationKey",
				claim.getDecisionNote(), claim.getReportUrl(),
				claim.getInsuranceSum());
		SendMessageToBVIS(msg);
	}

	private static void SendMessageToBVIS(String msg) {
		RestHelper.SendMessage(Constants.BVIS_CAMUNDA_REST, msg);
	}

}
