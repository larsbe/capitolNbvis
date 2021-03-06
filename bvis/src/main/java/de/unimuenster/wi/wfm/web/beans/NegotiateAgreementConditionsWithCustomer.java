package de.unimuenster.wi.wfm.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.*;
import javax.inject.Inject;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;

import de.unimuenster.wi.wfm.ejb.CarDataServiceBean;
import de.unimuenster.wi.wfm.ejb.NegotiationCaseServiceBean;
import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestServiceBean;
import de.unimuenster.wi.wfm.persistence.CarData;
import de.unimuenster.wi.wfm.persistence.CarPool;
import de.unimuenster.wi.wfm.persistence.Customer;
import de.unimuenster.wi.wfm.persistence.NegotiationCase;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.web.Misc;

@ManagedBean
@ViewScoped
public class NegotiateAgreementConditionsWithCustomer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private BusinessProcess businessProcess;
	
	@Inject
	private TaskForm taskForm;
	
	private RentalAgreementRequest rentalAgreementRequest;	
	private NegotiationCase negotiationCase;
	@EJB
	private RentalAgreementRequestServiceBean rentalAgreementRequestService;
	@EJB
	private NegotiationCaseServiceBean negotiationCaseService;
	@EJB
	private CarDataServiceBean carDataService;
	
	private long rentalAgreementRequestId;
	private long selectedCarDataId;
	private CarPool carPool;

	
	public NegotiationCase getNegotiationCase() {
		if (negotiationCase == null){
			negotiationCase = new NegotiationCase();
			negotiationCase.setDate(new Date());
		}			
		return negotiationCase;
	}
	
	public RentalAgreementRequest getRentalAgreementRequest() {
		if (rentalAgreementRequest == null){
			rentalAgreementRequest = rentalAgreementRequestService.getRentalAgreementRequest(getRentalAgreementRequestId());
		}			
		return rentalAgreementRequest;
	}
	
	public long getRentalAgreementRequestId() {
		rentalAgreementRequestId = (Long) businessProcess.getVariable("rentalAgreementRequestId");
		return rentalAgreementRequestId;
	}

	public Collection<CarData> getCarDatas() {
		return carDataService.getAllCarDatas();
	}
	
	public long getSelectedCarDataId() {
		return selectedCarDataId;
	}

	public void setSelectedCarDataId(long id) {
		selectedCarDataId = id;
	}
	
	public CarPool getCarPool() {
		if(carPool == null) {
			carPool = new CarPool();
		}
		return carPool;
	}

	public void addCarPool() {
		carPool.setCarData(carDataService.getCarData(getSelectedCarDataId()));	
		carPool.setRentalAgreementRequest(getRentalAgreementRequest());
		carPool = null;
	}
	
	public void submit() {
		try {
			
			
			// add selected carData
//			List<CarData> carsData = new ArrayList<CarData>();
//			carsData.add(carDataService.getCarData(getSelectedCarDataId()));
//			getRentalAgreementRequest().setCarsData(carsData);
			
			// store entity in database
			// store negotiation case
			negotiationCase = negotiationCaseService.merge(getNegotiationCase());
			
			// store update rentalAgreementRequest
			getRentalAgreementRequest().setNegotiationCase(negotiationCase);
			rentalAgreementRequestService.merge(getRentalAgreementRequest());
		

			
			// complete user task form
			taskForm.completeTask();
						
			// store process variables of this process...
			// store flag "insuranceConditionsApproved"
			businessProcess.setVariable( "insuranceConditionsApproved", getRentalAgreementRequest().getNegotiationCase().getConditionsApproved());
			
		} catch (IOException e){
			throw new RuntimeException("Cannot complete task", e);
		}
	}	
	
}
