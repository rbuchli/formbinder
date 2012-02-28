package org.vaadin.addon.formbinder.test;

import com.vaadin.ui.Form;
import com.vaadin.ui.TextField;

public class PersonAddressDetailsView extends Form {
	
	private TextField streetField = new TextField("Street");
	private TextField zipCodeField = new TextField("Zip code");
	private TextField cityField = new TextField("City");
	
	public PersonAddressDetailsView() {
		setCaption("Address");
		streetField.setDebugId(getClass().getSimpleName() + ".street");
		getLayout().addComponent(streetField);
		getLayout().addComponent(zipCodeField);
		getLayout().addComponent(cityField);
	}
	

}
