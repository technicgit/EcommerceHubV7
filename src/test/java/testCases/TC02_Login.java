package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;

public class TC02_Login extends BaseClass {

	@Test(
			groups = {"sanity", "regression"},
			retryAnalyzer = utilities.RetryAnalyzer.class)
	public void testLogin() {

		logger.info("========== Login Test Started ==========");

		try {
			HomePage hp = new HomePage(getDriver());

			logger.debug("Opening the login page");
			hp.clickMyAccount();
			hp.goToLogin();

			LoginPage lp = new LoginPage(getDriver());

			logger.debug("Entering login credentials");
			lp.setEmail("darrenfadaie@gmail.com");
			lp.setPwd("Simplep@ss123!");
			lp.clickLogin();

			logger.info("Login submitted");

			AccountPage ap = new AccountPage(getDriver());
			boolean status = ap.getMyAccountConfirmation().isDisplayed();

			Assert.assertTrue(
					status,
					"My Account heading must be displayed after login");

			logger.info("Login verification passed");

		} catch (AssertionError e) {
			logger.error("Login assertion failed", e);
			throw e;

		} catch (Exception e) {
			logger.error("Login test failed", e);
			throw e;

		} finally {
			logger.info("========== Login Test Finished ==========");
		}
	}
}