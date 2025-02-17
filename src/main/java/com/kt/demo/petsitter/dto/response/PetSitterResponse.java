package com.kt.demo.petsitter.dto.response;

import com.kt.demo.petsitter.domain.PetSitter;
import lombok.Builder;

import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record PetSitterResponse(
    Long id,
    Long userId,
    String name,
    String region,
    LocalTime availableStartTime,
    LocalTime availableEndTime,
    Integer price,
    Set<String> petTypes
) {
    public static PetSitterResponse from(PetSitter petSitter) {
        return PetSitterResponse.builder()
            .id(petSitter.getId())
            .userId(petSitter.getUser().getId())
            .name(petSitter.getUser().getName())
            .region(petSitter.getRegion())
            .availableStartTime(petSitter.getAvailableStartTime())
            .availableEndTime(petSitter.getAvailableEndTime())
            .price(petSitter.getPrice())
            .petTypes(petSitter.getPetSitterPetTypes().stream()
                .map(petSitterPetType -> petSitterPetType.getPetType().getName())
                .collect(Collectors.toSet()))
            .build();
    }
} 