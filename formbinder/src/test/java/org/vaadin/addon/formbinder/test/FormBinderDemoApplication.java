package org.vaadin.addon.formbinder.test;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.vaadin.addon.formbinder.ViewBoundForm;

import com.vaadin.Application;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class FormBinderDemoApplication extends Application {

	public static void main(String[] args) throws Exception {
		startInEmbeddedJetty();
	}

	public static Server startInEmbeddedJetty() throws Exception {
		Server server = new Server(8888);
		ServletContextHandler handler = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		handler.addServlet(Servlet.class, "/*");
		server.setHandler(handler);
		server.start();
		return server;
	}

	// mapping used to if demo war is built from these sources
	@WebServlet(urlPatterns = "/*")
	public static class Servlet extends AbstractApplicationServlet {
		@Override
		protected Application getNewApplication(HttpServletRequest request)
				throws ServletException {
			return new FormBinderDemoApplication();
		}

		@Override
		protected Class<? extends Application> getApplicationClass()
				throws ClassNotFoundException {
			return FormBinderDemoApplication.class;
		}
	}

	private Window mainWindow;

	@Override
	public void init() {
		mainWindow = new Window("FormBinder example and test application");

		((VerticalLayout) mainWindow.getContent()).setSpacing(true);

		Label label = new Label("Hello Vaadin user");
		mainWindow.addComponent(label);

		addBasicExample();

		addAnnotationMappedExample();

		addSubFormExample();

		setMainWindow(mainWindow);
	}

	private void addSubFormExample() {

		// Here we have two "views" for one "monolithic" item
		PersonMainDetailsView personMainDetailsView = new PersonMainDetailsView();
		PersonAddressDetailsView personAddressDetailsView = new PersonAddressDetailsView();

		// Add two views to a layout that is used in master form
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setMargin(true);
		verticalLayout.setSpacing(true);
		verticalLayout.addComponent(personMainDetailsView);
		verticalLayout.addComponent(personAddressDetailsView);

		ViewBoundForm viewBoundForm = new ViewBoundForm();
		viewBoundForm
				.setCaption("Example how to split datamodel to 'subfomrs' at UI level");
		viewBoundForm.setLayout(verticalLayout);

		// configure form (delegated to PreCreatedFieldsHelper) to search
		// suitable fields from sub form views
		viewBoundForm.setCustomFieldSources(personAddressDetailsView,
				personMainDetailsView);

		final PersonPojo personPojo = new PersonPojo();
		viewBoundForm.setItemDataSource(new BeanItem<PersonPojo>(personPojo));

		Button button3 = new Button("Show pojo state");
		button3.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				mainWindow.showNotification("Pojo state: " + personPojo);
			}
		});

		// for testing
		button3.setDebugId("pojo3button");

		mainWindow.addComponent(viewBoundForm);
		mainWindow.addComponent(button3);
	}

	private void addAnnotationMappedExample() {
		// create an example pojo with some default content
		final MyExamplePojo secondExamplePojo = new MyExamplePojo();
		secondExamplePojo.setFirstName("Matti");
		secondExamplePojo.setLastName("Meikäläinen");
		secondExamplePojo.setBirthDate(new Date());
		secondExamplePojo.setWeight(71);

		// create a form with MyExamplePojoViewWithCustomNames, which contains
		// fields annotated with PropertyId
		ViewBoundForm formBoundWithAnnotation = new ViewBoundForm(
				new MyExamplePojoViewWithCustomNames());
		formBoundWithAnnotation
				.setCaption("Another example that uses annotations instead of naming convention");
		// wrap pojo in a bean item and bind it to the form
		formBoundWithAnnotation.setItemDataSource(new BeanItem<MyExamplePojo>(
				secondExamplePojo));

		// add form to main window
		mainWindow.addComponent(formBoundWithAnnotation);

		Button button2 = new Button("Show pojo state");
		button2.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				mainWindow.showNotification("Pojo state: " + secondExamplePojo);
			}
		});
		// for testing
		button2.setDebugId("pojo2button");

		mainWindow.addComponent(button2);
	}

	private void addBasicExample() {
		// create an example pojo with some default content
		final MyExamplePojo myExamplePojo = new MyExamplePojo();
		myExamplePojo.setFirstName("Matti");
		myExamplePojo.setLastName("Meikäläinen");
		myExamplePojo.setBirthDate(new Date());
		myExamplePojo.setWeight(71);

		// create a form with MyExamplePojoView, which contains fields like
		// TextField firstNameField, lastNameField etc
		ViewBoundForm form = new ViewBoundForm(new MyExamplePojoView());
		form.setCaption("Simple bean editor with custom tailored view");
		// wrap pojo in a bean item and bind it to the form
		form.setItemDataSource(new BeanItem<MyExamplePojo>(myExamplePojo));

		// add form to main window
		mainWindow.addComponent(form);

		Button button = new Button("Show pojo state");
		button.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				mainWindow.showNotification("Pojo state: " + myExamplePojo);
			}
		});
		// for testing
		button.setDebugId("pojobutton");

		mainWindow.addComponent(button);
	}

}
