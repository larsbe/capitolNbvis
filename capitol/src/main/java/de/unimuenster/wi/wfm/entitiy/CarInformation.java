package de.unimuenster.wi.wfm.entitiy;

/**
 * @author JoGe
 *
 */
public class CarInformation {

	private String hsn;
	private String tsn;
	private String name;
	private String enginePower;
	private String engineSize;
	private String fuelType;
	
	public CarInformation(String pHSN, String pTSN) {
		setHsn(pHSN);
		setTsn(pTSN);
		setName("");
		setEnginePower("");
		setEngineSize("");
		setFuelType("");
	}
	
	public String getHsn() {
		return hsn;
	}
	public void setHsn(String hsn) {
		this.hsn = hsn;
	}
	public String getTsn() {
		return tsn;
	}
	public void setTsn(String tsn) {
		this.tsn = tsn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnginePower() {
		return enginePower;
	}
	public void setEnginePower(String enginePower) {
		this.enginePower = enginePower;
	}
	public String getEngineSize() {
		return engineSize;
	}
	public void setEngineSize(String engineSize) {
		this.engineSize = engineSize;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	
}
