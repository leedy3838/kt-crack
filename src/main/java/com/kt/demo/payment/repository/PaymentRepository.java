package com.kt.demo.payment.repository;

import com.kt.demo.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Boolean existsByReservationId(Long reservationId);
} 