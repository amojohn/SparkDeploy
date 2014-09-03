package com.hansight.deploy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansight.deploy.dao.HostDAO;
import com.hansight.deploy.entity.Host;
import com.hansight.deploy.service.HostService;

@Service("hostService")
public class HostServiceBean extends BaseService<Host, Long> implements
		HostService {
	private HostDAO hostDAO;

	public HostDAO getHostDAO() {
		return hostDAO;
	}

	@Autowired
	public void setHostDAO(HostDAO hostDAO) {
		this.hostDAO = hostDAO;
		super.dao = hostDAO;
	}

}
