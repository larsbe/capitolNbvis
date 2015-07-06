package de.unimuenster.wi.wfm.delegates;

import static org.camunda.spin.Spin.JSON;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.CarDataServiceBean;
import de.unimuenster.wi.wfm.ejb.CustomerServiceBean;
import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestServiceBean;
import de.unimuenster.wi.wfm.persistence.CarData;
import de.unimuenster.wi.wfm.persistence.CarPool;
import de.unimuenster.wi.wfm.persistence.Customer;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequestType;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;

@Named
public class CreateNewRentalAgreementRequestDelegate implements JavaDelegate {
	
	@Inject
	private BusinessProcess businessProcess;
	@EJB
	private RentalAgreementRequestServiceBean rentalAgreementRequestService;
	@EJB
	private CustomerServiceBean customerService;
	@EJB
	private CarDataServiceBean carDataService;

	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("CreateNewRentalAgreementRequestDelegateDelegate");

//		RentalAgreementMessage rentalAgreementMsg = JSON((String)delegateExecution.getVariable("agreementConditions"))
//				.mapTo(RentalAgreementMessage.class);
				
		//create and store entities
//		Customer customer = customerService.createObjectFromMessage(rentalAgreementMsg);
//		Collection<CarData> carsData = carDataService.createCarDataFromMessage(rentalAgreementMsg);
//		RentalAgreementRequest req = rentalAgreementRequestService.createObjectFromMessage(rentalAgreementMsg, customer, carsData);
		
		
		//create and store entities
		Customer customer = new Customer();		
		customer.setAddress((String) businessProcess.getVariable("address"));
		customer.setCompany((String) businessProcess.getVariable("company"));
		customer.setEmail((String) businessProcess.getVariable("email"));
		customer.setName((String) businessProcess.getVariable("name"));
		customer.setPhoneNumber((String) businessProcess.getVariable("phoneNumber"));
		customer = customerService.merge(customer);
		
		
		RentalAgreementRequest req = new RentalAgreementRequest(); 
		req.setCustomer(customer);
		req.setDate(new Date());
		
		String solutionType = (String) businessProcess.getVariable("solutionType");
				
		if(solutionType.equals("INDIVIDUAL") || solutionType.equals("individual")){
			// INDIVIDUAL
			req.setRentalAgreementRequestType(RentalAgreementRequestType.INDIVIDUAL);
			req.setRequirementsOfCustomer((String) businessProcess.getVariable("additionalInfo"));
			
		}else{
			// STANDARD
			req.setRentalAgreementRequestType(RentalAgreementRequestType.STANDARD);
			
			CarData carData = carDataService.getCarDataByName((String) businessProcess.getVariable("carType"));
			if( carData != null){
				Collection<CarPool> carPool = new ArrayList<CarPool>();
				CarPool carPool_line = new CarPool();
				carPool_line.setCarData(carData);
				carPool_line.setQuantity(1);
				carPool.add(carPool_line);
				req.setCarPool(carPool);
			}
		}
		req = rentalAgreementRequestService.merge(req);
		

		// store process variables of this process...
		// store flag "individualSolutionRequested"
		businessProcess.setVariable( "individualSolutionRequested", (req.getRentalAgreementRequestType() == RentalAgreementRequestType.INDIVIDUAL));
		// store rentalAgreementRequestId
		businessProcess.setVariable("rentalAgreementRequestId", req.getId());
		// store flag, that customer is on site
		businessProcess.setVariable("isCustomerOnSite", false);
		
		
		
	}

}
