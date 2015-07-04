package de.unimuenster.wi.wfm.delegates;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import de.unimuenster.wi.wfm.ejb.CustomerServiceBean;
import de.unimuenster.wi.wfm.ejb.NegotiationCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.Customer;
import de.unimuenster.wi.wfm.entitiy.NegotiationCase;
import de.unimuenster.wi.wfm.sharedLib.data.CarData;
import de.unimuenster.wi.wfm.sharedLib.data.CustomerData;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceBenefit;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceType;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;

@Named
public class CreateNegotiationCaseDelegate implements JavaDelegate {

	@EJB
	private NegotiationCaseServiceBean negotiationService;
	@EJB
	private CustomerServiceBean customerService;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		// Retrieve Process Vars
		Map<String, Object> variables = delegateExecution.getVariables();
		
		// Content will be deleted at the end ...
		Map<String, Object> variablesToRemove = new HashMap<String, Object>();
		
		// Create new Case
		NegotiationCase negotiation = new NegotiationCase();
		
//		Customer customer = customerService.getCustomerByName((String) variables.get("customer"));
//		negotiation.setCustomer(customer);
//		negotiation.setAgreementInfo((String) variables.get("agreementConditions"));
//		variablesToRemove.put("agreementConditions", variables.get("agreementConditions"));
//		negotiation.setConditionsApproved(false);
//		
//		// Store negotiation
//		negotiation = negotiationService.createNegotiationCase(negotiation);
//		
//		// Publish ID
//		delegateExecution.setVariable("caseID", negotiation.getId());
		
		// Remove process vars (not longer needed)
		delegateExecution.removeVariables(variablesToRemove.keySet());
		
//		RentalAgreementMessage message = new RentalAgreementMessage();
//		message.setRentalAgreementRequestId(1);
//		message.setAdditionalInfo("Test");
//		CustomerData customerData = new CustomerData();
//		customerData.setName("Robert Voscort");
//		customerData.setAddress("TestAddress");
//		customerData.setCompany("Capitol");
//		customerData.setEmail("robert.voscort@capitol.to");
//		customerData.setPhoneNumber("0190666666");
//		message.setCustomerData(customerData);
//		CarData cars = new CarData();
//		cars.setAmount(8);
//		cars.setInsuranceType(InsuranceType.PARTIAL);
//		cars.setHsntsn("XXX");
//		cars.setBenefits(Arrays.asList(InsuranceBenefit.AWESOME));
//		message.setCarsData(cars);
//		
//		ObjectValue caseValue = Variables.objectValue(message)
//				   .serializationDataFormat(Variables.SerializationDataFormats.JSON)
//				   .create();
//		
//		delegateExecution.setVariable("message", caseValue);
	}

}
