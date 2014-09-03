package com.hansight.deploy.core.conf.persist;

public class Value {
	private String value;
	private String description;
	private boolean finalType;

	public Value() {
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isFinal() {
		return finalType;
	}

	public void setFinal(boolean finalType) {
		this.finalType = finalType;
	}
}