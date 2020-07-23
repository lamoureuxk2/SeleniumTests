package basicTests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
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
 * To write a test for a new page, copy all the data from this up until the first @Test
 * Then replace the PAGE_TO_TEST variable with the appropriate page.
 * Write @Test methods specific to the page you are testing
 * 
 * Ensure the PATH_TO_GECKODRIVER is set to your path to geckodriver.exe
 * @author Karl
 * 
 * Much of the code for logging is taken from 
 * https://garygregory.wordpress.com/2015/09/14/the-art-of-test-driven-development-logging-junit-test-results/
 *
 */
public abstract class AbstractPageTest {
	
	protected static String TEST_NAME;
	protected static String PAGE_TO_TEST;
	//Location of your gecko-driver executable
	protected static final String PATH_TO_GECKODRIVER = "C:\\Selenium\\geckodriver.exe";
	protected static final String LOG_DIR = "logs/";
	protected static final String LOGIN_BUTTON_ID = "LoginButton";
	protected static final String LOGIN_POPUP_ID = "LoginPopUp";
	protected static final String SIGN_UP_POPUP_ID = "SignUpPopUp";
	protected static final String LOGIN_SUBMIT_BUTTON_ID = "LoginSubmitButton";
	protected static final String LOGIN_EMAIL_TEXT_FIELD = "LoginEmailTextField";
	protected static final String LOGIN_PASSWORD_TEXT_FIELD = "LoginPasswordTextField";
	protected static final String TEST_EMAIL_ADDRESS = "test@gmail.com";
	protected static final String TEST_PASSWORD = "Welcome2BU!";
	protected static StringBuilder builder = new StringBuilder();
	
	//Keeps track of any custom error message 
	protected static StringBuilder errorLogs = new StringBuilder();
	protected static PrintWriter writer;
	
	protected static WebDriver driver;
	
	protected static final String EOL = 
	        System.getProperty("line.separator");
	
	/*
	 * Before all tests, set the geckodriver, webdriver, and Printwriter for logging
	 */
	@BeforeClass
	public static void beforeClass() {
		System.setProperty("webdriver.gecko.driver", PATH_TO_GECKODRIVER);
		driver = new FirefoxDriver();
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try {
			String timestamp = new Timestamp(System.currentTimeMillis()).toString();
			timestamp = timestamp.replace(" ", "_");
			timestamp = timestamp.replace(":", "-");
			timestamp = timestamp.replace(".", "-");
			writer = new PrintWriter(LOG_DIR + TEST_NAME + "__" + timestamp + ".log");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/*
	 * This is what determines what is written for logging after each test
	 */
	@Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
        	builder.append(new Timestamp(System.currentTimeMillis())  + " | ");
            if (description != null) {
                builder.append(description);
            }
            if (e != null) {
                builder.append(' ');
                builder.append(e);
            }
            builder.append(" FAIL");
            builder.append(EOL);
            builder.append(errorLogs);
            
            //Clear errorLogs after each use
            errorLogs = new StringBuilder(); 
        }
 
        @Override
        protected void succeeded(Description description) {
        	builder.append(new Timestamp(System.currentTimeMillis()) + " | ");
            if (description != null) {
                builder.append(description);
            }
            builder.append(" OK");
            builder.append(EOL);
        }
    };
	
    /**
     * After all tests, write the logs to the logs folder and to console
     */
	@AfterClass
    public static void afterClass() {
        System.out.print(builder);
        writer.print(builder);
        writer.close();
        driver.quit();
    }
	
	@Test
	/**
	 * Test login button
	 */
	public void testLoginButton() {
		driver.get(PAGE_TO_TEST);
		WebElement loginButton = driver.findElement(By.id(LOGIN_BUTTON_ID));
		loginButton.click();
		WebElement loginPopUp = driver.findElement(By.id(LOGIN_POPUP_ID));
		WebElement email = driver.findElement(By.id(LOGIN_EMAIL_TEXT_FIELD));
		WebElement password = driver.findElement(By.id(LOGIN_PASSWORD_TEXT_FIELD));
		//enter email and password
		email.sendKeys(TEST_EMAIL_ADDRESS);
		password.sendKeys(TEST_PASSWORD);
		//Click login submit button
		driver.findElement(By.id(LOGIN_SUBMIT_BUTTON_ID)).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(loginPopUp.isDisplayed()) {
			Assert.fail("Login Stalling");
		}
		

	}
	
	/**
	 * Test all links on a page
	 * Fails if any link fails to load a page
	 * Logs any time that the attempted url does not match the reached url
	 */
	@Test
	public void testAllLinks() {
		System.out.println(PAGE_TO_TEST);
		System.out.println(TEST_NAME);
		driver.get(PAGE_TO_TEST);
		List<WebElement> allLinks = driver.findElements(By.tagName("a"));
		String[] handles = new String[2];
		
		for(int index = 0; index < allLinks.size(); index++) {
			WebElement link = allLinks.get(index);
			String correctUrl = link.getAttribute("href");
			
			((JavascriptExecutor) driver).executeScript
			("arguments[0].scrollIntoView(true);", link);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} 
			
			//This is a shift+click for new tab
			Actions actions = new Actions(driver);
			actions.keyDown(Keys.SHIFT)
		    .click(link)
		    .keyUp(Keys.SHIFT)
		    .build()
		    .perform();
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			handles = driver.getWindowHandles().toArray(handles);
			
			if(handles[1] == null) {
				System.out.println("Tried to go to: " + correctUrl);
				
				errorLogs.append(("Tried to go to: " + correctUrl + " and failed"));
			}
			
			System.out.println(handles[0] + " " + handles[1]);
			driver.switchTo().window(handles[1]);
			
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			String currentUrl = driver.getCurrentUrl();
			if(!currentUrl.equals(correctUrl)) {
				errorLogs.append("URL mismatch: Tried to go to " + correctUrl
						+ " went to " + currentUrl + EOL);
				
			}
			//Assert.assertEquals(correctUrl, currentUrl);
			System.out.println("Link Successful");
			// close window
			driver.close();
			driver.switchTo().window(handles[0]);
			
		}
		
	}
	
	
	
}
