package de.unimuenster.wi.wfm.sharedLib.data;

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
}
