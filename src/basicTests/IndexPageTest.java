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
 * Test for the Index page of Agile Warriors
 * @author Karl
 *
 */
public class IndexPageTest extends AbstractPageTest {

	@BeforeClass
	public static void beforeClassSetPageSpecifics() {
		TEST_NAME = "IndexPageTest";
		PAGE_TO_TEST = "https://agile-warriors.bubbleapps.io/";
	}
}
