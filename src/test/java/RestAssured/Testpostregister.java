package RestAssured;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;


public class Testpostregister {
    private record RegisterRequest(String name, String email, String password) {}

    @Test
    public void testPostRegister() {
        String email = "test-" + UUID.randomUUID() + "@example.com";

        RegisterRequest request =
            new RegisterRequest("Test User", email, "Test12345!");

        given()
            .spec(ApiSpecifications.api())
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/users/register")
        .then()
            .statusCode(200)
            .body("message", equalTo("User registered successfully"))
            .body("data", nullValue())

            .log().all();
    }
}