package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.ScreenshotUtil;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;



public class BaseTest {
    public WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;

    protected static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(BaseTest.class);

    @BeforeSuite
    public void setUpSuite() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File screenshotsDir = new File("reports/screenshots");
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/InsiderTestReport_" + timeStamp + ".html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        sparkReporter.config().setDocumentTitle("Insider Careers Test Report");
        sparkReporter.config().setReportName("Automation Test Results");

        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
    }

    @BeforeMethod
    public void setUp(Method method) {
        test = extent.createTest(method.getName());
        logger.info("Starting test: {}", method.getName());

        String browser = System.getProperty("browser", ConfigReader.getProperty("browser"));
        driver = DriverFactory.getDriver(browser);
        driver.manage().window().maximize();
        driver.get(ConfigReader.getProperty("base.url"));

        try {
            WebElement acceptAllBtn = driver.findElement(By.xpath("//a[contains(text(),'Decline All')]"));
            acceptAllBtn.click();

        } catch (Exception e) {
            System.out.println("Cookie banner not found");
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = ScreenshotUtil.takeScreenshot(driver, result.getName());
            logger.error("Test failed: {}", result.getName(), result.getThrowable());
            if (screenshotPath != null) {
                try {
                    test.fail("Test Failed: " + result.getThrowable(),
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                    test.fail("Stack Trace: " + result.getThrowable());
                } catch (Exception e) {
                    test.fail("Test Failed: " + result.getThrowable());
                    test.fail("Screenshot could not be added: " + e.getMessage());
                }
            } else {
                test.fail("Test Failed: " + result.getThrowable());
            }

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Passed");
            logger.info("Test passed: {}", result.getName());
        } else {
            test.log(Status.SKIP, "Test Skipped");
            logger.warn("Test skipped: {}", result.getName());
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void tearDownSuite() {
        if (extent != null) {
            extent.flush();
        }
    }
}