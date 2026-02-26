package api.tests;

import api.config.BaseTest;
import api.models.UserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("Auth API")
class AuthApiTest extends BaseTest {

    @Test
    @DisplayName("Регистрация — 201, возвращает токен")
    void register() {
        given()
                .body(randomUser())
        .when()
                .post("/auth/register")
        .then()
                .statusCode(201)
                .body("access_token", notNullValue())
                .body("token_type", equalTo("bearer"));
    }

    @Test
    @DisplayName("Логин — 200, возвращает токен")
    void login() {
        UserRequest user = randomUser();
        given().body(user).when().post("/auth/register").then().statusCode(201);

        given()
                .body(user)
        .when()
                .post("/auth/login")
        .then()
                .statusCode(200)
                .body("access_token", notNullValue())
                .body("token_type", equalTo("bearer"));
    }
}
