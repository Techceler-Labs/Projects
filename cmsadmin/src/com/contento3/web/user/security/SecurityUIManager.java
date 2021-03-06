package com.contento3.web.user.security;

import com.contento3.cms.constant.NavigationConstant;
import com.contento3.common.security.PermissionsHelper;
import com.contento3.web.UIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

public class SecurityUIManager implements UIManager {

	/**
	 * Used to get service beans from spring context.
	 */
    private SpringContextHelper helper;
    
	/**
	 * Tab sheet to display user management ui
	 */
	TabSheet userMgmtTabSheet = null;
	/**
	 * Navigation item for user manager
	 */
	private String[] navigationItems = {NavigationConstant.USER_GRP_MGMT,NavigationConstant.USER_MANAGER,NavigationConstant.USR_ROLE_MGMT,NavigationConstant.USR_PRMSN_MGMT};

	/**
	 * 
	 */
	private TabSheet uiTabSheet;
	
	public SecurityUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper) {
		this.helper = helper;
		this.uiTabSheet = uiTabSheet;
	}

	@Override
	public void render() {
	}

	@Override
	public Component render(final String command) {
		return uiTabSheet;
	}

	@Override
	public Component render(final String command,final Integer entityFilterId) {
		return uiTabSheet;
	}

	/**
	 * Renders all the navigation items in the User Manager section
	 * @param hwContainer
	 */
	@Override
	public Component render(final String command,final HierarchicalContainer hwContainer) {
		
		if (PermissionsHelper.isPermitted("SECURITY:NAVIGATION") && command.equals(NavigationConstant.SECURITY)){
			//Add the group screen tab and also add the child items
			renderUserNavigationItem(hwContainer);
		}
		else if (PermissionsHelper.isPermitted("GROUP:NAVIGATION") && command.equals(NavigationConstant.USER_GRP_MGMT)){
			uiTabSheet = (TabSheet) renderElementUI("Group");
		}
		else if (PermissionsHelper.isPermitted("USER:NAVIGATION") && command.equals(NavigationConstant.USER_MANAGER)){
			uiTabSheet = (TabSheet) renderElementUI("User");
		}
		else if (PermissionsHelper.isPermitted("ROLE:NAVIGATION") && command.equals(NavigationConstant.USR_ROLE_MGMT)){
			uiTabSheet = (TabSheet) renderElementUI("Role");
		}
		else if (PermissionsHelper.isPermitted("PERMISSION:NAVIGATION") && command.equals(NavigationConstant.USR_PRMSN_MGMT)){
			uiTabSheet = (TabSheet) renderElementUI("Permission");
		}
		return uiTabSheet;
	}
	
	public void renderUserNavigationItem(final HierarchicalContainer hwContainer){
		for (String navigationItem : navigationItems){
			if ((navigationItem.equals(NavigationConstant.USER_GRP_MGMT) && PermissionsHelper.isPermitted("GROUP:NAVIGATION")) ||
				(navigationItem.equals(NavigationConstant.USER_MANAGER) && PermissionsHelper.isPermitted("USER:NAVIGATION"))  ||
				(navigationItem.equals(NavigationConstant.USR_ROLE_MGMT) && PermissionsHelper.isPermitted("ROLE:NAVIGATION"))  ||
				(navigationItem.equals(NavigationConstant.USR_PRMSN_MGMT) && PermissionsHelper.isPermitted("PERMISSION:NAVIGATION")))
			{
				Item item = hwContainer.addItem(navigationItem);
				if (null != item){
					item.getItemProperty("name").setValue(navigationItem);
					hwContainer.setParent(navigationItem, NavigationConstant.SECURITY);
					hwContainer.setChildrenAllowed(navigationItem, false);
				}
			}
		}
	}
	
	private Component renderElementUI(final String element){
		TabSheet elementTab = null;
		if(element.equals("Group")){
			GroupUIManager groupManager = new GroupUIManager(uiTabSheet,helper);
			elementTab = (TabSheet) groupManager.render(null);
		}
		else if(element.equals("User")){
			UserUIManager userManager = new UserUIManager(uiTabSheet,helper);
			elementTab = (TabSheet) userManager.render(null);
		}		
		else if(element.equals("Role")){
			RoleUIManager roleManager = new RoleUIManager(uiTabSheet,helper);
			elementTab = (TabSheet) roleManager.render(null);
		}
		else if(element.equals("Permission")){
			//resee it
			PermissionUIManager permissionManager = new PermissionUIManager(uiTabSheet,helper);
			elementTab = (TabSheet) permissionManager.render(null);
		}
		return elementTab;
	}
}
