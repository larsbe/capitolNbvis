package de.unimuenster.wi.wfm.ejb;

import java.util.Collection;
import javax.ejb.Remote;
import de.unimuenster.wi.wfm.persistence.StandardAgreementType;

@Remote
public interface StandardAgreementTypeService {


	public Collection<StandardAgreementType> getAllStandardAgreementTypes();
	
	public StandardAgreementType getStandardAgreementType(long id);
}
