package ru.olgavakula.tests;


import org.junit.jupiter.api.Test;
import java.io.File;
import static io.restassured.RestAssured.given;


public class FavoriteTests extends BaseTest {
    private final String PATH_TO_IMAGE = "src/test/resources/fruits.jpeg";


    @Test
    void uploadFileImageTest() {
        given()
                .headers("Authorization", token)
                .multiPart("image", new File(PATH_TO_IMAGE))
                .expect()
                .statusCode(200)
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath();

    }

    @Test
    void getImageTest() {
        given()
                .header("Authorization", token)
                .when()
                .get("https://api.imgur.com/3/image/{imageHash}", "dPFuXoi")
                .then()
                .statusCode(200);
    }

    @Test
    void favoriteImageTest() {
        given()
                .header("Authorization", token)
                .when()
                .post("https://api.imgur.com/3/image/{imageHash}/favorite", "dPFuXoi")
                .then()
                .statusCode(200);
    }

    @Test
    void deleteImageTest() {
        given()
                .headers("Authorization", token)
                .when()
                .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}", username, "kPTuog7KuOt4OCC")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void unFavoriteImageTest() {
        given()
                .header("Authorization", token)
                .when()
                .post("https://api.imgur.com/3/image/{imageHash}/favorite", "dPFuXoi")
                .then()
                .statusCode(200);
    }

}


