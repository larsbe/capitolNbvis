package de.unimuenster.wi.wfm.delegates;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.CustomerServiceBean;
import de.unimuenster.wi.wfm.ejb.InsuranceContractServiceBean;
import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.CarData;
import de.unimuenster.wi.wfm.entitiy.CaseStatus;
import de.unimuenster.wi.wfm.entitiy.Customer;
import de.unimuenster.wi.wfm.entitiy.InsuranceBenefit;
import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;

@Named
public class createNewStandardRentalAgreementDelegate implements JavaDelegate{
	
	@EJB
	private InsuranceContractServiceBean insuranceContractService;
	
	@EJB
	private CustomerServiceBean customerServiceBean;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		/*
		// Retrieve Process Vars
		Map<String, Object> variables = delegateExecution.getVariables();
		
		// Content will be deleted at the end ...
		Map<String, Object> variablesToRemove = new HashMap<String, Object>();
		
		//Create CUstomer		
		Customer customer = new Customer();
		if(!checkIfCustomerExists(name){
			
			
			customer.setName(name);
			customer.setAddress(address);
			customer.setCompany(company);
			customer.setEmail(email);
			customer.setPhoneNumber(phoneNumber);
			customerServiceBean.createCustomer(customer);
			
		}
		customer = customerServiceBean.getCustomerByName(name);
		
		//Create CarData
		Collection <CarData> carData;
		
		//Create Benefits
		Collection <InsuranceBenefit> insuranceBenefit;
		
		// Create new Contract
		InsuranceContract contract = new InsuranceContract();
		contract.setCustomer(customer);
		contract.setAdditionalInfo(additionalInfo);
		contract.setBenefits(insuranceBenefit);
		contract.setInsurancePrice(insurancePrice);
		contract.setInsuranceType(insuranceType);
		*/
		
	}
	
	private boolean checkIfCustomerExists(String name) {
	  try{
		  if(customerServiceBean.getCustomerByName(name)!=null)
			  return true;
	  }catch (Exception e){}
	  return false;
	}
}
