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
import de.unimuenster.wi.wfm.sharedLib.data.AgreementConditions;
import de.unimuenster.wi.wfm.sharedLib.data.CarData;
import de.unimuenster.wi.wfm.sharedLib.data.CustomerData;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceBenefit;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceType;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;
import static org.camunda.spin.Spin.JSON;

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
		
		RentalAgreementMessage rentalAgreementMsg = JSON((String)delegateExecution.getVariable("agreementConditions"))
				.mapTo(RentalAgreementMessage.class);
		
		//first set agreement id, as this is our correlation key in our collaboration
		delegateExecution.setVariable("rentalAgreementRequestIdBVIS", rentalAgreementMsg.getRentalAgreementRequestId());
		
		
		Customer customer = customerService.getCustomerByName((String) variables.get("customer"));
		negotiation.setCustomer(customer);
		negotiation.setAgreementInfo((String) variables.get("agreementConditions"));
		variablesToRemove.put("agreementConditions", variables.get("agreementConditions"));
		negotiation.setConditionsApproved(false);
		
		// Store negotiation
		negotiation = negotiationService.createNegotiationCase(negotiation);
		
		// Publish ID
		delegateExecution.setVariable("caseID", negotiation.getId());
		
		// Remove process vars (not longer needed)
		
		
		//createExampleMessage(delegateExecution, variablesToRemove);
	}

	private void createExampleMessage(DelegateExecution delegateExecution,
			Map<String, Object> variablesToRemove) {
		delegateExecution.removeVariables(variablesToRemove.keySet());
		
		RentalAgreementMessage message = new RentalAgreementMessage();
		message.setRentalAgreementRequestId(1);
		message.setAdditionalInfo("Test");
		message.setBenefits(Arrays.asList(InsuranceBenefit.AWESOME));
		message.setInsuranceType(InsuranceType.PARTIAL);
		CustomerData customerData = new CustomerData();
		customerData.setName("Robert Voscort");
		customerData.setAddress("TestAddress");
		customerData.setCompany("Capitol");
		customerData.setEmail("robert.voscort@capitol.to");
		customerData.setPhoneNumber("0190666666");
		message.setCustomerData(customerData);
		CarData car1 = new CarData();
		car1.setLicenseNumber("TR-L-2608");
		car1.setHsn("489032");
		car1.setTsn("8593402");
		CarData car2 = new CarData();
		car2.setLicenseNumber("MS-X-320");
		car2.setHsn("49302");
		car2.setTsn("520358");
		message.setCarsData(Arrays.asList(car1, car2));
		
		String json = JSON(message).toString();
		
		delegateExecution.setVariable("message", json);
	}

}
