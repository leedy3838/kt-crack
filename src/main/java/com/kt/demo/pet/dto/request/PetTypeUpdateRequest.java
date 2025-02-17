package com.kt.demo.pet.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PetTypeUpdateRequest(
    @NotBlank(message = "이름은 필수입니다") String name,
    @NotBlank(message = "코드는 필수입니다") String code
) {
} 