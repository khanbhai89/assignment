package FacebookLogin;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import commonConfig.Config;

public class HappyPath extends Config {

	@Test
	public void testLogin() {
		
		report = new ExtentReports("test-output/FacebookReport.html", true);
		test = report.startTest("Login to Mamas & Papas through Facebook.");
		takeScreenShot ("siteloaded");
		
		driver.findElement(By.linkText("Sign In / Register")).click();
		driver.findElement(By.linkText("Facebook Connect")).click();

		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));

		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		
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
			takeScreenShot ("Log-out");
			test.log(LogStatus.INFO, "Logging Out");
			driver.findElement(By.id("logout-link")).click();
		}
		else {
			
			takeScreenShot ("Not-Logged-in");
			test.log(LogStatus.FAIL, "User is not logged in through Facebook.");
			Assert.assertFalse(!isElementPresent(By.cssSelector("#user-info-box")));
		}
		
		if (isElementPresent(By.cssSelector("#welcome-and-cart > a.login-button"))) {
			
			takeScreenShot ("Logged-Out");
			test.log(LogStatus.PASS, "User is logged out through Facebook.");
			Assert.assertTrue(isElementPresent(By.cssSelector("#welcome-and-cart > a.login-button")));
		}
		else {
			takeScreenShot ("Not-Logged-Out");
			test.log(LogStatus.FAIL, "User is not logged out through Facebook.");
			Assert.assertFalse(!isElementPresent(By.cssSelector("#welcome-and-cart > a.login-button")));
		}
		
	}
	
}
