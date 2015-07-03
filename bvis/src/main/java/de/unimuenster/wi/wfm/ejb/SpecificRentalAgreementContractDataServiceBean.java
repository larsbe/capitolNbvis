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
import de.unimuenster.wi.wfm.persistence.SpecificRentalAgreementContractData;

@Stateless
public class SpecificRentalAgreementContractDataServiceBean implements SpecificRentalAgreementContractDataService {

	@Inject
	private TaskForm taskForm;

	@PersistenceContext
	protected EntityManager em;



	
	public SpecificRentalAgreementContractData getSpecificRentalAgreementContractData(long id) {
		SpecificRentalAgreementContractData specificRentalAgreementContractData = em.find(SpecificRentalAgreementContractData.class, id);
		if (specificRentalAgreementContractData == null)
			throw new IllegalArgumentException(String.format(
					"Customer with ID %s not found", id));
		return specificRentalAgreementContractData;
	}

}
