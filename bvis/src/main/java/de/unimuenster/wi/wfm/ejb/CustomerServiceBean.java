package de.unimuenster.wi.wfm.ejb;

import java.io.IOException;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.camunda.bpm.engine.cdi.jsf.TaskForm;

import de.unimuenster.wi.wfm.persistence.Customer;

@Stateless
public class CustomerServiceBean implements CustomerService {

	@Inject
	private TaskForm taskForm;

	@PersistenceContext
	protected EntityManager em;

	public Customer createCustomer(Customer Customer) {
		em.persist(Customer);
		return Customer;
	}

	public Collection<Customer> getAllCustomers() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
		Root<Customer> rootEntry = cq.from(Customer.class);
		return em.createQuery(cq.select(rootEntry)).getResultList();
	}

	public Customer getCustomer(long id) {
		Customer Customer = em.find(Customer.class, id);
		if (Customer == null)
			throw new IllegalArgumentException(String.format(
					"Customer with ID %s not found", id));
		return Customer;
	}

	public Customer mergeAndCompleteTask(Customer customer) {
		// Merge detached entity with current persisted state
		customer = em.merge(customer);

		try {
			// Complete user task from
			taskForm.completeTask();
		} catch (IOException e) {
			// Rollback both transactions on error
			throw new RuntimeException("Cannot complete task", e);
		}
		return customer;
	}

}
