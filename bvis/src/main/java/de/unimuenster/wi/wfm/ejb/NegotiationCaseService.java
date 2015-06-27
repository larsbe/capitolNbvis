package de.unimuenster.wi.wfm.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import de.unimuenster.wi.wfm.persistence.NegotiationCase;

@Remote
public interface NegotiationCaseService {
	

	public NegotiationCase createNegotiationCase(NegotiationCase negotiationCase);
	
	public Collection<NegotiationCase> getAllNegotiationCases();
	
	public NegotiationCase getNegotiationCase(long id);

	boolean editNegotiationCase(NegotiationCase NegotiationCase);
}

