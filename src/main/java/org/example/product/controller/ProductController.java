package org.example.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.common.dto.PageResponse;
import org.example.product.dto.CreateProductRequest;
import org.example.product.dto.ProductResponse;
import org.example.product.dto.UpdateProductRequest;
import org.example.product.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product")
public class ProductController {

    private final ProductService service;

    @Operation(summary = "Create product")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void create(
            @RequestBody
            @Valid
            CreateProductRequest request) {

        service.create(request);

    }

    @Operation(summary = "Update product by id")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public void update(
            @PathVariable Long id,
            @RequestBody UpdateProductRequest request) {

        service.update(id, request);
    }

    @Operation(summary = "Soft Delete product by id")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {

        service.delete(id);

    }

    @Operation(summary = "Get product by id")
    @GetMapping("/{id}")
    public ProductResponse getProduct(
            @PathVariable Long id) {

        return service.getProduct(id);
    }

    // GET /api/products?page=0&size=10
    @Operation(summary = "Get products")
    @GetMapping
    public PageResponse<ProductResponse> getProducts(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        return service.getProducts(
                page,
                size);

    }
}