package com.contento3.cms.page.category.service;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.common.service.Service;

public interface CategoryService extends Service<CategoryDto>{
	
	/**
	 * Finds the category by categoryName
	 * @param categoryName
	 * @return CategoryDto
	 */
	CategoryDto findCategoryByName(final String categoryName);
	

}//end
