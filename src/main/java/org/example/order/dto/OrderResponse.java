package org.example.order.dto;

import java.time.LocalDateTime;

public record OrderResponse(

        Long id,

        Long productId,

        String productName,

        Integer quantity,

        LocalDateTime createdAt

) {
}