package de.unimuenster.wi.wfm.util.rest;

import de.unimuenster.wi.wfm.entitiy.LiabilityCase;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;
import de.unimuenster.wi.wfm.sharedLib.rest.BvisREST;
import de.unimuenster.wi.wfm.sharedLib.rest.RestHelper;
import de.unimuenster.wi.wfm.util.Constants;

public class REST extends RestHelper {

	public static void SendLiabilityCaseRejectionInformation(LiabilityCase claim) {
		String msg = BvisREST
				.LiabilityCaseRejectionInformation(claim.getBvisCaseID(),
						claim.getDecisionNote(), claim.getReportUrl());
		SendMessageToBVIS(msg);
	}

	public static void SendLiabilityCasePaymentInformation(LiabilityCase claim) {
		String msg = BvisREST.LiabilityCasePaymentInformation(claim.getBvisCaseID(),
				claim.getDecisionNote(), claim.getReportUrl(),
				claim.getInsuranceSum());
		SendMessageToBVIS(msg);
	}
	
	public static void SendAgreementConditionsWithInsurance(RentalAgreementMessage rentalAgreementMsg) {
		String msg = BvisREST.AgreementConditionsWithInsurance(
				Long.valueOf(rentalAgreementMsg.getRentalAgreementRequestId()).toString(),
				rentalAgreementMsg
		);
		System.out.println(msg);
		SendMessageToBVIS(msg);
	}

	private static void SendMessageToBVIS(String msg) {
		RestHelper.SendMessage(Constants.BVIS_CAMUNDA_REST, msg);
	}

}
