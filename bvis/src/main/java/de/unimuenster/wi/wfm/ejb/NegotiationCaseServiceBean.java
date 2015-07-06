package de.unimuenster.wi.wfm.ejb;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.unimuenster.wi.wfm.persistence.NegotiationCase;

@Stateless
public class NegotiationCaseServiceBean {
	
	@PersistenceContext
	protected EntityManager em;
	
	public NegotiationCase createNegotiationCase(NegotiationCase NegotiationCase) {
		em.persist(NegotiationCase);
		return NegotiationCase;
	}
	
	public Collection<NegotiationCase> getAllNegotiationCases() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<NegotiationCase> cq = cb.createQuery(NegotiationCase.class);
		Root<NegotiationCase> rootEntry = cq.from(NegotiationCase.class);
		return em.createQuery(cq.select(rootEntry)).getResultList();
	}
	
	public NegotiationCase getNegotiationCase(long id) {
		NegotiationCase NegotiationCase = em.find(NegotiationCase.class, id);
		if(NegotiationCase == null)
			throw new IllegalArgumentException(String.format("NegotiationCase with ID %s not found", id));
		return NegotiationCase;
	}
	
	public NegotiationCase merge(NegotiationCase negotiationCase) {
		return em.merge(negotiationCase);
	}
}
