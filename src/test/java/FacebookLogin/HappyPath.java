package FacebookLogin;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.apache.commons.io.FileUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class HappyPath {

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private ExtentReports report;
	private ExtentTest test;

	@BeforeClass
	public void beforeClass() {
		driver = new ChromeDriver();
		baseUrl = "https://www.mamasandpapas.ae/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		report = new ExtentReports("test-output/FacebookReport.html", true);
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

	@Test
	public void testLogin(){
		driver.get(baseUrl + "/");
		
		test = report.startTest("Login to Mamas & Papas through Facebook.");
		test.log(LogStatus.INFO, "Site Loaded");
		TakeScreenShot ("siteloaded");

		if (isAlertPresent()) {
			closeAlertAndGetItsText();
		}

		if (isElementPresent(By.cssSelector("#onesignal-popover-container"))) {

			TakeScreenShot ("pop-over");
			test.log(LogStatus.INFO, "Pop-up for promotions is displayed.");
			driver.findElement(By.cssSelector("#onesignal-popover-cancel-button")).click();
			test.log(LogStatus.INFO, "Pop-up for promotions is closed.");
		}

		if (isElementPresent(By.cssSelector("#campaign-modal > div:nth-child(1) > div:nth-child(1)"))) {

			TakeScreenShot ("modal-pop-up");

			driver.findElement(By.cssSelector(
					"#campaign-modal > div.vertical-alignment-helper > div.modal-dialog.vertical-align-center > div.modal-content > div.modal-header > button.close"))
					.click();
			test.log(LogStatus.INFO, "Modal Pop-up for promotions is closed.");
		}

		driver.findElement(By.linkText("Sign In / Register")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.linkText("Facebook Connect")).click();

		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));

		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		test.log(LogStatus.INFO, "Transfered to Facebook.");
		
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("zointpr_thurnson_1465123943@tfbnw.net");
		driver.findElement(By.id("pass")).clear();
		driver.findElement(By.id("pass")).sendKeys("1234qwe");
		driver.findElement(By.id("loginbutton")).click();	

		if (isElementPresent(By.cssSelector("#user-info-box"))) {
			
			driver.findElement(By.cssSelector("#user-info-box > span:nth-child(1)")).click();
			test.log(LogStatus.PASS, "User is logged in through Facebook.");
			Assert.assertTrue(isElementPresent(By.cssSelector("#user-info-box")));
			TakeScreenShot ("Log-out");
			test.log(LogStatus.INFO, "Logging Out");
			driver.findElement(By.id("logout-link")).click();
		}
		else {
			
			TakeScreenShot ("Not-Logged-in");
			test.log(LogStatus.FAIL, "User is not logged in through Facebook.");
			Assert.assertFalse(!isElementPresent(By.cssSelector("#user-info-box")));
		}
		
		if (isElementPresent(By.cssSelector("#welcome-and-cart > a.login-button"))) {
			
			TakeScreenShot ("Logged-Out");
			test.log(LogStatus.PASS, "User is logged out through Facebook.");
			Assert.assertTrue(isElementPresent(By.cssSelector("#welcome-and-cart > a.login-button")));
		}
		else {
			TakeScreenShot ("Not-Logged-Out");
			test.log(LogStatus.FAIL, "User is not logged out through Facebook.");
			Assert.assertFalse(!isElementPresent(By.cssSelector("#welcome-and-cart > a.login-button")));
		}
		
	}
	
	private void TakeScreenShot (String name) {
		
		test.log(LogStatus.INFO, "Taking Screenshot");
		//File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		//try {
		//	FileUtils.copyFile(screenShot, new File("screenshots/" + name + ".png"));
		//} catch (IOException e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
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
