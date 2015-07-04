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
	private String classKH;
	private String classTK;
	private String classVK;
	private String year;
	private String imageUrl;
	
	public CarInformation(String pHSN, String pTSN, String pYear) {
		setHsn(pHSN);
		setTsn(pTSN);
		setName("");
		setEnginePower("");
		setEngineSize("");
		setFuelType("");
		setClassKH("");
		setClassTK("");
		setClassVK("");
		setYear(pYear);
		setImageUrl("http://placehold.it/250x250?text=No+Image");
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
	public String getClassKH() {
		return classKH;
	}

	public void setClassKH(String classKH) {
		this.classKH = classKH;
	}

	public String getClassTK() {
		return classTK;
	}

	public void setClassTK(String classTK) {
		this.classTK = classTK;
	}

	public String getClassVK() {
		return classVK;
	}

	public void setClassVK(String classVK) {
		this.classVK = classVK;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	
}
