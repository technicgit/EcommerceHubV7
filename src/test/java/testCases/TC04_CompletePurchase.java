package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CategoryPage;
import pageObjects.CheckoutPage;
import pageObjects.ConfirmationPage;
import pageObjects.LoginPage;
import pageObjects.ProductPage;
import testBase.BaseClass;

public class TC04_CompletePurchase extends BaseClass {

	@Test(
			groups = {"sanity", "regression"},
			retryAnalyzer = utilities.RetryAnalyzer.class)
	public void testCompletePurchase() throws InterruptedException {

		logger.info("========== Complete Purchase Test Started ==========");

		try {
			CategoryPage cp = new CategoryPage(getDriver());

			logger.debug("Opening all laptops and notebooks");
			cp.clickLaptopsAndNotebooks();
			cp.clickShowAll();

			logger.debug("Selecting HP LP3065");
			cp.selectHPProduct();

			ProductPage pp = new ProductPage(getDriver());

			logger.debug("Setting the delivery date");
			pp.setDeliveryDate();

			Assert.assertTrue(
					pp.isDeliveryDateCorrect(),
					"Delivery date must be five days from today.");

			logger.info("Delivery date verified");

			pp.clickAddToCart();

			Assert.assertTrue(
					pp.isSuccessMessageDisplayed(),
					"Add to Cart Failed!");

			logger.info("Product added to cart");

			pp.clickCheckout();
			logger.info("Checkout opened");

			CheckoutPage cop = new CheckoutPage(getDriver());
			cop.clickLogin();

			LoginPage lp = new LoginPage(getDriver());

			logger.debug("Entering login credentials");
			lp.setEmail("darrenfadaie@gmail.com");
			lp.setPwd("Simplep@ss123!");
			lp.clickLogin();

			logger.info("Login submitted");

			logger.debug("Completing checkout and submitting the order");
			cop.completeCheckout();

			ConfirmationPage confirmationPage =
					new ConfirmationPage(getDriver());

			Assert.assertTrue(
					confirmationPage.isOrderPlaced(),
					"Order placement failed!");

			logger.info("Order placement verification passed");

		} catch (AssertionError e) {
			logger.error("Complete purchase assertion failed", e);
			throw e;

		} catch (Exception e) {
			logger.error("Complete purchase test failed", e);
			throw e;

		} finally {
			logger.info("========== Complete Purchase Test Finished ==========");
		}
	}
}