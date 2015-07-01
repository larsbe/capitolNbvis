package de.unimuenster.wi.wfm.application;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.unimuenster.wi.wfm.persistence.Customer;
import de.unimuenster.wi.wfm.persistence.StandardAgreementType;

@Startup
@Singleton
public class ApplicationInitializer {

	@PersistenceContext
	EntityManager em;
	


	@PostConstruct
	public void initialise() {

		
		// create Customers
		Customer c1 = new Customer();
		c1.setName("David Jauernig");
		c1.setEmail("david.jauernig@googlemail.com");
		c1.setAddress("Gruppenarbeitsraum");
		em.persist(c1);

		c1 = new Customer();
		c1.setName("Andi Hermann");
		c1.setEmail("a_herm14@uni-muenster.de");
		c1.setAddress("McFit");
		em.persist(c1);

		
//		 create StandardAgreementTypes
		StandardAgreementType sat1 = new StandardAgreementType();
		sat1.setName("Bronze");
		sat1.setDetail("Bronze Contract details");
		em.persist(sat1);
		
		StandardAgreementType sat2 = new StandardAgreementType();
		sat2.setName("Silver");
		sat2.setDetail("Silver Contract details");
		em.persist(sat2);
		
		StandardAgreementType sat3 = new StandardAgreementType();
		sat3.setName("Gold");
		sat3.setDetail("Gold Contract details");
		em.persist(sat3);
	}

}