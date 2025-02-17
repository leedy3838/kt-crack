package com.kt.demo.petsitter.controller;

import com.kt.demo.global.dto.ResponseTemplate;
import com.kt.demo.petsitter.dto.request.PetSitterCreateRequest;
import com.kt.demo.petsitter.dto.response.PetSitterResponse;
import com.kt.demo.petsitter.dto.response.PetSitterStatusResponse;
import com.kt.demo.petsitter.service.PetSitterService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseTemplate<?>> createProfile(
            @Valid @RequestBody PetSitterCreateRequest request,
            HttpSession session
    ) {
        String email = (String) session.getAttribute("loginUser");
        PetSitterResponse profile = petSitterService.createProfile(email, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseTemplate.from(profile));
    }

    @Operation(summary = "펫시터 프로필 목록 조회", description = "펫시터 프로필 목록 조회")
    @GetMapping
    public ResponseEntity<ResponseTemplate<?>> getAllProfiles() {
        List<PetSitterResponse> profiles = petSitterService.getAllProfiles();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(profiles));
    }

    @Operation(summary = "펫시터 프로필 상세 조회", description = "펫시터 프로필 상세 조회")
    @GetMapping("/profile")
    public ResponseEntity<ResponseTemplate<?>> getProfile(
            HttpSession session, @RequestParam(required = false) Long petSitterId
    ) {
        String email = (String) session.getAttribute("loginUser");
        PetSitterResponse profile =
                petSitterId == null ? petSitterService.getProfile(email) : petSitterService.getProfile(petSitterId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(profile));
    }

    @Operation(summary = "펫시터 프로필 수정", description = "펫시터 프로필 수정")
    @PutMapping("/profile")
    public ResponseEntity<ResponseTemplate<?>> updateProfile(
            @Valid @RequestBody PetSitterCreateRequest request,
            HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        PetSitterResponse profile = petSitterService.updateProfile(email, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(profile));
    }

    @Operation(summary = "펫시터 신청", description = "펫시터 신청")
    @PostMapping("/apply")
    public ResponseEntity<ResponseTemplate<?>> apply(HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        petSitterService.apply(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "펫시터 상태 확인", description = "펫시터 상태 확인")
    @GetMapping("/status")
    public ResponseEntity<ResponseTemplate<?>> getStatus(HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        PetSitterStatusResponse status = petSitterService.getStatus(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(status));
    }

    @Operation(summary = "펫시터 프로필 존재 여부 확인", description = "펫시터 프로필 존재 여부 확인")
    @GetMapping("/profile/exists")
    public ResponseEntity<ResponseTemplate<?>> checkProfileExists(HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        boolean exists = petSitterService.existsByUserEmail(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(exists));
    }

    @Operation(summary = "펫시터 탈퇴", description = "펫시터 탈퇴")
    @DeleteMapping
    public ResponseEntity<ResponseTemplate<?>> withdraw(HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        petSitterService.withdraw(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }
} 