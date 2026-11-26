package RestAssured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class ApiSpecifications {
    private ApiSpecifications() {}

    public static RequestSpecification api() {
        return new RequestSpecBuilder()
            .setBaseUri(System.getProperty(
                "api.baseUrl",
                "http://localhost:8081/Financial-Pilot-AI-1.0-SNAPSHOT"))
            .setAccept(ContentType.JSON)
            .build();
    }
}