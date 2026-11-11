package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//i[@class='fa-solid fa-user']")
	WebElement link_MyAccount;

	@FindBy(xpath = "//a[normalize-space()='Login']")
	WebElement link_Login;

	public void clickMyAccount() {
		link_MyAccount.click();
	}

	public void goToLogin() {
		link_Login.click();
	}
}