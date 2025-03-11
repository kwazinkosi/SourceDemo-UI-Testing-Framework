package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import utils.ConfigReader;

import java.text.SimpleDateFormat;
import java.util.Date;



public class ExtentManager {
	public static ExtentReports createInstance(String suiteName) {
		// Generate timestamp and report path with suite name
		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		String reportPath = "reports/sourcedemo_" + suiteName + "_Report_" + timestamp + ".html";

		ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
		reporter.config().setReportName("SauceDemo Test Automation Report - " + suiteName);
		reporter.config().setDocumentTitle("Test Results - " + suiteName);

		// Optional: Load styling from JSON/XML config
		// reporter.loadJSONConfig(new File("src/test/resources/extent-config.json"));

		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);

		// Add environment details
		extent.setSystemInfo("Author", "Kwazi Zwane");
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("Browser", ConfigReader.getProperty("browser_name")); // Assuming ConfigReader exists

		extent.setSystemInfo("Browser Mode", ConfigReader.getProperty("browser_mode"));
		extent.setSystemInfo("Environment", "QA");

		return extent;
	}
}