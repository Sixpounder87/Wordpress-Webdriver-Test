package testcases;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import seleniumObjectMap.ReadPropertyData;

public class LoginWordpress {

	private WebDriver driver;
	private WebDriverWait wait;
	private final static int TIMEOUTsec = 10;

	@BeforeClass
	public void preconditions() {
		System.setProperty("webdriver.chrome.driver",
				ReadPropertyData.getDriverPath());
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, TIMEOUTsec);
	}

	@Test
	public void testWordpress() {

		// Step 1. Open the main page and check by id "login".
		driver.get(ReadPropertyData.getBaseUrl());
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));

		// Step 2. Authentication.
		final WebElement userLogin = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.id("user_login")));
		assertTrue(userLogin.isDisplayed());
		final WebElement userPassword = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.id("user_pass")));
		assertTrue(userPassword.isDisplayed());
		final WebElement enterButton = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.id("wp-submit")));
		assertTrue(enterButton.isDisplayed() && enterButton.isEnabled());

		userLogin.sendKeys(ReadPropertyData.getUsername());
		userPassword.sendKeys(ReadPropertyData.getUserPassword());
		enterButton.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector("div.wrap")));

		// Step 3. Creating unique post.
		final WebElement records = wait
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("li#wp-admin-bar-new-content > a > span.ab-label")));
		assertTrue(records.isDisplayed() && records.isEnabled());
		records.click();
		final WebElement title = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.cssSelector("input#title")));
		title.sendKeys("Unique post\tFuckin' firefox driver doesn't work properly! That's why I'm using Chrome");

		// Use sleep below as a workaround. The spinner won't appear and the
		// post won't be published without sleep-call
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		final WebElement publishButton = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.id("publish")));
		assertTrue(publishButton.isDisplayed() && publishButton.isEnabled());
		publishButton.click();
		(new WebDriverWait(driver, TIMEOUTsec))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath("//div[@id='message']/p[text()='Запись опубликована. ']")));

	}

	@AfterClass
	public void postconditions() {
		driver.quit();
	}
}
