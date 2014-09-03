package com.hansight.deploy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansight.deploy.dao.ActionDAO;
import com.hansight.deploy.entity.Action;
import com.hansight.deploy.service.ActionService;

@Service("actionService")
public class ActionServiceBean extends BaseService<Action, Long> implements
		ActionService {
	private ActionDAO actionDAO;

	public ActionDAO getActionDAO() {
		return actionDAO;
	}

	@Autowired
	public void setActionDAO(ActionDAO actionDAO) {
		this.actionDAO = actionDAO;
		super.dao = actionDAO;
	}

}
