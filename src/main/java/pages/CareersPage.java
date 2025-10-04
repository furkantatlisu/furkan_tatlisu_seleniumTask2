package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.base.BasePage;

public class CareersPage extends BasePage {

    // Optimized XPath and CSS Selectors
    private final By teamsBlock= By.cssSelector("section#career-find-our-calling");
    private final By locationsBlock = By.cssSelector("section#career-our-location");
    private final By lifeAtInsiderBlock = By.xpath("//div[@class='elementor-widget-wrap elementor-element-populated e-swiper-container']");

    // Constructor
    public CareersPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCareersPageOpened() {
        waitForPageToLoad();
        return driver.getCurrentUrl().contains("/careers");
    }

    public boolean areAllBlocksDisplayed() {
        boolean locationsVisible = isElementDisplayed(locationsBlock);
        boolean teamsVisible = isElementDisplayed(teamsBlock);
        boolean lifeAtInsiderVisible = isElementDisplayed(lifeAtInsiderBlock);

        logger.info("Locations block visible: {}", locationsVisible);
        logger.info("Teams block visible: {}", teamsVisible);
        // logger.info("Life at Insider block visible: " + lifeAtInsiderVisible);

        return locationsVisible && teamsVisible && lifeAtInsiderVisible;
    }

}
