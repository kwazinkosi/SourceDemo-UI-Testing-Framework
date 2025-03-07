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
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        
        WebDriver driver = DriverFactory.getDriver();
        
        // Capture screenshot
        String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
        
        // Attach to report
        if (screenshotPath != null) {
            test.get().fail("Screenshot: " + 
                test.get().addScreenCaptureFromPath(screenshotPath));
        }
        test.get().fail(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}