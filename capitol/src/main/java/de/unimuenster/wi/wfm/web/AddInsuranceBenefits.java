package de.unimuenster.wi.wfm.web;

import static org.camunda.spin.Spin.JSON;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;

import de.unimuenster.wi.wfm.ejb.InsuranceContractServiceBean;
import de.unimuenster.wi.wfm.entitiy.CarData;
import de.unimuenster.wi.wfm.entitiy.IndividualInsuranceBenefitEntity;
import de.unimuenster.wi.wfm.entitiy.InsuranceBenefitEntity;
import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceBenefit;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceType;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;
import de.unimuenster.wi.wfm.util.InsurancePriceCalculator;

@Named
@ConversationScoped
public class AddInsuranceBenefits implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// the BusinessProcess to access the process variables
	@Inject
	private BusinessProcess businessProcess;
	
	// Inject task form available through the camunda cdi artifact
	@Inject
	private TaskForm taskForm;

	@EJB
	private InsuranceContractServiceBean insuranceContractService;
	private InsuranceContract contract;
	private long contractId;
	
	private InsuranceBenefit newBenefit;
	private BigDecimal priceOfNewIndividualBenefit = BigDecimal.ZERO;
	private String nameOfNewIndividualBenefit = "";
	
	private RentalAgreementMessage rentalAgreementMsg;
	
	public long getContractId() {
		contractId = (Long) businessProcess.getVariable("contractId");
		return contractId;
	}
	
	private RentalAgreementMessage getRentalAgreementMsg() {
		if (rentalAgreementMsg == null) {
			rentalAgreementMsg = JSON((String)businessProcess.getVariable("agreementConditions"))
					.mapTo(RentalAgreementMessage.class);
		}
		return rentalAgreementMsg;
	}
	
	public String getAdditionalInfo() {
		return getRentalAgreementMsg().getAdditionalInfo();
	}
	
	public InsuranceContract getContract() {
		if (contract == null) {
			contract = insuranceContractService.getInsuranceContract(getContractId());
		}
		return contract;
	}
	
	public void update() {
		contract =  insuranceContractService.mergeInsuranceContract(contract);
	}
	
	public void submitForm() {
		
		try {
			contract =  insuranceContractService.mergeInsuranceContract(contract);
			//Doesn't work so far!!!!!!!!!!!!!!!!!!!!
			taskForm.completeTask();
		} catch (IOException e) {
			// Rollback both transactions on error
			throw new RuntimeException("Cannot complete task", e);
		}
	}
	
	public void addBenefit() {
		try  {
			InsuranceBenefitEntity benefitEntity = new InsuranceBenefitEntity();
			benefitEntity.setInsuranceBenefit(newBenefit);
			insuranceContractService.addInsuranceBenefitEntities(contractId, benefitEntity);
			contract = insuranceContractService.getInsuranceContract(getContractId());
		} catch (EJBException e) {
			System.out.println("oops an error");
		}
	}

	public void removeFromBenefits(InsuranceBenefitEntity benefitEntity) {
		insuranceContractService.removeFromInsuranceBenefitEntities(benefitEntity);
		contract = insuranceContractService.getInsuranceContract(getContractId());
	}
	
	public void addIndividualBenefit() {
		try  {
			IndividualInsuranceBenefitEntity benefitEntity = new IndividualInsuranceBenefitEntity();
			benefitEntity.setPrice(priceOfNewIndividualBenefit);
			benefitEntity.setName(nameOfNewIndividualBenefit);
			insuranceContractService.addIndividualInsuranceBenefitEntities(contractId, benefitEntity);
			contract = insuranceContractService.getInsuranceContract(getContractId());
			System.out.println("New number of individual benefits " + contract.getIndividualInsuranceBenefitEntity().size());
		} catch (EJBException e) {
			System.out.println("oops an error");
		}
	}

	public void removeFromIndividualBenefits(IndividualInsuranceBenefitEntity benefitEntity) {
		insuranceContractService.removeFromIndividualInsuranceBenefitEntities(benefitEntity);
		contract = insuranceContractService.getInsuranceContract(getContractId());
	}
	
	public InsuranceBenefit getNewBenefit() {
		return newBenefit;
	}
	
	public void setNewBenefit(InsuranceBenefit benefit) {
		newBenefit = benefit;
	}
	
	public String getPriceOfNewIndividualBenefit() {
		return priceOfNewIndividualBenefit.toString();
	}

	public void setPriceOfNewIndividualBenefit(
			String priceOfNewIndividualBenefit) {
		this.priceOfNewIndividualBenefit = new BigDecimal(priceOfNewIndividualBenefit);
	}

	public String getNameOfNewIndividualBenefit() {
		return nameOfNewIndividualBenefit;
	}

	public void setNameOfNewIndividualBenefit(String nameOfNewIndividualBenefit) {
		this.nameOfNewIndividualBenefit = nameOfNewIndividualBenefit;
	}

	public List<InsuranceBenefit> getAllBenefits() {
		return Arrays.asList(InsuranceBenefit.values());
	}
	
	public List<InsuranceType> getAllInsuranceTypes() {
		return Arrays.asList(InsuranceType.values());
	}
	
	public BigDecimal getSuggestedPrice() {
		BigDecimal price = BigDecimal.ZERO;
		for (CarData car : contract.getCardatas()) {
			price = price.add(InsurancePriceCalculator.calculateInsurancePrice(
					car.getHsn(),
					car.getTsn(),
					Integer.valueOf(car.getYear()).toString(),
					contract.getInsuranceBenefitEntity(),
					contract.getIndividualInsuranceBenefitEntity(),
					contract.getInsuranceType()
			));
		}
		return price;
	}

}
