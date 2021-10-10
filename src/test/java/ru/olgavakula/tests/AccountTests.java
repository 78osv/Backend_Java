package ru.olgavakula.tests;

import io.restassured.http.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeEach;
import io.restassured.builder.ResponseSpecBuilder;
import ru.olgavakula.data.GetAccountResponse;
import static ru.olgavakula.Endpoints.GET_ACCOUNT;

public class AccountTests extends BaseTest {
    GetAccountResponse getAccountResponse;

    @BeforeEach
    void beforeTest() {
        positiveResponseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectHeader("Access-Control-Allow-Credentials", "true")
                .build();

        requestSpecificationWithAuth = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.ANY)
                .build();
    }

    @Test
    void getAccountInfoTest() {
        given()
                .spec(requestSpecificationWithAuth)
                .when()
                .get(GET_ACCOUNT, username)
                .prettyPeek()
                .then()
                .spec(positiveResponseSpecification);
    }

    @Test
    void getAccountInfoWithExternalEndpointTest() {
        getAccountResponse =
                given()
                        .spec(requestSpecificationWithAuth)
                        .when()
                        .get(GET_ACCOUNT, username)
                        .then()
                        .extract()
                        .body()
                        .as(GetAccountResponse.class);
        assertThat(getAccountResponse.getStatus(), equalTo(200));

    }

    @Test
    void getAccountInfoWithLoggingTest() {
        given()
                .spec(requestSpecificationWithAuth)
                .log()
                .method()
                .log()
                .uri()
                .when()
                .get(GET_ACCOUNT, username)
                .prettyPeek()
                .then()
                .statusCode(200);

    }

    @Test
    void getAccountInfoWithAssertionsInGivenTest() {
        given()
                .spec(requestSpecificationWithAuth)
                .log()
                .method()
                .log()
                .uri()
                .expect()
                .statusCode(200)
                .body("data.url", equalTo(username))
                .body("success", equalTo(true))
                .body("status", equalTo(200))
                .contentType("application/json")
                .when()
                .get(GET_ACCOUNT, username)
                .prettyPeek();
    }

    @Test
    void getAccountInfoWithAssertionsAfterTest() {
        Response response = given()
                .spec(requestSpecificationWithAuth)
                .log()
                .method()
                .log()
                .uri()
                .when()
                .get(GET_ACCOUNT, username)
                .prettyPeek();
        assertThat(response.jsonPath().get("data.url"), equalTo(username));
    }


}

