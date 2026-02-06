package com.autoflex.inventory.service;

import com.autoflex.inventory.dto.ProductRequest;
import com.autoflex.inventory.dto.ProductResponse;
import com.autoflex.inventory.entity.Product;
import com.autoflex.inventory.exception.ResourceNotFoundException;
import com.autoflex.inventory.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;
    
    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
            product.id,
            product.code,
            product.name,
            product.value,
            product.createdAt,
            product.updatedAt
        );
    }

    private Product toEntity(ProductRequest request) {
        Product product = new Product();
        product.code = request.getCode();
        product.name = request.getName();
        product.value = request.getValue();
        return product;
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.listAll()
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        return toResponse(product);
    }

    public ProductResponse getProductByCode(String code) {
        Product product = Product.findByCode(code);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found with code: " + code);
        }
        return toResponse(product);
    }

    @Transactional
    public ProductResponse createProduct(@Valid ProductRequest request) {
        if (Product.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Product code already exists: " + request.getCode());
        }

        Product product = toEntity(request);
        productRepository.persist(product);
        return toResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, @Valid ProductRequest request) {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }

        if (!product.code.equals(request.getCode()) && 
            productRepository.existsByCodeAndIdNot(request.getCode(), id)) {
            throw new IllegalArgumentException("Product code already exists: " + request.getCode());
        }

        product.code = request.getCode();
        product.name = request.getName();
        product.value = request.getValue();

        productRepository.persist(product);
        return toResponse(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.delete(product);
    }

    public List<ProductResponse> searchProductsByName(String name) {
        return productRepository.findByNameContaining(name)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsSortedByValueDesc() {
        return productRepository.findAllOrderByValueDesc()
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByValueRange(BigDecimal minValue, BigDecimal maxValue) {
        return productRepository.findByValueRange(minValue, maxValue)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public boolean productExists(Long id) {
        return productRepository.findById(id) != null;
    }

    public long getProductCount() {
        return productRepository.count();
    }

    public BigDecimal getTotalProductsValue() {
        List<Product> products = productRepository.listAll();
        return products.stream()
            .map(product -> product.value)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
