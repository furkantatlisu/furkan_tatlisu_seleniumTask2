package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
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
        try {
            File reportsDir = new File("reports");
            if (!reportsDir.exists()) {
                boolean created = reportsDir.mkdirs();
                logger.info("Reports directory created: {}", created);
            }

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = "reports/InsiderTestReport_" + timeStamp + ".html";

            logger.info("Creating report at: {}", new File(reportPath).getAbsolutePath());

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            sparkReporter.config().setDocumentTitle("Insider Careers Test Report");
            sparkReporter.config().setReportName("Automation Test Results");

            logger.info("Extent Reports configured successfully");

        } catch (Exception e) {
            logger.error("Failed to setup Extent Reports: {}", e.getMessage(), e);
        }
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
            System.out.println("Cookies declined!");

        } catch (Exception e) {
            System.out.println("Cookie banner not found");
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Failed: " + result.getThrowable());
            // logger.error("Test failed: " + result.getName(), result.getThrowable());
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