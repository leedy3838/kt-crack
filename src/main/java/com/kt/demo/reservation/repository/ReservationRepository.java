package com.kt.demo.reservation.repository;

import com.kt.demo.reservation.domain.Reservation;
import com.kt.demo.reservation.domain.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByPetSitterUserEmailAndStatus(String email, ReservationStatus status);
    List<Reservation> findAllByUserEmail(String email);
    List<Reservation> findAllByPetSitterUserEmail(String email);
} 