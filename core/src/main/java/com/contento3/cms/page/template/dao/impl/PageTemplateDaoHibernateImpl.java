package com.contento3.cms.page.template.dao.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.contento3.cms.page.section.model.PageSectionTypeEnum;
import com.contento3.cms.page.template.dao.PageTemplateDao;
import com.contento3.cms.page.template.model.PageTemplate;
import com.contento3.cms.page.template.model.PageTemplatePK;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class PageTemplateDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<PageTemplate,PageTemplatePK>  implements PageTemplateDao {

	public PageTemplateDaoHibernateImpl() {
		super(PageTemplate.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PageTemplate> findByPageAndPageSectionType(final Integer pageId,final Integer pageSectionTypeId) {
		Validate.notNull(pageId,"pageId cannot be null");
		Validate.notNull(pageSectionTypeId,"pageSectionTypeId cannot be null");
		
		Criteria criteria = this.getSession()
		.createCriteria(PageTemplate.class)
		.add(Restrictions
		.eq("primaryKey.page.pageId", pageId)).add(Restrictions
		.eq("primaryKey.sectionType.id", pageSectionTypeId));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PageTemplate> findByPageAndPageSectionType(final Integer pageId,final PageSectionTypeEnum pageSectionType) {
		Validate.notNull(pageId,"pageId cannot be null");
		Validate.notNull(pageSectionType,"pageSectionType cannot be null");
		
		Criteria criteria = this.getSession()
		.createCriteria(PageTemplate.class);
		
		Criterion pageCriteria = Restrictions.eq("primaryKey.page.pageId", pageId);
		Criterion pageSectionTypeCriteria = Restrictions.eq("primaryKey.sectionType.id", 7);
		
		criteria.add(Restrictions.and(pageCriteria, pageSectionTypeCriteria));
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PageTemplate> findByPageId(final Integer pageId) {
		Validate.notNull(pageId,"pageId cannot be null");
		
		Criteria criteria = this.getSession()
		.createCriteria(PageTemplate.class)
		.add(Restrictions
		.eq("primaryKey.page.pageId", pageId));

		return criteria.list();
	}

	@Override
	public Collection<PageTemplate> findByTemplateId(Integer templateId) {
		Validate.notNull(templateId,"templateId cannot be null");
		
		Criteria criteria = this.getSession()
		.createCriteria(PageTemplate.class)
		.add(Restrictions
		.eq("primaryKey.template.templateId", templateId));

		return criteria.list();
	}

}
