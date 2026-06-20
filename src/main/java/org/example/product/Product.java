package org.example.product;

import java.math.BigDecimal;

public record Product(
        Long id,
        String name,
        BigDecimal price,
        Integer stock,
        Boolean deleted
) {
}