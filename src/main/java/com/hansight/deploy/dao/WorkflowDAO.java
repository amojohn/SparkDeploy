package com.hansight.deploy.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.hansight.deploy.entity.Workflow;

public interface WorkflowDAO extends PagingAndSortingRepository<Workflow, Long> {

}
