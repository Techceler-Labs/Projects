package com.contento3.security.role.service;
import com.contento3.common.assembler.AssemblerNew;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.role.model.Role;

public interface RoleAssembler extends AssemblerNew<RoleDto, Role>{
	Role dtoToDomain(RoleDto dto,Role domain);
}
