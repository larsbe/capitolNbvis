package de.unimuenster.wi.wfm.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.unimuenster.wi.wfm.entitiy.IndividualInsuranceBenefitEntity;

@Stateless
public class IndividualInsuranceBenefitEntityServiceBean {

	@PersistenceContext
	protected EntityManager em;
	
	public IndividualInsuranceBenefitEntity createIndividualInsuranceBenefitEntity(IndividualInsuranceBenefitEntity individualInsuranceBenefitEntity) {
		em.persist(individualInsuranceBenefitEntity);
		em.flush();
		return individualInsuranceBenefitEntity;
	}
}
