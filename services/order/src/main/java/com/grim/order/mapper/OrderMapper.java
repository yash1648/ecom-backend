package com.grim.order.mapper;

import com.grim.order.dto.OrderRequest;
import com.grim.order.dto.OrderResponse;
import com.grim.order.model.Order;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
        public Order toOrder(@Valid OrderRequest order) {
            return Order.builder()
                    .id(order.id())
                    .customerId(order.customerId())
                    .reference(order.reference())
                    .totalAmount(order.amount())
                    .paymentMethod(order.paymentMethod())
                    .build();
    }

    public OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );

    }
}
