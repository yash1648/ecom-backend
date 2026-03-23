package com.grim.order.payment;

import com.grim.order.dto.CustomerResponse;
import com.grim.order.model.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(

        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
