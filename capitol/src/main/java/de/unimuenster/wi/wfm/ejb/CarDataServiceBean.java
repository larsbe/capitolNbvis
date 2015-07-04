package de.unimuenster.wi.wfm.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.unimuenster.wi.wfm.entitiy.CarData;

@Stateless
public class CarDataServiceBean {
	
	@PersistenceContext
	protected EntityManager em;
	
	public CarData createCarData(CarData carData) {
		em.persist(carData);
		em.flush();
		return carData;
	}

}
