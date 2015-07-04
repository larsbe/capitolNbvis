package de.unimuenster.wi.wfm.web;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;

import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.CarInformation;
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
	private LiabilityCase liabilityCase;
	private long liabilityCaseId;
	
	public long getLiabilityCaseId() {
		liabilityCaseId = (Long) businessProcess.getVariable("caseID");
		return liabilityCaseId;
	}
	
	public LiabilityCase getLiabilityCase() {
		if (liabilityCase == null) {
			liabilityCase = liabilityCaseService.getLiabilityCase(getLiabilityCaseId());
		}
		return liabilityCase;
	}
	
	/* BEGIN: Test CarInformation */
	
	private CarInformation carInformation;
	
	public CarInformation getCarInformation() {
		if(carInformation == null)
			//TODO: HSN and TSN from Process Variables or Contract
			carInformation = new CarInformation("0005", "156", "2006");
		return carInformation;
	}
	
	public void loadAdditionalCarInformation() {
		carInformation = CarInformationService.GetCarInformation(carInformation.getHsn(), carInformation.getTsn(), carInformation.getYear());
	}
	
	/* END: Test CarInformation */
	
	public void submitForm() throws IOException {
		liabilityCase =  liabilityCaseService.mergeLiabilityCaseAndCompleteTask(liabilityCase);
	}

}
