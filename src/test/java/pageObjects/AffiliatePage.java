package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AffiliatePage extends BasePage {

	public AffiliatePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//footer//a[normalize-space()='Affiliate']")
	WebElement affiliateLink;

	@FindBy(id = "input-company")
	WebElement inputCompany;

	@FindBy(id = "input-website")
	WebElement inputWebsite;

	@FindBy(id = "input-tax")
	WebElement inputTax;

	@FindBy(id = "input-payment-cheque")
	WebElement chequePayment;

	@FindBy(id = "input-cheque")
	WebElement inputCheque;

	@FindBy(id = "input-agree")
	List<WebElement> agreementCheckboxes;

	@FindBy(xpath = "//form[@id='form-affiliate']//button[@type='submit']")
	WebElement continueButton;

	@FindBy(xpath = "//div[contains(@class,'alert-success') and contains(.,'affiliate account')]")
	WebElement successMessage;

	public void navigateToAffiliateForm() throws InterruptedException {
		scrollAndClick(affiliateLink);
	}

	public void fillAffiliateDetails(String company, String website, String tax, String chequeName)
			throws InterruptedException {
		inputCompany.clear();
		inputCompany.sendKeys(company);
		inputWebsite.clear();
		inputWebsite.sendKeys(website);
		inputTax.clear();
		inputTax.sendKeys(tax);
		scrollAndClick(chequePayment);
		inputCheque.clear();
		inputCheque.sendKeys(chequeName);

		driver.manage().timeouts().implicitlyWait(Duration.ZERO);
		try {
			if (agreementCheckboxes.size() > 0) {
				WebElement agreement = agreementCheckboxes.get(0);
				if (agreement.isSelected() == false) {
					scrollAndClick(agreement);
				}
			}
		} finally {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		}
		scrollAndClick(continueButton);
	}

	public boolean isAffiliateAdded() {
		driver.manage().timeouts().implicitlyWait(Duration.ZERO);
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOf(successMessage));
			return successMessage.isDisplayed() && successMessage.getText().contains("Success");
		} finally {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		}
	}

	private void scrollAndClick(WebElement element) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(500);
		element.click();
	}
}