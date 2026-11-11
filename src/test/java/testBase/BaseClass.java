package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

	protected final Logger logger = LogManager.getLogger(getClass());

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public Properties p;

	public static WebDriver getDriver() {
		return driver.get();
	}

	@BeforeClass(groups = {"sanity", "regression", "datadriven"})
	@Parameters({"os", "browser"})
	public void openApp(String os, String br) throws IOException {

		logger.info("========== Browser Setup Started ==========");
		logger.info("Operating System: {} | Browser: {}", os, br);

		try {
			p = new Properties();

			try (FileReader file = new FileReader(
					"src/test/resources/config.properties")) {
				p.load(file);
			}

			logger.debug("Configuration file loaded");

			WebDriver localDriver;

			String executionEnv = p.getProperty("execution_env", "local");

			logger.info("Execution environment: {}", executionEnv);

			if (executionEnv.equalsIgnoreCase("remote")) {

				String gridURL = "http://localhost:4444";

				switch (br.toLowerCase()) {
				case "chrome":
					ChromeOptions chromeOptions = new ChromeOptions();
					chromeOptions.setPlatformName(os.toLowerCase());

					localDriver = new RemoteWebDriver(
							URI.create(gridURL).toURL(), chromeOptions);
					break;

				case "edge":
					EdgeOptions edgeOptions = new EdgeOptions();
					edgeOptions.setPlatformName(os.toLowerCase());

					localDriver = new RemoteWebDriver(
							URI.create(gridURL).toURL(), edgeOptions);
					break;

				case "firefox":
					FirefoxOptions firefoxOptions = new FirefoxOptions();
					firefoxOptions.setPlatformName(os.toLowerCase());

					localDriver = new RemoteWebDriver(
							URI.create(gridURL).toURL(), firefoxOptions);
					break;

				default:
					throw new IllegalArgumentException("Invalid Browser: " + br);
				}

			} else if (executionEnv.equalsIgnoreCase("local")) {

				switch (br.toLowerCase()) {
				case "chrome":
					localDriver = new ChromeDriver();
					break;

				case "edge":
					localDriver = new EdgeDriver();
					break;

				case "firefox":
					localDriver = new FirefoxDriver();
					break;

				default:
					throw new IllegalArgumentException("Invalid Browser: " + br);
				}

			} else {
				throw new IllegalArgumentException(
						"Invalid execution_env: " + executionEnv);
			}

			driver.set(localDriver);
			logger.debug("WebDriver assigned to the current thread");

			getDriver().manage().timeouts()
					.implicitlyWait(Duration.ofSeconds(30));

			getDriver().manage().window()
					.setSize(new Dimension(1920, 1080));

			String appURL = p.getProperty("appURL");

			if (appURL == null || appURL.trim().isEmpty()) {
				throw new IllegalStateException(
						"appURL is missing in config.properties");
			}

			getDriver().get(appURL);

			logger.info("Application opened: {}", appURL);
			logger.info("========== Browser Setup Completed ==========");

		} catch (IOException e) {
			logger.error("Failed to load configuration file", e);
			cleanupDriver();
			throw e;

		} catch (RuntimeException e) {
			logger.error("Browser setup failed", e);
			cleanupDriver();
			throw e;
		}
	}

	@AfterClass(
			groups = {"sanity", "regression", "datadriven"},
			alwaysRun = true)
	public void closeApp() {

		logger.info("========== Browser Teardown Started ==========");

		try {
			if (getDriver() != null) {
				getDriver().quit();
				logger.info("Browser closed successfully");
			} else {
				logger.warn("No browser session to close");
			}

		} catch (RuntimeException e) {
			logger.error("Failed to close browser", e);
			throw e;

		} finally {
			driver.remove();
			logger.debug("ThreadLocal WebDriver reference removed");
			logger.info("========== Browser Teardown Completed ==========");
		}
	}

	public String captureScreen(String tname) throws IOException {

		try {
			if (getDriver() == null) {
				throw new IllegalStateException(
						"WebDriver is not initialized");
			}

			String timeStamp =
					new SimpleDateFormat("yyyyMMddHHmmssSSS")
							.format(new Date());

			TakesScreenshot takesScreenshot =
					(TakesScreenshot) getDriver();

			File sourceFile =
					takesScreenshot.getScreenshotAs(OutputType.FILE);

			File directory = new File(
					System.getProperty("user.dir"), "screenshots");

			Files.createDirectories(directory.toPath());

			File targetFile = new File(
					directory, tname + "_" + timeStamp + ".png");

			Files.copy(
					sourceFile.toPath(),
					targetFile.toPath(),
					StandardCopyOption.REPLACE_EXISTING);

			logger.info("Screenshot saved: {}", targetFile.getAbsolutePath());

			return targetFile.getAbsolutePath();

		} catch (IOException e) {
			logger.error("Failed to save screenshot for: {}", tname, e);
			throw e;

		} catch (RuntimeException e) {
			logger.error("Failed to capture screenshot for: {}", tname, e);
			throw e;
		}
	}

	private void cleanupDriver() {

		try {
			if (getDriver() != null) {
				getDriver().quit();
			}

		} catch (RuntimeException e) {
			logger.error("Failed to clean up browser after setup failure", e);

		} finally {
			driver.remove();
		}
	}
}