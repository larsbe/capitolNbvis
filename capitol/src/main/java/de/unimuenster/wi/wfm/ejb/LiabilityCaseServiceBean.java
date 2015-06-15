package de.unimuenster.wi.wfm.ejb;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.unimuenster.wi.wfm.entitiy.LiabilityCase;

@Stateless
public class LiabilityCaseServiceBean implements LiabilityCaseService {
	
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
	
	public LiabilityCase editLiabilityCase(LiabilityCase liabilityCase) {
		em.merge(liabilityCase);
		return getLiabilityCase(liabilityCase.getId());
	}
}

