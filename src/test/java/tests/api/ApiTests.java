package tests.api;

import com.aventstack.extentreports.Status;
import framework.ApiTestFixtures;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTests extends ApiTestFixtures {
    private static final Logger log = LoggerFactory.getLogger(ApiTests.class);

    @Test
    void createBooking() {
        var test = report.startTest("createBooking", "Validar o metodo POST");
        String requestBody = """
                {
                    "firstname": "John",
                    "lastname": "Doe",
                    "totalprice": 120,
                    "depositpaid": true,
                    "bookingdates": {
                        "checkin": "2024-12-25",
                        "checkout": "2024-12-30"
                    },
                    "additionalneeds": "Breakfast"
                }
                """;

        try {
            test.createNode("Arrange");
            var a = given()
                    .contentType(ContentType.JSON)
                    .body(requestBody);

            test.createNode("Act");
            var aa = a.when()
                    .post("/booking");

            test.createNode("Assert");
            var aaa = aa.then()
                    .statusCode(200)
                    .body("booking.firstname", equalTo("John"))
                    .body("booking.lastname", equalTo("Doe"))
                    .extract()
                    .response();

            log.info("Created Booking ID: {}", aaa.jsonPath().getInt("bookingid"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getBooking() {
        var test = report.startTest("getBooking", "Validar o metodo GET");
        int bookingId = 1;

        try {
            test.createNode("Arrange");
            var a = given();

            test.createNode("Act");
            var aa = a.when()
                    .get("/booking/" + bookingId);

            test.createNode("Assert");
            var aaa = aa.then()
                    .statusCode(200)
                    .body("firstname", notNullValue())
                    .body("lastname", notNullValue());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateBooking() {
        var test = report.startTest("updateBooking", "Validar o metodo PUT");
        int bookingId = 1;
        String requestBody = """
                {
                    "firstname": "Jane",
                    "lastname": "Smith",
                    "totalprice": 150,
                    "depositpaid": false,
                    "bookingdates": {
                        "checkin": "2024-12-26",
                        "checkout": "2024-12-31"
                    },
                    "additionalneeds": "Lunch"
                }
                """;

        try {
            test.createNode("Arrange");
            var a = given()
                    .contentType(ContentType.JSON)
                    .auth()
                    .preemptive()
                    .basic("admin", "password123")
                    .body(requestBody);

            test.createNode("Act");
            var aa = a.when()
                    .put("/booking/" + bookingId);

            test.createNode("Assert");
            var aaa = aa.then()
                    .statusCode(200)
                    .body("firstname", equalTo("Jane"))
                    .body("lastname", equalTo("Smith"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void partialUpdateBooking() {
        var test = report.startTest("partialUpdateBooking", "Validar o metodo PATCH");
        int bookingId = 1;
        String requestBody = """
                {
                    "firstname": "UpdatedName"
                }
                """;

        try {
            test.createNode("Arrange");
            var a = given()
                    .contentType(ContentType.JSON)
                    .auth()
                    .preemptive()
                    .basic("admin", "password123")
                    .body(requestBody);

            test.createNode("Act");
            var aa = a.when()
                    .patch("/booking/" + bookingId);

            test.createNode("Assert");
            var aaa = aa.then()
                    .statusCode(200)
                    .body("firstname", equalTo("UpdatedName"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteBooking() {
        var test = report.startTest("deleteBooking", "Validar o metodo DELETE");
        int bookingId = 1;

        try {
            var n1 = test.createNode("Arrange");
            var a = given()
                    .auth()
                    .preemptive()
                    .basic("admin", "password123");
            a.log().everything();
            a.log().all();
            a.log().body();
            a.log().headers();
//            n1.log(Status.INFO, a.log());

            var n2 = test.createNode("Act");
            var aa = a.when()
                    .delete("/booking/" + bookingId);

            var n3 = test.createNode("Assert");
            var aaa = aa.then();
            aaa.statusCode(201);

            n3.log(Status.INFO, aaa.log().status().toString());
            n3.log(Status.INFO, aaa.log().body().toString());
            n3.log(Status.INFO, aaa.log().cookies().toString());
            n3.log(Status.INFO, aaa.log().headers().toString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getBookingIds() {
        var test = report.startTest("getBookingIds", "Validar o metodo GET para IDs de reservas");

        try {
            test.createNode("Arrange");
            var a = given();

            test.createNode("Act");
            var aa = a.when()
                    .get("/booking");

            test.createNode("Assert");
            var aaa = aa.then()
                    .statusCode(200)
                    .body("$", not(empty()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
