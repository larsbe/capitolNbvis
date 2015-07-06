package de.unimuenster.wi.wfm.sharedLib.rest;

public class CamundaValueInfo {

	protected String objectTypeName;
	protected String serializationDataFormat;
	
	public CamundaValueInfo(String objectTypeName,
			String serializationDataFormat) {
		super();
		this.objectTypeName = objectTypeName;
		this.serializationDataFormat = serializationDataFormat;
	}
	public String getObjectTypeName() {
		return objectTypeName;
	}
	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}
	public String getSerializationDataFormat() {
		return serializationDataFormat;
	}
	public void setSerializationDataFormat(String serializationDataFormat) {
		this.serializationDataFormat = serializationDataFormat;
	}
	
	public String toString() {
		return "{\"objectTypeName\": \""+objectTypeName+"\", \"serializationDataFormat\": \""+serializationDataFormat+"\"}";
	}
	
}
