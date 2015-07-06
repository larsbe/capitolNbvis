package de.unimuenster.wi.wfm.sharedLib.rest;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CamundaMessage {

	/* The name of the message to deliver. */
	protected String messageName;
	/*
	 * Used for correlation of process instances that wait for incoming
	 * messages. Will only correlate to executions that belong to a process
	 * instance with the provided business key.
	 */
	protected String businessKey;
	/*
	 * Used for correlation of process instances that wait for incoming
	 * messages. Has to be a JSON object containing key-value pairs that are
	 * matched against process instance variables during correlation. Each key
	 * is a variable name and each value a JSON variable value object with the
	 * following properties.
	 */
	protected Map<String, CamundaMessageVariable> correlationKeys;
	/*
	 * A map of variables that is injected into the triggered execution or
	 * process instance after the message has been delivered. Each key is a
	 * variable name and each value a JSON variable value object with the
	 * following properties.
	 */
	protected Map<String, CamundaMessageVariable> processVariables;
	/*
	 * A Boolean value that indicates whether the message should be correlated
	 * to exactly one entity or multiple entities. If the value is set to false
	 * the message will be correlated to exactly one entity (execution or
	 * process definition). If the value is set to true the message will be
	 * correlated to multiple executions and a process definition that can be
	 * instantiated by this message in one go.
	 */
	protected boolean all;

	public CamundaMessage(String messageName) {
		this.messageName = messageName;
		businessKey = null;
		correlationKeys = new HashMap<String, CamundaMessageVariable>();
		processVariables = new HashMap<String, CamundaMessageVariable>();
		all = false;
	}

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public Map<String, CamundaMessageVariable> getCorrelationKeys() {
		return correlationKeys;
	}
	
	public void addCorrelationKey(String key, CamundaMessageVariable value) {
		getCorrelationKeys().put(key, value);
	}

	public Map<String, CamundaMessageVariable> getProcessVariables() {
		return processVariables;
	}
	
	public void addProcessVariables(String key, CamundaMessageVariable value) {
		getProcessVariables().put(key, value);
	}

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();		
		sb.append("{\"messageName\" : \""+messageName+"\",");
		sb.append("\"businessKey\" : \""+businessKey+"\",");
		sb.append("\"correlationKeys\" : ");
		sb.append(ListToJson(correlationKeys));
		sb.append(",");
		sb.append("\"processVariables\" : ");
		sb.append(ListToJson(processVariables));
		sb.append(",");
		sb.append("\"all\" : \""+all+"\"");
		sb.append("}");
		return sb.toString();
	}
	
	private String ListToJson(Map<String, CamundaMessageVariable> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		Iterator<Map.Entry<String, CamundaMessageVariable >> it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, CamundaMessageVariable > pair = it.next();
	        sb.append("\""+pair.getKey()+"\" : ");
	        sb.append(pair.getValue().toString());
	        if(it.hasNext())
	        	sb.append(", ");
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    sb.append("}");
		return sb.toString();
	}
	
}
