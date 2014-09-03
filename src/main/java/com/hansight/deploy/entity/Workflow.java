package com.hansight.deploy.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "T_WORKFLOW")
public class Workflow {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String type;
	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "start_id")
	private Action start;

	@OneToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY, mappedBy = "workflow")
	private List<Action> actions;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
