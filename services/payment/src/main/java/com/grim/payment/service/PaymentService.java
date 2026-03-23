package com.grim.payment.service;

import com.grim.payment.dto.PaymentRequest;
import com.grim.payment.kafka.NotificationProducer;
import com.grim.payment.mapper.PaymentMapper;
import com.grim.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationProducer notificationProducer;
    public Integer createPayment(PaymentRequest payment) {
        var newPayment=paymentRepository.save(paymentMapper.toPayment(payment));
            notificationProducer.sendNotification(
                    new com.grim.payment.dto.PaymentNotificationRequest(
                            payment.orderReference(),
                            payment.amount(),
                            payment.paymentMethod(),
                            payment.customer().firstname(),
                            payment.customer().lastname(),
                            payment.customer().email()
                    )
            );
        return newPayment.getId();


    }
}
