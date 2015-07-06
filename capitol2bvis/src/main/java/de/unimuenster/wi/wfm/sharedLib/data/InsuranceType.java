package de.unimuenster.wi.wfm.sharedLib.data;

import java.math.BigDecimal;

public enum InsuranceType {
	PARTIAL, FULL, GENERAL;
	
	@Override
	  public String toString() {
	    switch(this) {
	      case PARTIAL: return "General Liability (CP 500€)";
	      case FULL: return "Partial Cover (CP 500€)";
	      case GENERAL: return "Fully Comprehensive (CP 500€)";
	      default: throw new IllegalArgumentException();
	    }
	  }
	
	public BigDecimal toPrice(){
		switch(this) {
	      case PARTIAL: return BigDecimal.valueOf(0);
	      case FULL: return BigDecimal.valueOf(100);
	      case GENERAL: return BigDecimal.valueOf(250);
	      default: throw new IllegalArgumentException();
	    }
	}
}
