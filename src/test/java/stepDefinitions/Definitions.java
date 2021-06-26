package stepDefinitions;

import base.BaseClass;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utilities.AssertSteps;
import utilities.SetRequestSteps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Definitions extends BaseClass {

    RequestSpecification requestSpec = RestAssured.given();
    SetRequestSteps setRequestSteps = new SetRequestSteps();
    AssertSteps assertSteps = new AssertSteps();
    Response res;

    @Given("^I set the URI to base url$")
    public void setBaseURL() {
        String baseUri = props.getProperty("baseUrl");
        setRequestSteps.setBaseUri(requestSpec, baseUri);
        //To set the step in report
        test.info("I set the URI to base url: " + baseUri);
    }

    @When("^I set the path to \"(.*)\"$")
    public void setPath(String path) {
        resourcePath = path;
        test.info("I set the path to '" + path + "'");
    }

    @When("^I set the method to (get|GET|put|PUT|post|POST|delete|DELETE|PATCH|patch)$")
    public void setMethod(String method) {
        operation = method;
        test.info("I set the method to " + method);
    }

    @And("^I set the parameters with the below data$")
    public void setParams(DataTable dt) {
        List<Map<String, String>> listOfMaps = dt.asMaps(String.class, String.class);
        for (Map<String, String> listOfMap : listOfMaps) {
            setRequestSteps.setParameters(requestSpec, listOfMap.get("Param"), listOfMap.get("Value"));
        }
        test.info("I set the parameters with data: " + listOfMaps);
    }

    @And("^I execute the request$")
    public void executeRequest() {
        res = setRequestSteps.executeRequest(requestSpec, operation, resourcePath);
        test.info("I execute the request");
        System.out.println("Response body: " + res.getBody().asString());
    }

    @Then("^The status code is \"(.*)\"$")
    public void checkStatusCode(int statusCode) {
        boolean isStatusMatch = assertSteps.validateStatusCode(res, statusCode);
        if (isStatusMatch) {
            test.pass("The status code is " + res.getStatusCode());
        } else {
            test.fail("The status code is not " + res.getStatusCode());
            Assert.fail();
        }
    }

    @And("^The response time is less than \"(.*)\" ms$")
    public void checkResponseTime(long responseTime) {
        latency = res.getTime();
        System.out.println("Response time: " + latency);
        boolean isResponseTimeLess = assertSteps.validateResponsetime(res, responseTime);
        if (isResponseTimeLess) {
            test.pass("The response time is less than " + responseTime + " ms");
        } else {
            test.fail("The response time is greater than " + responseTime + " ms");
            Assert.fail();
        }
    }

    @And("^I retrieve all \"(.*)\" pets of category \"(.*)\"$")
    public void getPetsWithCategory(String status, String category) {
        //Print out all pets of a category
        JsonPath jsonPath = res.jsonPath();
        List<String> listOfCategories = jsonPath.get("category.name");
        HashMap<Number, String> petMap = new HashMap<>();
        String nameOfPet;
        Number id;

        // Checking if there are any pets matched with category. Then storing id and name of those pets in a hashmap
        for (int categoryNo = 0; categoryNo < listOfCategories.size(); categoryNo++) {
            if (listOfCategories.get(categoryNo).equals(category)) {
                id = jsonPath.get("id[" + categoryNo + "]");
                nameOfPet = jsonPath.get("name[" + categoryNo + "]");
                petMap.put(id, nameOfPet);
            }
        }

        // If found, pets are printed with id and name
        if (petMap.size() > 0) {
            System.out.println("Pets with category: '" + category + "'");
            for (Map.Entry<Number, String> pet : petMap.entrySet()) {
                System.out.println("Id: " + pet.getKey() + ", Name: " + pet.getValue());
            }
            test.info("I retrieve all " + status + " pets with category " + category + ": " + petMap.values());
        } else {
            test.info("There is no " + status + " pet with category '" + category + "'");
        }
    }

    @And("^I verify the \"(.*)\" is invalid")
    public void validateInvalidStatusError(String status) {
        JsonPath jsonPath = res.jsonPath();
        String actualErrorMsg = jsonPath.get("message");
        String expectedErrorMsg = "Input error: query parameter `status value `" + status + "` is not in the allowable values `[available, pending, sold]`";
        if (actualErrorMsg.equals(expectedErrorMsg)) {
            test.pass("I verify the error msg for invalid status is as expected");
        } else {
            test.fail("I validate the error msg for invalid status is not as expected");
            Assert.fail();
        }
    }
}