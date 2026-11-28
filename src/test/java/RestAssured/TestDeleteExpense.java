package RestAssured;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import java.util.Map;

public class TestDeleteExpense {
    @Test(priority = 1)
    public void testDeleteExpense() {
        String expenseId = given()
            .spec(ApiSpecifications.api())
            .contentType(ContentType.JSON)
            .body(Map.of(
                "userId", 11,
                "accountId", 3,
                "title", "Expense for delete test",
                "category", "Test",
                "amount", 10.0,
                "date", "2026-11-27",
                "note", "Delete test"))
        .when()
            .post("/expenses")
        .then()
            .statusCode(201)
            .body("data.expenseId", notNullValue())
            .extract()
            .jsonPath()
            .getString("data.expenseId");

        given()
            .spec(ApiSpecifications.api())
            .pathParam("expenseId", expenseId)
            .queryParam("userId", 11)
        .when()
            .delete("/expenses/{expenseId}")
        .then()
            .log().ifValidationFails()
            .statusCode(200)
            .body("message", equalTo("Expense Deleted Successfully"))
            .log().all();    }
}