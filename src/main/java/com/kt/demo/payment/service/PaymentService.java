package com.kt.demo.payment.service;

import com.kt.demo.payment.PaymentRequest;
import com.kt.demo.payment.domain.Payment;
import com.kt.demo.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment registerPayment(PaymentRequest paymentRequest) {
        Payment payment = Payment.builder()
                .paymentId(paymentRequest.paymentId())
                .reservationId(paymentRequest.reservationId())
                .paymentDate(LocalDateTime.now())
                .build();

        return paymentRepository.save(payment);
    }
} 