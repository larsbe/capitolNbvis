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
	private int classKH;
	private int classTK;
	private int classVK;
	private String year;
	private String imageUrl;
	
	public CarInformation(String pHSN, String pTSN, String pYear) {
		setHsn(pHSN);
		setTsn(pTSN);
		setName("");
		setEnginePower("");
		setEngineSize("");
		setFuelType("");
		setClassKH(0);
		setClassTK(0);
		setClassVK(0);
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
	public int getClassKH() {
		return classKH;
	}

	public void setClassKH(int classKH) {
		this.classKH = classKH;
	}

	public int getClassTK() {
		return classTK;
	}

	public void setClassTK(int classTK) {
		this.classTK = classTK;
	}

	public int getClassVK() {
		return classVK;
	}

	public void setClassVK(int classVK) {
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
