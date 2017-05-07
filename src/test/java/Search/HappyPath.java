package Search;

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
	public void testSearchExist() {
		
		report = new ExtentReports("test-output/SearchReport.html", true);
		test = report.startTest("Search Text Exists.");
		searhText = "red";
		
		driver.findElement(By.name("q")).clear();
		driver.findElement(By.name("q")).sendKeys(searhText);
		test.log(LogStatus.INFO, "Searching for text \"red\".");
		
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(conf.getString("showAllButton"))));
		
		Assert.assertTrue(isElementPresent(By.cssSelector("button.show-all")));
		
		if (isElementPresent(By.cssSelector(conf.getString("showAllButton")))) {
			
			test.log(LogStatus.PASS, "Button show all exists.");
		}
		else {
			takeScreenShot ("showalldontexist");
			test.log(LogStatus.FAIL, "Button show all doesn't exist.");
		}
		
		driver.findElement(By.name("q")).clear();
		driver.findElement(By.name("q")).sendKeys(searhText);
		driver.findElement(By.cssSelector(conf.getString("showAllButton"))).click();
		test.log(LogStatus.INFO, "Searching for text \"red\".");
		String items = driver.findElement(By.cssSelector(".count > span:nth-child(1)")).getAttribute("innerHTML");
		
		String[] array1 = items.split("/");
		
		int itemsOnPage = Integer.parseInt(array1[0]);
		int totalItems = Integer.parseInt(array1[0]);

		if (itemsOnPage > 12 && itemsOnPage > totalItems) {
			Assert.assertTrue(isElementPresent(By.className("btn btn-empty load-more")));
			test.log(LogStatus.PASS, "Load more button exists.");
		}
		else {
			Assert.assertFalse(isElementPresent(By.className("btn btn-empty load-more")));
			takeScreenShot ("loadmoredontexist");
			test.log(LogStatus.FAIL, "Load more button doesn't exist.");
		}
		
		report.endTest(test);
		
	}
	
	@Test
	public void testSearchdDontExist() {
		driver.get(baseUrl + "/");
		
		test = report.startTest("Search Text Dont Exists.");
		
		searhText = "noitemswiththiskeyword";
		
		driver.findElement(By.name("q")).clear();
		driver.findElement(By.name("q")).sendKeys(searhText);
		driver.findElement(By.cssSelector("button.search")).click();
		test.log(LogStatus.INFO, "Searching for text \"noitemswiththiskeyword\".");
		
		String resultMessage = driver.findElement(By.cssSelector(".blank-search-message")).getAttribute("innerHTML");
		String expectedResultMessage = "You searched for <strong>"+ searhText +"</strong>, we're sorry we couldn't find anything to match your search.";
		Assert.assertEquals(resultMessage, expectedResultMessage);
		
		if (resultMessage.equals(expectedResultMessage)) {
			test.log(LogStatus.PASS, "Correct text is displayed.");
		}
		else {
			test.log(LogStatus.PASS, "Incorrect text is displayed.");
		}
		
	}
	
}
