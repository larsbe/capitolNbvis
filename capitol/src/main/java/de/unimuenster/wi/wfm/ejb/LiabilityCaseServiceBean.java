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

import de.unimuenster.wi.wfm.entitiy.LiabilityCase;

@Stateless
public class LiabilityCaseServiceBean {
	
	// Inject task form available through the camunda cdi artifact
	@Inject
	private TaskForm taskForm;
	
	@PersistenceContext
	protected EntityManager em;
	
	public LiabilityCase createLiabilityCase(LiabilityCase liabilityCase) {
		em.persist(liabilityCase);
		em.flush();
		return liabilityCase;
	}
	
	public Collection<LiabilityCase> getAllLiabilityCases() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<LiabilityCase> cq = cb.createQuery(LiabilityCase.class);
		Root<LiabilityCase> rootEntry = cq.from(LiabilityCase.class);
		return em.createQuery(cq.select(rootEntry)).getResultList();
	}
	
	public LiabilityCase getLiabilityCase(long id) {
		LiabilityCase liabilityCase = em.find(LiabilityCase.class, id);
		if(liabilityCase == null)
			throw new IllegalArgumentException(String.format("LiabilityCase with ID %s not found", id));
		return liabilityCase;
	}
	
	public LiabilityCase mergeLiabilityCase(LiabilityCase liabilityCase) {
		// Merge detached order entity with current persisted state
		em.merge(liabilityCase);
		return getLiabilityCase(liabilityCase.getId());
	}
	
	public LiabilityCase mergeLiabilityCaseAndCompleteTask(LiabilityCase liabilityCase) {
		// Merge detached order entity with current persisted state
		em.merge(liabilityCase);
		try {
			// Complete user task from
			taskForm.completeTask();
		} catch (IOException e) {
			// Rollback both transactions on error
			throw new RuntimeException("Cannot complete task", e);
		}
		return getLiabilityCase(liabilityCase.getId());
	}
}

