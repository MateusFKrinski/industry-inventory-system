package com.autoflex.inventory.resource;

import com.autoflex.inventory.dto.ProductRequest;
import com.autoflex.inventory.dto.ProductResponse;
import com.autoflex.inventory.service.ProductService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Products", description = "Operations for managing products")
public class ProductResource {

    @Inject
    ProductService productService;

    @GET
    @Operation(
        summary = "Get all products",
        description = "Returns a list of all products in the system"
    )
    @APIResponse(
        responseCode = "200",
        description = "List of products",
        content = @Content(mediaType = "application/json",
            schema = @Schema(type = SchemaType.ARRAY, implementation = ProductResponse.class))
    )
    public Response getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return Response.ok(products).build();
    }

    @GET
    @Path("/{id}")
    @Operation(
        summary = "Get product by ID",
        description = "Returns a single product by its ID"
    )
    @APIResponse(
        responseCode = "200",
        description = "Product found",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductResponse.class))
    )
    @APIResponse(
        responseCode = "404",
        description = "Product not found"
    )
    public Response getProductById(
        @Parameter(description = "Product ID", required = true)
        @PathParam("id") Long id) {
        ProductResponse product = productService.getProductById(id);
        return Response.ok(product).build();
    }

    @GET
    @Path("/code/{code}")
    @Operation(
        summary = "Get product by code",
        description = "Returns a single product by its code"
    )
    public Response getProductByCode(
        @Parameter(description = "Product code", required = true)
        @PathParam("code") String code) {
        ProductResponse product = productService.getProductByCode(code);
        return Response.ok(product).build();
    }

    @GET
    @Path("/search")
    @Operation(
        summary = "Search products by name",
        description = "Returns products that match the search term in their name"
    )
    public Response searchProducts(
        @Parameter(description = "Search term")
        @QueryParam("name") String name) {
        List<ProductResponse> products = productService.searchProductsByName(name);
        return Response.ok(products).build();
    }

    @GET
    @Path("/sorted/value-desc")
    @Operation(
        summary = "Get products sorted by value (descending)",
        description = "Returns products sorted from highest to lowest value"
    )
    public Response getProductsSortedByValueDesc() {
        List<ProductResponse> products = productService.getProductsSortedByValueDesc();
        return Response.ok(products).build();
    }

    @GET
    @Path("/filter/value-range")
    @Operation(
        summary = "Filter products by value range",
        description = "Returns products within the specified value range"
    )
    public Response getProductsByValueRange(
        @Parameter(description = "Minimum value")
        @QueryParam("min") BigDecimal minValue,
        @Parameter(description = "Maximum value")
        @QueryParam("max") BigDecimal maxValue) {
        List<ProductResponse> products = productService.getProductsByValueRange(minValue, maxValue);
        return Response.ok(products).build();
    }

    @POST
    @Operation(
        summary = "Create a new product",
        description = "Creates a new product with the provided data"
    )
    @APIResponse(
        responseCode = "201",
        description = "Product created successfully",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductResponse.class))
    )
    @APIResponse(
        responseCode = "400",
        description = "Invalid input data"
    )
    public Response createProduct(@Valid ProductRequest request) {
        ProductResponse product = productService.createProduct(request);
        return Response.status(Response.Status.CREATED).entity(product).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(
        summary = "Update an existing product",
        description = "Updates the product with the specified ID"
    )
    @APIResponse(
        responseCode = "200",
        description = "Product updated successfully",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductResponse.class))
    )
    @APIResponse(
        responseCode = "404",
        description = "Product not found"
    )
    @APIResponse(
        responseCode = "400",
        description = "Invalid input data"
    )
    public Response updateProduct(
        @Parameter(description = "Product ID", required = true)
        @PathParam("id") Long id,
        @Valid ProductRequest request) {
        ProductResponse product = productService.updateProduct(id, request);
        return Response.ok(product).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(
        summary = "Delete a product",
        description = "Deletes the product with the specified ID"
    )
    @APIResponse(
        responseCode = "204",
        description = "Product deleted successfully"
    )
    @APIResponse(
        responseCode = "404",
        description = "Product not found"
    )
    public Response deleteProduct(
        @Parameter(description = "Product ID", required = true)
        @PathParam("id") Long id) {
        productService.deleteProduct(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/count")
    @Operation(
        summary = "Get total product count",
        description = "Returns the total number of products in the system"
    )
    public Response getProductCount() {
        long count = productService.getProductCount();
        return Response.ok(count).build();
    }

    @GET
    @Path("/stats/total-value")
    @Operation(
        summary = "Get total value of all products",
        description = "Returns the sum of values of all products"
    )
    public Response getTotalProductsValue() {
        BigDecimal totalValue = productService.getTotalProductsValue();
        return Response.ok(totalValue).build();
    }

    @GET
    @Path("/health")
    @Operation(
        summary = "Product service health check",
        description = "Checks if the product service is operational"
    )
    public Response healthCheck() {
        return Response.ok("Product service is operational").build();
    }
}
