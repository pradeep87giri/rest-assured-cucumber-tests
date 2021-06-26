package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

public class BaseClass {

    //Log, Config and Report references
    public static Logger log;
    public static FileInputStream fis;
    public static String propertiesFilePath;
    public static Properties props;
    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    //Information related to request
    public static String resourcePath;
    public static String operation;
    public static long latency;
}
