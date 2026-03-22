package com.grim.order.dto;

import com.grim.order.model.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(
    Integer id,
    String reference,
    BigDecimal amount,
    PaymentMethod paymentMethod,
    String customerId
    ) {
}
