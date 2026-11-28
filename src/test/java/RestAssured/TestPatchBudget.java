package RestAssured;

import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestPatchBudget {
    private record BudgetPatchRequest(double amount) {}

    @Test
    public void patchBudget() {
        given()
            .spec(ApiSpecifications.api())
            .contentType("application/merge-patch+json")
            .pathParam("userId", 11)
            .pathParam("month", 7)
            .pathParam("year", 2026)
            .body(new BudgetPatchRequest(90000))
        .when()
            .patch("/budget/{userId}/{month}/{year}")
        .then()
            .statusCode(200)
            .body("success", equalTo(true));
    }
}