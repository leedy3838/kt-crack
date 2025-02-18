package com.kt.demo.reservation.dto.response;

import com.kt.demo.reservation.domain.Reservation;
import com.kt.demo.reservation.domain.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MyReservationResponse(
    Long id,
    String userName,
    LocalDateTime startTime,
    LocalDateTime endTime,
    ReservationStatus status,
    String message,
    Integer price,
    Boolean isPayed
) {
    public static MyReservationResponse from(Reservation reservation, Boolean isPayed) {
        return MyReservationResponse.builder()
            .id(reservation.getId())
            .userName(reservation.getPetSitter().getUser().getName())
            .startTime(reservation.getStartTime())
            .endTime(reservation.getEndTime())
            .status(reservation.getStatus())
            .message(reservation.getMessage())
            .price(reservation.getPrice())
            .isPayed(isPayed)
            .build();
    }
} 