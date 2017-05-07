package commonConfig;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Config {
	
	public WebDriver driver;
	public String baseUrl;
	public boolean acceptNextAlert = true;
	public StringBuffer verificationErrors = new StringBuffer();
	public ExtentReports report;
	public ExtentTest test;
	public String searhText;
	public PropertiesConfiguration conf=null;
	
	@Parameters("browser")
	
	@BeforeClass
	public void beforeClass( String browser) {
				
		try {
			conf=new PropertiesConfiguration("src/main/java/commonConfig/cssClasses.properties");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		if (browser.equalsIgnoreCase("firefox")) {
			 
			  driver = new FirefoxDriver();  
		 
		 }
		else if (browser.equalsIgnoreCase("chrome")) {

			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--kiosk");
			driver = new ChromeDriver(chromeOptions);
		}
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseUrl = "https://www.mamasandpapas.ae";		
		driver.get(baseUrl + "/");
		
		if (isAlertPresent()) {
			closeAlertAndGetItsText();
		}

		if (isElementPresent(By.cssSelector("#onesignal-popover-container"))) {

			driver.findElement(By.cssSelector("#onesignal-popover-cancel-button")).click();

		}

		if (isElementPresent(By.cssSelector("#campaign-modal"))) {
			
			
			By loadingPopup = By.cssSelector("#onesignal-popover-container");
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingPopup));
			
			driver.findElement(By.cssSelector(
					"#campaign-modal > div.vertical-alignment-helper > div.modal-dialog.vertical-align-center > div.modal-content > div.modal-header > button.close"))
					.click();

		}
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
		report.endTest(test);
		report.flush();
		
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}
	
	public void takeScreenShot (String name) {
		
//		test.log(LogStatus.INFO, "Taking Screenshot");
//		File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//		try {
//			FileUtils.copyFile(screenShot, new File("screenshots/" + name + ".png"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	public String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
	
}
