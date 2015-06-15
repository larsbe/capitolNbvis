package de.unimuenster.wi.wfm.ejb;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;

@Stateless
public class InsuranceContractServiceBean {
	
	@PersistenceContext
	protected EntityManager em;
	
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
}

