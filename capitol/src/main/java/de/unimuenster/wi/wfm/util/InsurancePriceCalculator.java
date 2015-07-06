package de.unimuenster.wi.wfm.util;

import java.math.BigDecimal;
import java.util.Collection;

import de.unimuenster.wi.wfm.entitiy.CarInformation;
import de.unimuenster.wi.wfm.entitiy.IndividualInsuranceBenefitEntity;
import de.unimuenster.wi.wfm.entitiy.InsuranceBenefitEntity;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceBenefit;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceType;

public class InsurancePriceCalculator {

	public static BigDecimal calculateInsurancePrice(String hsn, String tsn, String year, Collection<InsuranceBenefitEntity> insuranceBenefits, Collection<IndividualInsuranceBenefitEntity> individualInsuranceBenefitEntitys, InsuranceType insuranceType){
		BigDecimal price = BigDecimal.ZERO;
		BigDecimal priceDependentOfCar;
		BigDecimal priceOfBenefits;
		BigDecimal priceOfIndividualBenefits;
		CarInformation carinformation;
		carinformation = CarInformationService.GetCarInformation(hsn, tsn, year);
		
		priceDependentOfCar= BigDecimal.valueOf(getClassificationForInsuranceType(insuranceType,carinformation)).multiply(BigDecimal.valueOf(10.21));
		System.out.println("Price dependent of car " + priceDependentOfCar);
		priceOfBenefits=getSumOfAllInsuranceBenefits(insuranceBenefits);
		System.out.println("Price of standard benefits " + priceOfBenefits);
		priceOfIndividualBenefits=getSumOfAllIndividualInsuranceBenefits(individualInsuranceBenefitEntitys);
		System.out.println("Price of individual benefits" + priceOfIndividualBenefits);
		
		if (insuranceType != null) {
			price = insuranceType.toPrice();
			System.out.println("Insurance Contract price " + price);
		}
		price = price.add(priceDependentOfCar).add(priceOfBenefits).add(priceOfIndividualBenefits);
		return price;
	}
	
	private static int getClassificationForInsuranceType(InsuranceType insuranceType, CarInformation carInformation){
		if (insuranceType != null) {
			switch(insuranceType) {
		      case PARTIAL: return carInformation.getClassTK();
		      case FULL: return carInformation.getClassVK();
		      case GENERAL: return carInformation.getClassKH();
		      default: throw new IllegalArgumentException();
		    }
		}
		return 0;
	}
	
	private static BigDecimal getSumOfAllInsuranceBenefits(Collection<InsuranceBenefitEntity> insuranceBenefits){
		BigDecimal sum;
		sum = BigDecimal.ZERO;
		if (insuranceBenefits != null) {
			for(InsuranceBenefitEntity insuranceBenefit : insuranceBenefits){
				sum = sum.add(insuranceBenefit.getInsuranceBenefit().toPrice());
			}
		}
		return sum;
	}
	
	private static BigDecimal getSumOfAllIndividualInsuranceBenefits(Collection<IndividualInsuranceBenefitEntity> individualInsuranceBenefitEntitys){
		BigDecimal sum;
		sum = BigDecimal.ZERO;
		if (individualInsuranceBenefitEntitys != null) {
			for(IndividualInsuranceBenefitEntity insuranceBenefit : individualInsuranceBenefitEntitys){
				sum = sum.add(insuranceBenefit.getPrice());
			}
		}
		return sum;
	}
}
