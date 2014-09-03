package com.hansight.deploy.service.impl;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import com.hansight.deploy.service.Service;

public abstract class BaseService<T, ID extends Serializable> implements
		Service<T, ID> {
	protected CrudRepository<T, ID> dao;

	@Override
	public T save(T t) {
		return dao.save(t);
	}

	@Override
	public T mod(T t) {
		return dao.save(t);
	}

	@Override
	public void del(ID id) {
		dao.delete(id);
	}

	@Override
	public T get(ID id) {
		return dao.findOne(id);
	}

	@Override
	public Iterable<T> getAll() {
		return dao.findAll();
	}

	@Override
	public Iterable<T> save(Iterable<T> arr) {
		return dao.save(arr);
	}

}
