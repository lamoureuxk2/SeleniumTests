package basicTests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;

public class IndexPageTest {
	
	//Location of your gecko driver executable
	public static final String PATH_TO_GECKODRIVER = "C:\\Selenium\\geckodriver.exe";
	public static final String PAGE_TO_TEST = "https://agile-warriors.bubbleapps.io/version-test";
	static WebDriver driver;

	public static void main(String[] args) {
		System.setProperty("webdriver.gecko.driver", PATH_TO_GECKODRIVER);
		driver = new FirefoxDriver();
		
		System.out.println("Assessment Link works: " + testAssessmentLink());
	
	}
	
	public static boolean testAssessmentLink() {
		driver.get(PAGE_TO_TEST);
		WebElement assessmentLink = driver.findElement(By.id("AssessmentLink"));
		assessmentLink.click();
		String url = driver.getCurrentUrl();
		return url.equals("https://agile-warriors.bubbleapps.io/version-test/maturity_assessment");
	}
	
	

}
