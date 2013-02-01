package com.contento3.web.content.document.listener;

import java.util.Collection;
import java.util.UUID;

import com.contento3.account.service.AccountService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.dam.document.dto.DocumentDto;
import com.contento3.dam.document.service.DocumentService;
import com.contento3.dam.document.service.DocumentTypeService;
import com.contento3.dam.storagetype.dto.StorageTypeDto;
import com.contento3.dam.storagetype.service.StorageTypeService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.content.document.DocumentForm;
import com.contento3.web.content.document.DocumentTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.TabSheet.Tab;

public class DocumentEditListener implements ClickListener {
	private static final long serialVersionUID = 1L;
	
	private DocumentService documentService;
	private AccountService accountService;
	private DocumentTypeService documentTypeService;
	private StorageTypeService storageTypeService;
	private SpringContextHelper contextHelper;
	private Window parentWindow;
	
	private TabSheet tabSheet;
	private Tab documentTab;
	private DocumentForm documentForm;
	private Table documentTable;
	private Integer documentId;
	private Integer accountId;
	
	public DocumentEditListener(final Tab documentTab, final DocumentForm documentForm,final Table documentTable, 
			final Integer documentId,final Integer accountId){
		this.documentTab = documentTab;
		this.documentForm = documentForm;
		this.documentTable = documentTable;
		this.documentId = documentId;
		this.accountId = accountId;
		
		this.tabSheet = documentForm.getTabSheet();
		this.parentWindow = documentForm.getParentWindow();
		this.contextHelper = documentForm.getContextHelper();
		this.documentService = (DocumentService) contextHelper.getBean("documentService");
		this.accountService = (AccountService) contextHelper.getBean("accountService");
		this.documentTypeService = (DocumentTypeService) contextHelper.getBean("documentTypeService");
		this.storageTypeService = (StorageTypeService) contextHelper.getBean("storageTypeService");
	}
	
	@Override
	public void click(ClickEvent event) {
		if(documentForm.getUploadedDocument() == null){
			parentWindow.showNotification("You must upload a document to Save.");
			return;
		}
		
		DocumentDto documentDto;
		StorageTypeDto storageTypeDto = (StorageTypeDto) storageTypeService.findByName("DATABASE");
		
		if(documentId == null)
			documentDto = new DocumentDto();
		else
			documentDto = this.documentService.findById(documentId);
		
		documentDto.setDocumentTitle( documentForm.getDocumentTitle().getValue().toString() );
		documentDto.setDocumentTypeDto( documentTypeService.findByName(documentForm.getSelectedDocumentType()) );
		documentDto.setAccount( accountService.findAccountById(accountId) );
		documentDto.setDocumentContent( documentForm.getUploadedDocument() );
		documentDto.setStorageTypeDto(storageTypeDto);
		documentDto.setDocumentUuid( UUID.randomUUID().toString() );
		
		try {
			documentService.update(documentDto);
		} catch (EntityAlreadyFoundException e) {
			e.printStackTrace();
		}
		
		String notification = documentDto.getDocumentTitle() + " updated successfully"; 
		parentWindow.showNotification(notification);
		tabSheet.removeTab(documentTab);
		resetTable();
		tabSheet.removeTab(documentTab);
	}

	/**
	 * Reset table
	 */
	 @SuppressWarnings("rawtypes")
	 private void resetTable(){
		final AbstractTableBuilder tableBuilder = new DocumentTableBuilder(this.parentWindow,this.contextHelper,this.tabSheet,this.documentTable);
		final Collection<DocumentDto> document = this.documentService.findByAccountId(accountId);
		tableBuilder.rebuild((Collection) document);
	}

}
