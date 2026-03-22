package com.grim.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        Integer id,
        @NotNull(message = "Name cannot be null")
        String name,
        @NotNull(message = "Description cannot be null")
        String description,
        @NotNull(message = "Available quantity cannot be null")
        @Positive(message = "Available quantity must be positive")
        double availableQuantity,
        @Positive(message = "Price must be positive")
        BigDecimal price,
        @NotNull(message = "product category cannot be null")
        Integer categoryId
) {
}
