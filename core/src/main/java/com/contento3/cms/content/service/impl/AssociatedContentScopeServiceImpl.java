package com.contento3.cms.content.service.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.content.dao.AssociatedContentScopeDao;
import com.contento3.cms.content.dto.AssociatedContentScopeDto;
import com.contento3.cms.content.service.AssociatedContentScopeAssembler;
import com.contento3.cms.content.service.AssociatedContentScopeService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;

@Transactional(readOnly = false , propagation = Propagation.REQUIRES_NEW)
public class AssociatedContentScopeServiceImpl implements AssociatedContentScopeService {

	/**
	 * Assembler for conversion
	 */
	private AssociatedContentScopeAssembler associatedContentScopeAssembler;
	
	/**
	 * AssociatedContentScopeDao for Dao related activity
	 */
	private AssociatedContentScopeDao associatedContentScopeDao;
	
	public AssociatedContentScopeServiceImpl(final AssociatedContentScopeAssembler associatedContentScopeAssembler,
			final AssociatedContentScopeDao associatedContentScopeDao) {
		this.associatedContentScopeAssembler = associatedContentScopeAssembler;
		this.associatedContentScopeDao = associatedContentScopeDao;
	}
	
	@Override
	public Object create(final AssociatedContentScopeDto dto)
			throws EntityAlreadyFoundException {
		return associatedContentScopeDao.persist(associatedContentScopeAssembler.dtoToDomain(dto));
	}

	@Override
	public void delete(final AssociatedContentScopeDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		associatedContentScopeDao.delete(associatedContentScopeAssembler.dtoToDomain(dtoToDelete));

	}

}