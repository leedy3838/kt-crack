package com.kt.demo.reservation.dto.response;

import com.kt.demo.reservation.domain.Reservation;
import com.kt.demo.reservation.domain.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReservationResponse(
    Long id,
    String userName,
    LocalDateTime startTime,
    LocalDateTime endTime,
    ReservationStatus status,
    String message
) {
    public static ReservationResponse from(Reservation reservation) {
        return ReservationResponse.builder()
            .id(reservation.getId())
            .userName(reservation.getUser().getName())
            .startTime(reservation.getStartTime())
            .endTime(reservation.getEndTime())
            .status(reservation.getStatus())
            .message(reservation.getMessage())
            .build();
    }
} 