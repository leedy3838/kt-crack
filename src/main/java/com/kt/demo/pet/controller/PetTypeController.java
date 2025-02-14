package com.kt.demo.pet.controller;

import com.kt.demo.pet.dto.response.PetTypeResponse;
import com.kt.demo.pet.service.PetTypeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pet-types")
@RequiredArgsConstructor
public class PetTypeController {

    private final PetTypeService petTypeService;

    @Operation(summary = "반려동물 타입 목록 조회", description = "반려동물 타입 목록 조회")
    @GetMapping
    public ResponseEntity<List<PetTypeResponse>> getAllPetTypes() {
        return ResponseEntity.ok(petTypeService.getAllPetTypes());
    }
} 