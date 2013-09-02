package com.contento3.site.template.loader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.log4j.Logger;

import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.site.template.assembler.Assembler;
import com.contento3.site.template.dto.TemplateContentDto;

import freemarker.cache.TemplateLoader;

/**
 * Used to load freemarker templates from database.
 * @author hammad.afridi
 *
 */
public class FreemarkerTemplateLoader implements TemplateLoader {

	private static final Logger LOGGER = Logger.getLogger(FreemarkerTemplateLoader.class);

	private Assembler pageAssembler;

	private TemplateService templateService;
	
	/**
	 * Site id for which we are processing the page.
	 */
	private Integer siteId;

	@Override
	public void closeTemplateSource(Object arg0) throws IOException {
	}

	@Override
	public Object findTemplateSource(String path) throws IOException {
		TemplateContentDto dto=null;
		String[] pathSplitter = path.split(":");

		// Check if its an article
		// Get the global template for article
		if (pathSplitter[0].contains("article")){
			try {
				pathSplitter = path.split("/");
				dto = loadArticleTemplate(pathSplitter[0]);
			} catch (Exception e) {
				throw new IOException(String.format("Requested template [%s] not found",pathSplitter[0]),e);
			}
		}
		else { //Otherwise its an actual page
			//A path can be a PAGE path or a TEMPLATE path.
			//It will be a page path when THE MAIN PAGE is requested. 
			//If this is a main page then there can be different cases:
			//			a. The page has a custom layout i.e. a single template.
			//
			//          b. The page can have multiple page section 
			//			with each section having single template.
			//
			//          c. The page can have multiple page section with 
			//			each section having multiple template.
			
			//Page request should have 2 elements with 
			//format: "pageuri:siteid_locale", e.g. /mypage:1_en
			//1. pageuri
			//2. siteid
	
			if (pathSplitter.length==2){
				try {
					siteId = Integer.parseInt(pathSplitter[1].split("_")[0]);
					dto = pageAssembler.assemble(siteId,String.format("/%s",pathSplitter[0]));
				} catch (PageNotFoundException e) {
					throw new IOException("Requested page not found",e);
				}
				catch (NumberFormatException e) {
					//throw new NumberFormatException("Requested page with path ["+path+"]does not have the site component");
					return new TemplateContentDto();
				}
			}
			//Otherwise the path is actually a template 
			//path which is included in one of the template 
			//used above in one of the 3 ways, the format
			//is : templatepath_locale e.g. /path/to/template_en
			// We can safely ignore the pathSplitter 
			// and then try splitting by '_'
			else {
				try {
					pathSplitter = path.split("_");
					dto = pageAssembler.assembleInclude(siteId, pathSplitter[0]);
				} catch (Exception e) {
					throw new IOException(String.format("Requested template [%s] not found",path),e);
				}
			}
		}
		return dto;
	}

	private TemplateContentDto loadArticleTemplate(final String pathSplitter) throws PageNotFoundException{
		return pageAssembler.fetchDefaultTemplate("ARTICLE");
	}
	
	@Override
	public long getLastModified(Object arg0) {
		return -1;
	}

	@Override
	public Reader getReader(Object source, String encoding) throws IOException {
		if (null == ((TemplateContentDto)source).getContent())
			return new StringReader("");

		return new StringReader(((TemplateContentDto)source).getContent());
	}

	public void setPageAssembler(final Assembler pageAssembler){
		this.pageAssembler = pageAssembler;
	}
		
	public void setTemplateService(final TemplateService templateService){
		this.templateService = templateService;
	}

}
