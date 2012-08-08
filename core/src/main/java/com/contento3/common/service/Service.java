package com.contento3.common.service;

import com.contento3.common.exception.EntityAlreadyFoundException;

public interface Service<T> {

	public Object create(T dto) throws EntityAlreadyFoundException;

}
