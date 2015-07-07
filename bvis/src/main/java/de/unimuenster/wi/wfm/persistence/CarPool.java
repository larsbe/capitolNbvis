package de.unimuenster.wi.wfm.persistence;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class CarPool extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	protected int quantity;
	@ManyToOne
	protected CarData carData;

	@ManyToOne
	@NotNull(message = "You have to enter a RentalAgreementRequest")
	protected RentalAgreementRequest rentalAgreementRequest;

	public RentalAgreementRequest getRentalAgreementRequest() {
		return this.rentalAgreementRequest;
	}
	
	public void setRentalAgreementRequest(RentalAgreementRequest rentalAgreementRequest) {
			if(this.rentalAgreementRequest == rentalAgreementRequest)
				return;
			if(this.rentalAgreementRequest != null)
				this.rentalAgreementRequest.carPool.remove(this.rentalAgreementRequest);
			this.rentalAgreementRequest = rentalAgreementRequest;
			if(rentalAgreementRequest != null)
				rentalAgreementRequest.addCarPool(this);
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public CarData getCarData() {
		return carData;
	}

	public void setCarData(CarData carData) {
		this.carData = carData;
	}
}