package de.unimuenster.wi.wfm.sharedLib.rest;

import static org.camunda.spin.Spin.JSON;
import de.unimuenster.wi.wfm.sharedLib.constants.Capitol;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;

public class CapitolREST {

	public static String RevisedAgreementConditions(
			Long correlationKey_rentalAgreementRequestIdBVIS,
			RentalAgreementMessage rentalAgreementMsg,
			Boolean conditionsApproved) {
		CamundaMessage msg = new CamundaMessage(
				Capitol.REST_MSG_REVISED_AGREEMENTL_CONDITIONS);
		msg.addCorrelationKey("rentalAgreementRequestIdBVIS",
				new CamundaMessageVariable(
						correlationKey_rentalAgreementRequestIdBVIS.toString(),
						CamundaVariableType.LONG));
		String json = JSON(rentalAgreementMsg).toString();
		msg.addProcessVariables(
				"agreementConditions",
				new CamundaMessageVariable(
						json,
						CamundaVariableType.STRING,
						"de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage",
						"application/json"));
		msg.addProcessVariables("conditionsApproved",
				new CamundaMessageVariable(conditionsApproved.toString(),
						CamundaVariableType.BOOLEAN));
		return msg.toString();
	}

	public static String ContractStatus(
			Long correlationKey_rentalAgreementRequestIdBVIS, Boolean signed) {
		CamundaMessage msg = new CamundaMessage(
				Capitol.REST_MSG_CONTRACT_STATUS);
		msg.addCorrelationKey("rentalAgreementRequestIdBVIS",
				new CamundaMessageVariable(
						correlationKey_rentalAgreementRequestIdBVIS.toString(),
						CamundaVariableType.LONG));
		msg.addProcessVariables("contractSigned",
				new CamundaMessageVariable(signed.toString(),
						CamundaVariableType.BOOLEAN));
		return msg.toString();
	}

	public static String NewStandardRentalAgreement(
			RentalAgreementMessage rentalAgreementMsg) {
		CamundaMessage msg = new CamundaMessage(
				Capitol.REST_MSG_NEW_STANDARD_RENTAL_AGREEMENT);
		String json = JSON(rentalAgreementMsg).toString();
		msg.addProcessVariables(
				"agreementConditions",
				new CamundaMessageVariable(
						json,
						CamundaVariableType.STRING,
						"de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage",
						"application/json"));
		return msg.toString();
	}

	public static String NewNegotiationCase(
			RentalAgreementMessage rentalAgreementMsg) {
		CamundaMessage msg = new CamundaMessage(
				Capitol.REST_MSG_NEW_AGREEMENT_CONDITIONS);
		String json = JSON(rentalAgreementMsg).toString();
		msg.addProcessVariables(
				"agreementConditions",
				new CamundaMessageVariable(
						json,
						CamundaVariableType.STRING,
						"de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage",
						"application/json"));
		return msg.toString();
	}

	public static String NewLiabilityCase(Long contractNo, String claimDetails,
			Double estimateOfCosts, String[] imageUrls, String licenseNumber, Long bvisCaseID) {
		CamundaMessage msg = new CamundaMessage(
				Capitol.REST_MSG_NEW_LIABILITY_CASE);
		msg.addProcessVariables("contractNo", new CamundaMessageVariable(
				contractNo.toString(), CamundaVariableType.LONG));
		msg.addProcessVariables("licenseNumber", new CamundaMessageVariable(
				licenseNumber.toString(), CamundaVariableType.STRING));
		msg.addProcessVariables("bvisCaseID", new CamundaMessageVariable(
				bvisCaseID.toString(), CamundaVariableType.LONG));
		msg.addProcessVariables("estimateOfCosts", new CamundaMessageVariable(
				estimateOfCosts.toString(), CamundaVariableType.DOUBLE));
		msg.addProcessVariables("claimDetails", new CamundaMessageVariable(
				claimDetails, CamundaVariableType.STRING));
		if (imageUrls.length > 0) {
			msg.addProcessVariables("imageCount", new CamundaMessageVariable(
					String.valueOf(imageUrls.length),
					CamundaVariableType.INTEGER));
			for (int i = 0; i < imageUrls.length; i++)
				msg.addProcessVariables("image_" + (i + 1),
						new CamundaMessageVariable(imageUrls[i],
								CamundaVariableType.STRING));
		}
		return msg.toString();
	}

}
