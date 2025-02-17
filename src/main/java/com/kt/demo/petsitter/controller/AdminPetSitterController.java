package com.kt.demo.petsitter.controller;

import com.kt.demo.global.dto.ResponseTemplate;
import com.kt.demo.petsitter.dto.response.PetSitterStatusResponse;
import com.kt.demo.petsitter.service.PetSitterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/pet-sitters")
@RequiredArgsConstructor
public class AdminPetSitterController {

    private final PetSitterService petSitterService;

    @Operation(summary = "펫시터 신청 목록 조회", description = "승인 대기 중인 펫시터 신청 목록을 조회합니다")
    @GetMapping("/pending")
    public ResponseEntity<ResponseTemplate<?>> getPendingPetSitters() {
        List<PetSitterStatusResponse> pendingPetSitters = petSitterService.getPendingPetSitters();
        
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(pendingPetSitters));
    }

    @Operation(summary = "펫시터 신청 승인", description = "펫시터 신청을 승인합니다")
    @PostMapping("/{petSitterId}/approve")
    public ResponseEntity<ResponseTemplate<?>> approvePetSitter(@PathVariable Long petSitterId) {
        petSitterService.approvePetSitter(petSitterId);
        
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "펫시터 신청 거절", description = "펫시터 신청을 거절합니다")
    @PostMapping("/{petSitterId}/reject")
    public ResponseEntity<ResponseTemplate<?>> rejectPetSitter(@PathVariable Long petSitterId) {
        petSitterService.rejectPetSitter(petSitterId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }
} 