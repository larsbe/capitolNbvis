package de.unimuenster.wi.wfm.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.unimuenster.wi.wfm.entitiy.CarData;
import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;

@Stateless
public class CarDataServiceBean {
	
	@PersistenceContext
	protected EntityManager em;
	
	public CarData createCarData(CarData carData) {
		em.persist(carData);
		em.flush();
		return carData;
	}

	public void createCarDataFromMessage(RentalAgreementMessage rentalAgreementMsg, InsuranceContract contract) {
		for(de.unimuenster.wi.wfm.sharedLib.data.CarData carData : rentalAgreementMsg.getCarsData()) {
			CarData carDataEntity = new CarData();
			carDataEntity.setInsuranceContract(contract);
			carDataEntity.setHsn(carData.getHsn());
			carDataEntity.setTsn(carData.getTsn());
			carDataEntity.setLicenseNumber(carData.getLicenseNumber());
			this.createCarData(carDataEntity);
		}
	}
}
