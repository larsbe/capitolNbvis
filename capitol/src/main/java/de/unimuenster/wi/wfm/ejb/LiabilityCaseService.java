package de.unimuenster.wi.wfm.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import de.unimuenster.wi.wfm.entitiy.LiabilityCase;

@Remote
public interface LiabilityCaseService {
	

	public LiabilityCase createLiabilityCase(LiabilityCase liabilityCase);
	
	public Collection<LiabilityCase> getAllLiabilityCases();
	
	public LiabilityCase getLiabilityCase(long id);

	boolean editLiabilityCase(LiabilityCase liabilityCase);
}

