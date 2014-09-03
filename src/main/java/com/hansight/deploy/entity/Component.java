package com.hansight.deploy.entity;

public class Component {
	private Long id;
	private ComponentType type;
	private String role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ComponentType getType() {
		return type;
	}

	public void setType(ComponentType type) {
		this.type = type;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
