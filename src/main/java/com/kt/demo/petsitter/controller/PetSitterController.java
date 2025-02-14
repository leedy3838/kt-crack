package com.kt.demo.petsitter.controller;

import com.kt.demo.petsitter.dto.request.PetSitterCreateRequest;
import com.kt.demo.petsitter.dto.response.PetSitterResponse;
import com.kt.demo.petsitter.dto.response.PetSitterStatusResponse;
import com.kt.demo.petsitter.service.PetSitterService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pet-sitters")
@RequiredArgsConstructor
public class PetSitterController {

    private final PetSitterService petSitterService;

    @Operation(summary = "펫시터 프로필 등록", description = "펫시터 프로필 등록")
    @PostMapping("/profile")
    public ResponseEntity<PetSitterResponse> createProfile(
            @RequestBody PetSitterCreateRequest request,
            HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        return ResponseEntity.ok(petSitterService.createProfile(email, request));
    }

    @Operation(summary = "펫시터 프로필 목록 조회", description = "펫시터 프로필 목록 조회")
    @GetMapping
    public ResponseEntity<List<PetSitterResponse>> getAllProfiles() {
        return ResponseEntity.ok(petSitterService.getAllProfiles());
    }

    @Operation(summary = "펫시터 프로필 상세 조회", description = "펫시터 프로필 상세 조회")
    @GetMapping("/profile")
    public ResponseEntity<PetSitterResponse> getProfile(
            HttpSession session, @RequestParam(required = false) Long petSitterId
    ) {

        if (petSitterId == null) {
            String email = (String) session.getAttribute("loginUser");
            return ResponseEntity.ok(petSitterService.getProfile(email));
        }

        return ResponseEntity.ok(petSitterService.getProfile(petSitterId));
    }

    @Operation(summary = "펫시터 프로필 수정", description = "펫시터 프로필 수정")
    @PutMapping("/profile")
    public ResponseEntity<PetSitterResponse> updateProfile(
            @RequestBody PetSitterCreateRequest request,
            HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        return ResponseEntity.ok(petSitterService.updateProfile(email, request));
    }

    @Operation(summary = "펫시터 신청", description = "펫시터 신청")
    @PostMapping("/apply")
    public ResponseEntity<Void> apply(HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        petSitterService.apply(email);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "펫시터 상태 확인", description = "펫시터 상태 확인")
    @GetMapping("/status")
    public ResponseEntity<PetSitterStatusResponse> getStatus(HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        return ResponseEntity.ok(petSitterService.getStatus(email));
    }

    @Operation(summary = "펫시터 프로필 존재 여부 확인", description = "펫시터 프로필 존재 여부 확인")
    @GetMapping("/profile/exists")
    public ResponseEntity<Boolean> checkProfileExists(HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        return ResponseEntity.ok(petSitterService.existsByUserEmail(email));
    }

    @Operation(summary = "펫시터 탈퇴", description = "펫시터 탈퇴")
    @DeleteMapping
    public ResponseEntity<Void> withdraw(HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        petSitterService.withdraw(email);
        return ResponseEntity.ok().build();
    }
} 