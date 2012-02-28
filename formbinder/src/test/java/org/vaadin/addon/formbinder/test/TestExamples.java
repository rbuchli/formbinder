package org.vaadin.addon.formbinder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * WebDriver tests against the demo/example app.
 */
public class TestExamples {

	private Server startInEmbeddedJetty;
	private FirefoxDriver driver;

	@Before
	public void startServer() throws Exception {
		startInEmbeddedJetty = FormBinderDemoApplication.startInEmbeddedJetty();
		driver = new FirefoxDriver();
	}

	@After
	public void stopServer() throws Exception {
		if (startInEmbeddedJetty != null) {
			startInEmbeddedJetty.stop();
		}
		driver.close();
	}

	@Test
	public void testSubFormExample() {
		openTestApp();

		String inputId = PersonAddressDetailsView.class.getSimpleName() + ".street";
		String initialValue = "Ruukinkatu";
		String enteredValue = "Foo";
		String buttonId = "pojo3button";
		testFormWithInput(inputId, initialValue, enteredValue, buttonId);
		
	}
	
	@Test
	public void testWithAnnotationMethod() {
		openTestApp();
		String inputId = MyExamplePojoViewWithCustomNames.class.getSimpleName() + ".firstname";
		String initialValue = "Matti";
		String enteredValue = "Foo";
		String buttonId = "pojo2button";
		testFormWithInput(inputId, initialValue, enteredValue, buttonId);
		
	}

	private void openTestApp() {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get("http://localhost:8888");
	}
	
	@Test
	public void testDefaultNamingConvention() {
		openTestApp();

		String inputId = MyExamplePojoView.class.getSimpleName() + ".firstname";
		String initialValue = "Matti";
		String enteredValue = "Foo";
		String buttonId = "pojobutton";
		testFormWithInput(inputId, initialValue, enteredValue, buttonId);
		
	}


	private void testFormWithInput(String inputId, String initialValue, String enteredValue, String buttonId) {
		WebElement streetinput = driver.findElement(By
				.id(inputId));
		String value = streetinput.getAttribute("value");
		assertEquals(initialValue, value);
		streetinput.clear();
		streetinput.sendKeys(enteredValue);
		driver.findElement(By.id(buttonId)).click();
		
		List<WebElement> findElements = driver.findElements(By.className("v-Notification"));
		boolean contains = findElements.get(findElements.size() -1).getText().contains(enteredValue);
		assertTrue(contains);
	}

}
