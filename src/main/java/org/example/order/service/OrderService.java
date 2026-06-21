package org.example.order.service;

import lombok.RequiredArgsConstructor;
import org.example.common.exception.InsufficientStockException;
import org.example.common.exception.UserNotFoundException;
import org.example.order.OrderRepository;
import org.example.order.dto.CreateOrderRequest;
import org.example.order.dto.OrderResponse;
import org.example.product.ProductRepository;
import org.example.user.User;
import org.example.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createOrder(
            String username,
            CreateOrderRequest request) {

        User user =
                userRepository.findByUsername(username)
                        .orElseThrow(
                                UserNotFoundException::new);

        boolean success =
                productRepository.decreaseStock(
                        request.productId(),
                        request.quantity());

        if (!success) {
            throw new InsufficientStockException();
        }

        orderRepository.save(
                user.id(),
                request.productId(),
                request.quantity());
    }

    public List<OrderResponse> getMyOrders(
            String username) {

        User user =
                userRepository.findByUsername(username)
                        .orElseThrow(
                                UserNotFoundException::new);

        return orderRepository.findByUserId(
                user.id());
    }

}