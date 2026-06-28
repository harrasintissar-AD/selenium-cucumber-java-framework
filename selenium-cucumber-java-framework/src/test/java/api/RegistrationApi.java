package api;

import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ConfigReader;
import static org.testng.Assert.assertTrue;

public class RegistrationApi {

    public static Map<String, String> registerUser() {

        String username = "user" + System.currentTimeMillis();
        String password = "Pass123!";
        String baseUrl = ConfigReader.getProperty("regi.url");

        Response response = given()
                .contentType(ContentType.URLENC)
                .formParam("customer.firstName", "Test")
                .formParam("customer.lastName", "User")
                .formParam("customer.address.street", "Street 1")
                .formParam("customer.address.city", "Ottawa")
                .formParam("customer.address.state", "ON")
                .formParam("customer.address.zipCode", "K1A0B1")
                .formParam("customer.phoneNumber", "")
                .formParam("customer.ssn", "1234")
                .formParam("customer.username", username)
                .formParam("customer.password", password)
                .formParam("repeatedPassword", password)
                .when()
                .post(baseUrl + "/register.htm");
               // .then()
               // .statusCode(200)
               // .extract().response();

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: ");
        System.out.println(response.getBody().asString());

        Map<String, String> credentials = new HashMap<>();

        credentials.put("username", username);
        credentials.put("password", password);

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("Customer Created"));

        return credentials;
    }
}