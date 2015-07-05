package de.unimuenster.wi.wfm.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.unimuenster.wi.wfm.entitiy.Customer;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;
import de.unimuenster.wi.wfm.sharedLib.data.CustomerData;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;

@Stateless
public class CustomerServiceBean {

	@PersistenceContext
	protected EntityManager em;
	
	public Customer createCustomer(Customer customer) {
		em.persist(customer);
		em.flush();
		return customer;
	}
	
	public Customer createCustomerFromMessage(RentalAgreementMessage rentalAgreementMsg) {
		CustomerData customerData = rentalAgreementMsg.getCustomerData();
		Customer customer = this.getCustomerByName(customerData.getName());
		customer.setAddress(customerData.getAddress());
		customer.setCompany(customerData.getCompany());
		customer.setEmail(customerData.getEmail());
		customer.setPhoneNumber(customerData.getPhoneNumber());
		return this.mergeCustomer(customer);
	}
	
	public Customer getCustomerByName(String name) {
		Query q = em.createQuery("FROM Customer c WHERE c.name=:name", Customer.class)
			.setParameter("name", name);
		System.out.println("Attention");
		if (q.getResultList().size() == 0) {
			Customer customer = new Customer();
			customer.setName(name);
			em.persist(customer);
			return customer;
		} else {
			return (Customer) q.getSingleResult();
		}
	}
	
	public Customer getCustomer(long id) {
		Customer customer = em.find(Customer.class, id);
		if(customer == null)
			throw new IllegalArgumentException(String.format("Customer with ID %s not found", id));
		return customer;
	}
	
	public Customer mergeCustomer(Customer customer) {
		// Merge detached order entity with current persisted state
		em.merge(customer);
		return getCustomer(customer.getId());
	}

}
