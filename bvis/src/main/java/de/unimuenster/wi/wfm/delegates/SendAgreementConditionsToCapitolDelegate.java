package de.unimuenster.wi.wfm.delegates;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestServiceBean;
import de.unimuenster.wi.wfm.persistence.CarPool;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequestType;
import de.unimuenster.wi.wfm.sharedLib.data.CarData;
import de.unimuenster.wi.wfm.sharedLib.data.CustomerData;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;
import de.unimuenster.wi.wfm.util.rest.REST;

@Named
public class SendAgreementConditionsToCapitolDelegate implements JavaDelegate {

	@Inject
	private BusinessProcess businessProcess;
	
	@Inject
	RentalAgreementRequestServiceBean rentalAgreementRequestService;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("SendAgreementConditionsToCapitolDelegate");
		
		
		RentalAgreementRequest rentalAgreementRequest = rentalAgreementRequestService.getRentalAgreementRequest((Long) businessProcess.getVariable("rentalAgreementRequestId"));
		
		//build RentalAgreementMessage
		RentalAgreementMessage rentalAgreementMsg = new RentalAgreementMessage();
		rentalAgreementMsg.setRentalAgreementRequestId(rentalAgreementRequest.getId());
		
		CustomerData customer = new CustomerData();
		customer.setName(rentalAgreementRequest.getCustomer().getName());
		customer.setAddress(rentalAgreementRequest.getCustomer().getAddress());
		customer.setPhoneNumber(rentalAgreementRequest.getCustomer().getPhoneNumber());
		customer.setEmail(rentalAgreementRequest.getCustomer().getEmail());
		customer.setCompany(rentalAgreementRequest.getCustomer().getCompany());
		rentalAgreementMsg.setCustomerData(customer);
		
		rentalAgreementMsg.setCarsData(new ArrayList<CarData>());
		for (CarPool carPool : rentalAgreementRequest.getCarPool()) {
			int amount = carPool.getQuantity();
			while (amount > 0) {
				CarData carData = new CarData();
				carData.setHsn(carPool.getCarData().getHsn());
				carData.setTsn(carPool.getCarData().getTsn());
				carData.setLicenseNumber(carPool.getCarData().getLicenseNumber());
				carData.setYear(carPool.getCarData().getYear());
				rentalAgreementMsg.getCarsData().add(carData);
				amount--;
			}
		}
		
		rentalAgreementMsg.setAdditionalInfo(rentalAgreementRequest.getRequirementsOfCustomer());
		
		if (rentalAgreementRequest.getRentalAgreementRequestType() == RentalAgreementRequestType.INDIVIDUAL) {
			//send individual agreement conditions to capitol
			REST.SendIndividualAgreementConditions(rentalAgreementMsg);
		} else {
			//send standard agreement conditions to capitol
			REST.SendStandardAgreementConditions(rentalAgreementMsg);
		}
		
	}

}
