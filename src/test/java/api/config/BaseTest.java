package api.config;

import api.models.UserRequest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class BaseTest {

    @BeforeAll
    protected static void setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(System.getProperty("base.url", "http://localhost:8000"))
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    protected static UserRequest randomUser() {
        return new UserRequest("user_" + UUID.randomUUID() + "@test.com", "Pass123!");
    }

    protected static String registerAndGetToken() {
        return given()
                .body(randomUser())
                .post("/auth/register")
                .then().statusCode(201)
                .extract().jsonPath().getString("access_token");
    }
}
