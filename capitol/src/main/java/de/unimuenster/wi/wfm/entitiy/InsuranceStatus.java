package de.unimuenster.wi.wfm.entitiy;

public enum InsuranceStatus {
	SUBMITTED, //initial status; contract was send to us by BVIS and needs to be processed
	PROCESSED, //contract was processed by capitol and send to BVIS; response is pending
	REVISED, //contract was revised by BVIS but not yet re-processed by capitol
	ACTIVE, //contract is active and new liability cases can be processed
	CLOSED //customer or bvis rejected rental agreement
}
