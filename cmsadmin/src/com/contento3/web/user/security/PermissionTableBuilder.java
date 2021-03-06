package com.contento3.web.user.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;

import com.contento3.common.dto.Dto;
import com.contento3.security.entity.dto.EntityDto;
import com.contento3.security.entityoperation.dto.EntityOperationDto;
import com.contento3.security.permission.dto.PermissionDto;
import com.contento3.security.permission.service.PermissionService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.listner.PermissionDeleteClickListener;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.BaseTheme;

public class PermissionTableBuilder extends AbstractTableBuilder {
	
	final SpringContextHelper contextHelper;

	final PermissionService permissionService;

	public PermissionTableBuilder(final SpringContextHelper helper,final Table table)
	{
		super(table);
		this.contextHelper = helper;
		this.permissionService = (PermissionService) contextHelper.getBean("permissionService");
	}

	@Override
	public void assignDataToTable(Dto dto, Table permissiontable, Container permissioncontainer) {
		try{
			final PermissionDto permission = (PermissionDto) dto;
			Item item = permissioncontainer.addItem(permission.getId());
			item.getItemProperty("permission").setValue(permission.getId());
			EntityDto entityDto = permission.getEntity();
			//Item item2 = permissioncontainer.addItem(entityDto.getId());
			item.getItemProperty("entity").setValue(entityDto.getName());
			EntityOperationDto entityOperationDto = permission.getEntityOperation();
			//Item item3 = permissioncontainer.addItem(entityOperationDto.getId());
			item.getItemProperty("entityoperation").setValue(entityOperationDto.getName());
			
			if (SecurityUtils.getSubject().isPermitted("PERMISSION:EDIT")){
				//adding edit button item into list
			    final Button editLink = new Button("Edit permission",new PermissionPopup(contextHelper, permissiontable));
				editLink.setCaption("Edit");
				editLink.setData(permission.getId());
				editLink.addStyleName("edit");
				editLink.setStyleName(BaseTheme.BUTTON_LINK);
				item.getItemProperty("edit").setValue(editLink);
			}
			
			if (SecurityUtils.getSubject().isPermitted("PERMISSION:DELETE")){
				//adding delete button item  into list
				final Button deleteLink = new Button();
				deleteLink.setCaption("Delete");
				deleteLink.setData((permission.getId()));
				deleteLink.addStyleName("delete");
				deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
				item.getItemProperty("delete").setValue(deleteLink);
				deleteLink.addClickListener(new PermissionDeleteClickListener(permission, permissionService, deleteLink, permissiontable));
			}
		}catch(final AuthorizationException ex){
			Notification.show("Unauthorized operation", "You are not authorized to perform this operation",Notification.Type.TRAY_NOTIFICATION);
		}
	}

	@Override
	public void buildHeader(Table permissiontable, Container permissioncontainer) {
		// TODO Auto-generated method stub
		permissioncontainer.addContainerProperty("permission", Integer.class, null);
		permissioncontainer.addContainerProperty("entity", String.class, null);
		permissioncontainer.addContainerProperty("entityoperation", String.class, null);
		permissioncontainer.addContainerProperty("edit", Button.class, null);
		permissioncontainer.addContainerProperty("delete", Button.class, null);

		permissiontable.setWidth(100, Unit.PERCENTAGE);
		permissiontable.setContainerDataSource(permissioncontainer);
		
	}
	/**
	 * Create empty table
	 */
	@Override
	public void buildEmptyTable(final Container permissionContainer) {
		Item item = permissionContainer.addItem("-1");
		item.getItemProperty("permission").setValue("No record found.");
	}


}
