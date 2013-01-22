package com.contento3.web.content.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.service.DocumentService;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.service.ImageService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.content.document.listener.DocumentFormBuilderListner;
import com.contento3.web.content.image.ImageMgmtUIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.TabSheet.Tab;

public class DocumentMgmtUIManager implements UIManager {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(DocumentMgmtUIManager.class);
	
	/**
	 * Used to get service beans from spring context.
	 */
	private SpringContextHelper contextHelper;
	
	/**
     * Represents the parent window of the ui
     */
	private Window parentWindow;
	
	/**
	 * TabSheet serves as the parent container for the document manager
	 */

	private TabSheet tabSheet;

	/**
	 * main layout for document manager screen
	 */
	private VerticalLayout verticalLayout = new VerticalLayout();
	
	/**
	 * Article table which shows documents
	 */
	private final Table documentTable =  new Table("Documents");

	/**
	 * Document service for document related operations
	 */
	private DocumentService documentService;
	
	/**
	 * Account id
	 */
	private Integer accountId;
	
	/**
	 * Constructor
	 * @param TabSheet
	 * @param contextHelper
	 * @param parentWindow
	 */
	public DocumentMgmtUIManager(final TabSheet tabSheet, final SpringContextHelper contextHelper,final Window parentWindow) {
		this.tabSheet = tabSheet;
		this.contextHelper = contextHelper;
		this.parentWindow = parentWindow;
		this.documentService = (DocumentService) this.contextHelper.getBean("documentService");
		
		//get account if from session
		WebApplicationContext webContext = (WebApplicationContext) parentWindow.getApplication().getContext();
		this.accountId = (Integer) webContext.getHttpSession().getAttribute("accountId");
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component render(String command) {
		this.tabSheet.setHeight(100, Sizeable.UNITS_PERCENTAGE);
		final Tab articleTab = tabSheet.addTab(verticalLayout, "Document Management");
		articleTab.setClosable(true);
		this.verticalLayout.setSpacing(true);
		this.verticalLayout.setWidth(100,Sizeable.UNITS_PERCENTAGE);
		renderDocumentComponent();
		return this.tabSheet;
	}

	@Override
	public Component render(String command, Integer entityFilterId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void renderDocumentComponent(){
		Label documentHeading = new Label("Document Manager");
		documentHeading.setStyleName("screenHeading");
		this.verticalLayout.addComponent(documentHeading);
		this.verticalLayout.addComponent(new HorizontalRuler());
		this.verticalLayout.setMargin(true);
		addDocumentButton();
		this.verticalLayout.addComponent(new HorizontalRuler());
		renderDocumentTable();
	}
	
	/**
	 * Render document table
	 */
	@SuppressWarnings("unchecked")
	private void renderDocumentTable() {
		final AbstractTableBuilder tableBuilder = new DocumentTableBuilder(this.parentWindow ,this.contextHelper, this.tabSheet, this.documentTable);
		Collection<DocumentDto> documents = this.documentService.findByAccountId(accountId);
		tableBuilder.build((Collection)documents);
		this.verticalLayout.addComponent(this.documentTable);
		
	}

	/**
	 * Display "Add Document" button on the top of tab 
	 */
	private void addDocumentButton(){
		Button addButton = new Button("Add Document");
		addButton.addListener(new DocumentFormBuilderListner(this.contextHelper, this.parentWindow,this.tabSheet,this.documentTable));
		this.verticalLayout.addComponent(addButton);
	}
	
}
