package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC02_Login2_DataDriven extends BaseClass {

	@Test(
			groups = {"sanity", "regression", "datadriven"},
			dataProvider = "LoginData",
			dataProviderClass = DataProviders.class,
			retryAnalyzer = utilities.RetryAnalyzer.class)
	public void testLogin(String email, String pwd) {

		logger.info("========== Data Driven Login Test Started ==========");
		logger.info("Testing login for: {}", email);

		try {
			logger.debug("Opening the application home page");
			getDriver().get(p.getProperty("appURL"));

			HomePage hp = new HomePage(getDriver());

			logger.debug("Opening the login page");
			hp.clickMyAccount();
			hp.goToLogin();

			LoginPage lp = new LoginPage(getDriver());

			logger.debug("Entering login credentials");
			lp.setEmail(email);
			lp.setPwd(pwd);
			lp.clickLogin();

			logger.info("Login submitted");

			AccountPage ap = new AccountPage(getDriver());
			boolean status = ap.getMyAccountConfirmation().isDisplayed();

			if (status == true) {
				logger.info("Login successful for: {}", email);

				ap.clickMyAccountDropDown();
				ap.clickLogout();

				logger.info("Logout completed");

				Assert.assertTrue(status);
			} else {
				Assert.fail(
						"My Account heading was not displayed for: " + email);
			}

		} catch (AssertionError e) {
			logger.error("Login assertion failed for: {}", email, e);
			throw e;

		} catch (Exception e) {
			logger.error("Data driven login failed for: {}", email, e);
			throw e;

		} finally {
			logger.info("========== Data Driven Login Test Finished ==========");
		}
	}
}