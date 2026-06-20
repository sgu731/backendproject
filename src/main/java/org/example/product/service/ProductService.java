package org.example.product.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.common.dto.PageResponse;
import org.example.common.exception.ProductNotFoundException;
import org.example.product.Product;
import org.example.product.ProductRepository;
import org.example.product.dto.CreateProductRequest;
import org.example.product.dto.ProductResponse;
import org.example.product.dto.UpdateProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public void create(
            @Valid
            @RequestBody
            CreateProductRequest request) {

        Product product =
                new Product(
                        null,
                        request.name(),
                        request.price(),
                        request.stock(),
                        false);

        repository.save(product);
    }

    public void update(
            Long id,
            UpdateProductRequest request) {

        repository.findById(id)
                .orElseThrow(
                        ProductNotFoundException::new);

        repository.update(id, request);

    }

    public void delete(Long id) {

        repository.findById(id)
                .orElseThrow(
                        ProductNotFoundException::new);

        repository.delete(id);

    }

    public ProductResponse getProduct(Long id) {

        Product product =
                repository.findById(id)
                        .orElseThrow(
                                ProductNotFoundException::new);

        return new ProductResponse(
                product.id(),
                product.name(),
                product.price(),
                product.stock()
        );
    }

    public PageResponse<ProductResponse> getProducts(
            int page,
            int size) {

        List<ProductResponse> content =
                repository.findAll(page, size)
                        .stream()
                        .map(product ->
                                new ProductResponse(
                                        product.id(),
                                        product.name(),
                                        product.price(),
                                        product.stock()
                                ))
                        .toList();

        long total =
                repository.count();

        return new PageResponse<>(
                content,
                page,
                size,
                total
        );
    }
}