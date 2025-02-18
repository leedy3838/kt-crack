package com.kt.demo.payment.controller;

import com.kt.demo.global.dto.ResponseTemplate;
import com.kt.demo.payment.PaymentRequest;
import com.kt.demo.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "결제 등록", description = "결제 정보를 등록합니다.")
    @PostMapping
    public ResponseEntity<ResponseTemplate<?>> registerPayment(
            @RequestBody PaymentRequest paymentRequest
    ) {
        var payment = paymentService.registerPayment(paymentRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseTemplate.from(payment));
    }
} 