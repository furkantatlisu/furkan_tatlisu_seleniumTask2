package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;


public class DriverFactory {

    private static System LogManager;
    protected static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(DriverFactory.class);
    private static final List<String> COMMON_ARGUMENTS = Arrays.asList(
            "--ignore-certificate-errors",
            "--disable-popup-blocking",
            "--start-maximized",
            "--disable-notifications",
            "--disable-gpu",
            "--no-sandbox",
            "--disable-dev-shm-usage",
            "--disable-blink-features=AutomationControlled",
            "--disable-extensions",
            "--disable-plugins",
            "--dns-prefetch-disable",
            "--incognito"  // Chrome, Edge ve Firefox'ta çalışır
    );
    public static WebDriver getDriver(String browser) {
        WebDriver driver;
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL"); // exp: http://selenium-chrome:4444/wd/hub


        try {
            String b = browser == null ? System.getProperty("browser", "chrome") : browser;
            switch (b.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    COMMON_ARGUMENTS.forEach(chromeOptions::addArguments);
                    chromeOptions.setAcceptInsecureCerts(true);
                    if (remoteUrl != null && !remoteUrl.isEmpty()) {
                        driver = new RemoteWebDriver(new URL(remoteUrl), chromeOptions);
                    } else {
                        driver = new ChromeDriver(chromeOptions);
                    }
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    COMMON_ARGUMENTS.forEach(edgeOptions::addArguments);
                    edgeOptions.setAcceptInsecureCerts(true);
                    if (remoteUrl != null && !remoteUrl.isEmpty()) {
                        driver = new RemoteWebDriver(new URL(remoteUrl), edgeOptions);
                    } else {
                        driver = new EdgeDriver(edgeOptions);
                    }
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    COMMON_ARGUMENTS.forEach(firefoxOptions::addArguments);
                    firefoxOptions.setAcceptInsecureCerts(true);
                    if (remoteUrl != null && !remoteUrl.isEmpty()) {
                        driver = new RemoteWebDriver(new URL(remoteUrl), firefoxOptions);
                    } else {
                        driver = new FirefoxDriver(firefoxOptions);
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Browser not supported: " + b);
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            logger.info(b + " driver initialized successfully (remote=" + (remoteUrl != null) + ")");
        } catch (Exception e) {
            logger.error("Failed to initialize driver", e);
            throw new RuntimeException("Driver initialization failed", e);
        }

        return driver;
    }
}
