package RestAssured;

import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TestPostExpense {

    static String ExpenseId;
    
    private record ExpenseRequest(
    int userId,
    int accountId,
    String title,
    String category,
    double amount,
    String date,
    String note
) {}

@Test(priority = -1)
public void testPostExpense() {
    ExpenseRequest expense = new ExpenseRequest(
        11, 3, "Groceries", "Food", 42.50, "2026-11-27", "Weekly shopping"
    );

    Response response = given()
        .spec(ApiSpecifications.api())
        .contentType(ContentType.JSON)
        .body(expense)
    .when()
        .post("/expenses");

    response.then()
        .log().ifValidationFails()
        .statusCode(201)
        .body("success", equalTo(true))
        .body("message", equalTo("Expense Added Successfully"))
        .body("data.userId", equalTo(Integer.valueOf(expense.userId())))
        .body("data.accountId", equalTo(expense.accountId()))
        .body("data.title", equalTo(expense.title()))
        .body("data.category", equalTo(expense.category()))
        .body("data.amount", equalTo((float) expense.amount()))
        .body("data.date", equalTo(expense.date()))
        .body("data.note", equalTo(expense.note()))
        .log().all();
        
        
        ExpenseId = response.body().jsonPath().getString("data.expenseId");
}
}
