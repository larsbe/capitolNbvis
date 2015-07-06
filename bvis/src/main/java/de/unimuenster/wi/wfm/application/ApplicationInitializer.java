package de.unimuenster.wi.wfm.application;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.unimuenster.wi.wfm.persistence.CarData;
import de.unimuenster.wi.wfm.persistence.Customer;
import de.unimuenster.wi.wfm.persistence.RentalAgreementContract;
import de.unimuenster.wi.wfm.persistence.StandardAgreementType;

@Startup
@Singleton
public class ApplicationInitializer {

	@PersistenceContext
	EntityManager em;
	


	@PostConstruct
	public void initialise() {

		
		// create Customers
		Customer c1 = new Customer();
		c1.setName("David Jauernig");
		c1.setEmail("david.jauernig@googlemail.com");
		c1.setAddress("Gruppenarbeitsraum");
		em.persist(c1);

		c1 = new Customer();
		c1.setName("Andi Hermann");
		c1.setEmail("a_herm14@uni-muenster.de");
		c1.setAddress("McFit");
		em.persist(c1);
		
		
		// create some Cars
		
		CarData carData61 = new CarData();
		carData61.setName("Audi A4 2.8");
		carData61.setHsn("0588");
		carData61.setTsn("597");
		carData61.setYear("2011");
		carData61.setLicenseNumber("WfM WfM WfM");
		em.persist(carData61);
		
		CarData carData62 = new CarData();
		carData62.setName("Audi A8 4.2");
		carData62.setHsn("0588");
		carData62.setTsn("581");
		carData62.setYear("2014");
		carData62.setLicenseNumber("WfM");
		em.persist(carData62);
		
		CarData carData63 = new CarData();
		carData63.setName("BMW 325 I Cabrio");
		carData63.setHsn("0005");
		carData63.setTsn("531");
		carData63.setYear("2012");
		carData63.setLicenseNumber("WfM everyday");
		em.persist(carData63);
		
		CarData carData64 = new CarData();
		carData64.setName("BMW 530 D");
		carData64.setHsn("0005");
		carData64.setTsn("761");
		carData64.setYear("2013");
		carData64.setLicenseNumber("WfM-4-Life");
		em.persist(carData64);
		
		CarData carData65 = new CarData();
		carData65.setName("Audi S3 CABRIO 2.0 TFSI");
		carData65.setHsn("0588");
		carData65.setTsn("BAS");
		carData65.setYear("2013");
		carData65.setLicenseNumber("MS-WFM-1337");
		em.persist(carData65);
		
		CarData carData6 = new CarData();
		carData6.setName("Nissan MICRA 1.2");
		carData6.setHsn("1329");
		carData6.setTsn("ACJ");
		carData6.setYear("2013");
		carData6.setLicenseNumber("WfM-4-Ever");
		em.persist(carData6);
		

		
		RentalAgreementContract contract = new RentalAgreementContract();
		contract.setCustomer(c1);
		contract.setDate(new Date());
		em.persist(contract);
		

		
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