package RestAssured;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;


public class Testpostlogin {
    @Test
    public void testPostLogin() {

        String email = "Elsamummy1233456rtt@gmail.com";
        given()
            .spec(ApiSpecifications.api())
            .contentType(ContentType.JSON)
        .body("""
        {
          "email": "%s",
          "password": "Password12367789"
        }
                """.formatted(email))
        .when()
            .post("/users/login")
        .then()
            .statusCode(200)
            .body("message", equalTo("Login Successful"))
            .body("data.userId", notNullValue())
            .body("data.email", equalTo(email))
            .log().all();   
             }
}