package de.unimuenster.wi.wfm.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.unimuenster.wi.wfm.entitiy.Customer;

@Stateless
public class CustomerServiceBean {

	@PersistenceContext
	protected EntityManager em;
	
	public Customer getCustomerByName(String name) {
		//TODO: retrieve customer, if no customer is found, throw exception
		Customer customer = new Customer();
		customer.setName(name);
		em.persist(customer);
		return customer;
	}

}
