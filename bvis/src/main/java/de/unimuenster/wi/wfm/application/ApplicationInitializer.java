package de.unimuenster.wi.wfm.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.BusinessProcess;

import de.unimuenster.wi.wfm.persistence.CarData;
import de.unimuenster.wi.wfm.persistence.CarPool;
import de.unimuenster.wi.wfm.persistence.Customer;
import de.unimuenster.wi.wfm.persistence.RentalAgreementContract;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequestType;
import de.unimuenster.wi.wfm.persistence.StandardAgreementType;

@Startup
@Singleton
public class ApplicationInitializer {

	@PersistenceContext
	EntityManager em;
	
	@Inject
	private BusinessProcess businessProcess;


	@PostConstruct
	public void initialise() {

		
		// create Customers
		Customer c1 = new Customer();
		c1.setName("David Jauernig");
		c1.setEmail("david.jauernig@googlemail.com");
		c1.setAddress("Gruppenarbeitsraum");
		em.persist(c1);

		Customer c2 = new Customer();
		c2 = new Customer();
		c2.setName("Andi Hermann");
		c2.setEmail("a_herm14@uni-muenster.de");
		c2.setAddress("McFit");
		em.persist(c2);
		
		
		// create some Cars
		
		CarData carData61 = new CarData();
		carData61.setName("Audi A4 2.8");
		carData61.setHsn("0588");
		carData61.setTsn("597");
		carData61.setYear("2011");
		carData61.setLicenseNumber("WfM WfM WfM");
		carData61 = em.merge(carData61);
		
		CarData carData62 = new CarData();
		carData62.setName("Audi A8 4.2");
		carData62.setHsn("0588");
		carData62.setTsn("581");
		carData62.setYear("2014");
		carData62.setLicenseNumber("WfM");
		carData62 = em.merge(carData62);
		
		CarData carData63 = new CarData();
		carData63.setName("BMW 325 I Cabrio");
		carData63.setHsn("0005");
		carData63.setTsn("531");
		carData63.setYear("2012");
		carData63.setLicenseNumber("WfM everyday");
		carData63 = em.merge(carData63);
		
		CarData carData64 = new CarData();
		carData64.setName("BMW 530 D");
		carData64.setHsn("0005");
		carData64.setTsn("761");
		carData64.setYear("2013");
		carData64.setLicenseNumber("WfM-4-Life");
		carData64 = em.merge(carData64);
		
		CarData carData65 = new CarData();
		carData65.setName("Audi S3 CABRIO 2.0 TFSI");
		carData65.setHsn("0588");
		carData65.setTsn("BAS");
		carData65.setYear("2013");
		carData65.setLicenseNumber("MS-WFM-1337");
		carData65 = em.merge(carData65);
		
		CarData carData6 = new CarData();
		carData6.setName("Nissan MICRA 1.2");
		carData6.setHsn("1329");
		carData6.setTsn("ACJ");
		carData6.setYear("2013");
		carData6.setLicenseNumber("WfM-4-Ever");
		carData6 = em.merge(carData6);
		

		RentalAgreementRequest req = new RentalAgreementRequest();
		req.setRentalAgreementRequestType(RentalAgreementRequestType.INDIVIDUAL);
		req.setCustomer(c2);
		req.setDate(new Date());
		req.setRequirementsOfCustomer("Ich habe tolle Anforderungen");
		req = em.merge(req);
		
		Collection<CarPool> carPool = new ArrayList<CarPool>();
		CarPool carPool_Item = new CarPool();
		carPool_Item.setCarData(carData61);
		carPool_Item.setQuantity(3);
		carPool_Item.setRentalAgreementRequest(req);
		carPool.add(carPool_Item);
		
		carPool_Item.setCarData(carData65);
		carPool_Item.setQuantity(7);
		carPool_Item.setRentalAgreementRequest(req);
		carPool.add(carPool_Item);
		
		req.setCarPool(carPool);
		req = em.merge(req);
		
		RentalAgreementContract contract = new RentalAgreementContract();
		contract.setCustomer(c1);
		contract.setDate(new Date());
		contract.setRentalAgreementRequest(req);
		contract = em.merge(contract);
		

		
		// ------ store business process variables -------
		businessProcess.setVariable("contractNoBVIS", contract.getId());

		
//		 create StandardAgreementTypes
		StandardAgreementType sat1 = new StandardAgreementType();
		sat1.setName("Bronze");
		sat1.setDetail("Bronze Contract details");
		em.persist(sat1);
		
		StandardAgreementType sat2 = new StandardAgreementType();
		sat2.setName("Silver");
		sat2.setDetail("Silver Contract details");
		em.persist(sat2);
		
		StandardAgreementType sat3 = new StandardAgreementType();
		sat3.setName("Gold");
		sat3.setDetail("Gold Contract details");
		em.persist(sat3);
	}

}