package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.base.BasePage;

public class HomePage extends BasePage {

    // Optimized CSS Selectors
    private final By homePageLogo = By.cssSelector("a[href='https://useinsider.com/']");
    private final By companyMenu = By.xpath("//a[contains(@class,'dropdown-toggle') and normalize-space(text())='Company']");
    private final By careersOption = By.xpath("//a[contains(text(),'Careers') and contains(@class,'dropdown-sub')]");

    // Constructor
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Methods
    public boolean isHomePageOpened() {
        waitForPageToLoad();
        boolean isUrlCorrect = driver.getCurrentUrl().contains("useinsider.com");
        boolean isLogoDisplayed = isElementDisplayed(homePageLogo);

        logger.info("Home Page - URL correct: {}, Logo displayed: {}", isUrlCorrect, isLogoDisplayed);
        return isUrlCorrect && isLogoDisplayed;
    }

    public void navigateToCareersPage() {
        clickElement(companyMenu);
        clickElement(careersOption);
        logger.info("Navigated to Careers page");
    }
}