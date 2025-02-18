package com.kt.demo.payment.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String paymentId; // 결제 번호

    @Column(nullable = false)
    private Long reservationId; // 예약 ID

    @Column(nullable = false)
    private LocalDateTime paymentDate; // 결제 일시

    @Builder
    public Payment(String paymentId, Long reservationId, LocalDateTime paymentDate) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.paymentDate = paymentDate;
    }
} 