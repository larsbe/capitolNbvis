package de.unimuenster.wi.wfm.sharedLib.data;

import java.math.BigDecimal;

public enum InsuranceBenefit {
	BRAKEDOWNCOVER, 
	ABROADBRAKEDOWNCOVER, 
	WITHOUTOWNRISK,
	ROADSTONE,
	HAILDAMAGE;
	
	@Override
	  public String toString() {
	    switch(this) {
	      case BRAKEDOWNCOVER: return "Accident and Breakdown Cover";
	      case ABROADBRAKEDOWNCOVER: return "Accident and Breakdown Cover (Abroad)";
	      case WITHOUTOWNRISK: return "Without Own Risk";
	      case ROADSTONE: return "Road Stone";
	      case HAILDAMAGE: return "Hail Damage";
	      default: throw new IllegalArgumentException();
	    }
	  }
	
	public BigDecimal toPrice(){
		switch(this) {
		  case BRAKEDOWNCOVER: return BigDecimal.valueOf(20);
	      case ABROADBRAKEDOWNCOVER: return BigDecimal.valueOf(10);
	      case WITHOUTOWNRISK: return BigDecimal.valueOf(100);
	      case ROADSTONE: return BigDecimal.valueOf(10);
	      case HAILDAMAGE: return BigDecimal.valueOf(10);
	      default: throw new IllegalArgumentException();
	    }
	}
}
