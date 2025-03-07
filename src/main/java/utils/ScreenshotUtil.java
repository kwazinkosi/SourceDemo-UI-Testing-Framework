package utils;

import org.openqa.selenium.WebDriver;
import java.io.File;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScreenshotUtil {
    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            String directory = "test-output/screenshots/";
            new File(directory).mkdirs();
            
            String filePath = directory + testName + "_" + System.currentTimeMillis() + ".png";
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(filePath));
            return filePath;
        } catch (Exception e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
}