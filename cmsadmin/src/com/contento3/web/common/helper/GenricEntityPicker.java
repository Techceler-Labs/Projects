package com.contento3.web.common.helper;


import java.util.Collection;

import org.apache.log4j.Logger;

import com.contento3.common.dto.Dto;
import com.contento3.util.CachedTypedProperties;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

/**
 * Abstract GenricEntityPicker class
 */
public  class GenricEntityPicker extends CustomComponent implements Window.CloseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(GenricEntityPicker.class);
	
	/**
	 * Dtos to be listed in table
	 */
	private final Collection<Dto> dtos;

	/**
	 * Dtos that are already assigned and need to be checked in the table
	 */
	private final Collection<Dto> assignedDto;
	/**
	 * Vertical layout to add components
	 */
	private VerticalLayout vLayout;

	/**
	 * Contains list of columns to be generated
	 */
	private Collection<String> listOfColumns;

	/**
	 * Table builder for genric entity picker
	 */
	GenricEntityTableBuilder tableBuilder;
	
	/**
	 * Tree Table builder for genric entity picker
	 */
	GenericTreeTableBuilder treeTableBuilder;

	/**
	 *  Reference to main window
	 */
	Window mainwindow; 

	/**
	 * The window to be opened
	 */
	Window popupWindow; 

	/**
	 * Button for opening the window
	 */
	Button openbutton; 

	/**
	 *  A button in the window
	 */
	Button closebutton; 

	/**
	 * Used to get service beans from spring context.
	 */
	SpringContextHelper helper;

	boolean isModalWindowClosable = true;

	/**
	 * Vertical layout to add components
	 */
	VerticalLayout popupMainLayout = new VerticalLayout();

	/**
	 * Contain calling object
	 */
	EntityListener entityListener;
	
	/**
	 * Generate Tree table 
	 */
	boolean isHierarchicalTable;
	
	/**
	 * Width of popup
	 */
	String width = "37"; //default width
	
	/**
	 * Height of popup
	 */
	String height = "40";//default height

	/**
	 * Constructor
	 * @param dtos
	 * @param listOfColumns
	 * @param vLayout
	 */

	public GenricEntityPicker(final Collection<Dto> dtos,final Collection<Dto> assignedDtos,final Collection<String> listOfColumns,
			final VerticalLayout vLayout,final Window mainWindow,EntityListener entityListener,final boolean isHierarchicalTable) {
		this.listOfColumns = listOfColumns;
		this.dtos = dtos;
		this.vLayout = vLayout;
		this.mainwindow = mainWindow;
		this.entityListener = entityListener;
		this.assignedDto = assignedDtos;
		this.isHierarchicalTable = isHierarchicalTable;
	}

	/**
	 * Build Table
	 */
	public void build() { 
		String pageLength = "5"; //default
		try {
			final CachedTypedProperties entityPickerPoperties = CachedTypedProperties.getInstance("entityPickerConfigure.properties");
			width = entityPickerPoperties.getProperty("width");
			height = entityPickerPoperties.getProperty("height");
			pageLength = entityPickerPoperties.getProperty("tablePageLength");
		} catch (ClassNotFoundException e) {
			LOGGER.error("Unable to read entityPickerConfigure.properties,Reason:"+e);
		}
		
		if(vLayout.getComponentCount()>0){
	        vLayout.removeAllComponents();
		}
		
		if(!isHierarchicalTable){
			tableBuilder = new GenricEntityTableBuilder(entityListener,this,dtos,assignedDto, listOfColumns, vLayout);
			tableBuilder.build();
			tableBuilder.table.setPageLength(Integer.parseInt(pageLength));
		}else{
			treeTableBuilder = new GenericTreeTableBuilder(entityListener,dtos,assignedDto, listOfColumns, vLayout);
			treeTableBuilder.build();
			treeTableBuilder.treeTable.setPageLength(Integer.parseInt(pageLength));
		}
		
        renderPopUp();
	}

	/**
	 * Render pop-up screen
	 */
	public void renderPopUp() {
			
	        /* Create a new window. */
			popupWindow = new Window();
			popupWindow.setPositionX(200);
	    	popupWindow.setPositionY(100);
	    	popupWindow.setHeight(Integer.parseInt(height),Sizeable.UNITS_PERCENTAGE);
	    	popupWindow.setWidth(Integer.parseInt(width),Sizeable.UNITS_PERCENTAGE);

	    	/* Add the window inside the main window. */
	        mainwindow.addWindow(popupWindow);

	        /* Listen for close events for the window. */
	        popupWindow.addListener(this);
	        popupWindow.setModal(true);
	        if(entityListener.getCaption() !=  null)
	        popupWindow.setCaption(entityListener.getCaption());
	        popupMainLayout.setSpacing(true);
	        popupMainLayout.addComponent(vLayout);
	        popupWindow.addComponent(popupMainLayout);
	        popupWindow.setResizable(false);

	    }

	/**
	 * Handle Close button click and close the window.
	 */
	public void closeButtonClick(Button.ClickEvent event) {

		if (!isModalWindowClosable) {
			/* Windows are managed by the application object. */
			mainwindow.removeWindow(popupWindow);
		}
	}

	/**
	 * Window close event
	 */
	@Override
	public void windowClose(CloseEvent e) {
	}

	public void setTableCaption(String caption){
		if(this.tableBuilder != null){
			this.tableBuilder.table.setCaption(caption);
		}
		else if(this.treeTableBuilder != null){
			this.treeTableBuilder.treeTable.setCaption(caption);
		}
	}
}