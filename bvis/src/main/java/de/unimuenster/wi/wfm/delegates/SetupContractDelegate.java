package de.unimuenster.wi.wfm.delegates;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.CustomerServiceBean;
import de.unimuenster.wi.wfm.ejb.RentalAgreementContractServiceBean;
import de.unimuenster.wi.wfm.ejb.RentalAgreementRequestServiceBean;
import de.unimuenster.wi.wfm.persistence.RentalAgreementContract;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.web.Misc;

@Named
public class SetupContractDelegate implements JavaDelegate {

	@Inject
	private BusinessProcess businessProcess;

	@EJB
	private CustomerServiceBean customerService;
	@EJB
	private RentalAgreementRequestServiceBean rentalAgreementRequestService;
	@EJB
	private RentalAgreementContractServiceBean rentalAgreementContractService;

	private RentalAgreementRequest rentalAgreementRequest;
	private long rentalAgreementRequestId;
	private RentalAgreementContract rentalAgreementContract;

	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		System.out.println("SetupContractDelegate");
		
		try {

			
			// store entity in database
			getRentalAgreementContract().setCustomer(getRentalAgreementRequest().getCustomer());
			getRentalAgreementContract().setRentalAgreementRequest(getRentalAgreementRequest());
			this.rentalAgreementContract = rentalAgreementContractService
					.merge(getRentalAgreementContract());

			getRentalAgreementRequest().setRentalAgreementContract(
					this.rentalAgreementContract);
			this.rentalAgreementRequest = rentalAgreementRequestService
					.merge(getRentalAgreementRequest());

			// store process variables of this process...
			// store flag "contractNoBVIS"
			businessProcess.setVariable("contractNoBVIS",
					getRentalAgreementContract().getId());


		} catch (EJBException e) {
			// add all validation errors
			Misc.ValidateBean(getRentalAgreementRequest());

		}

	}

	public RentalAgreementRequest getRentalAgreementRequest() {
		if (rentalAgreementRequest == null) {
			rentalAgreementRequest = rentalAgreementRequestService
					.getRentalAgreementRequest(getRentalAgreementRequestId());
		}
		return rentalAgreementRequest;
	}

	public long getRentalAgreementRequestId() {
		rentalAgreementRequestId = (Long) businessProcess
				.getVariable("rentalAgreementRequestId");
		return rentalAgreementRequestId;
	}

	public RentalAgreementContract getRentalAgreementContract() {
		if (rentalAgreementContract == null){
			rentalAgreementContract = new RentalAgreementContract();
		rentalAgreementContract.setDate(new Date());
		}
		
		return rentalAgreementContract;
	}

	public void setRentalAgreementContract(
			RentalAgreementContract rentalAgreementContract) {
		this.rentalAgreementContract = rentalAgreementContract;
	}

}
