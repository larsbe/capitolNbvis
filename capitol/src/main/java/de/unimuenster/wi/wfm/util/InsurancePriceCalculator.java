package de.unimuenster.wi.wfm.util;

import java.math.BigDecimal;
import java.util.Collection;

import de.unimuenster.wi.wfm.entitiy.CarInformation;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceBenefit;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceType;

public class InsurancePriceCalculator {

	public BigDecimal calculateInsurancePrice(String hsn, String tsn, String year, Collection<InsuranceBenefit> insuranceBenefits, InsuranceType insuranceType){
		BigDecimal price;
		CarInformation carinformation;
		carinformation = CarInformationService.GetCarInformation(hsn, tsn, year);
		
		
		return BigDecimal.valueOf(125.23);
	}
}
