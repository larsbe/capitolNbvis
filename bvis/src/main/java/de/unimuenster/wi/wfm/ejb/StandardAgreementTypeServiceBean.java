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
import de.unimuenster.wi.wfm.persistence.StandardAgreementType;

@Stateless
public class StandardAgreementTypeServiceBean {

	@Inject
	private TaskForm taskForm;

	@PersistenceContext
	protected EntityManager em;


	public Collection<StandardAgreementType> getAllStandardAgreementTypes() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<StandardAgreementType> cq = cb.createQuery(StandardAgreementType.class);
		Root<StandardAgreementType> rootEntry = cq.from(StandardAgreementType.class);
		return em.createQuery(cq.select(rootEntry)).getResultList();
	}
	
	public StandardAgreementType getStandardAgreementType(long id) {
		StandardAgreementType standardAgreementType = em.find(StandardAgreementType.class, id);
		if (standardAgreementType == null)
			throw new IllegalArgumentException(String.format(
					"Customer with ID %s not found", id));
		return standardAgreementType;
	}

}
