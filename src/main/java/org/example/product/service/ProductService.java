package org.example.product.service;

import lombok.RequiredArgsConstructor;
import org.example.AuditAction;
import org.example.audit.service.AuditLogService;
import org.example.common.dto.PageResponse;
import org.example.common.exception.ProductNotFoundException;
import org.example.product.Product;
import org.example.product.ProductRepository;
import org.example.product.dto.CreateProductRequest;
import org.example.product.dto.ProductResponse;
import org.example.product.dto.ProductSearchRequest;
import org.example.product.dto.UpdateProductRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final AuditLogService auditLogService;

    public void create(
            CreateProductRequest request,
            String username) {

        Product product =
                new Product(
                        null,
                        request.name(),
                        request.price(),
                        request.stock(),
                        false);

        repository.save(product);

        auditLogService.log(
                username,
                AuditAction.CREATE_PRODUCT,
                null,
                product.toString());
    }

    public void update(
            Long id,
            UpdateProductRequest request,
            String username) {

        Product oldProduct =
                repository.findById(id)
                        .orElseThrow(
                                ProductNotFoundException::new);

        repository.update(id, request);

        Product newProduct =
                repository.findById(id)
                        .orElseThrow(
                                ProductNotFoundException::new);

        auditLogService.log(
                username,
                AuditAction.UPDATE_PRODUCT,
                oldProduct.toString(),
                newProduct.toString());
    }

    public void delete(
            Long id,
            String username) {

        Product oldProduct =
                repository.findById(id)
                        .orElseThrow(
                                ProductNotFoundException::new);

        repository.delete(id);

        auditLogService.log(
                username,
                AuditAction.DELETE_PRODUCT,
                oldProduct.toString(),
                null);
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

    public PageResponse<ProductResponse> search(
            ProductSearchRequest request) {

        List<ProductResponse> content =

                repository.search(request)
                        .stream()
                        .map(product ->
                                new ProductResponse(
                                        product.id(),
                                        product.name(),
                                        product.price(),
                                        product.stock()
                                ))
                        .toList();

        Long total =
                repository.count(request);

        return new PageResponse<>(

                content,

                request.page(),

                request.size(),

                total
        );
    }
}