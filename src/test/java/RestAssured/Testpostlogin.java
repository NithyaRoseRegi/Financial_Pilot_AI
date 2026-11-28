package RestAssured;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;


public class Testpostlogin {
    @Test(priority = -2)
    public void testPostLogin() {

        String email = System.getenv("API_TEST_EMAIL");
        String password = System.getenv("API_TEST_PASSWORD");

        if (email == null || email.isBlank()
                || password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "Set API_TEST_EMAIL and API_TEST_PASSWORD to run this test");
        }

        given()
            .spec(ApiSpecifications.publicApi())
            .contentType(ContentType.JSON)
            .body(java.util.Map.of("email", email, "password", password))
        .when()
            .post("/users/login")
        .then()
            .log().ifError()
            .statusCode(200)
            .body("message", equalTo("Login Successful"))
            .body("data.userId", notNullValue())
            .body("data.accessToken", notNullValue())
            .body("data.email", equalTo(email));
             }
}