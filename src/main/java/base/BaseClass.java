package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class BaseClass {

    public static Logger log;
    public static FileInputStream fis;
    public static String propertiesFilePath;
    public static Properties props = new Properties();
    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    // Load Property file
    public static void loadPropFile() {
        propertiesFilePath = "src/main/resources/Config.properties";
        try {
            fis = new FileInputStream(propertiesFilePath);
            props.load(fis);
        } catch (Exception e) {
            System.out.println("Problem in loading file page");
            log.debug("Problem in loading file page");
            e.printStackTrace();
        }
    }

    // Read properties
    public static String getProperty(String key) {
        return props.getProperty(key);
    }

}
