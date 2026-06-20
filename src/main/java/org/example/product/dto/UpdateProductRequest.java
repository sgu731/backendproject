package org.example.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record UpdateProductRequest(

        @NotBlank
        String name,

        @DecimalMin("0.0")
        BigDecimal price,

        @Min(0)
        Integer stock

) {
}