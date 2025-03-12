package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import utils.DriverFactory;
import utils.ScreenshotUtil;

public class ReportListener implements ITestListener {
	
    private ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private long startTime;

    @Override
    public void onStart(ITestContext context) {
        // Capture suite name and initialize ExtentReports
        String suiteName = context.getSuite().getName();
        extent = ExtentManager.createInstance(suiteName);
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Create test with method name and assign suite category
        String testDescription = result.getMethod().getDescription();
        String browser = result.getTestContext().getCurrentXmlTest().getParameter("browser");
        String suiteName = result.getTestContext().getSuite().getName();
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(), testDescription)
            .assignCategory(suiteName).assignCategory(browser);

        // Log parameters if present
        Object[] parameters = result.getParameters();
        if (parameters != null && parameters.length > 0) {
            extentTest.info("Test Parameters: " + Arrays.toString(parameters));
        }

        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Capture screenshot for passed tests (optional)
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
            if (screenshotPath != null) {
                try {
                    test.get().pass("Screenshot: " + 
                        test.get().addScreenCaptureFromPath(screenshotPath));
                } catch (Exception e) {
                    test.get().pass("Failed to attach screenshot: " + e.getMessage());
                }
            }
        }
        test.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
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
        test.get().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test skipped: " + result.getThrowable().getMessage());
    }

    @Override
    public void onFinish(ITestContext context) {
        // Calculate execution time
        long totalTime = System.currentTimeMillis() - startTime;
        String formattedTime = formatDuration(totalTime);

        // Add execution metrics
        extent.setSystemInfo("Total Execution Time", formattedTime);
        extent.setSystemInfo("Passed Tests", String.valueOf(context.getPassedTests().size()));
        extent.setSystemInfo("Failed Tests", String.valueOf(context.getFailedTests().size()));
        extent.setSystemInfo("Skipped Tests", String.valueOf(context.getSkippedTests().size()));

        // Add summary table
        String[][] summary = {
            {"Total Tests", String.valueOf(context.getAllTestMethods().length)},
            {"Passed", String.valueOf(context.getPassedTests().size())},
            {"Failed", String.valueOf(context.getFailedTests().size())},
            {"Skipped", String.valueOf(context.getSkippedTests().size())},
            {"Execution Time", formattedTime}
        };

        extent.createTest("Execution Summary")
              .info(MarkupHelper.createTable(summary));

        // Flush the report
        extent.flush();
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static ExtentTest createStep(String stepName) {
        return getTest().createNode(stepName);
    }
    
    private String formatDuration(long millis) {
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}