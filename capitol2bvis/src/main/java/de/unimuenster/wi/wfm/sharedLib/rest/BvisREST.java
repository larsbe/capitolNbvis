package de.unimuenster.wi.wfm.sharedLib.rest;

import static org.camunda.spin.Spin.JSON;
import de.unimuenster.wi.wfm.sharedLib.constants.Bvis;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;

public class BvisREST {

	public static String LiabilityCaseRejectionInformation(Long claimIdBVIS,
			String decisionNote, String reportUrl) {
		CamundaMessage msg = new CamundaMessage(Bvis.REST_MSG_CLAIM_INFORMATION);
		msg.addCorrelationKey("claimIdBVIS", new CamundaMessageVariable(
				claimIdBVIS.toString(), CamundaVariableType.LONG));
		msg.addProcessVariables("claimCovered", new CamundaMessageVariable(
				Boolean.toString(false), CamundaVariableType.BOOLEAN));
		msg.addProcessVariables("insuranceSum", new CamundaMessageVariable(
				Double.toString(0.0), CamundaVariableType.DOUBLE));
		msg.addProcessVariables("rejectionInfo", new CamundaMessageVariable(
				decisionNote, CamundaVariableType.STRING));
		msg.addProcessVariables("reportUrl", new CamundaMessageVariable(
				reportUrl, CamundaVariableType.STRING));
		return msg.toString();
	}

	public static String LiabilityCasePaymentInformation(Long claimIdBVIS,
			String decisionNote, String reportUrl, Double payment) {
		CamundaMessage msg = new CamundaMessage(Bvis.REST_MSG_CLAIM_INFORMATION);
		msg.addCorrelationKey("claimIdBVIS", new CamundaMessageVariable(
				claimIdBVIS.toString(), CamundaVariableType.LONG));
		msg.addProcessVariables("claimCovered", new CamundaMessageVariable(
				Boolean.toString(true), CamundaVariableType.BOOLEAN));
		msg.addProcessVariables("insuranceSum", new CamundaMessageVariable(
				Double.toString(payment), CamundaVariableType.DOUBLE));
		msg.addProcessVariables("rejectionInfo", new CamundaMessageVariable(
				decisionNote, CamundaVariableType.STRING));
		msg.addProcessVariables("reportUrl", new CamundaMessageVariable(
				reportUrl, CamundaVariableType.STRING));
		return msg.toString();
	}

	public static String AgreementConditionsWithInsurance(
			String correlationKey, RentalAgreementMessage rentalAgreementMsg) {
		CamundaMessage msg = new CamundaMessage(
				Bvis.REST_MSG_AGREEMENT_CONDITIONS_WITH_INSURANCE_BENEFITS);
		String json = JSON(rentalAgreementMsg).toString();
		msg.addCorrelationKey("rentalAgreementRequestId",
				new CamundaMessageVariable(correlationKey,
						CamundaVariableType.LONG));
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
