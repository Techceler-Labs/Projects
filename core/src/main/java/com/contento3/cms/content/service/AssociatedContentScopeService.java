package com.contento3.cms.content.service;

import java.util.Collection;

import com.contento3.cms.content.dto.AssociatedContentScopeDto;
import com.contento3.common.service.Service;

public interface AssociatedContentScopeService extends Service<AssociatedContentScopeDto> {

	public Collection<AssociatedContentScopeDto> allContentScope();
	
	/**
	 * Find content scope by id
	 * @param contentScopeId
	 * @return
	 */
	public AssociatedContentScopeDto findById(final Integer contentScopeId);
}
