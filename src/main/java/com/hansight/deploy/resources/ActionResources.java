package com.hansight.deploy.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hansight.deploy.service.ActionService;

@Controller
@Scope("request")
@Path("/action")
public class ActionResources {
	@Autowired
	private ActionService actionService;

	@GET
	public String listAll() {
		return "hello world";
	}

	public ActionService getActionService() {
		return actionService;
	}

	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}

}
