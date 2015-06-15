package de.unimuenster.wi.wfm.application;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import de.unimuenster.wi.wfm.ejb.CustomerServiceBean;
import de.unimuenster.wi.wfm.ejb.LiabilityCaseService;
import de.unimuenster.wi.wfm.entitiy.CaseStatus;
import de.unimuenster.wi.wfm.entitiy.Customer;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;

@Startup
@Singleton
public class DemoDataGenerator {

	@EJB
	private LiabilityCaseService liabilityCaseService;
	
	@EJB
	private CustomerServiceBean customerService;
	
	@PostConstruct
	public void initialise() {
		
		Customer c1 = new Customer();
		c1.setName("BVIS");
		customerService.createCustomer(c1);
		
		LiabilityCase lc1 = new LiabilityCase();
		lc1.setStatus(CaseStatus.NEW);
		lc1.setCustomer(c1);
		
		liabilityCaseService.createLiabilityCase(lc1);

	}

	
}
