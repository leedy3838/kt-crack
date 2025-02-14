package com.kt.demo.petsitter.dto.response;

import com.kt.demo.petsitter.domain.PetSitter;
import java.util.Collections;
import java.util.List;
import lombok.Builder;

@Builder
public record PetSitterStatusResponse(
    boolean exists,
    boolean isActivated,
    String region,
    Integer price,
    List<String> availablePetTypes
) {
    public static PetSitterStatusResponse from(PetSitter petSitter) {
        return PetSitterStatusResponse.builder()
            .exists(true)
            .isActivated(petSitter.getIsActivated())
            .region(petSitter.getRegion())
            .price(petSitter.getPrice())
            .availablePetTypes(petSitter.getPetSitterPetTypes().stream()
                .map(petSitterPetType -> petSitterPetType.getId().getPetType().getName())
                .toList())
            .build();
    }

    public static PetSitterStatusResponse notExists() {
        return PetSitterStatusResponse.builder()
            .exists(false)
            .isActivated(false)
            .region(null)
            .price(null)
            .availablePetTypes(Collections.emptyList())
            .build();
    }
} 