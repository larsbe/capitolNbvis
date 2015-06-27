package de.unimuenster.wi.wfm.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import de.unimuenster.wi.wfm.persistence.Customer;

@Remote
public interface CustomerService {

	public Customer createCustomer(Customer Customer);

	public Collection<Customer> getAllCustomers();

	public Customer getCustomer(long id);

	public Customer mergeAndCompleteTask(Customer customer);

}
