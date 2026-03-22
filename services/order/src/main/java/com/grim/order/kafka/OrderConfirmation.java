package com.grim.order.kafka;

import com.grim.order.dto.CustomerResponse;
import com.grim.order.dto.PurchaseResponse;
import com.grim.order.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
