package com.hansight.deploy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansight.deploy.dao.WorkflowDAO;
import com.hansight.deploy.entity.Workflow;
import com.hansight.deploy.service.WorkflowService;

@Service("userService")
public class WorkflowServiceBean extends BaseService<Workflow, Long> implements
		WorkflowService {
	private WorkflowDAO workflowDAO;

	public WorkflowDAO getWorkflowDAO() {
		return workflowDAO;
	}

	@Autowired
	public void setWorkflowDAO(WorkflowDAO workflowDAO) {
		this.workflowDAO = workflowDAO;
		super.dao = workflowDAO;
	}

}
