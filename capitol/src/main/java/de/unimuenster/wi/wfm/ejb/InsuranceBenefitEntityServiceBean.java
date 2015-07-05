package de.unimuenster.wi.wfm.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.unimuenster.wi.wfm.entitiy.InsuranceBenefitEntity;

@Stateless
public class InsuranceBenefitEntityServiceBean {

	@PersistenceContext
	protected EntityManager em;
	
	public InsuranceBenefitEntity createInsuranceBenefitEntity(InsuranceBenefitEntity insuranceBenefitEntity) {
		em.persist(insuranceBenefitEntity);
		em.flush();
		return insuranceBenefitEntity;
	}
}
