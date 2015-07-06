package de.unimuenster.wi.wfm.util.rest;

import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;
import de.unimuenster.wi.wfm.sharedLib.rest.CapitolREST;
import de.unimuenster.wi.wfm.sharedLib.rest.RestHelper;
import de.unimuenster.wi.wfm.util.Constants;

public class REST extends RestHelper {

	public static void SendIndividualAgreementConditions(RentalAgreementMessage rentalAgreementMsg) {
		String msg = CapitolREST.NewNegotiationCase(
				rentalAgreementMsg
		);
		System.out.println(msg);
		SendMessageToCapitol(msg);
	}
	
	public static void SendStandardAgreementConditions(RentalAgreementMessage rentalAgreementMsg) {
		String msg = CapitolREST.NewStandardRentalAgreement(
				rentalAgreementMsg
		);
		System.out.println(msg);
		SendMessageToCapitol(msg);
	}
	
	public static void SendRevisedAgreementConditions(long correlationKey, RentalAgreementMessage rentalAgreementMsg, boolean approved) {
		String msg = CapitolREST.RevisedAgreementConditions(
				correlationKey,
				rentalAgreementMsg,
				approved
		);
		System.out.println(msg);
		SendMessageToCapitol(msg);
	}

	public static void SendMessageToCapitol(String msg) {
		RestHelper.SendMessage(Constants.CAMUNDA_REST_CAPITOL, msg);
	}

}
