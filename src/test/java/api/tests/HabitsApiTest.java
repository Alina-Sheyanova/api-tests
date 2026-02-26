package api.tests;

import api.config.BaseTest;
import api.models.HabitRequest;
import api.models.ToggleRequest;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("Habits API")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HabitsApiTest extends BaseTest {

    private static String token;
    private static String habitId;
    private static final String TODAY = LocalDate.now().toString();

    @BeforeAll
    static void createUser() {
        token = registerAndGetToken();
    }

    @Test
    @Order(1)
    @DisplayName("Создать привычку — 201")
    void createHabit() {
        habitId = given()
                .header("Authorization", "Bearer " + token)
                .body(new HabitRequest("Зарядка", "15 мин", 30))
        .when()
                .post("/habits")
        .then()
                .statusCode(201)
                .body("name", equalTo("Зарядка"))
                .body("goalDays", equalTo(30))
                .extract().jsonPath().getString("id");
    }

    @Test
    @Order(2)
    @DisplayName("Список привычек — 200")
    void getHabits() {
        given()
                .header("Authorization", "Bearer " + token)
        .when()
                .get("/habits")
        .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    @Order(3)
    @DisplayName("Toggle — отметить выполненной")
    void toggleHabit() {
        given()
                .header("Authorization", "Bearer " + token)
                .body(new ToggleRequest(TODAY))
        .when()
                .post("/habits/" + habitId + "/toggle")
        .then()
                .statusCode(200)
                .body("completedDates", hasItem(TODAY));
    }

    @Test
    @Order(4)
    @DisplayName("Удалить привычку — 204")
    void deleteHabit() {
        given()
                .header("Authorization", "Bearer " + token)
        .when()
                .delete("/habits/" + habitId)
        .then()
                .statusCode(204);
    }
}
