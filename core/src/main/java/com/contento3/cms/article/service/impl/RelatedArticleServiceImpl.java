package com.contento3.cms.article.service.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.article.dao.RelatedArticleDao;
import com.contento3.cms.article.dto.RelatedArticleDto;
import com.contento3.cms.article.model.RelatedArticleLinkPK;
import com.contento3.cms.article.service.RelatedArticleAssembler;
import com.contento3.cms.article.service.RelatedArticleService;
import com.contento3.common.exception.EntityAlreadyFoundException;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class RelatedArticleServiceImpl implements RelatedArticleService{
	
	private RelatedArticleAssembler  relatedArticleAssembler;
	private RelatedArticleDao relatedArticleDao;
	public RelatedArticleServiceImpl(final RelatedArticleAssembler relatedArticleAssembler,
			RelatedArticleDao relatedArticleDao) {
		// TODO Auto-generated constructor stub
		this.relatedArticleAssembler = relatedArticleAssembler;
		this.relatedArticleDao	= relatedArticleDao;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void create(RelatedArticleDto dto)
			throws EntityAlreadyFoundException {
		// TODO Auto-generated method stub
		
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public 	Collection<RelatedArticleDto> findRelatedArticles(Integer articleId){
		return relatedArticleAssembler.domainsToDtos(relatedArticleDao.findRelatedArticles(articleId));
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void deleteRelatedArticle(Integer articleId) {
		relatedArticleDao.deleteRelatedArticle(articleId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void deleteRelatedArticles(Integer articleId,
			Collection<Integer> relatedArticleIds) {
		// TODO Auto-generated method stub
		relatedArticleDao.deleteRelatedArticles(articleId, relatedArticleIds);
		
	}

}