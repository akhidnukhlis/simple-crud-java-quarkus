package akhid;

import akhid.development.model.postgres.Cake;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class CakeUnitTest {
    String id = "2aaed562-b3aa-4952-8c80-fab6147d0a4a";
    String tittle = "cake strawberry";
    String description = "Strawberry cake is a cake that uses strawberry as a primary ingredient.";
    Integer rating = 4;
    String image = "https://sugargeekshow.com/wp-content/uploads/2019/07/fresh-strawberry-cake-5.jpg";

    @Order(1)
    @Test
    @DisplayName("submit cake")
    public void submitCake() {
        Cake cake = new Cake();
        cake.id = id;
        cake.tittle = tittle;
        cake.description = description;
        cake.rating = rating;
        cake.image = image;
        given()
                .header("Content-Type", "application/json")
                .body(cake)
                .when().post("/api/v1/product/cake/submit")
                .then()
                .statusCode(201)
                .body("successfully created object", (Matcher<?>) cake);
    }

    @Order(2)
    @Test
    @DisplayName("find all cakes")
    public void findAll() {
        Cake cake = given()
                .when().get("/api/v1/product/cakes")
                .then()
                    .statusCode(200)
                    .extract()
                    .body().as(Cake.class);
        assertNotNull(cake);
    }

    @Order(3)
    @Test
    @DisplayName("get all cakes by id")
    public void findById() {
        Cake cake = given()
                .pathParam("id", id)
                .when().get("/api/v1/product/cake/id/{id}")
                .then()
                    .statusCode(200)
                    .extract()
                    .body().as(Cake.class);
        assertNotNull(cake);
        assertEquals(1L, cake.id);
    }

}
