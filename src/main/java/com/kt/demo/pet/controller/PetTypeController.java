package com.kt.demo.pet.controller;

import com.kt.demo.global.dto.ResponseTemplate;
import com.kt.demo.pet.dto.request.PetTypeCreateRequest;
import com.kt.demo.pet.dto.request.PetTypeUpdateRequest;
import com.kt.demo.pet.dto.response.PetTypeResponse;
import com.kt.demo.pet.service.PetTypeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pet-types")
@RequiredArgsConstructor
public class PetTypeController {

    private final PetTypeService petTypeService;

    @Operation(summary = "반려동물 타입 목록 조회", description = "반려동물 타입 목록 조회")
    @GetMapping
    public ResponseEntity<ResponseTemplate<?>> getAllPetTypes() {
        List<PetTypeResponse> allPetTypes = petTypeService.getAllPetTypes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(allPetTypes));
    }

    @PostMapping
    public ResponseEntity<ResponseTemplate<?>> createPetType(
        @Valid @RequestBody PetTypeCreateRequest request
    ) {
        PetTypeResponse petType = petTypeService.createPetType(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(petType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate<?>> updatePetType(
        @PathVariable Long id,
        @Valid @RequestBody PetTypeUpdateRequest request
    ) {
        PetTypeResponse petTypeResponse = petTypeService.updatePetType(id, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(petTypeResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTemplate<?>> deletePetType(@PathVariable Long id) {
        petTypeService.deletePetType(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }
} 