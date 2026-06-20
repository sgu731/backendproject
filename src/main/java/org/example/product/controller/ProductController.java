package org.example.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.common.dto.PageResponse;
import org.example.product.dto.CreateProductRequest;
import org.example.product.dto.ProductResponse;
import org.example.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public void create(
            @RequestBody
            @Valid
            CreateProductRequest request) {

        service.create(request);

    }

    // GET /api/products?page=0&size=10
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