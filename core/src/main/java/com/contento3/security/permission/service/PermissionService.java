package com.contento3.security.permission.service;

import java.util.Collection;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.service.SimpleService;
import com.contento3.security.permission.dto.PermissionDto;

public interface PermissionService extends SimpleService<PermissionDto>{
	Collection<PermissionDto> findPermissionByEntityId(final Integer entityId);
	PermissionDto findById(Integer id);
	Collection<PermissionDto> findAllPermissions();
	void update(PermissionDto dtoToUpdate) throws EntityAlreadyFoundException;
	Collection<PermissionDto> findPermissionByRoleId(Integer roleId);
	void delete(PermissionDto dtoToDelete) throws EntityCannotBeDeletedException;
}
