package de.unimuenster.wi.wfm.sharedLib.data;

public enum InsuranceType {
	PARTIAL, FULL, GENERAL;
	
	@Override
	  public String toString() {
	    switch(this) {
	      case PARTIAL: return "General Liability";
	      case FULL: return "Partial Cover";
	      case GENERAL: return "Fully Comprehensive";
	      default: throw new IllegalArgumentException();
	    }
	  }
}
