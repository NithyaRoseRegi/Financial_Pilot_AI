package RestAssured;

import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class TestputExpense {
 
    
    private record ExpenseRequest(
    int userId,
    int accountId,
    String title,
    String category,
    double amount,
    String date,
    String note
) {}

    @Test
    public void testPutExpense() {
        ExpenseRequest expense = new ExpenseRequest(
            11, 3, "Test Expense", "TestCategory", 95000.0, "2023-10-10", "Test Note"
        );

        given()
            .spec(ApiSpecifications.api())
            .contentType(ContentType.JSON)
            .pathParam("expenseId", TestPostExpense.ExpenseId)
            .body(expense)
        .when()
            .put("/expenses/{expenseId}")
        .then()
            .log().ifValidationFails()
            .statusCode(200)
            .body("success", equalTo(true))
            .body("message", equalTo("Expense Updated Successfully."))
            .body("data.userId", equalTo(Integer.valueOf(expense.userId())))
            .body("data.accountId", equalTo(expense.accountId()))
            .body("data.title", equalTo(expense.title()))
            .body("data.category", equalTo(expense.category()))
            .body("data.amount", equalTo((float) expense.amount()))
            .body("data.date", equalTo(expense.date()))
            .body("data.note", equalTo(expense.note()))
            .log().all();
    }
}