package testRunners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import managers.PropertyManager;
import managers.ReportManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        plugin = {"pretty", "html:target/cucumber-reports", "json:target/cucumber.json"},
        glue = {"stepDefinitions"}
)


public class RunCucumberTest {

    static PropertyManager propertyManager = new PropertyManager();
    static ReportManager reportManager = new ReportManager();

    @BeforeClass
    public static void before_class() {
        //Get report path and Initialize report
        propertyManager.loadPropFile();
        String reportPath = propertyManager.getProperty("reportConfigPath");
        reportManager.initializeReport(reportPath);
    }

    @AfterClass
    public static void after_class() {
        //Finalize report
        reportManager.flush();
    }
}


