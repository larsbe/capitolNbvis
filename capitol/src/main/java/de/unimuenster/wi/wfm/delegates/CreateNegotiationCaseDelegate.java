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

import com.sun.mail.util.BEncoderStream;

import de.unimuenster.wi.wfm.ejb.CarDataServiceBean;
import de.unimuenster.wi.wfm.ejb.CustomerServiceBean;
import de.unimuenster.wi.wfm.ejb.InsuranceContractServiceBean;
import de.unimuenster.wi.wfm.ejb.NegotiationCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.Customer;
import de.unimuenster.wi.wfm.entitiy.InsuranceBenefitEntity;
import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.entitiy.NegotiationCase;
import de.unimuenster.wi.wfm.entitiy.CarData;
import de.unimuenster.wi.wfm.sharedLib.data.AgreementConditions;
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
	@EJB
	private InsuranceContractServiceBean insuranceContractService;
	@EJB
	private CarDataServiceBean carDataService;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		RentalAgreementMessage rentalAgreementMsg = JSON((String)delegateExecution.getVariable("agreementConditions"))
				.mapTo(RentalAgreementMessage.class);
		
		//first set agreement id, as this is our correlation key in our collaboration
		delegateExecution.setVariable("rentalAgreementRequestIdBVIS", rentalAgreementMsg.getRentalAgreementRequestId());
		
		//create and store entities
		Customer customer = createCustomer(rentalAgreementMsg);
		InsuranceContract contract = createInsuranceContract(rentalAgreementMsg, customer);
		createCarData(rentalAgreementMsg, contract);
		
		delegateExecution.setVariable("contractId", contract.getId());
		
		//createExampleMessage(delegateExecution, variablesToRemove);
	}

	private void createCarData(RentalAgreementMessage rentalAgreementMsg, InsuranceContract contract) {
		for(de.unimuenster.wi.wfm.sharedLib.data.CarData carData : rentalAgreementMsg.getCarsData()) {
			CarData carDataEntity = new CarData();
			carDataEntity.setInsuranceContract(contract);
			carDataEntity.setHsn(carData.getHsn());
			carDataEntity.setTsn(carData.getTsn());
			carDataEntity.setLicenseNumber(carData.getLicenseNumber());
			carDataService.createCarData(carDataEntity);
		}
	}

	private InsuranceContract createInsuranceContract(RentalAgreementMessage rentalAgreementMsg, Customer customer) {
		InsuranceContract insuranceContract = new InsuranceContract();
		insuranceContract.setRentalAgreementIdBVIS(rentalAgreementMsg.getRentalAgreementRequestId());
		insuranceContract.setCustomer(customer);
		insuranceContract.setInsuranceType(rentalAgreementMsg.getInsuranceType());
		insuranceContract.setAdditionalInfo(rentalAgreementMsg.getAdditionalInfo());
		insuranceContract = insuranceContractService.createInsuranceContract(insuranceContract);
		//create benefit entities and add them to insuranceContract
		for (InsuranceBenefit benefit : rentalAgreementMsg.getBenefits()) {
			InsuranceBenefitEntity benefitEntity = new InsuranceBenefitEntity();
			benefitEntity.setInsuranceBenefit(benefit);
			insuranceContractService.addInsuranceBenefitEntities(insuranceContract.getId(), benefitEntity);
		}
		return insuranceContract;
	}

	private Customer createCustomer(RentalAgreementMessage rentalAgreementMsg) {
		CustomerData customerData = rentalAgreementMsg.getCustomerData();
		Customer customer = customerService.getCustomerByName(customerData.getName());
		customer.setAddress(customerData.getAddress());
		customer.setCompany(customerData.getCompany());
		customer.setEmail(customerData.getEmail());
		customer.setPhoneNumber(customerData.getPhoneNumber());
		return customerService.mergeCustomer(customer);
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
		de.unimuenster.wi.wfm.sharedLib.data.CarData car1 = new de.unimuenster.wi.wfm.sharedLib.data.CarData();
		car1.setLicenseNumber("TR-L-2608");
		car1.setHsn("489032");
		car1.setTsn("8593402");
		de.unimuenster.wi.wfm.sharedLib.data.CarData car2 = new de.unimuenster.wi.wfm.sharedLib.data.CarData();
		car2.setLicenseNumber("MS-X-320");
		car2.setHsn("49302");
		car2.setTsn("520358");
		message.setCarsData(Arrays.asList(car1, car2));
		
		String json = JSON(message).toString();
		
		delegateExecution.setVariable("message", json);
	}

}
