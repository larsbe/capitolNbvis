package de.unimuenster.wi.wfm.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;

@Remote
public interface RentalAgreementRequestService {

	public RentalAgreementRequest createRentalAgreementRequest(RentalAgreementRequest rentalAgreementRequest);

	public Collection<RentalAgreementRequest> getAllRentalAgreementRequests();

	public RentalAgreementRequest getRentalAgreementRequest(long id);

	public RentalAgreementRequest merge(RentalAgreementRequest rentalAgreementRequest);

}
