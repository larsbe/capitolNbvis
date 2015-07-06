package de.unimuenster.wi.wfm.web;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;

import de.unimuenster.wi.wfm.ejb.CarDataServiceBean;
import de.unimuenster.wi.wfm.ejb.InsuranceContractServiceBean;
import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.CarData;
import de.unimuenster.wi.wfm.entitiy.CarInformation;
import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;
import de.unimuenster.wi.wfm.util.CarInformationService;

@Named
@ConversationScoped
public class CalculateCarsFairValue implements Serializable{
	
	private static final long serialVersionUID = 1L;

	// the BusinessProcess to access the process variables
	@Inject
	private BusinessProcess businessProcess;
	
	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;
	
	@EJB
	private InsuranceContractServiceBean insuranceContractService;
	
	@EJB
	private CarDataServiceBean carDataService;
	
	private LiabilityCase liabilityCase;
	private long liabilityCaseId;
	
	public long getLiabilityCaseId() {
		liabilityCaseId = (Long) businessProcess.getVariable("caseID");
		return liabilityCaseId;
	}
	
	public LiabilityCase getLiabilityCase() {
		if (liabilityCase == null) {
			liabilityCase = liabilityCaseService.getLiabilityCase(getLiabilityCaseId());
			InsuranceContract contract = insuranceContractService.getInsuranceContract(liabilityCase.getInsuranceContract().getId());
			liabilityCase.setInsuranceContract(contract);
		}
		return liabilityCase;
	}
	
	public CarData getCarData(String licenseNumber){
		CarData carData =carDataService.getCarData(licenseNumber);
		return carData;
	}
	
	
	private CarInformation carInformation;
	
	public CarInformation getCarInformation() {
		CarData carData = new CarData();
		if(carInformation == null){
			carData = getCarData(getLiabilityCase().getLicenseNumber());
			carInformation = new CarInformation(carData.getHsn(), carData.getTsn(), String.valueOf(carData.getYear()));
		}
		return carInformation;
	}
	
	
	public void loadAdditionalCarInformation() {
		carInformation = CarInformationService.GetCarInformation(carInformation.getHsn(), carInformation.getTsn(), carInformation.getYear());
	}
	
	
	public void submitForm() throws IOException {
		liabilityCase =  liabilityCaseService.mergeLiabilityCaseAndCompleteTask(liabilityCase);
	}

}
