package RestAssured;

import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Testgetlogin {
    @Test
    public void testGetLogin() {
        given()
            .spec(ApiSpecifications.api())
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .body("message", equalTo("Users Retrieved Successfully"))
            .log().all();    }
}
