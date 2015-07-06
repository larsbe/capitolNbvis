package de.unimuenster.wi.wfm.ejb;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.unimuenster.wi.wfm.persistence.CarData;
import de.unimuenster.wi.wfm.persistence.Customer;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;

@Stateless
public class CarDataServiceBean {

	@PersistenceContext
	protected EntityManager em;

	public CarData createCarData(CarData CarData) {
		em.persist(CarData);
		return CarData;
	}
	
	public Collection<CarData> createCarDataFromMessage(RentalAgreementMessage rentalAgreementMsg) {
		Collection<CarData> carsData = new ArrayList<CarData>();
		
		for(de.unimuenster.wi.wfm.sharedLib.data.CarData carData : rentalAgreementMsg.getCarsData()) {
			CarData carDataEntity = new CarData();
			carDataEntity.setHsn(carData.getHsn());
			carDataEntity.setTsn(carData.getTsn());
			carDataEntity.setLicenseNumber(carData.getLicenseNumber());
			carDataEntity.setYear(carData.getYear());
			
			carDataEntity = this.createCarData(carDataEntity);
			carsData.add(carDataEntity);
		}
		return carsData;
	}
	
	
	public CarData getCarDataByName(String name) {
		Query q = em.createQuery("FROM CarData c WHERE c.name=:name", CarData.class)
			.setParameter("name", name);
			// return existing carData
			return (CarData) q.getSingleResult();
	}

	public Collection<CarData> getAllCarDatas() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CarData> cq = cb.createQuery(CarData.class);
		Root<CarData> rootEntry = cq.from(CarData.class);
		return em.createQuery(cq.select(rootEntry)).getResultList();
	}

	public CarData getCarData(long id) {
		System.out.println("....getCarData(" + id + ")");
		System.out.println("....try to find");
		CarData carData = em.find(CarData.class, id);
		System.out.println("...found following: " + carData);
		if (carData == null)
			throw new IllegalArgumentException(String.format(
					"CarData with ID %s not found", id));
		return carData;
	}

	public CarData merge(CarData carData) {
		// Merge detached entity with current persisted state
		carData = em.merge(carData);


		return carData;
	}

}
