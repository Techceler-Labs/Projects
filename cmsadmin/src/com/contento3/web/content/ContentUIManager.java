package com.contento3.web.content;

import com.contento3.cms.constant.NavigationConstant;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.content.article.ArticleMgmtUIManager;
import com.contento3.web.content.image.ImageLibraryPopup;
import com.contento3.web.content.image.ImageMgmtUIManager;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


/**
 * Entry point for all the logic related to CONTENT MANAGER functionality.
 * @author HAMMAD
 *
 */
public class ContentUIManager implements UIManager{

	/**
	 * Helper class to get beans from the spring application context.
	 */
    private SpringContextHelper helper;
    
    /**
     * Parent window that contains all the ui components.Used primarily to set notifications.
     */
	private Window parentWindow;
	
	/**
	 * Main tabsheet that hold all the content.
	 */
	private TabSheet elementTab;
	
	/**
	 * Represents the navigation items in the Content Manager section.
	 */
	private String[] navigationItems = {NavigationConstant.CONTENT_ART_MGMT,NavigationConstant.CONTENT_IMG_MGMT,NavigationConstant.CONTENT_VID_MGMT};
	
	final CssLayout verticalLayout = new CssLayout();


	/**
	 * Class constructor
	 * @param helper
	 * @param parentWindow
	 */
	public ContentUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper,final Window parentWindow){
		this.helper = helper;
		this.parentWindow = parentWindow;
		this.elementTab = uiTabSheet;
	}

	@Override
	public void render(){
	}

	@Override
	public Component render(final String command){
		Component componentToReturn = null;
		return componentToReturn;
	}

	@Override
	public Component render(final String command,final Integer id){
		return null;
	}

	@Override
	public Component render(final String command,final HierarchicalContainer hwContainer){
		Component tabsheet = null;
		if (command.equals(NavigationConstant.CONTENT_MANAGER)){
			//Add the content screen tab and also add the child items
			//tabsheet = new TabSheet();
			renderContentNavigationItems(hwContainer);
		}
		else if (command.equals(NavigationConstant.CONTENT_ART_MGMT)){
			tabsheet = renderArticleUI();
		}
		else if (command.equals(NavigationConstant.CONTENT_IMG_MGMT)){
			tabsheet = renderImageUI();
		}
		else if (command.equals(NavigationConstant.CONTENT_VID_MGMT)){
			tabsheet = renderVideoUI();
		}
		return tabsheet;
	}

	/**
	 * Renders all the navigation items in the Content Manager section
	 * @param hwContainer
	 */
	public void renderContentNavigationItems(final HierarchicalContainer hwContainer){
		for (String navigationItem : navigationItems){
			Item item = hwContainer.addItem(navigationItem);
			if (null != item){
				item.getItemProperty("name").setValue(navigationItem);
				hwContainer.setParent(navigationItem, NavigationConstant.CONTENT_MANAGER);
				hwContainer.setChildrenAllowed(navigationItem, false);
			}
		}
	}

	private Component renderArticleUI(){
		return renderContentElementUI("Article");
	}

	private Component renderVideoUI(){
		TabSheet videoTab = new TabSheet();
		return renderContentElementUI("Video");
	}

	private Component renderImageUI(){
		TabSheet imageTab = new TabSheet();
		return renderContentElementUI("Image");
	}

	/**
	 * Calls the appropiate sub ui manager based on the argument.
	 * @param element Tells what sub ui manager is require to be returned.
	 * @return
	 */
	private Component renderContentElementUI(final String element){
		//final TabSheet elementTab = new TabSheet();
		elementTab.setHeight(100, Sizeable.UNITS_PERCENTAGE);

		
		if(element.equals("Article")){
			ArticleMgmtUIManager articleManager = new ArticleMgmtUIManager(elementTab,helper, parentWindow);
			elementTab = (TabSheet) articleManager.render(null);
		}
		else if(element.equals("Image")){
		
			Button button = new Button();
			verticalLayout.addComponent(button);
			verticalLayout.setSizeFull();
			final ImageMgmtUIManager imageMgmtUIMgr = new ImageMgmtUIManager(elementTab,helper,parentWindow);
			elementTab.addTab(verticalLayout, String.format("%s Management",element));
			
			Label imageHeading = new Label("Image Manager");
			imageHeading.setStyleName("screenHeading");
			verticalLayout.addComponent(imageHeading);
			verticalLayout.addComponent(new HorizontalRuler());
			verticalLayout.setMargin(true);
			verticalLayout.setSizeFull();
			
			HorizontalLayout horizLayout = new HorizontalLayout();
			horizLayout.setSpacing(true);
			button.addListener(new ClickListener(){
				public void buttonClick(ClickEvent event){
					VerticalLayout newArticleLayout = new VerticalLayout();
					Tab createNew = elementTab.addTab(newArticleLayout, String.format("Create new %s",element));
					createNew.setClosable(true);
					elementTab.setSelectedTab(newArticleLayout);
					newArticleLayout.addComponent(imageMgmtUIMgr.renderAddScreen());
					newArticleLayout.setHeight("100%");
				}
			});
			button.setCaption(String.format("Add %s",element));
			horizLayout.addComponent(button);
			
			Button addLibraryButton = new Button("Add Library",new ImageLibraryPopup(parentWindow, helper),"openButtonClick");
			horizLayout.addComponent(addLibraryButton);
			verticalLayout.addComponent(horizLayout);
			verticalLayout.addComponent(new HorizontalRuler());
			verticalLayout.addComponent(imageMgmtUIMgr.listImage(1));
		}

		return elementTab;
	}


}