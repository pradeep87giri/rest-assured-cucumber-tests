package testRunners;

import base.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        plugin = {"pretty", "html:target/cucumber-reports", "json:target/cucumber.json"},
        glue = {"stepDefinitions"}
)


public class TestRunner extends BaseClass {

    @BeforeClass
    public static void before_class() {
        //Load property file
        loadPropFile();

        //Initialize extent report
        htmlReporter = new ExtentHtmlReporter(getProperty("reportConfigPath"));
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @AfterClass
    public static void after_class() {
        extent.flush();
    }
}


