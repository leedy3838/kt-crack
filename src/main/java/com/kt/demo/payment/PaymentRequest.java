package com.kt.demo.payment;

public record PaymentRequest(
        String paymentId,
        Long reservationId
) {
}
