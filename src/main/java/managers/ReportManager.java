package managers;

import base.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ReportManager extends BaseClass {

    public void initializeReport(String reportPath) {
        //Initialize extent report
        htmlReporter = new ExtentHtmlReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    public void flush() {
        extent.flush();
    }
}
