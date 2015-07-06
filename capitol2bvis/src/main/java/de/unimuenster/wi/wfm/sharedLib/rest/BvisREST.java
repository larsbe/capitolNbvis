package de.unimuenster.wi.wfm.sharedLib.rest;

import static org.camunda.spin.Spin.JSON;
import de.unimuenster.wi.wfm.sharedLib.constants.Bvis;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;

public class BvisREST {

	public static String LiabilityCaseRejectionInformation(String correlationKey, String decisionNote, String reportUrl) {
		String json = "";
		return json;
	}

	public static String LiabilityCasePaymentInformation(String correlationKey, String decisionNote, String reportUrl, Double payment) {
		String json = "";
		return json;
	}
	
	public static String AgreementConditionsWithInsurance(String correlationKey, RentalAgreementMessage rentalAgreementMsg) {
			CamundaMessage msg = new CamundaMessage(
					Bvis.REST_MSG_AGREEMENT_CONDITIONS_WITH_INSURANCE_BENEFITS);
			String json = JSON(rentalAgreementMsg).toString();
			msg.addCorrelationKey("rentalAgreementRequestId", new CamundaMessageVariable(correlationKey, CamundaVariableType.LONG));
			msg.addProcessVariables(
					"agreementConditions",
					new CamundaMessageVariable(
							json,
							CamundaVariableType.STRING,
							"de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage",
							"application/json"));
			return msg.toString();
		
	}

}
