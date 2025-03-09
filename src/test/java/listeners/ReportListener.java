package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import utils.DriverFactory;
import utils.ScreenshotUtil;

public class ReportListener implements ITestListener {
    
	private static final ExtentReports extent = ExtentManager.createInstance();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static long startTime;
    
    @Override
    public void onStart(ITestContext context) {
        startTime = System.currentTimeMillis();
    }
    
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
    	long totalTime = System.currentTimeMillis() - startTime;
        String formattedTime = formatDuration(totalTime);

        // Add system info
        extent.setSystemInfo("Total Execution Time", formattedTime);
        extent.setSystemInfo("Passed Tests", String.valueOf(context.getPassedTests().size()));
        extent.setSystemInfo("Failed Tests", String.valueOf(context.getFailedTests().size()));
        extent.setSystemInfo("Skipped Tests", String.valueOf(context.getSkippedTests().size()));

        // Custom summary table
        String[][] summary = {
            {"Total Tests", String.valueOf(context.getAllTestMethods().length)},
            {"Passed", String.valueOf(context.getPassedTests().size())},
            {"Failed", String.valueOf(context.getFailedTests().size())},
            {"Skipped", String.valueOf(context.getSkippedTests().size())},
            {"Execution Time", formattedTime}
        };

        extent.createTest("Execution Summary")
              .info(MarkupHelper.createTable(summary));

        extent.flush();
    }

    private String formatDuration(long millis) {
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}