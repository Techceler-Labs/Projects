package com.contento3.web.site.listener;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;



public final class YesNoDialog extends Window implements Button.ClickListener{

	//dialog height
	private static final int CONFIRMATION_DIALOG_HIEGHT = 100;
	//dialog width
	private static final int CONFIRMATION_DIALOG_WIDTH = 320;
	//constatnt of hundred percent used in dialog main layout width
	private static final int ONE_HUNDRED_PERCENT = 100;
	//serial version id
	private static final long serialVersionUID = 1L;
	//confirmation callback
	private final YesNoDialogCallback callback;
	//ok button
	private final Button okButton;
	//cancel button
	private final Button cancelButton;
	
	//constructor for YesNoDialog 
	//@param caption the dialog title
	//@param question the dialog question
	//@param ok and cancel button
	//@param callback
	
	public YesNoDialog(final String caption, final String question, final String okLabel,
			final String cancelLabel, final YesNoDialogCallback callback){
		
		super(caption);
		setWidth(CONFIRMATION_DIALOG_WIDTH, YesNoDialog.UNITS_PIXELS);
		setHeight(CONFIRMATION_DIALOG_HIEGHT, YesNoDialog.UNITS_PIXELS);
		okButton = new Button(okLabel, this);
		cancelButton = new Button(cancelLabel, this);
		setModal(true);
		
		this.callback = callback;
		
		if(question != null)
			addComponent(new Label(question));
		
		 
	
	//button layout
	 HorizontalLayout layout = new HorizontalLayout();
    layout.setSpacing(true);
    layout.addComponent(okButton);
    layout.addComponent(cancelButton);
    addComponent(layout);
    
	
	((VerticalLayout) getContent()).setHeight(ONE_HUNDRED_PERCENT,YesNoDialog.UNITS_PERCENTAGE);
	
	((VerticalLayout) getContent()).setComponentAlignment(layout,Alignment.BOTTOM_CENTER);
}// constructor body ends
	
	
	//event handler for button click
	
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
		if(getParent() != null)
			((Window) getParent()).removeWindow(this);
		
		callback.response(event.getSource() == okButton);
	}
	
	//intrface yesnodialog callback
	public interface YesNoDialogCallback {
		
		//user response
		void response(boolean ok);
	}
	
}