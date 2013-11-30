package com.contento3.web.user.listner;

import org.vaadin.dialogs.ConfirmDialog;

import com.contento3.common.service.Service;
import com.contento3.security.permission.dto.PermissionDto;
import com.contento3.security.permission.service.PermissionService;
import com.contento3.web.site.listener.EntityDeleteClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;


public class PermissionDeleteClickListener extends EntityDeleteClickListener<PermissionDto> {
	public PermissionDeleteClickListener(PermissionDto dtoToDelete,
			Service<PermissionDto> service, Button deleteLink, Table table) {
		super(dtoToDelete, service, deleteLink, table);
		// TODO Auto-generated constructor stub
	}
	@SuppressWarnings("serial")
	@Override
	public void buttonClick(ClickEvent event) {
		
		final Object id = getDeleteLink().getData();
		final Integer PermissionId = (Integer) getTable().getContainerProperty(id,"permission").getValue();
			if(getDtoToDelete().getId().equals(PermissionId)){

						ConfirmDialog.show(UI.getCurrent(), "Please Confirm:"," Are you really sure to delete?",
						        "Yes", "Cancel", new ConfirmDialog.Listener() {
	
						            public void onClose(ConfirmDialog dialog) {
						                if (dialog.isConfirmed()) {
						                    // Confirmed to continue
						                	deletePermission(id);
						                	
						                } else {
						                    // User did not confirm
						                    
						                }
						            }
						        });
					
				
			}
			
	}	
	private void deletePermission(Object id){
		try {
			PermissionDto dtoToDelete = ((PermissionService) getService()).findById(getDtoToDelete().getId());
			((PermissionService) getService()).delete(dtoToDelete);
			getTable().removeItem(id);
			getTable().setPageLength(getTable().getPageLength()-1);
			Notification.show(getDtoToDelete().getId()+" Permission deleted succesfully");
		} catch (Exception e) {
			String description= "Permission " + getDtoToDelete().getId() + " cannot be deleted.";
			Notification.show("Delete Failed",description,Notification.TYPE_WARNING_MESSAGE);
			e.printStackTrace();
		}
    	
	}
}