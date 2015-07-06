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

		RentalAgreementMessage message = new RentalAgreementMessage();
		message.setRentalAgreementRequestId(188);
		message.setAdditionalInfo("Test");
		message.setBenefits(Arrays.asList(InsuranceBenefit.BRAKEDOWNCOVER));
		message.setInsuranceType(InsuranceType.PARTIAL);
		CustomerData customerData = new CustomerData();
		customerData.setName("Robert Voscort");
		customerData.setAddress("TestAddress");
		customerData.setCompany("Capitol");
		customerData.setEmail("robert.voscort@capitol.to");
		customerData.setPhoneNumber("0190666666");
		message.setCustomerData(customerData);
		de.unimuenster.wi.wfm.sharedLib.data.CarData car1 = new de.unimuenster.wi.wfm.sharedLib.data.CarData();
		car1.setLicenseNumber("TR-L-2608");
		car1.setHsn("489032");
		car1.setTsn("8593402");
		de.unimuenster.wi.wfm.sharedLib.data.CarData car2 = new de.unimuenster.wi.wfm.sharedLib.data.CarData();
		car2.setLicenseNumber("MS-X-320");
		car2.setHsn("49302");
		car2.setTsn("520358");
		message.setCarsData(Arrays.asList(car1, car2));

		RestHelper.SendMessage("http://localhost:8080/engine-rest/engine/default/message", CapitolREST.NewStandardRentalAgreement(message));
	}

}
