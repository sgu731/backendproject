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
import org.springframework.security.core.Authentication;
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
            @Valid
            @RequestBody
            CreateProductRequest request,
            Authentication authentication) {

        service.create(request, authentication.getName());

    }

    @Operation(summary = "Update product by id")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void update(
            @PathVariable Long id,
            @RequestBody
            UpdateProductRequest request,
            Authentication authentication) {

        service.update(
                id,
                request,
                authentication.getName());
    }

    @Operation(summary = "Soft Delete product by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(
            @PathVariable Long id,
            Authentication authentication) {

        service.delete(
                id,
                authentication.getName());

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