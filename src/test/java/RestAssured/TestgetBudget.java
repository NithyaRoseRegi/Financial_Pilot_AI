package RestAssured;

import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestgetBudget {
    @Test
    public void testGetBudget() {
        given()
            .spec(ApiSpecifications.api())
            .pathParam("userId", 11)
        .when()
            .get("/budget/user/{userId}")
        .then()
            .log().ifValidationFails()
            .statusCode(200)
            .body("message", equalTo("Budgets Retrieved Successfully."))
            .log().all();    }
@Test
public void testGetBudgetByMonth() {
    given()
        .spec(ApiSpecifications.api())
        .pathParam("userId", 11)
        .pathParam("month", 7)
        .pathParam("year", 2026)
    .when()
        .get("/budget/{userId}/{month}/{year}")
    .then()
        .log().ifValidationFails()
        .statusCode(200)
        .body("message", equalTo("Budget Retrieved Successfully."))
        .log().all();
}

@Test
public void testGetRemainingBudget() {
    given()
        .spec(ApiSpecifications.api())
        .pathParam("userId", 11)
        .pathParam("month", 7)
        .pathParam("year", 2026)
    .when()
        .get("/budget/remaining/{userId}/{month}/{year}")
    .then()
        .log().ifValidationFails()
        .statusCode(200)
        .body("message", equalTo("Remaining Budget Retrieved Successfully."))
        .log().all();
}
}

