package com.grim.notification.kafka;

import com.grim.notification.email.EmailService;
import com.grim.notification.kafka.order.OrderConfirmation;
import com.grim.notification.kafka.payment.PaymentConfirmation;
import com.grim.notification.notification.Notification;
import com.grim.notification.notification.NotificationRepository;
import com.grim.notification.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Received payment confirmation: {}", paymentConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(java.time.LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

//        todo: send mail
        var customerName=paymentConfirmation.customerFirstname()+" "+paymentConfirmation.customerLastname();
        log.info("Customer name: {}", customerName);
        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );

    }
    @KafkaListener(topics = "order-topic")
    public void consumeOrderSuccessNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Received order confirmation: {}", orderConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(java.time.LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        var customerName=orderConfirmation.customer().firstname()+" "+orderConfirmation.customer().lastname();
        log.info("Customer name: {}", customerName);
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );


    }


}
