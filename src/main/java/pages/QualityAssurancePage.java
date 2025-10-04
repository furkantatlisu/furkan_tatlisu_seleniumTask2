package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.base.BasePage;
import utils.ConfigReader;

public class QualityAssurancePage extends BasePage {

    // Optimized XPath and CSS Selectors
    private final By seeAllQAJobsButton = By.xpath("//*[text()='See all QA jobs']");

    // Constructor
    public QualityAssurancePage(WebDriver driver) {
        super(driver);
    }

    public void goToQAPageUrl() {
        driver.get(ConfigReader.getProperty("qualityAssurance.url"));
    }

    // Methods
    public boolean isQualityAssurancePageOpened() {
        waitForPageToLoad();
        return driver.getCurrentUrl().contains("/careers/quality-assurance");
    }

    public void clickSeeAllQAJobs() {
        scrollToElement(seeAllQAJobsButton);
        clickElement(seeAllQAJobsButton);
        logger.info("Clicked 'See all QA jobs' button");
    }


}