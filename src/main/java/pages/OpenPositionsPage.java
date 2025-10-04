package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.base.BasePage;

import java.util.List;

public class OpenPositionsPage extends BasePage {

    // Optimized XPath and CSS Selectors
    private final By locationFilter = By.xpath("//span[@id='select2-filter-by-location-container']");
    private final By departmentFilter = By.cssSelector("#select2-filter-by-department-container");
    private final By qaDepartmentFilter = By.xpath("//span[@title='Quality Assurance']");
    private final By jobsList = By.cssSelector("div#jobs-list");
    private final By jobItems = By.cssSelector("div.position-list-item");
    private final By positionTitle = By.cssSelector("p.position-title");
    private final By positiondepartment = By.cssSelector("span.position-department");
    private final By positionLocation = By.cssSelector("div.position-location");
    private final By viewRoleButton = By.cssSelector("a[href*='jobs']");

    // Constructor
    public OpenPositionsPage(WebDriver driver) {
        super(driver);
    }

    // Methods
    public boolean isOpenPositionsPageOpened() {
        try {
            waitForPageToLoad();
            return driver.getCurrentUrl().contains("/careers/open-positions") &&
                    isElementDisplayed(qaDepartmentFilter);
        } catch (Exception e) {
            logger.error("Open Positions page check failed: {}", e.getMessage());
            return false;
        }
    }

    public void filterJobsByLocationAndDepartment(String location, String department) throws InterruptedException {
        // Filter by location
        Thread.sleep(1000);
        selectFromDropdown(locationFilter, location);
        logger.info("Selected location: {}", location);

        // Filter by department
        selectFromDropdown(departmentFilter, department);
        logger.info("Selected department: {}", department);

        // Wait for results to load
        waitForPageToLoad();
    }

    public boolean isJobsListPresent() {
        return isElementDisplayed(jobsList) &&
                !findElements(jobItems).isEmpty();
    }

    public List<WebElement> getJobItems() {
        return findElements(jobItems);
    }

    public boolean verifyJobDetails(String expectedDepartment, String expectedLocation) throws InterruptedException {
        Thread.sleep(3000);
        List<WebElement> jobs = getJobItems();
        if (jobs.isEmpty()) {
            logger.warn("No jobs found in the list");
            return false;
        }

        for (WebElement job : jobs) {
            WebElement departmentElement = job.findElement(positiondepartment);
            WebElement locationElement = job.findElement(positionLocation);
            WebElement titleElement = job.findElement(positionTitle);
            scrollToElement(titleElement);
            Thread.sleep(2000);
            String dept = departmentElement.getText();
            String location = locationElement.getText();
            logger.info("Verifying job - Position: {}, Department: {}, Location: {}", positionTitle, positiondepartment, positionLocation);

            if (!dept.contains(expectedDepartment) ||
                    !location.contains(expectedLocation)) {
                logger.error("Job verification failed for: {}", positionTitle);
                return false;
            }
        }
        return true;
    }

    public void clickViewRoleForFirstJob() {
        List<WebElement> jobs = getJobItems();

        if (!jobs.isEmpty()) {
            try {
                WebElement firstJob = jobs.get(0);
                logger.info("Found {} jobs. Attempting to click View Role for first job.", jobs.size());

                // Hover over the job element
                logger.debug("Hovering over the first job element...");
                hoverOverElement(firstJob);

                try {
                    Thread.sleep(1000);
                    logger.debug("Wait completed after hover");
                } catch (InterruptedException e) {
                    logger.warn("Thread sleep interrupted after hover", e);
                    Thread.currentThread().interrupt();
                }

                // Find and click View Role button
                logger.debug("Locating View Role button...");
                WebElement viewRoleBtn = firstJob.findElement(viewRoleButton);

                logger.debug("Scrolling to View Role button...");
                scrollToElement(viewRoleBtn); // Düzeltme: viewRoleButton yerine viewRoleBtn

                logger.info("Clicking View Role button for position: {}",
                        firstJob.findElement(positionTitle).getText());
                viewRoleBtn.click();
                logger.info("Successfully clicked 'View Role' button");

                // Wait for new tab to open
                logger.debug("Waiting for new tab to open...");
                try {
                    Thread.sleep(2000);
                    logger.debug("Wait completed for new tab");
                } catch (InterruptedException e) {
                    logger.warn("Thread sleep interrupted while waiting for new tab", e);
                    Thread.currentThread().interrupt();
                }

                // Check if new tab opened
                int tabCount = driver.getWindowHandles().size();
                logger.info("Current number of open tabs: {}", tabCount);

            } catch (NoSuchElementException e) {
                logger.error("View Role button not found in the first job: {}", e.getMessage());
                throw e;
            } catch (Exception e) {
                logger.error("Unexpected error while clicking View Role button: {}", e.getMessage(), e);
                throw e;
            }
        } else {
            logger.warn("No jobs available to click View Role. Jobs list is empty.");
        }
    }
}