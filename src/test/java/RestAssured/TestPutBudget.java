package RestAssured;

import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class TestPutBudget {
    private record BudgetRequest(int userId, int month, int year, double amount) {}

    @Test
    public void testPutBudget() {
        BudgetRequest budget = new BudgetRequest(
            11, 6, 2026, 85000.00
        );

        given()
            .spec(ApiSpecifications.api())
            .contentType(ContentType.JSON)
            .body(budget)
        .when()
            .put("/budget")
        .then()
            .log().ifValidationFails()
            .statusCode(200)
            .body("success", equalTo(true))
            .body("message", equalTo("Budget Updated Successfully."))
            .body("data.userId", equalTo(budget.userId()))
            .body("data.month", equalTo(budget.month()))
            .body("data.year", equalTo(budget.year()))
            .log().all();
    }
}