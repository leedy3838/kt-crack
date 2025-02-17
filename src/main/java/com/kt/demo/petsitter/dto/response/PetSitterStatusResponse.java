package com.kt.demo.petsitter.dto.response;

import com.kt.demo.petsitter.domain.PetSitter;
import com.kt.demo.petsitter.domain.PetSitterStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PetSitterStatusResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final PetSitterStatus status;

    public static PetSitterStatusResponse from(PetSitter petSitter) {
        return PetSitterStatusResponse.builder()
                .id(petSitter.getId())
                .name(petSitter.getUser().getName())
                .email(petSitter.getUser().getEmail())
                .status(petSitter.getStatus())
                .build();
    }

    public static PetSitterStatusResponse notExists() {
        return PetSitterStatusResponse.builder()
                .status(null)
                .build();
    }
} 