package com.contento3.web.content;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpSession;

import com.contento3.account.dto.AccountDto;
import com.contento3.account.service.AccountService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.service.ImageService;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.Application;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ImageMgmtUIManager extends CustomComponent implements Upload.SucceededListener,
											Upload.FailedListener,
											Upload.Receiver{

	private static final long serialVersionUID = 5131819177752243660L;

    private SpringContextHelper helper;
	private Window parentWindow;

	private Application application;
	
    Panel root;         // Root element for contained components.
    Panel imagePanel;   // Panel that contains the uploaded image.
    File  file;         // File to write to.
    FileResource imageResource;

    TextField altTextField;
    
    public ImageMgmtUIManager(final SpringContextHelper helper,final Window parentWindow){
		this.helper = helper;
		this.parentWindow = parentWindow;
	}
    
    public Component renderAddScreen(){
		root = new Panel("Upload image");

		VerticalLayout imageLayout = new VerticalLayout();
		
		altTextField = new TextField();
		altTextField.setCaption("Alt text");
	
		imageLayout.addComponent(altTextField);
		
		// Create the Upload component.
		Upload upload = new Upload("Upload Image", this);
		// Listen for events regarding the success of upload.
        upload.addListener((Upload.SucceededListener) this);
        upload.addListener((Upload.FailedListener) this);
        imageLayout.addComponent(upload);
        
        root.addComponent(upload);
        root.addComponent(new Label("Click 'Browse' to "+
        "select a file and then click 'Upload'."));

        // Create a panel for displaying the uploaded image.
        imagePanel = new Panel("Uploaded image");
        imagePanel.addComponent(
                         new Label("No image uploaded yet"));
        root.addComponent(imagePanel);
        
        imageLayout.addComponent(root);
        return imageLayout;
	}
	
    FileOutputStream fos ;
	// Callback method to begin receiving the upload.
    public OutputStream receiveUpload(String filename,
                                      String MIMEType) {
        fos = null; // Output stream to write to
        file = new File(filename);
        try {
            // Open the file for writing.
            fos = new FileOutputStream(file);
        } catch(Exception e){} 
            

        return fos; // Return the output stream to write to
    }
    
    // This is called if the upload is finished.
    public void uploadSucceeded(Upload.SucceededEvent event) {
        // Log the upload on screen.
        root.addComponent(new Label("File " + event.getFilename()
                + " of type '" + event.getMIMEType()
                + "' uploaded."));

      
        
        
        imageResource =
            new FileResource(file, parentWindow.getApplication());
   imagePanel.addComponent(new Embedded("", imageResource));
   imageResource.setCacheTime(0);
        // Display the uploaded file in the image panel.
      //  imagePanel.addComponent(new Embedded("", imageResource));

        
        FileInputStream fis = null;
    	byte[] bFile = new byte[(int) file.length()];
 		try {
 			fis = new FileInputStream(file);

 		} catch (FileNotFoundException e) {
// 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
        try {
        	
 			fis.read(bFile);
 			
            ImageDto imageDto = new ImageDto();
            imageDto.setAltText(altTextField.getValue().toString());
            imageDto.setImage(bFile);
            imageDto.setName("asdfasds");
            
            //Get accountId from the session
            WebApplicationContext ctx = ((WebApplicationContext) parentWindow.getApplication().getContext());
            HttpSession session = ctx.getHttpSession();
            Integer accountId =(Integer)session.getAttribute("accountId");

            AccountService accountService = (AccountService)helper.getBean("accountService");
            AccountDto accountDto = new AccountDto();
            accountDto.setAccountId(accountId);
            ImageService imageService = (ImageService)helper.getBean("imageService");
            imageDto.setAccountDto(accountDto);
 	        fis.close();

				imageService.create(imageDto);
			} catch (EntityAlreadyFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        catch (final java.io.FileNotFoundException e) {
            // Error while opening the file. Not reported here.
            e.printStackTrace();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
      
		}

    imagePanel.removeAllComponents();
    imagePanel.setSizeFull();
    imageResource =
        new FileResource(file, parentWindow.getApplication());
imagePanel.addComponent(new Embedded("", imageResource));
imageResource.setCacheTime(0);

            }

    // This is called if the upload fails.
    public void uploadFailed(Upload.FailedEvent event) {
        // Log the failure on screen.
        root.addComponent(new Label("Uploading "
                + event.getFilename() + " of type '"
                + event.getMIMEType() + "' failed."));
    }
    
    @Override
    public void attach() {
        super.attach(); // Must call.

        application = this.getApplication();
        // Display the uploaded file in the image panel.
       imageResource =
                new FileResource(file, application);
       imagePanel.addComponent(new Embedded("", imageResource));
       imageResource.setCacheTime(0);

    }

	public void loadImages(final VerticalLayout verticalLayout) {
		ImageService imageService = (ImageService)helper.getBean("imageService");
		final ImageDto imageDto = imageService.findImageById("1ch63f07dp31u1rytew6c3a34l");
		
		StreamResource.StreamSource imageSource = new StreamResource.StreamSource() {
			@Override
			public InputStream getStream() {
			return new ByteArrayInputStream(imageDto.getImage());
			}
			};
		
			StreamResource imageResource = new StreamResource(imageSource, "abc.png", parentWindow.getApplication());
			imageResource.setCacheTime(0);

		Embedded embeded = new Embedded("test image",imageResource);
		embeded.requestRepaint();
		verticalLayout.addComponent(embeded);
	}

}
