package com.hansight.deploy.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.hansight.deploy.entity.Action;

public interface ActionDAO extends PagingAndSortingRepository<Action, Long> {

}
