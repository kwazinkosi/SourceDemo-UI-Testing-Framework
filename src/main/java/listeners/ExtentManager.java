package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;
    
    public static ExtentReports createInstance() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String reportPath = "reports/SauceDemo-Test-Report_" + timestamp + ".html";
            
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setReportName("SauceDemo Test Automation Report"); // Fixed name
            reporter.config().setDocumentTitle("Test Results");
            
            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Author", "Kwazi Zwane");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
        }
        return extent;
    }
}