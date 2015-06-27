package de.unimuenster.wi.wfm.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.unimuenster.wi.wfm.entitiy.NegotiationCase;

@Stateless
public class NegotiationCaseServiceBean {
	
	@PersistenceContext
	protected EntityManager em;
	
	public NegotiationCase createNegotiationCase(NegotiationCase negotiationCase) {
		em.persist(negotiationCase);
		return negotiationCase;
	}
	
	public NegotiationCase getNegotiationCase(long id) {
		NegotiationCase negotiationCase = em.find(NegotiationCase.class, id);
		if(negotiationCase == null)
			throw new IllegalArgumentException(String.format("NegotiationCase with ID %s not found", id));
		return negotiationCase;
	}

	public NegotiationCase editNegotiationCase(NegotiationCase negotiationCase) {
		em.merge(negotiationCase);
		return getNegotiationCase(negotiationCase.getId());
	}
}
