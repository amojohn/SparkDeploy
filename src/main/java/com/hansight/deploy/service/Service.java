package com.hansight.deploy.service;

import java.io.Serializable;

public interface Service<T, ID extends Serializable> {
	T save(T t);

	T mod(T t);

	void del(ID id);

	T get(ID id);

	Iterable<T> getAll();

	Iterable<T> save(Iterable<T> arr);
}
