package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import testBase.BaseClass;

public class TC01_LaunchApplication extends BaseClass {

	@Test(
			groups = {"sanity", "regression"},
			retryAnalyzer = utilities.RetryAnalyzer.class)
	public void testLaunchApplication() {

		logger.info("========== Launch Application Test Started ==========");

		try {
			logger.debug("Retrieving application title");

			String actualTitle = getDriver().getTitle();
			String expectedTitle = "Your store of fun";

			logger.info("Actual title: {}", actualTitle);

			Assert.assertEquals(
					actualTitle,
					expectedTitle,
					"Application title verification failed");

			logger.info("Application title verification passed");

		} catch (AssertionError e) {
			logger.error("Application title assertion failed", e);
			throw e;

		} catch (Exception e) {
			logger.error("Launch application test failed", e);
			throw e;

		} finally {
			logger.info("========== Launch Application Test Finished ==========");
		}
	}
}