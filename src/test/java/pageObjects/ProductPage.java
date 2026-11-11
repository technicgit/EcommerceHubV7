package pageObjects;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends BasePage {

	public ProductPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='input-option-225']")
	WebElement input_DeliveryDate;

	@FindBy(xpath = "//button[@id='button-cart']")
	WebElement btn_AddToCart;

	@FindBy(xpath = "//div[contains(@class,'alert-success') and contains(.,'HP LP3065')]")
	WebElement alert_Success;

	@FindBy(xpath = "//span[normalize-space()='Checkout']")
	WebElement link_Checkout;

	@FindBy(xpath = "//button[@title='Add to Wish List']")
	WebElement wishlistIcon;

	public void setDeliveryDate() {
		LocalDate date = LocalDate.now().plusDays(5);
		String formattedDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));

		scrollToView(input_DeliveryDate);
		input_DeliveryDate.clear();

		if (((RemoteWebDriver) driver).getCapabilities()
				.getBrowserName().equalsIgnoreCase("firefox")) {
			input_DeliveryDate.sendKeys(Keys.TAB);

			((JavascriptExecutor) driver).executeScript(
					"arguments[0].value = arguments[1];"
					+ "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));"
					+ "arguments[0].dispatchEvent(new Event('change', {bubbles: true}));",
					input_DeliveryDate, date.toString());
		} else {
			input_DeliveryDate.sendKeys(formattedDate);
			input_DeliveryDate.sendKeys(Keys.TAB);
		}
	}
	
	public boolean isDeliveryDateCorrect() {
		String expectedDate = LocalDate.now().plusDays(5).toString();
		String actualDate = input_DeliveryDate.getDomProperty("value");

		System.out.println("Browser: " + driver.getClass().getSimpleName()
				+ " Expected: [" + expectedDate
				+ "] Actual: [" + actualDate + "]");

		return expectedDate.equals(actualDate);
	}

	public void clickAddToCart() throws InterruptedException {
		scrollToView(btn_AddToCart);
		Thread.sleep(500);
		btn_AddToCart.click();
	}

	public void addToWishlist() throws InterruptedException {
		scrollToView(wishlistIcon);
		Thread.sleep(500);
		wishlistIcon.click();
	}

	public boolean isSuccessMessageDisplayed() {
		driver.manage().timeouts().implicitlyWait(Duration.ZERO);
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOf(alert_Success));
			return alert_Success.isDisplayed() && alert_Success.getText().contains("Success");
		} finally {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		}
	}

	public void clickCheckout() throws InterruptedException {
		scrollToView(link_Checkout);
		Thread.sleep(500);
		link_Checkout.click();
	}

	private void scrollToView(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
}