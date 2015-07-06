package de.unimuenster.wi.wfm.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
			carDataEntity.setYear(Integer.parseInt(carData.getYear()));
			this.createCarData(carDataEntity);
		}
	}
	
	public CarData getCarData(String licenseNumber){
		CarData carData = new CarData();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CarData> cq = cb.createQuery(CarData.class);
		Root<CarData> root=cq.from(CarData.class);
		cq.where(cb.equal(root.<String>get("licenseNumber"),licenseNumber));
		try {
			CarData c=em.createQuery(cq).getSingleResult();
			carData = c;
		}
		catch (  Exception e) {
			return carData;
		}
		return carData;
	}
}
