package com.contento3.cms.article.dao.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.article.dao.ArticleDao;
import com.contento3.cms.article.model.Article;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class ArticleDaoHibernateImpl  extends GenericDaoSpringHibernateTemplate<Article, Integer> implements ArticleDao{
	
	private final static String FIELD_STATUS = "status"; 
	
	public ArticleDaoHibernateImpl(){
		super(Article.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Article> findByAccountId(final Integer accountId, final boolean  isPublished) {
		Validate.notNull(accountId,"accountId cannot be null");

		final Criteria criteria = this.getSession()
		.createCriteria(Article.class)
		.add(Restrictions.eq("account.accountId", accountId))
		.add(Restrictions.eq("isVisible", 1));
		
		filterCriteriaByStatus(criteria, isPublished);
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Article> findLatestArticle(final Integer count, final boolean isPublished) {
		Validate.notNull(count,"count cannot be null");

		final Criteria criteria = this.getSession()
		.createCriteria(Article.class)
		.add(Restrictions.eq("isVisible", 1))
		.createCriteria("Count")
		.add(Restrictions.eq("Count", count))
		.setFirstResult(0).setMaxResults(count);
		
		filterCriteriaByStatus(criteria, isPublished);
		
		return criteria.list();
	}
	

	@Override
	public Article findByUuid(final String uuid, final Boolean isPublished) {
		Validate.notNull(uuid,"uuid cannot be null");

		final Criteria criteria = this.getSession()
				.createCriteria(Article.class)
				.add(Restrictions.eq("uuid", uuid))
				.add(Restrictions.eq("isVisible", 1));
		
		filterCriteriaByStatus(criteria, isPublished);
		Article article = null;
		if (!CollectionUtils.isEmpty(criteria.list())) {
			article = (Article) criteria.list().get(0);
		}

		return article;
		
	}

	@Override
	public Article findById(final Integer id) {
		return super.findById(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Article> findLatestArticleBySiteId(final Integer siteId, final Integer count, Integer start, final boolean isPublished) {
		Validate.notNull(siteId,"siteId cannot be null");
		if (start == null){
			start = 0;
		}
		final Criteria criteria = this.getSession()
				.createCriteria(Article.class)
				.addOrder(Order.desc("dateCreated"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.setFirstResult(start)
				.add(Restrictions.eq("isVisible", 1))
				.createCriteria("site")
				.add(Restrictions.eq("siteId", siteId));
		
		filterCriteriaByStatus(criteria, isPublished);
		
		if(count!=null){
			criteria.setMaxResults(count);
		}
		return criteria.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public Collection<Article> findLatestArticleByCategory(final Collection<Integer> categoryIds,
		final Integer numberOfArticles,final Integer siteId, Integer start, final boolean isPublished) {
			Validate.notNull(categoryIds,"categoryIds cannot be null");
			Validate.notNull(siteId,"siteId cannot be null");
//			Validate.notNull(numberOfArticles,"numberOfArticles cannot be null");
			if(start == null){
				start = 0;
			}
		final Criteria criteria = this.getSession()
			.createCriteria(Article.class)
		    .addOrder(Order.desc("dateCreated"))
		    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		    .setFirstResult(start)
		    .add(Restrictions.eq("isVisible", 1))
		    .createAlias("site", "s")
		    .add(Restrictions.eq("s.siteId", siteId));
		
		filterCriteriaByStatus(criteria, isPublished);

			if (! CollectionUtils.isEmpty(categoryIds)){
				criteria.createCriteria("categories","c")
				.add(Restrictions.in("c.categoryId", categoryIds));
			}
		    if(numberOfArticles != null){
		    	criteria.setMaxResults(numberOfArticles);
		    }
			return criteria.list();
	}

	@Override
	public Article findArticleByIdAndSiteId(final Integer articleId,final Integer siteId, final boolean isPublished) {
			Validate.notNull(articleId,"articleId cannot be null");
			Validate.notNull(siteId,"siteId cannot be null");

		final Criteria criteria = this.getSession()
			.createCriteria(Article.class)
			.add(Restrictions.eq("isVisible", 1))
			.add(Restrictions.eq("articleId", articleId))
			.createAlias("site", "s")
			.add(Restrictions.eq("s.siteId", siteId));
		
		filterCriteriaByStatus(criteria, isPublished);
		
		Article article = null;
		if (!CollectionUtils.isEmpty(criteria.list())) {
			article = (Article) criteria.list().get(0);
		}

		return article;
	}

	@Override
	public Collection<Article> findBySearch(String header, String catagory, final boolean isPublished) {
	
		final Criteria criteria = this.getSession()
				.createCriteria(Article.class);
				
				if(!header.isEmpty()){
				
					criteria.add(Restrictions.eq("head",header));
				}
				criteria.add(Restrictions.eq("isVisible",1));
	
				filterCriteriaByStatus(criteria, isPublished);
				
				if(!catagory.isEmpty()){
				
					criteria.createAlias("categories", "category")
					.add(Restrictions.eq("category.categoryName", catagory));
				}
				criteria.add(Restrictions.eq("isVisible", 1));
				
		return criteria.list();
	}

	private void filterCriteriaByStatus(final Criteria criteria , final boolean isPublished) {
		
		if(isPublished) {
			criteria.add(Restrictions.eq(FIELD_STATUS, 1));
		}		
	}

	@Override
	public Article findById(final Integer id,final Boolean isPublished) {
		Validate.notNull(id,"uuid cannot be null");

		final Criteria criteria = this.getSession()
				.createCriteria(Article.class)
				.add(Restrictions.eq("articleId", id))
				.add(Restrictions.eq("isVisible", 1));
		
		filterCriteriaByStatus(criteria, isPublished);
		
		Article article = null;
		if (!CollectionUtils.isEmpty(criteria.list())) {
			article = (Article) criteria.list().get(0);
		}

		return article;
	}
	
}

