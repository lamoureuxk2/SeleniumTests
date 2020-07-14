package basicTests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;

/**
 * To write a test for a new page, copy all the data from this up until the first @Test
 * Then replace the PAGE_TO_TEST variable with the appropriate page.
 * Write @Test methods specific to the page you are testing
 * 
 * Ensure the PATH_TO_GECKODRIVER is set to your path to geckodriver.exe
 * @author Karl
 *
 */
public class IndexPageTest {
	
	//Location of your geckodriver executable
	public static final String PATH_TO_GECKODRIVER = "C:\\Selenium\\geckodriver.exe";
	public static final String PAGE_TO_TEST = "https://agile-warriors.bubbleapps.io/version-test";
	private static final String LOG_DIR = "logs/";
	private static final String TEST_NAME = "IndexPageTest";
	private static StringBuilder builder = new StringBuilder();
	private static PrintWriter writer;
	
	static WebDriver driver;
	
	private static final String EOL = 
	        System.getProperty("line.separator");
	
	@BeforeClass
	public static void beforeClass() {
		System.setProperty("webdriver.gecko.driver", PATH_TO_GECKODRIVER);
		driver = new FirefoxDriver();
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
	
	@AfterClass
    public static void afterClass() {
        System.out.print(builder);
        writer.print(builder);
        writer.close();
    }
	
	@Test
	public void testAssessmentLink() {
		
		driver.get(PAGE_TO_TEST);
		WebElement assessmentLink = driver.findElement(By.id("AssessmentLink"));
		assessmentLink.click();
		String url = driver.getCurrentUrl();
		Assert.assertEquals(url, "https://agile-warriors.bubbleapps.io/version-test/maturity_assessment");
	}
	
}
