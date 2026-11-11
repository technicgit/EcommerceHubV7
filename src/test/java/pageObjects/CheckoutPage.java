package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BasePage {

	public CheckoutPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//strong[normalize-space()='login page']")
	WebElement loginPageLink;

	@FindBy(id = "input-shipping-existing")
	WebElement existingShippingAddress;

	@FindBy(id = "input-shipping-address")
	WebElement shippingAddressDropdown;

	@FindBy(id = "button-shipping-methods")
	WebElement shippingMethodsButton;

	@FindBy(id = "button-shipping-method")
	WebElement flatShippingButton;

	@FindBy(id = "button-payment-methods")
	WebElement paymentMethodsButton;

	@FindBy(id = "button-payment-method")
	WebElement codButton;

	@FindBy(xpath = "//div[@id='checkout-confirm']//button[contains(.,'Confirm Order')]")
	WebElement confirmButton;

	public void clickLogin() {
		loginPageLink.click();
	}

	public void completeCheckout() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(Duration.ZERO);

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

			wait.until(ExpectedConditions.elementToBeClickable(existingShippingAddress)).click();
			wait.until(ExpectedConditions.elementToBeClickable(shippingAddressDropdown));

			Select shippingAddress = new Select(shippingAddressDropdown);
			shippingAddress.selectByIndex(1);
			Thread.sleep(2000);

			wait.until(ExpectedConditions.elementToBeClickable(shippingMethodsButton)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modal-shipping")));
			Thread.sleep(1000);

			wait.until(ExpectedConditions.elementToBeClickable(flatShippingButton)).click();

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("modal-shipping")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(
					By.xpath("//div[contains(@class,'modal-backdrop')]")));

			wait.until(ExpectedConditions.elementToBeClickable(paymentMethodsButton)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modal-payment")));
			Thread.sleep(1000);

			wait.until(ExpectedConditions.elementToBeClickable(codButton)).click();

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("modal-payment")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(
					By.xpath("//div[contains(@class,'modal-backdrop')]")));

			wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView(true);", confirmButton);
			confirmButton.click();

		} finally {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		}
	}
}