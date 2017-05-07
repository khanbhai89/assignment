package ProductDescriptionPage;

import org.openqa.selenium.*;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import commonConfig.Config;

public class HappyPath extends Config {

	@Test
	public void PDPElementsExists() {
		
		report = new ExtentReports("test-output/PDPReport.html", true);
		test = report.startTest("PDP Elements Exists");
		//TakeScreenShot ("siteloaded");
		driver.get(baseUrl + "/capella-bouncer-catch-a-star-208904313.html");

		Assert.assertTrue(isElementPresent(By.cssSelector(conf.getString("titleClass"))));
		
		if (isElementPresent(By.cssSelector(conf.getString("titleClass")))) {
			
			test.log(LogStatus.PASS, "Title exists on PDP.");
		}
		else {
			
			test.log(LogStatus.FAIL, "Title doesn't exists on PDP.");
		}
		
		Assert.assertTrue(
				isElementPresent(By.cssSelector(conf.getString("favoriteButton"))));
		
		if (isElementPresent(By.cssSelector(conf.getString("favoriteButton")))) {
			
			test.log(LogStatus.PASS, "Favorite button exists on PDP.");
		}
		else {
			
			test.log(LogStatus.FAIL, "Favorite button doesn't exists on PDP.");
		}
		
		if (conf.getString("multipleImagesClass").equals(returnClass ("div.slider:nth-child(1)"))) {

			Assert.assertTrue(isElementPresent(
					By.cssSelector(conf.getString("multipleImages"))));

			if (isElementPresent(
					By.cssSelector(conf.getString("multipleImages")))) {
				
				test.log(LogStatus.PASS, "Multiple Images are displayed.");
				Assert.assertTrue(isElementPresent(By.cssSelector("figure.slick-current:nth-child(2) > a:nth-child(1) > img:nth-child(1)")));
			}

		} else {
			
			Assert.assertTrue(isElementPresent(By
					.cssSelector(conf.getString("singleImages"))));

			if (isElementPresent(By
					.cssSelector(conf.getString("singleImages")))) {
				
				test.log(LogStatus.PASS, "Single Image is displayed.");
				Assert.assertTrue(isElementPresent(By.cssSelector(
						"div.slider:nth-child(5) > div:nth-child(1) > div:nth-child(1) > figure:nth-child(1) > a:nth-child(1) > img:nth-child(1)")));
			}
		}

		Assert.assertTrue(isElementPresent(
				By.cssSelector("div.product-info.col-md-5 > div.action-buttons.row > div > button > span")));
		
		report.endTest(test);

	}
	
	@Test
	public void multiImagesIfExists() {
		
		driver.get(baseUrl + "/capella-bouncer-catch-a-star-208904313.html");
		
		test = report.startTest("Multi Images Exists");
		
		if (conf.getString("multipleImagesClass").equals(returnClass ("div.slider:nth-child(1)"))) {

			Assert.assertTrue(imagesAreSame());
			
			if (imagesAreSame()) {
				
				test.log(LogStatus.PASS, "Correct Image is selected.");
			}
			else {
				
				test.log(LogStatus.FAIL, "Correct Image is not selected.");
			}
			
			test.log(LogStatus.INFO, "Slider image is changed.");
			driver.findElement(By.xpath("//figure[3]/a/img")).click();
			Assert.assertTrue(imagesAreSame());
			
			if (imagesAreSame()) {
				
				test.log(LogStatus.PASS, "Correct Image is selected.");
			}
			else {
				
				test.log(LogStatus.FAIL, "Correct Image is not selected.");
			}
			
		   // driver.findElement(By.xpath("//figure/a/img")).click();
		   // Assert.assertTrue(imagesAreSame());
		   // driver.findElement(By.xpath("//figure[2]/a/img")).click();
		   // Assert.assertTrue(imagesAreSame());
		   // driver.findElement(By.xpath("//figure/a/img")).click();
		   // Assert.assertTrue(imagesAreSame());
		   // driver.findElement(By.xpath("//figure[2]/a/img")).click();
		   // Assert.assertTrue(imagesAreSame());
		} 
		
		report.endTest(test);
		
	}
	
	@Test
	public void qauntityButton(){
		
		driver.get(baseUrl + "/capella-bouncer-catch-a-star-208904313.html");
		
		test = report.startTest("Quantity Button Check.");
		
		if (quantityNumber () <= 1) {
			Assert.assertTrue(isElementPresent(By.cssSelector("a.decrease.disabled")));
			
			if (isElementPresent(By.cssSelector("a.decrease.disabled"))) {
				
				test.log(LogStatus.PASS, "Decrease button is disabled on 1.");
			}
			else {
				
				test.log(LogStatus.FAIL, "Decrease button is enabled on 1.");
			}
			
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
			
			if (isElementPresent(By.cssSelector("a.decrease.disabled"))) {
				
				test.log(LogStatus.PASS, "Increase button is disabled on 1.");
			}
			else {
				
				test.log(LogStatus.FAIL, "Increase button is enabled on 1.");
			}
		}
	    
	    report.endTest(test);
	}
	
	@Test
	public void favorites () {
		
		driver.get(baseUrl + "/capella-bouncer-catch-a-star-208904313.html");
		
		test = report.startTest("Favorite Button Check.");
		
		String loginBoxDisplayed = "container-fluid modal fade in";
		String favoritedButton = "btn btn-empty toggle-favorites favorited";
		
		if(isElementPresent(By.cssSelector("#welcome-and-cart > a.login-button"))) {
			
			driver.findElement(By.cssSelector(conf.getString("favoriteButton"))).click();
			Assert.assertTrue(loginBoxDisplayed.equals(returnClass ("#login-modal")));
			Assert.assertFalse(favoritedButton.equals(returnClass (conf.getString("favoriteButton"))));
			
			if (loginBoxDisplayed.equals(returnClass ("#login-modal"))) {
				
				test.log(LogStatus.PASS, "Login form is opened when user is not registered.");
			}
			else {
				
				test.log(LogStatus.FAIL, "Login form is not opened when user is not registered.");
			}
			
		}

		if (isElementPresent(By.cssSelector("#user-info-box"))) {
			
			driver.findElement(By.cssSelector(conf.getString("favoriteButton"))).click();
			Assert.assertFalse(loginBoxDisplayed.equals(returnClass ("#login-modal")));
			Assert.assertTrue(favoritedButton.equals(returnClass (conf.getString("favoriteButton"))));
			
			if (favoritedButton.equals(returnClass (conf.getString("favoriteButton")))) {
				
				test.log(LogStatus.PASS, "Favorite Button is enabled.");
			}
			else {
				
				test.log(LogStatus.FAIL, "Favorite Button is disabled.");
			}
		}
		
		report.endTest(test);
		
	}
	
	private String returnClass (String CSSSelector) {
				
		return driver.findElement(By.cssSelector(CSSSelector)).getAttribute("class");
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

	
}
