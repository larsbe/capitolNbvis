package tests;

import static org.camunda.spin.Spin.JSON;


import de.unimuenster.wi.wfm.sharedLib.data.CustomerData;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;

public class CreateEampleJSONs {

	public static void main(String args[]){
		
		RentalAgreementMessage message = new RentalAgreementMessage();
		message.setAdditionalInfo("blaues Auto");
		
		CustomerData customerData = new CustomerData();
		customerData.setName("Jörgi");
		customerData.setAddress("TestAddress");
		customerData.setCompany("ERCIS");
		customerData.setEmail("david.jauernig@googlemail.com");
		customerData.setPhoneNumber("0190666666");
		message.setCustomerData(customerData);
		
		
		
		String json = JSON(message).toString();
		System.out.println("............JSON incoming............: " + json);
	}
	
}
