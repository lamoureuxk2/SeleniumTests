package basicTests;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.*;
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.*;

/**
 * Test for the Contact-Us page of Agile Warriors
 * @author Karl
 *
 */
public class ContactUsPageTest extends AbstractPageTest {
	
	static final String TEST_EMAIL_ADDRESS = "agilewarriorstester@gmail.com";
	static final String TEST_EMAIL_PASSWORD = "Test123!";
	static final String GMAIL_URL = "https://mail.google.com/mail/u/0/#inbox";
	static final String GMAIL_FILTER_HTML_CLASS = "G-asx T-I-J3 J-J5-Ji";
	static final String GMAIL_EMAIL_FIELD_ID = "identifierId";
	static final String GMAIL_NEXT_BUTTON_CLASS = "VfPpkd-RLmnJb";
	static final String AGILE_EMAIL_TITLE = "Agile Warriors Admin";
	
	@BeforeClass
	public static void beforeClassSetPageSpecifics() {
		TEST_NAME = "ContactUsPageTest";
		PAGE_TO_TEST = "https://agile-warriors.bubbleapps.io/contact_us";
//		driver.close();
//		FirefoxOptions opts = new FirefoxOptions();
//		opts.addArguments("-private");
//		driver = new FirefoxDriver(opts);
	}
	
	/**
	 * Requests contact from the webpage, then checks the gmail's unread messages for a response
	 * Fails if no unread email from the admin is present
	 */
	@Test
	public void testRequestContact() {
		//Code starts by sending a contact request at the page
		driver.get(PAGE_TO_TEST);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.id("FirstName")).sendKeys("The");
		driver.findElement(By.id("LastName")).sendKeys("Tester");
		driver.findElement(By.id("JobTitle")).sendKeys("Tester");
		driver.findElement(By.id("WorkEmail")).sendKeys(TEST_EMAIL_ADDRESS);
		driver.findElement(By.id("Phone")).sendKeys("1111111111");
		driver.findElement(By.id("Company")).sendKeys("AgileWarriors");
		driver.findElement(By.id("Submit")).click();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//The rest of this code is seeing if the email is in the test email inbox
		driver.get(GMAIL_URL);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement gmailEmailField = driver.findElement(By.id(GMAIL_EMAIL_FIELD_ID));
		gmailEmailField.sendKeys(TEST_EMAIL_ADDRESS);
		driver.findElement(By.className(GMAIL_NEXT_BUTTON_CLASS)).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement passwordField = driver.findElement(By.name("password"));
		passwordField.sendKeys(TEST_EMAIL_PASSWORD);
		driver.findElement(By.className(GMAIL_NEXT_BUTTON_CLASS)).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//q is the id for the query field 
		driver.findElement(By.name("q")).sendKeys("is:unread");
		driver.findElement(By.name("q")).sendKeys(Keys.RETURN);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//will fail if no email found
		WebElement email = driver.findElement(By.name(AGILE_EMAIL_TITLE));
		
		//reads the email 
		((JavascriptExecutor) driver).executeScript
		("arguments[0].scrollIntoView(true); arguments[0].click();", email);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} 
		
		
	}
}
