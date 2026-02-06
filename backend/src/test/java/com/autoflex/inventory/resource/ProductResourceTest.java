package com.autoflex.inventory.resource;

import com.autoflex.inventory.dto.ProductRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductResourceTest {

    private static Long createdProductId;

    private static final String PRODUCT_CODE = "TEST001";
    private static final String PRODUCT_NAME = "Test Product";
    private static final BigDecimal PRODUCT_VALUE = new BigDecimal("999.99");

    @BeforeEach
    void setup() {
        RestAssured.basePath = "/api/products";
    }

    @Test
    @Order(1)
    void testCreateProduct() {
        ProductRequest request = new ProductRequest();
        request.setCode(PRODUCT_CODE);
        request.setName(PRODUCT_NAME);
        request.setValue(PRODUCT_VALUE);

        Number id =
            given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("code", equalTo(PRODUCT_CODE))
                .body("name", equalTo(PRODUCT_NAME))
                .body("value", equalTo(999.99f))
                .body("id", notNullValue())
                .extract()
                .path("id");

        createdProductId = id.longValue();
        Assertions.assertNotNull(createdProductId);
    }

    @Test
    @Order(2)
    void testCreateProductWithDuplicateCode() {
        ProductRequest request = new ProductRequest();
        request.setCode(PRODUCT_CODE);
        request.setName("Another Product");
        request.setValue(new BigDecimal("500.00"));

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post()
            .then()
            .statusCode(400);
    }

    @Test
    @Order(3)
    void testGetAllProducts() {
        given()
            .when()
            .get()
            .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(1))
            .body("code", hasItem(PRODUCT_CODE));
    }

    @Test
    @Order(4)
    void testGetProductById() {
        Assertions.assertNotNull(createdProductId);

        given()
            .pathParam("id", createdProductId)
            .when()
            .get("/{id}")
            .then()
            .statusCode(200)
            .body("id", equalTo(createdProductId.intValue()))
            .body("code", equalTo(PRODUCT_CODE))
            .body("name", equalTo(PRODUCT_NAME));
    }

    @Test
    @Order(5)
    void testGetProductByCode() {
        given()
            .pathParam("code", PRODUCT_CODE)
            .when()
            .get("/code/{code}")
            .then()
            .statusCode(200)
            .body("code", equalTo(PRODUCT_CODE));
    }

    @Test
    @Order(6)
    void testUpdateProduct() {
        ProductRequest request = new ProductRequest();
        request.setCode("TEST001-UPDATED");
        request.setName("Updated Test Product");
        request.setValue(new BigDecimal("1299.99"));

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .pathParam("id", createdProductId)
            .when()
            .put("/{id}")
            .then()
            .statusCode(200)
            .body("code", equalTo("TEST001-UPDATED"))
            .body("name", equalTo("Updated Test Product"))
            .body("value", equalTo(1299.99f));
    }

    @Test
    @Order(7)
    void testSearchProductsByName() {
        given()
            .queryParam("name", "Updated")
            .when()
            .get("/search")
            .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(1))
            .body("name", everyItem(containsStringIgnoringCase("updated")));
    }

    @Test
    @Order(8)
    void testGetProductsSortedByValueDesc() {
        given()
            .when()
            .get("/sorted/value-desc")
            .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    @Order(9)
    void testGetProductCount() {
        int count =
            given()
                .when()
                .get("/count")
                .then()
                .statusCode(200)
                .extract()
                .as(Integer.class);

        Assertions.assertTrue(count >= 1);
    }

    @Test
    @Order(10)
    void testDeleteProduct() {
        given()
            .pathParam("id", createdProductId)
            .when()
            .delete("/{id}")
            .then()
            .statusCode(204);
    }

    @Test
    @Order(11)
    void testGetProductNotFound() {
        given()
            .pathParam("id", 99999)
            .when()
            .get("/{id}")
            .then()
            .statusCode(404);
    }

    @Test
    void testCreateProductWithInvalidData() {
        ProductRequest request = new ProductRequest();
        request.setCode("AB");
        request.setName("");
        request.setValue(new BigDecimal("-100"));

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post()
            .then()
            .statusCode(400);
    }
}
