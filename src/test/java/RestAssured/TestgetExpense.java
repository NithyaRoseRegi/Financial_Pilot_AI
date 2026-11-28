package RestAssured;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestgetExpense {
   @Test
public void testGetExpensesForUser() {
    int userId = 14;

    given()
        .spec(ApiSpecifications.api())
        .pathParam("userId", userId)
    .when()
        .get("/expenses/user/{userId}")
    .then()
        .log().ifValidationFails()
        .statusCode(200)
        .body("message", equalTo("Expenses Retrieved Successfully"))
        .body("data", notNullValue())
        .log().all();
}
@Test
public void testGetExpenseById() {
    int expenseId = 11; // Use an ID that exists

    given()
        .spec(ApiSpecifications.api())
        .pathParam("expenseId", expenseId)
    .when()
        .get("/expenses/{expenseId}")
    .then()
        .log().ifValidationFails()
        .statusCode(200)
        .body("message", equalTo("Expense Retrieved Successfully"))
        .body("data.expenseId", equalTo(expenseId))
        .log().all();
}
}
