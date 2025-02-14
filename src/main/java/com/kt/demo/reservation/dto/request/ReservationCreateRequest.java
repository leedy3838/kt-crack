package com.kt.demo.reservation.dto.request;

import com.kt.demo.petsitter.domain.PetSitter;
import com.kt.demo.reservation.domain.Reservation;
import com.kt.demo.user.domain.User;
import java.time.LocalDateTime;

public record ReservationCreateRequest(
    Long petSitterId,
    LocalDateTime startTime,
    LocalDateTime endTime,
    String message
) {
    public Reservation toEntity(User user, PetSitter petSitter) {
        return Reservation.builder()
            .user(user)
            .petSitter(petSitter)
            .startTime(startTime)
            .endTime(endTime)
            .message(message)
            .build();
    }
} 