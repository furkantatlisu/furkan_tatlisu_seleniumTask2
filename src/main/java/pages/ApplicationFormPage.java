package pages;

import org.openqa.selenium.WebDriver;
import pages.base.BasePage;

import java.util.ArrayList;

public class ApplicationFormPage extends BasePage {

    public ApplicationFormPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLeverApplicationPageOpened() {
        // Switch to new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.size() > 1) {
            driver.switchTo().window(tabs.get(1));
            boolean isLeverPage = driver.getCurrentUrl().contains("lever.co");
            logger.info("Lever application page opened: {}", isLeverPage);

            // Close tab and switch back
            driver.close();
            driver.switchTo().window(tabs.get(0));
            return isLeverPage;
        }
        return false;
    }

}
