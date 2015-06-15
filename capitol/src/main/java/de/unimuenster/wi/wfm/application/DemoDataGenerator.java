package de.unimuenster.wi.wfm.application;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import de.unimuenster.wi.wfm.ejb.CustomerServiceBean;
import de.unimuenster.wi.wfm.ejb.InsuranceContractServiceBean;
import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.CaseStatus;
import de.unimuenster.wi.wfm.entitiy.Customer;
import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;

@Startup
@Singleton
public class DemoDataGenerator {

	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;
	
	@EJB
	private CustomerServiceBean customerService;
	
	@EJB
	private InsuranceContractServiceBean insuranceContractService;
	
	@PostConstruct
	public void initialise() {
			
		Customer c1 = new Customer();
		c1.setName("BVIS");
		customerService.createCustomer(c1);
		
		InsuranceContract ic1 = new InsuranceContract();
		ic1.setCustomer(c1);
		insuranceContractService.createInsuranceContract(ic1);
		
		/* 
		LiabilityCase lc1 = new LiabilityCase();
		lc1.setStatus(CaseStatus.NEW);
		lc1.setInsuranceContract(ic1);
		liabilityCaseService.createLiabilityCase(lc1);
		*/
	}

	
}
