package SandboxJonas;

import java.util.Arrays;

import de.unimuenster.wi.wfm.sharedLib.data.CustomerData;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceBenefit;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceType;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;
import de.unimuenster.wi.wfm.sharedLib.rest.CapitolREST;
import de.unimuenster.wi.wfm.sharedLib.rest.RestHelper;

public class Sandbox {

	public static void main(String[] args) {

		System.out.println(CapitolREST.ContractStatus(3L, true));

		RestHelper.SendMessage("http://localhost:8080/engine-rest/engine/default/message", CapitolREST.ContractStatus(3L, true));
	}

}
