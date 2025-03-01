package com.kt.demo.pet.dto.response;

import com.kt.demo.pet.domain.PetType;
import lombok.Builder;

@Builder
public record PetTypeResponse(
    Long id,
    String name,
    String code
) {
    public static PetTypeResponse from(PetType petType) {
        return PetTypeResponse.builder()
            .id(petType.getId())
            .name(petType.getName())
            .code(petType.getCode())
            .build();
    }
} 