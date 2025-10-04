package pages.base;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class BasePage {
    public static WebDriver driver;
    public WebDriverWait wait;
    private static System LogManager;
    protected static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    protected WebElement findElement(By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not found: {}", locator, e);
            throw e;
        }
    }

    protected List<WebElement> findElements(By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (Exception e) {
            logger.error("Elements not found: {}", locator, e);
            throw e;
        }
    }

    public void clickElement(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            logger.info("Clicked element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to click element: {}", locator, e);
            throw e;
        }
    }

    protected void sendKeys(By locator, String text) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.clear();
            element.sendKeys(text);
            logger.info("Entered text '{}' into element: {}", text, locator);
        } catch (Exception e) {
            logger.error("Failed to enter text into element: {}", locator, e);
            throw e;
        }
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected String getElementText(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.getText();
        } catch (Exception e) {
            logger.error("Failed to get text from element: {}", locator, e);
            throw e;
        }
    }

    protected void selectFromDropdown(By locator, String visibleText) {
        try {
            WebElement dropdown = findElement(locator);
            dropdown.click();

            // Wait for options to appear and select by visible text
            String optionXpath = String.format("//li[contains(@class, 'select2-results__option') and normalize-space(text())='%s']", visibleText);
            WebElement option = dropdown.findElement(By.xpath(optionXpath));
            option.click();
            logger.info("Selected '{}' from dropdown: {}", visibleText, locator);
        } catch (Exception e) {
            logger.error("Failed to select from dropdown: {}", locator, e);
            throw e;
        }
    }

    protected void waitForPageToLoad() {
        try {
            wait.until(webDriver ->
                    ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            logger.warn("Page did not load completely");
        }
    }

    protected void scrollToElement(By locator) {
        try {
            WebElement element = findElement(locator);
            scrollToElement(element);
            logger.info("Scrolled to element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to scroll to element: {}", locator, e);
            throw e;
        }
    }
    protected void scrollToElement(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'nearest'});",
                    element
            );
            logger.info("Scrolled to element smoothly");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        } catch (Exception e) {logger.error("Failed to scroll to element: {}", e.getMessage());
            throw e;
        }
    }

    protected void hoverOverElement(By locator) {
            WebElement element = findElement(locator);
            hoverOverElement(element);

    }

    protected void hoverOverElement(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
            logger.info("Hovered over element");

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        } catch (Exception e) {
            logger.error("Hover over element failed: {}", e.getMessage());
            throw e;
        }
    }
}