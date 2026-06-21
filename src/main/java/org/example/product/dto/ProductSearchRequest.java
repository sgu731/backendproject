package org.example.product.dto;

import java.math.BigDecimal;

public record ProductSearchRequest(

        String keyword,

        BigDecimal minPrice,

        BigDecimal maxPrice,

        String sortBy,

        String direction,

        int page,

        int size

) {
}