package de.unimuenster.wi.wfm.ejb;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.unimuenster.wi.wfm.persistence.RentalAgreementContract;

@Stateless
public class RentalAgreementContractServiceBean {

	@PersistenceContext
	protected EntityManager em;

	
	public Collection<RentalAgreementContract> getAllRentalAgreementContracts() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<RentalAgreementContract> cq = cb.createQuery(RentalAgreementContract.class);
		Root<RentalAgreementContract> rootEntry = cq.from(RentalAgreementContract.class);
		return em.createQuery(cq.select(rootEntry)).getResultList();
	}

	public RentalAgreementContract getRentalAgreementContract(long id) {
		RentalAgreementContract rentalAgreementContract = em.find(RentalAgreementContract.class, id);
		if (rentalAgreementContract == null)
			throw new IllegalArgumentException(String.format(
					"RentalAgreementContract with ID %s not found", id));
		return rentalAgreementContract;
	}

	public RentalAgreementContract merge(RentalAgreementContract rentalAgreementContract) {
		// Merge detached entity with current persisted state
		rentalAgreementContract = em.merge(rentalAgreementContract);

		return rentalAgreementContract;
	}
}