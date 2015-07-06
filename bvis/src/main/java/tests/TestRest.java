package tests;

import static org.camunda.spin.Spin.JSON;

import java.util.Arrays;


import de.unimuenster.wi.wfm.sharedLib.constants.*;
import de.unimuenster.wi.wfm.sharedLib.data.CustomerData;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceBenefit;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceType;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;
import de.unimuenster.wi.wfm.util.rest.REST;

public class TestRest {

	public static void main(String arg[]) {
		restTestJSON();
	}
	
	
	private static void restTestJSON(){

	}
	
	
	
	private static void restTestJSONManually(){
		/* REST Test */
		String msg = "{\"messageName\" : \""
				+ Bvis.REST_MSG_AGREEMENT_CONDITIONS_WITH_INSURANCE_BENEFITS +"\","
					
					+"\r\n\"businessKey\" : null,"
					+"\r\n\"correlationKeys\" : "
					+"\r\n{"
					+"\r\n\"rentalAgreementRequestId\":{\"value\":\"8\",\"type\":\"Long\"}"
					+"\r\n},"
					+"\r\n\"processVariables\" :" 
					+"\r\n{"
					+"\r\n\"negoatioationCaseIdCapitol\" : {\"value\" : \"9990123\", \"type\": \"Long\"},"
					+"\r\n\"insuranceBenefits\" : {\"value\" : \"Tolle Insurance Benefits\", \"type\": \"String\"}"
					+"\r\n}"
					+"\r\n}"
					;
				
//		REST.SendMessageToCapitol(msg);
	}

}
