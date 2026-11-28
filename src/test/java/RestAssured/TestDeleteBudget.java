package RestAssured;

import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestDeleteBudget {
    @Test()
    public void testDeleteBudget() {
        given()
            .spec(ApiSpecifications.api())
            .pathParam("userId", 11)
            .pathParam("month", 6)
            .pathParam("year", 2026)
        .when()
            .delete("/budget/{userId}/{month}/{year}")
        .then()
            .log().ifValidationFails()
            .statusCode(200)
            .body("message", equalTo("Budget Deleted Successfully."))
            .log().all();    }
}