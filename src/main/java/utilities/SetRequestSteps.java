package utilities;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

public class SetRequestSteps {
    Response response;


    public void setBaseUri(RequestSpecification request, String baseUri) {
        request.baseUri(baseUri);
    }

    public Response executeRequest(RequestSpecification request, String method, String path) {
        switch (method.toLowerCase()) {
            case "get":
                response = request.get(path);
                break;
            case "post":
                response = request.post(path);
                break;
            case "put":
                response = request.put(path);
                break;
            case "patch":
                response = request.patch(path);
                break;
            case "delete":
                response = request.delete(path);
                break;
            default:
                System.out.println("Invalid HTTP Method!!");
        }
        return response;
    }

    public void setParameters(RequestSpecification request, String param, String value) {
        request.queryParam(param, value);
    }

}
