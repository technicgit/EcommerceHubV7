package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountPage;
import pageObjects.AffiliatePage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;

public class TC06_AddAffiliate extends BaseClass {

	@Test(
			groups = {"regression"},
			retryAnalyzer = utilities.RetryAnalyzer.class)
	public void testAddAffiliate() throws InterruptedException {

		logger.info("========== Add Affiliate Test Started ==========");

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

			AccountPage accountPage = new AccountPage(getDriver());

			Assert.assertTrue(
					accountPage.getMyAccountConfirmation().isDisplayed(),
					"My Account heading must be displayed after login");

			logger.info("Login verification passed");

			AffiliatePage ap = new AffiliatePage(getDriver());

			logger.debug("Opening the affiliate form");
			ap.navigateToAffiliateForm();

			logger.debug("Entering and submitting affiliate details");
			ap.fillAffiliateDetails(
					"CloudBerry",
					"https://testqa12345.com",
					"123456",
					"Darren Fadaie");

			Assert.assertTrue(
					ap.isAffiliateAdded(),
					"Affiliate details not added successfully.");

			logger.info("Affiliate details verification passed");

		} catch (AssertionError e) {
			logger.error("Affiliate assertion failed", e);
			throw e;

		} catch (Exception e) {
			logger.error("Add Affiliate test failed", e);
			throw e;

		} finally {
			logger.info("========== Add Affiliate Test Finished ==========");
		}
	}
}