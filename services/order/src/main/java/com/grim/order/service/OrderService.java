package com.grim.order.service;

import com.grim.order.dto.OrderLineRequest;
import com.grim.order.dto.OrderRequest;
import com.grim.order.dto.OrderResponse;
import com.grim.order.dto.PurchaseRequest;
import com.grim.order.exception.BusinessException;
import com.grim.order.kafka.OrderConfirmation;
import com.grim.order.kafka.OrderProducer;
import com.grim.order.mapper.OrderMapper;
import com.grim.order.payment.PaymentClient;
import com.grim.order.payment.PaymentRequest;
import com.grim.order.repository.CustomerClient;
import com.grim.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(@Valid OrderRequest order) {
//        Check the customer -> openFiegn
        var customer=customerClient.findCustomerById(order.customerId())
                .orElseThrow(()->new BusinessException("Cannot create order :: No customer exists with the provided id"));


//        Purchase product -> RestTemplate
        var purchasedProduct=productClient.purchaseProducts(order.products());

//        Persist order
        var saveOrder=orderRepository.save(orderMapper.toOrder(order));

        for(PurchaseRequest purchaseRequest: order.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            saveOrder.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // Request payment -> openFeign
        var paymentRequest=new PaymentRequest(
                order.amount(),
                order.paymentMethod(),
                saveOrder.getId(),
                saveOrder.getReference(),
                customer
        );
        paymentClient.requestOrderPaymentMethod(paymentRequest);


        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        order.reference(),
                        order.amount(),
                        order.paymentMethod(),
                        customer,
                        purchasedProduct
                )
        );

        return saveOrder.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository
                .findAll()
                .stream()
                .map(orderMapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::fromOrder)
                .orElseThrow(()->new EntityNotFoundException(String.format("No order exists with the provided id: %d", orderId)));
    }

}
