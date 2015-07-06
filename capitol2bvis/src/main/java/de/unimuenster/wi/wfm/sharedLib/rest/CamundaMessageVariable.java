package de.unimuenster.wi.wfm.sharedLib.rest;

public class CamundaMessageVariable {

	protected String value = "";
	protected CamundaVariableType type;
	protected CamundaValueInfo valueInfo;
	
	public CamundaMessageVariable(String value, CamundaVariableType type) {
		this.value = value;
		this.type = type;
	}
	
	public CamundaMessageVariable(String value, CamundaVariableType type, String objectTypeName, String serializationDataFormat) {
		this.value = value;
		this.type = type;
		this.valueInfo = new CamundaValueInfo(objectTypeName, serializationDataFormat);
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public CamundaVariableType getType() {
		return type;
	}
	public void setType(CamundaVariableType type) {
		this.type = type;
	}
	public CamundaValueInfo getValueInfo() {
		return valueInfo;
	}
	public void setValueInfo(CamundaValueInfo valueInfo) {
		this.valueInfo = valueInfo;
	}
	
	public String toString() {
		if(valueInfo != null)
			return "{\"type\": \""+type+"\", \"value\": \""+RestHelper.escape(value)+"\", \"valueInfo\": "+valueInfo.toString()+"}";
		else
			return "{\"type\": \""+type+"\", \"value\": \""+RestHelper.escape(value)+"\"}";
	}
	
}
