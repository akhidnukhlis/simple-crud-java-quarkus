package akhid;

import akhid.development.model.postgres.Cake;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

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
        final Cake cake = new Cake();
        cake.tittle = tittle;
        cake.description = description;
        cake.rating = rating;
        cake.image = image;

        given()
                .body(cake)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post("/api/v1/product/cake/submit")
                .then()
                    .statusCode(Response.Status.CREATED.getStatusCode())
                    .body(notNullValue());
    }

    @Order(2)
    @Test
    @DisplayName("find all cakes")
    public void findAll() {
        given()
                .when().get("/api/v1/product/cakes")
                .then()
                    .statusCode(Response.Status.OK.getStatusCode())
                    .body(notNullValue());
    }

    @Order(3)
    @Test
    @DisplayName("get cake by id")
    public void findById() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get("/api/v1/product/cake/id/{id}", id)
                .then()
                    .statusCode(Response.Status.OK.getStatusCode())
                    .body(notNullValue());
    }

    @Order(4)
    @Test
    @DisplayName("update cake by id")
    public void updateById() {
        final Cake cake = new Cake();
        cake.tittle = "apple cake";
        cake.rating = 5;

        given()
                .body(cake)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().put("/api/v1/product/cake/update/id/{id}", id)
                .then()
                    .statusCode(Response.Status.OK.getStatusCode())
                    .body(notNullValue());
    }

    @Order(5)
    @Test
    @DisplayName("delete cake by id")
    public void deleteById() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().delete("/api/v1/product/cake/delete/id/{id}", id)
                .then()
                    .statusCode(Response.Status.OK.getStatusCode())
                    .body(notNullValue());
    }

}
