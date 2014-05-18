package com.contento3.web.site;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.contento3.account.dto.AccountDto;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.common.dto.Dto;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.service.ImageService;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.common.helper.GenricEntityTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.content.image.ImageViewPopup;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.listener.SiteContentAssignerClickEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;

public class SiteContentAssignmentUIManager extends EntityListener  implements ClickListener{

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(SiteContentAssignmentUIManager.class);
	private final static String COLUNM_ARTICLE				= "article";
	private final static String COLUNM_IMAGES 				= "image";
	private final static String COLUNM_LIBRARY 				= "library";
	private final static String COLUNM_VIEW 				= "view";
	private final static String CONTENT_TYPE_ARTICLE 		= "Article";
	private final static String CONTENT_TYPE_IMAGE			= "Image";
	private final static String CONTENT_TYPE_VIDEO 			= "Video";
	private final static String CONTENT_TYPE_DOCUMENT 		= "Document";
	private final static String PICKER_TITLE_FOR_IMAGE 		= "Image assignment";
	private final static String PICKER_TITLE_FOR_ARTICLE 	= "Article assignment";


	private final TabSheet tabSheet;

	/**
	 * Helper use to load spring beans
	 */
	private final SpringContextHelper contextHelper;

	/**
	 * main layout for content assignment screen
	 */
	private VerticalLayout verticalLayout = new VerticalLayout();
	
	private VerticalLayout verticalLayoutForPopup = new VerticalLayout();

	private SiteService siteService;
	
	private ArticleService articleService;
	
	private ImageService imageService;

	private SiteDto siteDto = null;
	
	private Collection<Dto> assignedDtos;
	
	private ComboBox contentTypeComboBox;
	
	private String selectedType;
	
	private HashMap<Integer, ImageDto> images;
	
	public SiteContentAssignmentUIManager(TabSheet uiTabSheet,
			SpringContextHelper contextHelper) {
		
		tabSheet = uiTabSheet;
		this.contextHelper = contextHelper;
		this.siteService = (SiteService) contextHelper.getBean("siteService");
		this.articleService = (ArticleService) contextHelper.getBean("articleService");
		this.imageService = (ImageService) contextHelper.getBean("imageService");
	}

	public Component render(final Integer siteId){
		
		siteDto = siteService.findSiteById(siteId);
		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(true);

		Tab tab = tabSheet.addTab(verticalLayout);
		tab.setCaption("Site Content Assigner");
		tab.setClosable(true);
		tabSheet.setSelectedTab(verticalLayout);
		HorizontalLayout horizontal = new HorizontalLayout();
		//Pop-up that adds a new domain
		final FormLayout formLayout = new FormLayout();
		final Collection<String> contentTypeValue = new ArrayList<String>();
		contentTypeValue.add(CONTENT_TYPE_ARTICLE);
		contentTypeValue.add(CONTENT_TYPE_IMAGE);
		contentTypeValue.add(CONTENT_TYPE_VIDEO);
		contentTypeValue.add(CONTENT_TYPE_DOCUMENT);
		
		contentTypeComboBox = new ComboBox("Content Type",contentTypeValue);
		contentTypeComboBox.setImmediate(true);
		formLayout.addComponent(contentTypeComboBox);
				
		GridLayout grid = new GridLayout(1, 1);
		grid.addStyleName("bordertest");
		
		List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();
		listeners.add(new SiteContentAssignerClickEvent(this));
		
		ScreenToolbarBuilder builder = new ScreenToolbarBuilder(grid,"contentAsign",listeners);
		builder.build();
		VerticalLayout vertical = new VerticalLayout();
		vertical.addComponent(formLayout);
		horizontal.addComponent(vertical);
		horizontal.addComponent(grid);
		horizontal.setWidth(100,Unit.PERCENTAGE);
		
		horizontal.setExpandRatio(vertical, 8);
		horizontal.setExpandRatio(grid, 1);
		verticalLayout.addComponent(horizontal);
		verticalLayout.addComponent(new HorizontalRuler());
		return tabSheet;
	}

	
	@Override
	public void buttonClick(ClickEvent event) {

		Object contentType = contentTypeComboBox.getValue();
		if(contentType != null) {
			
			
			Collection<Dto> dtos = null;
			Collection<String> listOfColumns = new ArrayList<String>();
			String title = "";
			
			if( contentType.toString().equals(CONTENT_TYPE_ARTICLE) ) {
				
				selectedType = CONTENT_TYPE_ARTICLE;
				listOfColumns.add(COLUNM_ARTICLE);
				dtos = populateGenericDtoFromArticleDto(articleService.findByAccountId((Integer)SessionHelper.loadAttribute("accountId"), false));
				assignedDtos = populateGenericDtoFromArticleDto(articleService.findLatestArticleBySiteId(siteDto.getSiteId(),null,null, false));
				title = PICKER_TITLE_FOR_ARTICLE;
				
			} else if( contentType.toString().equals(CONTENT_TYPE_IMAGE) ) {
				
				selectedType = CONTENT_TYPE_IMAGE;
				listOfColumns.add(COLUNM_IMAGES);
				listOfColumns.add(COLUNM_LIBRARY);
				listOfColumns.add(GenricEntityTableBuilder.COLUNM_VIEW);
				
				Collection<ImageDto> imageList = imageService.findImageByAccountId((Integer)SessionHelper.loadAttribute("accountId"));
				dtos = populateGenericDtoFromImageDto(imageList);
				assignedDtos = populateGenericDtoFromImageDto(imageService.findLatestImagesBySiteId(siteDto.getSiteId(), 10));
				
				images = new HashMap<Integer, ImageDto>(imageList.size());
				
				for (ImageDto dto : imageList) {
					images.put(dto.getId(), dto);
				}
				title = PICKER_TITLE_FOR_IMAGE;
			}
			
			GenricEntityPicker contentPicker;
			contentPicker = new GenricEntityPicker(dtos,assignedDtos,listOfColumns,verticalLayoutForPopup,this,false);	
            this.setCaption(title);
			contentPicker.build();

		}
	}
	
		
	@Override
	public void updateList() {
		
		Collection<String> selectedItems =(Collection<String>) this.verticalLayoutForPopup.getData();
		if(selectedItems != null){
			if(selectedType.equals(CONTENT_TYPE_ARTICLE)) {
				updateListForArticle(selectedItems);
			} else if(selectedType.equals(CONTENT_TYPE_IMAGE)) {
				updateListForImage(selectedItems);
			}
		}
	}
	
	private void updateListForImage(Collection<String> selectedItems) {
		
		ImageDto imageDto = null;
		AccountDto accountDto = new AccountDto();
		accountDto.setAccountId((Integer)SessionHelper.loadAttribute("accountId"));
		for (String item : selectedItems) {
			Integer id = Integer.parseInt(item);
			imageDto = imageService.findImageByIdAndSiteId(id, siteDto.getSiteId());
			if(imageDto == null) {
				imageDto = imageService.findById(id);
				imageDto.getSiteDto().add(siteDto);
				imageDto.setAccountDto(accountDto);
				imageService.update(imageDto);
			} else if (assignedDtos.contains(((Dto)imageDto))) {
				assignedDtos.remove(((Dto)imageDto));
			}
		} 
		removeAssignedContentDtos(assignedDtos);
		Notification.show("Images saved successfully for site" + siteDto.getSiteName(), Notification.Type.TRAY_NOTIFICATION);
	}
	
	private void updateListForArticle(Collection<String> selectedItems) {
		
		ArticleDto articleDto = null;
		try {
			for(String name : selectedItems ){
				Integer articleId = Integer.parseInt(name);
				articleDto = articleService.findArticleByIdAndSiteId(articleId,siteDto.getSiteId(), false);
				if (articleDto==null){
					articleDto = articleService.findById(articleId);
					articleDto.getSite().add(siteDto);
					articleService.update(articleDto);
				}
				else if (assignedDtos.contains(((Dto)articleDto))) {
					assignedDtos.remove(((Dto)articleDto));
				}
			}//end outer for
			removeAssignedContentDtos(assignedDtos);
			Notification.show("Articles saved successfully for site" + siteDto.getSiteName(), Notification.Type.TRAY_NOTIFICATION);
		}
		catch (Exception e) {
			LOGGER.error("Unable to assign article to site" + siteDto.getSiteName());
			Notification.show("Unable to associate article with head" + articleDto .getHead()+ " for site" + siteDto.getSiteName(), Notification.Type.ERROR_MESSAGE);
		}
	}

	private void removeAssignedContentDtos(Collection<Dto> dtos){
		for(Dto dto:dtos ){
			if(selectedType.equals(CONTENT_TYPE_ARTICLE)) {
				ArticleDto articleDto = articleService.findById(dto.getId());
				articleDto.getSite().clear();
				articleService.update(articleDto);
			} else if(selectedType.equals(CONTENT_TYPE_IMAGE)) {
				ImageDto imageDto = imageService.findById(dto.getId());
				imageDto.getSiteDto().clear();
				AccountDto account = new AccountDto();
				account.setAccountId((Integer)SessionHelper.loadAttribute("accountId"));
				imageDto.setAccountDto(account);
				imageService.update(imageDto);
			}
		}
	}
	
	private Collection<Dto> populateGenericDtoFromArticleDto(final Collection <ArticleDto> articleDtos){
		Collection <Dto> dtos = new ArrayList<Dto>();
		for (ArticleDto articleDto : articleDtos){
			Dto dto = new Dto(articleDto.getId(),articleDto.getHead());
			dtos.add(dto);
		}
		return dtos;
	}
	
	private Collection<Dto> populateGenericDtoFromImageDto(final Collection <ImageDto> imageDtos){
		Collection <Dto> dtos = new ArrayList<Dto>();
		for (ImageDto imageDto : imageDtos){
			Dto dto = new Dto(imageDto.getId(),imageDto.getName());
			dto.getHashMap().put(COLUNM_LIBRARY, imageDto.getImageLibraryDto().getName());
			dto.getHashMap().put(COLUNM_VIEW, createButton(COLUNM_VIEW, dto));
			dtos.add(dto);
		}
		return dtos;
	}
	
private Button createButton(String name, Dto dto) {
		
		Button btn = new Button();
		btn.setCaption(name);
		btn.setData(dto.getId());
		btn.setStyleName("link");
		
		btn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				Object id =  event.getButton().getData();
				ImageDto dto = images.get(id);
				ImageViewPopup popup = ImageViewPopup.getInstance();
				popup.show(dto);
			}
		});
		return btn;
	}
		
}
