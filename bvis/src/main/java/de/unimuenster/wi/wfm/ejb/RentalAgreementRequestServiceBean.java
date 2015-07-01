package de.unimuenster.wi.wfm.ejb;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;

@Stateless
public class RentalAgreementRequestServiceBean implements RentalAgreementRequestService {

	@PersistenceContext
	protected EntityManager em;

	public RentalAgreementRequest createRentalAgreementRequest(RentalAgreementRequest RentalAgreementRequest) {
		em.persist(RentalAgreementRequest);
		return RentalAgreementRequest;
	}

	public Collection<RentalAgreementRequest> getAllRentalAgreementRequests() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<RentalAgreementRequest> cq = cb.createQuery(RentalAgreementRequest.class);
		Root<RentalAgreementRequest> rootEntry = cq.from(RentalAgreementRequest.class);
		return em.createQuery(cq.select(rootEntry)).getResultList();
	}

	public RentalAgreementRequest getRentalAgreementRequest(long id) {
		RentalAgreementRequest rentalAgreementRequest = em.find(RentalAgreementRequest.class, id);
		if (rentalAgreementRequest == null)
			throw new IllegalArgumentException(String.format(
					"RentalAgreementRequest with ID %s not found", id));
		return rentalAgreementRequest;
	}

	public RentalAgreementRequest merge(RentalAgreementRequest rentalAgreementRequest) {
		// Merge detached entity with current persisted state
		rentalAgreementRequest = em.merge(rentalAgreementRequest);

		return rentalAgreementRequest;
	}

}
