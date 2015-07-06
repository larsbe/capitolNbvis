package de.unimuenster.wi.wfm.util;

import java.math.BigDecimal;
import java.util.Collection;

import de.unimuenster.wi.wfm.entitiy.CarInformation;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceBenefit;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceType;

public class InsurancePriceCalculator {

	public BigDecimal calculateInsurancePrice(String hsn, String tsn, String year, Collection<InsuranceBenefit> insuranceBenefits, InsuranceType insuranceType){
		BigDecimal price;
		BigDecimal priceDependentOfCar;
		BigDecimal priceOfBenefits;
		CarInformation carinformation;
		carinformation = CarInformationService.GetCarInformation(hsn, tsn, year);
		
		priceDependentOfCar= BigDecimal.valueOf(getClassificationForInsuranceType(insuranceType,carinformation)).multiply(BigDecimal.valueOf(10.21));
		priceOfBenefits=getSumOfAllInsuranceBenefits(insuranceBenefits);
		
		price = insuranceType.toPrice().add(priceDependentOfCar).add(priceOfBenefits);
		return price;
	}
	
	public int getClassificationForInsuranceType(InsuranceType insuranceType, CarInformation carInformation){
		switch(insuranceType) {
	      case PARTIAL: return carInformation.getClassTK();
	      case FULL: return carInformation.getClassVK();
	      case GENERAL: return carInformation.getClassKH();
	      default: throw new IllegalArgumentException();
	    }
	}
	
	public BigDecimal getSumOfAllInsuranceBenefits(Collection<InsuranceBenefit> insuranceBenefits){
		BigDecimal sum;
		sum = BigDecimal.ZERO;
		for(InsuranceBenefit insuranceBenefit : insuranceBenefits){
			sum = sum.add(insuranceBenefit.toPrice());
		}
		return sum;
	}
}
