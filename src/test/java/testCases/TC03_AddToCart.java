package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CategoryPage;
import pageObjects.ProductPage;
import testBase.BaseClass;

public class TC03_AddToCart extends BaseClass {

	@Test(
			groups = {"sanity", "regression"},
			retryAnalyzer = utilities.RetryAnalyzer.class)
	public void testAddToCart() throws InterruptedException {

		logger.info("========== Add To Cart Test Started ==========");

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
			logger.info("Add to Cart clicked");

			Assert.assertTrue(
					pp.isSuccessMessageDisplayed(),
					"Add to Cart Failed!");

			logger.info("Add to Cart verification passed");

		} catch (AssertionError e) {
			logger.error("Add to Cart assertion failed", e);
			throw e;

		} catch (Exception e) {
			logger.error("Add to Cart test failed", e);
			throw e;

		} finally {
			logger.info("========== Add To Cart Test Finished ==========");
		}
	}
}