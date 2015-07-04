package de.unimuenster.wi.wfm.ejb;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.jsf.TaskForm;

import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;

@Stateless
public class InsuranceContractServiceBean {
	
	@PersistenceContext
	protected EntityManager em;
	
	@Inject
	private TaskForm taskForm;
	
	public InsuranceContract createInsuranceContract(InsuranceContract item) {
		em.persist(item);
		em.flush();
		return item;
	}
	
	public InsuranceContract getInsuranceContract(long id) {
		InsuranceContract item = em.find(InsuranceContract.class, id);
		if(item == null)
			throw new IllegalArgumentException(String.format("InsuranceContract with ID %s not found", id));
		return item;
	}
	
	public InsuranceContract mergeInsuranceContractAndCompleteTask(InsuranceContract insuranceContract) {
		// Merge detached order entity with current persisted state
		em.merge(insuranceContract);
		try {
			// Complete user task from
			taskForm.completeTask();
		} catch (IOException e) {
			// Rollback both transactions on error
			throw new RuntimeException("Cannot complete task", e);
		}
		return getInsuranceContract(insuranceContract.getId());
	}
}

