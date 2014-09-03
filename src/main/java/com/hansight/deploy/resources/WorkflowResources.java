package com.hansight.deploy.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hansight.deploy.service.WorkflowService;

@Controller
@Scope("request")
@Path("/workflow")
public class WorkflowResources {
	@Autowired
	private WorkflowService workflowService;

	@GET
	public String listAll() {
		return "hello world";
	}

	public WorkflowService getWorkflowService() {
		return workflowService;
	}

	public void setWorkflowService(WorkflowService workflowService) {
		this.workflowService = workflowService;
	}

}
