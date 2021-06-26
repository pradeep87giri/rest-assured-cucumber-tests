package utilities;

import io.restassured.response.Response;
import org.junit.Assert;

public class AssertSteps {

    public boolean validateStatusCode(Response response, int expectedStatusCode) {
        if (response.getStatusCode() == expectedStatusCode) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateResponsetime(Response response, long expectedresponseTime) {
        if (response.getTime() < expectedresponseTime) {
            return true;
        } else {
            return false;
        }
    }


}
