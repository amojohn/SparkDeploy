package com.hansight.deploy.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "T_ACTION")
public class Action {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String shell;
	private String param;
	private Long ok;
	private Long error;
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "workflowd_id")
	private Workflow workflow;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Long getOk() {
		return ok;
	}

	public void setOk(Long ok) {
		this.ok = ok;
	}

	public Long getError() {
		return error;
	}

	public void setError(Long error) {
		this.error = error;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
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

	public String getShell() {
		return shell;
	}

	public void setShell(String shell) {
		this.shell = shell;
	}

}
