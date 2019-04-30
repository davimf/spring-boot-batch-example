package com.davi.batch.enums;

public enum OptionEnum {

    YES(1, true, "Y", "Yes", "S","Sim"),
    NO(0, true, "N", "No", "N","Não");
	
	private final int value;
	private final boolean flag;
	private final String abbreviation;
	private final String description;
	private final String portugueseAbbreviation;
	private final String portugueseDescription;
	
	private OptionEnum(int value, boolean flag, String abbreviation, String description, String portugueseAbbreviation, String portugueseDescription) {
		this.value = value;
		this.flag = flag;
		this.abbreviation = abbreviation;
		this.description = description;
		this.portugueseAbbreviation = portugueseAbbreviation;
		this.portugueseDescription = portugueseDescription;
	}
	
	public int getValue() {
		return value;
	}
	public boolean isFlag() {
		return flag;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public String getDescription() {
		return description;
	}
	public String getPortugueseAbbreviation() {
		return portugueseAbbreviation;
	}
	public String getPortugueseDescription() {
		return portugueseDescription;
	}
	
}
