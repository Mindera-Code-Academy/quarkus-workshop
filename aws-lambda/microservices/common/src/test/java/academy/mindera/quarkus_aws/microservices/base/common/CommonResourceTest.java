package academy.mindera.quarkus_aws.microservices.base.common;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class CommonResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/api/token")
          .then()
             .statusCode(204);
            // .body(is("Hello from RESTEasy Reactive"));
    }

}