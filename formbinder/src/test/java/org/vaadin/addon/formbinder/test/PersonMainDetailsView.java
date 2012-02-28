package org.vaadin.addon.formbinder.test;

import com.vaadin.ui.Form;
import com.vaadin.ui.TextField;

public class PersonMainDetailsView extends Form {
	
	private TextField firstNameField = new TextField("First name");
	private TextField lastNameField = new TextField("Last name");
	private TextField ageField = new TextField("Age");
	
	public PersonMainDetailsView() {
		setCaption("General details");
		getLayout().addComponent(firstNameField);
		getLayout().addComponent(lastNameField);
		getLayout().addComponent(ageField);
	}
	

}
