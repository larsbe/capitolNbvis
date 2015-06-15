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
}
