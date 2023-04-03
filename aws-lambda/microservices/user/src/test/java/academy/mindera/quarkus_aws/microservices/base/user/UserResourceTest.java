package academy.mindera.quarkus_aws.microservices.base.user;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class UserResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/api/users")
          .then()
             .statusCode(200)
             .body(is("Hello from RESTEasy Reactive"));
    }

}