package com.kt.demo.petsitter.dto.request;

import com.kt.demo.petsitter.domain.PetSitter;
import com.kt.demo.petsitter.domain.PetSitterStatus;
import com.kt.demo.user.domain.User;
import java.time.LocalTime;
import java.util.Set;

public record PetSitterCreateRequest(
        String region,
        LocalTime availableStartTime,
        LocalTime availableEndTime,
        Integer price,
        Set<Long> petTypeIds
) {

    public PetSitter toEntity(User user) {
        return PetSitter.builder()
                .user(user)
                .region(region)
                .availableStartTime(availableStartTime)
                .availableEndTime(availableEndTime)
                .price(price)
                .status(PetSitterStatus.WAITING)
                .build();
    }
} 