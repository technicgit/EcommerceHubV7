package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountPage;
import pageObjects.CategoryPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.ProductPage;
import testBase.BaseClass;

public class TC05_AddToWishList extends BaseClass {

	@Test(
			groups = {"regression"},
			retryAnalyzer = utilities.RetryAnalyzer.class)
	public void testAddToWishList() throws InterruptedException {

		logger.info("========== Add To Wish List Test Started ==========");

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

			CategoryPage cp = new CategoryPage(getDriver());

			logger.debug("Opening all laptops and notebooks");
			cp.clickLaptopsAndNotebooks();
			cp.clickShowAll();

			logger.debug("Selecting HP LP3065");
			cp.selectHPProduct();

			ProductPage pp = new ProductPage(getDriver());
			pp.addToWishlist();

			logger.info("Add to Wish List clicked");

			Assert.assertTrue(
					pp.isSuccessMessageDisplayed(),
					"Wishlist message not shown.");

			logger.info("Wish list verification passed");

		} catch (AssertionError e) {
			logger.error("Wish list assertion failed", e);
			throw e;

		} catch (Exception e) {
			logger.error("Add to Wish List test failed", e);
			throw e;

		} finally {
			logger.info("========== Add To Wish List Test Finished ==========");
		}
	}
}