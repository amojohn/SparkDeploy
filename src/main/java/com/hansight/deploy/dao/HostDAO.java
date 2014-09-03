package com.hansight.deploy.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.hansight.deploy.entity.Host;

public interface HostDAO extends PagingAndSortingRepository<Host, Long> {

}
