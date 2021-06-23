package stepDefinitions;

import base.BaseClass;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

public class Hooks extends BaseClass {

    @Before
    public void beforeScenario(Scenario sc) {
        //Initialize log and reports
        log = LogManager.getLogger(sc.getName());
        test = extent.createTest(sc.getName());
    }


    @After
    public void afterScenario(Scenario sc) throws IOException {
        //Take a screenshot if scenario fails and attach it to the report
        if (sc.isFailed()) {
            test.fail("Scenario '" + sc.getName() + "' failed!!");
        }
    }
}
