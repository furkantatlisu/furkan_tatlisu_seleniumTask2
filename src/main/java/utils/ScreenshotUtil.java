package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            // take screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            // create screenshot file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + timeStamp + ".png";

            // create screenshots folder
            File directory = new File("reports/screenshots");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Save the file
            String filePath = directory.getAbsolutePath() + File.separator + fileName;
            File destination = new File(filePath);
            FileUtils.copyFile(source, destination);

            return filePath;

        } catch (IOException e) {
            System.out.println("Screenshot alınırken hata oluştu: " + e.getMessage());
            return null;
        }
    }
    public static String takeScreenshot(WebDriver driver) {
        return takeScreenshot(driver, "screenshot");
    }

}