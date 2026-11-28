package RestAssured;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class ApiSpecifications {
    private static final Duration TOKEN_REFRESH_INTERVAL = Duration.ofMinutes(14);

    private static String cachedToken;
    private static Instant tokenFetchedAt;

    private ApiSpecifications() {}

    public static RequestSpecification api() {
        return new RequestSpecBuilder()
            .addRequestSpecification(publicApi())
            .addHeader("Authorization", "Bearer " + accessToken())
            .build();
    }

    public static RequestSpecification publicApi() {
        return new RequestSpecBuilder()
            .setBaseUri(baseUrl())
            .setAccept(ContentType.JSON)
            .build();
    }

    private static String baseUrl() {
        return System.getProperty(
            "api.baseUrl",
            "http://localhost:8081/Financial-Pilot-AI-1.0-SNAPSHOT");
    }

    private static synchronized String accessToken() {
        if (cachedToken != null
                && tokenFetchedAt != null
                && Instant.now().isBefore(tokenFetchedAt.plus(TOKEN_REFRESH_INTERVAL))) {
            return cachedToken;
        }

        String email = requiredCredential("API_TEST_EMAIL");
        String password = requiredCredential("API_TEST_PASSWORD");

        Response response = given()
            .spec(publicApi())
            .contentType(ContentType.JSON)
            .body(Map.of("email", email, "password", password))
        .when()
            .post("/users/login");

        if (response.statusCode() != 200) {
            throw new IllegalStateException(
                "Test API login failed with HTTP " + response.statusCode()
                    + ": " + response.asString());
        }

        String token = response.jsonPath().getString("data.accessToken");
        if (token == null || token.isBlank()) {
            throw new IllegalStateException(
                "Test API login response did not contain data.accessToken");
        }

        cachedToken = token;
        tokenFetchedAt = Instant.now();
        return cachedToken;
    }

    private static String requiredCredential(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                "Set the " + name + " environment variable to run authenticated API tests");
        }
        return value;
    }
}