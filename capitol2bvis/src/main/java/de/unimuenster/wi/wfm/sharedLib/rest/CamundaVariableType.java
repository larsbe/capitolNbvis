package de.unimuenster.wi.wfm.sharedLib.rest;

public enum CamundaVariableType {

	LONG("Long"), DOUBLE("Double"), STRING("String"), OBJECT("Object"), INTEGER("Integer");

	private final String name;

	private CamundaVariableType(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}

	public String toString() {
		return name;
	}

}
