package org.example.product.service;

import lombok.RequiredArgsConstructor;
import org.example.common.dto.PageResponse;
import org.example.product.Product;
import org.example.product.ProductRepository;
import org.example.product.dto.CreateProductRequest;
import org.example.product.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public void create(
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