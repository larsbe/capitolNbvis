package de.unimuenster.wi.wfm.ejb;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.camunda.bpm.engine.cdi.jsf.TaskForm;

import de.unimuenster.wi.wfm.entitiy.Customer;
import de.unimuenster.wi.wfm.entitiy.IndividualInsuranceBenefitEntity;
import de.unimuenster.wi.wfm.entitiy.InsuranceBenefitEntity;
import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.entitiy.InsuranceStatus;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceBenefit;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;

@Stateless
public class InsuranceContractServiceBean {
	
	@PersistenceContext
	protected EntityManager em;
	
	@Inject
	private TaskForm taskForm;
	
	public InsuranceContract createInsuranceContract(InsuranceContract item) {
		em.persist(item);
		em.flush();
		return item;
	}
	
	public InsuranceContract createInsuranceContractFromMessage(RentalAgreementMessage rentalAgreementMsg, Customer customer) {
		InsuranceContract insuranceContract = new InsuranceContract();
		insuranceContract.setRentalAgreementIdBVIS(rentalAgreementMsg.getRentalAgreementRequestId());
		insuranceContract.setCustomer(customer);
		insuranceContract.setInsuranceType(rentalAgreementMsg.getInsuranceType());
		insuranceContract.setAdditionalInfo(rentalAgreementMsg.getAdditionalInfo());
		insuranceContract.setStatus(InsuranceStatus.SUBMITTED);
		insuranceContract = this.createInsuranceContract(insuranceContract);
		if (rentalAgreementMsg.getBenefits() != null) {
			//create benefit entities and add them to insuranceContract
			for (InsuranceBenefit benefit : rentalAgreementMsg.getBenefits()) {
				InsuranceBenefitEntity benefitEntity = new InsuranceBenefitEntity();
				benefitEntity.setInsuranceBenefit(benefit);
				this.addInsuranceBenefitEntities(insuranceContract.getId(), benefitEntity);
			}
		}
		return insuranceContract;
	}
	
	public InsuranceContract getInsuranceContract(long id) {
		InsuranceContract item = em.find(InsuranceContract.class, id);
		if(item == null)
			throw new IllegalArgumentException(String.format("InsuranceContract with ID %s not found", id));
		//force load of associations
		item.getCardatas().size();
		item.getIndividualInsuranceBenefitEntity().size();
		item.getInsuranceBenefitEntity().size();
		item.getLiabilityCases().size();
		return item;
	}
	
	public InsuranceContract getInsuranceContractByBVISid(long id) {
		Query q = em.createQuery("FROM InsuranceContract c WHERE c.rentalAgreementIdBVIS=:bvisId", InsuranceContract.class)
				.setParameter("bvisId", id);
		if (q.getResultList().size() > 0) {
			InsuranceContract item = (InsuranceContract) q.getSingleResult();
			//force load of associations
			return getInsuranceContract(item.getId());
		} else {
			throw new IllegalStateException("No contract with BVISid found " + id);
		}
	}
	
	public InsuranceContract mergeInsuranceContract(InsuranceContract insuranceContract) {
		// Merge detached order entity with current persisted state
		em.merge(insuranceContract);
		return getInsuranceContract(insuranceContract.getId());
	}
	
	public InsuranceContract mergeInsuranceContractAndCompleteTask(InsuranceContract insuranceContract) {
		// Merge detached order entity with current persisted state
		em.merge(insuranceContract);
		try {
			// Complete user task from
			taskForm.completeTask();
		} catch (IOException e) {
			// Rollback both transactions on error
			throw new RuntimeException("Cannot complete task", e);
		}
		return getInsuranceContract(insuranceContract.getId());
	}
	
	public void addInsuranceBenefitEntities(long id, InsuranceBenefitEntity insuranceBenefitEntity) {
		InsuranceContract insuranceContract = getInsuranceContract(id);
		insuranceBenefitEntity.setInsuranceContract(insuranceContract);
		em.persist(insuranceBenefitEntity);
	}

	public void removeFromInsuranceBenefitEntities(InsuranceBenefitEntity detachedInsuranceBenefitEntity) {
		InsuranceBenefitEntity insuranceBenefitEntities = em.merge(detachedInsuranceBenefitEntity);
		InsuranceContract insuranceContract = insuranceBenefitEntities.getInsuranceContract();
		insuranceContract.getInsuranceBenefitEntity().remove(insuranceBenefitEntities);
		em.remove(insuranceBenefitEntities);
	}
	
	public void addIndividualInsuranceBenefitEntities(long id, IndividualInsuranceBenefitEntity insuranceBenefitEntity) {
		System.out.println("Adding Individual Benefit");
		InsuranceContract insuranceContract = getInsuranceContract(id);
		insuranceBenefitEntity.setInsuranceContract(insuranceContract);
		em.persist(insuranceBenefitEntity);
	}

	public void removeFromIndividualInsuranceBenefitEntities(IndividualInsuranceBenefitEntity detachedInsuranceBenefitEntity) {
		IndividualInsuranceBenefitEntity insuranceBenefitEntities = em.merge(detachedInsuranceBenefitEntity);
		InsuranceContract insuranceContract = insuranceBenefitEntities.getInsuranceContract();
		insuranceContract.getIndividualInsuranceBenefitEntity().remove(insuranceBenefitEntities);
		em.remove(insuranceBenefitEntities);
	}
}

