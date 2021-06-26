package managers;

import base.BaseClass;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyManager extends BaseClass {

    // Load Property file
    public void loadPropFile() {
        props = new Properties();
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
    public String getProperty(String key) {
        return props.getProperty(key);
    }
}
