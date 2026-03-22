package com.grim.product.dto;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
        @NotNull(message = "Product id cannot be null")
        Integer productId,
        @NotNull(message = "Quantity cannot be null")
        double quantity
) {
}
