package de.unimuenster.wi.wfm.application;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.unimuenster.wi.wfm.persistence.Customer;

@Startup
@Singleton
public class ApplicationInitializer {

	@PersistenceContext
	EntityManager em;
	


	@PostConstruct
	public void initialise() {

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

	}

}