package Search;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.apache.commons.io.FileUtils;

public class HappyPath {
	private WebDriver driver;
	private String baseUrl;
	private String searhText;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private ExtentReports report;
	private ExtentTest test;

	@BeforeClass(alwaysRun = true)
	public void setUp() {
		driver = new ChromeDriver();
		baseUrl = "https://www.mamasandpapas.ae/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		report = new ExtentReports("test-output/SearchReport.html", true);
		
		driver.get(baseUrl + "/");
		
		TakeScreenShot ("siteloaded");
		
		if (isAlertPresent()) {
			closeAlertAndGetItsText();
		}

		if (isElementPresent(By.cssSelector("#onesignal-popover-container"))) {

			driver.findElement(By.cssSelector("#onesignal-popover-cancel-button")).click();
		}

		if (isElementPresent(By.cssSelector("#campaign-modal > div:nth-child(1) > div:nth-child(1)"))) {

			driver.findElement(By.cssSelector(
					"#campaign-modal > div.vertical-alignment-helper > div.modal-dialog.vertical-align-center > div.modal-content > div.modal-header > button.close"))
					.click();
		}
	}

	@Test
	public void testSearchExist() throws IOException {

		searhText = "red";
		
		driver.findElement(By.name("q")).clear();
		driver.findElement(By.name("q")).sendKeys(searhText);
		
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.show-all")));
		
		Assert.assertTrue(isElementPresent(By.cssSelector("button.show-all")));
		
		
		driver.findElement(By.name("q")).clear();
		driver.findElement(By.name("q")).sendKeys(searhText);
		driver.findElement(By.cssSelector("button.show-all")).click();
		String items = driver.findElement(By.cssSelector(".count > span:nth-child(1)")).getAttribute("innerHTML");
		
		String[] array1 = items.split("/");
		
		int ItemsOnPage = Integer.parseInt(array1[0]);
		int TotalItems = Integer.parseInt(array1[0]);

		if (ItemsOnPage > 12 && ItemsOnPage > TotalItems) {
			Assert.assertTrue(isElementPresent(By.className("btn btn-empty load-more")));
		}
		else {
			Assert.assertFalse(isElementPresent(By.className("btn btn-empty load-more")));
		}
		
	}
	
	@Test
	public void testSearchdDontExist() throws IOException {
		driver.get(baseUrl + "/");
		
		searhText = "noitemswiththiskeyword";
		
		driver.findElement(By.name("q")).clear();
		driver.findElement(By.name("q")).sendKeys(searhText);
		
		//Assert.assertFalse(isElementPresent(By.cssSelector("button.show-all")));
		
		
		driver.findElement(By.name("q")).clear();
		driver.findElement(By.name("q")).sendKeys(searhText);
		driver.findElement(By.cssSelector("div.input-field > input[type=\"submit\"]:nth-child(2)")).click();
		String ResultMessage = driver.findElement(By.cssSelector(".blank-search-message")).getAttribute("innerHTML");
		
		String ExpectedResultMessage = "You searched for <strong>"+ searhText +"</strong>, we're sorry we couldn't find anything to match your search.";
		Assert.assertEquals(ResultMessage, ExpectedResultMessage);
		
		//Assert.assertFalse(isElementPresent(By.className("btn btn-empty load-more")));
	}
	
	private void TakeScreenShot (String name) {
		
		test.log(LogStatus.INFO, "Taking Screenshot");
		File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenShot, new File("screenshots/" + name + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.quit();
		report.endTest(test);
		report.flush();
		
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
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
