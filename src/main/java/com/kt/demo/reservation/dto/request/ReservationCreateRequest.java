package com.kt.demo.reservation.dto.request;

import com.kt.demo.petsitter.domain.PetSitter;
import com.kt.demo.reservation.domain.Reservation;
import com.kt.demo.user.domain.User;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ReservationCreateRequest(
    @NotNull(message = "펫시터 ID는 필수입니다") Long petSitterId,
    @NotNull(message = "시작 시간은 필수입니다") LocalDateTime startTime,
    @NotNull(message = "종료 시간은 필수입니다") LocalDateTime endTime,
    String message
) {
    public Reservation toEntity(User user, PetSitter petSitter, Integer price) {
        return Reservation.builder()
            .user(user)
            .petSitter(petSitter)
            .startTime(startTime)
            .endTime(endTime)
            .message(message)
            .price(price)
            .build();
    }
} 