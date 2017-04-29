package ProductDescriptionPage;

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
	private String searhText;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private ExtentReports report;
	private ExtentTest test;

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		driver = new ChromeDriver();
		baseUrl = "https://www.mamasandpapas.ae/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		report = new ExtentReports("test-output/PDPReport.html", true);
		
		driver.get(baseUrl + "/");

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
	public void PDPElementsExists() {
		TakeScreenShot ("siteloaded");
		searhText = "Capella Bouncer";

		driver.findElement(By.name("q")).clear();
		driver.findElement(By.name("q")).sendKeys(searhText);

		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.show-all")));


		driver.findElement(By.name("q")).clear();
		driver.findElement(By.name("q")).sendKeys(searhText);
		driver.findElement(By.cssSelector("button.show-all")).click();
		driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/div[1]/div/section/div/div[3]/div[1]/a/figure/div/div/img")).click();

		Assert.assertTrue(isElementPresent(By.cssSelector("div.title-and-price-wrapper > h1")));
		Assert.assertTrue(
				isElementPresent(By.cssSelector("div.add-to-cart-and-quantity > div.quantity-wrapper.clearfix > div")));

		String ClassName = driver.findElement(By.cssSelector("div.slider:nth-child(1)")).getAttribute("class");
		String MultiImageClassName = "slider slider-nav thumbnails  slick-initialized slick-slider slick-vertical";
		
		if (ClassName.equals(MultiImageClassName)) {

			Assert.assertTrue(isElementPresent(
					By.cssSelector("div.product-images.col-md-7 > div.product-images-wrapper.easyzoom-wrapper")));

			if (isElementPresent(
					By.cssSelector("div.product-images.col-md-7 > div.product-images-wrapper.easyzoom-wrapper"))) {
				
				 Assert.assertTrue(isElementPresent(By.cssSelector("figure.slick-current:nth-child(2) > a:nth-child(1) > img:nth-child(1)")));
			}

		} else {
			
			Assert.assertTrue(isElementPresent(By
					.cssSelector("div.product-images.col-md-7 > div.product-images-wrapper.easyzoom-wrapper.expand")));

			if (isElementPresent(By
					.cssSelector("div.product-images.col-md-7 > div.product-images-wrapper.easyzoom-wrapper.expand"))) {

				Assert.assertTrue(isElementPresent(By.cssSelector(
						"div.slider:nth-child(5) > div:nth-child(1) > div:nth-child(1) > figure:nth-child(1) > a:nth-child(1) > img:nth-child(1)")));
			}
		}

		Assert.assertTrue(isElementPresent(
				By.cssSelector("div.product-info.col-md-5 > div.action-buttons.row > div > button > span")));

	}
	
	@Test
	public void MultiImagesIfExists() throws IOException {
		
		driver.get(baseUrl + "/capella-bouncer-catch-a-star-208904313.html");

		String ClassName = driver.findElement(By.cssSelector("div.slider:nth-child(1)")).getAttribute("class");
		String MultiImageClassName = "slider slider-nav thumbnails  slick-initialized slick-slider slick-vertical";
		
		if (ClassName.equals(MultiImageClassName)) {

			Assert.assertTrue(imagesAreSame());
			driver.findElement(By.xpath("//figure[3]/a/img")).click();
			Assert.assertTrue(imagesAreSame());
		   // driver.findElement(By.xpath("//figure/a/img")).click();
		   // Assert.assertTrue(imagesAreSame());
		   // driver.findElement(By.xpath("//figure[2]/a/img")).click();
		   // Assert.assertTrue(imagesAreSame());
		   // driver.findElement(By.xpath("//figure/a/img")).click();
		   // Assert.assertTrue(imagesAreSame());
		   // driver.findElement(By.xpath("//figure[2]/a/img")).click();
		   // Assert.assertTrue(imagesAreSame());
		} 
		
	}
	
	@Test
	public void QauntityButton() throws IOException {
		
		driver.get(baseUrl + "/capella-bouncer-catch-a-star-208904313.html");
		
		if (quantityNumber () <= 1) {
			Assert.assertTrue(isElementPresent(By.cssSelector("a.decrease.disabled")));
		}
			
		driver.findElement(By.cssSelector("a.increase")).click();
	    driver.findElement(By.cssSelector("a.increase")).click();
	    driver.findElement(By.cssSelector("a.increase")).click();
	    driver.findElement(By.cssSelector("a.increase")).click();
	    driver.findElement(By.cssSelector("a.increase")).click();
	    driver.findElement(By.cssSelector("a.increase")).click();
	    driver.findElement(By.cssSelector("a.increase")).click();
	    driver.findElement(By.cssSelector("a.increase")).click();
	    driver.findElement(By.cssSelector("a.increase")).click();
	    
	    if (quantityNumber () >= 10) {
			Assert.assertTrue(isElementPresent(By.cssSelector("a.increase.disabled")));
		}
	}
	
	@Test
	public void Favorites () throws IOException {
		
		driver.get(baseUrl + "/capella-bouncer-catch-a-star-208904313.html");
		
		String LoginBoxDisplayed = "container-fluid modal fade in";
		String FavoritedButton = "btn btn-empty toggle-favorites favorited";
		
		if(isElementPresent(By.cssSelector("#welcome-and-cart > a.login-button"))) {
			
			driver.findElement(By.cssSelector("div.action-buttons.row > div > button.btn.btn-empty.toggle-favorites > span")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Assert.assertTrue(LoginBoxDisplayed.equals(ReturnClass ("//*[@id=\"login-modal\"]")));
			Assert.assertFalse(FavoritedButton.equals(ReturnClass ("/html/body/div[2]/div[3]/div[2]/div[2]/div[3]/div/button")));
		}
		
		if (isElementPresent(By.cssSelector("#user-info-box"))) {
			
			driver.findElement(By.cssSelector("div.action-buttons.row > div > button.btn.btn-empty.toggle-favorites > span")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Assert.assertFalse(LoginBoxDisplayed.equals(ReturnClass ("//*[@id=\"login-modal\"]")));
			Assert.assertTrue(FavoritedButton.equals(ReturnClass ("/html/body/div[2]/div[3]/div[2]/div[2]/div[3]/div/button")));
		}
		
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
	
	private String ReturnClass (String xPath) {
				
		return driver.findElement(By.xpath(xPath)).getAttribute("class");
	}
	
	private int quantityNumber () {
		
		String quantity = driver.findElement(By.cssSelector("div.add-to-cart-and-quantity > div.quantity-wrapper.clearfix > div > label > span > span")).getAttribute("innerHTML");
		
		Assert.assertFalse(Integer.parseInt(quantity) < 1);
		Assert.assertFalse(Integer.parseInt(quantity) > 10);
		return Integer.parseInt(quantity);
	}
	
	private boolean imagesAreSame () {
		
		String ThumbNailImage = driver.findElement(By.cssSelector("div.slider.slider-nav.thumbnails.slick-initialized.slick-slider.slick-vertical > div > div > figure.media_image.slick-slide.slick-current.slick-active > a > img")).getAttribute("src");
		String ActiveImage = driver.findElement(By.cssSelector("div.slider.slider-for.slick-initialized.slick-slider > div > div > figure.media_image.slick-slide.slick-current.slick-active > a > img")).getAttribute("src");
		
		String ReplacedThumbImage = ThumbNailImage.replace("p=thmb,","p=zoom,");
		
		if (ActiveImage.equals(ReplacedThumbImage)) {
			return true;
		}
		return false;
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
