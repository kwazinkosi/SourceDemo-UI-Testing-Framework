package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import utils.DriverFactory;
import utils.ScreenshotUtil;

public class ReportListener implements ITestListener {
    private static final ExtentReports extent = ExtentManager.createInstance();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        // Create a new ExtentTest instance and set it in ThreadLocal
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Log test success
        test.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Log test failure
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            // Capture screenshot
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
            
            // Attach screenshot to report if it exists
            if (screenshotPath != null) {
                try {
                    test.get().fail("Screenshot: " + 
                        test.get().addScreenCaptureFromPath(screenshotPath));
                } catch (Exception e) {
                    test.get().fail("Failed to attach screenshot: " + e.getMessage());
                }
            } else {
                test.get().fail("Screenshot capture failed.");
            }
        } else {
            test.get().fail("WebDriver instance is null.");
        }
        
        // Log the exception
        test.get().fail(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush the ExtentReports instance
        extent.flush();
    }
}