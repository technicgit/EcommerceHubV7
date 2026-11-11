package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConfirmationPage extends BasePage {

	public ConfirmationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h1[normalize-space()='Your order has been placed!']")
	WebElement orderSuccessHeader;

	public boolean isOrderPlaced() {
		driver.manage().timeouts().implicitlyWait(Duration.ZERO);
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			return wait.until(ExpectedConditions.visibilityOf(orderSuccessHeader)).isDisplayed();
		} finally {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		}
	}
}