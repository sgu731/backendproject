package org.example.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.order.dto.CreateOrderRequest;
import org.example.order.dto.OrderResponse;
import org.example.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order")
public class OrderController {

    private final OrderService service;

    @Operation(summary = "Create order")
    @PostMapping
    public void createOrder(
            @RequestBody
            @Valid
            CreateOrderRequest request,

            Authentication authentication) {

        service.createOrder(
                authentication.getName(),
                request);
    }

    @Operation(summary = "Get my orders")
    @GetMapping
    public List<OrderResponse> getMyOrders(
            Authentication authentication) {

        return service.getMyOrders(
                authentication.getName());
    }

}