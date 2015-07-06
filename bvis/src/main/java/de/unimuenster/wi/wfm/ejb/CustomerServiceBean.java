package de.unimuenster.wi.wfm.ejb;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.unimuenster.wi.wfm.persistence.Customer;
import de.unimuenster.wi.wfm.sharedLib.data.CustomerData;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;

@Stateless
public class CustomerServiceBean {

	@PersistenceContext
	protected EntityManager em;

	public Customer createCustomer(Customer Customer) {
		em.persist(Customer);
		return Customer;
	}
	
	public Customer createObjectFromMessage(RentalAgreementMessage rentalAgreementMsg) {
		CustomerData customerData = rentalAgreementMsg.getCustomerData();
		Customer customer = this.getCustomerByName(customerData.getName());
		customer.setAddress(customerData.getAddress());
		customer.setCompany(customerData.getCompany());
		customer.setEmail(customerData.getEmail());
		customer.setPhoneNumber(customerData.getPhoneNumber());
		return this.merge(customer);
	}
	
	public Customer getCustomerByName(String name) {
		Query q = em.createQuery("FROM Customer c WHERE c.name=:name", Customer.class)
			.setParameter("name", name);
		
		if (q.getResultList().size() == 0) {
			// customer doesn't exist, create new one
			Customer customer = new Customer();
			customer.setName(name);
			em.persist(customer);
			return customer;
			
		} else {
			// return existing customer
			return (Customer) q.getSingleResult();
		}
	}

	public Collection<Customer> getAllCustomers() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
		Root<Customer> rootEntry = cq.from(Customer.class);
		return em.createQuery(cq.select(rootEntry)).getResultList();
	}

	public Customer getCustomer(long id) {
		Customer customer = em.find(Customer.class, id);
		if (customer == null)
			throw new IllegalArgumentException(String.format(
					"Customer with ID %s not found", id));
		return customer;
	}

	public Customer merge(Customer customer) {
		// Merge detached entity with current persisted state
		customer = em.merge(customer);


		return customer;
	}

}
