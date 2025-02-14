package com.kt.demo.reservation.controller;

import com.kt.demo.reservation.dto.request.ReservationCreateRequest;
import com.kt.demo.reservation.dto.response.ReservationResponse;
import com.kt.demo.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "펫시터의 대기중인 예약 목록 조회", description = "펫시터의 대기중인 예약 목록 조회")
    @GetMapping("/pending")
    public ResponseEntity<List<ReservationResponse>> getPendingReservations(HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        return ResponseEntity.ok(reservationService.getPendingReservations(email));
    }

    @Operation(summary = "펫시터의 모든 예약 목록 조회", description = "펫시터의 모든 예약 목록 조회")
    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations(HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        return ResponseEntity.ok(reservationService.getAllReservations(email));
    }

    @Operation(summary = "예약 수락", description = "펫시터가 예약을 수락합니다")
    @PostMapping("/{reservationId}/accept")
    public ResponseEntity<Void> acceptReservation(@PathVariable Long reservationId) {
        reservationService.acceptReservation(reservationId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "예약 거절", description = "펫시터가 예약을 거절합니다")
    @PostMapping("/{reservationId}/reject")
    public ResponseEntity<Void> rejectReservation(@PathVariable Long reservationId) {
        reservationService.rejectReservation(reservationId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "예약 요청", description = "사용자가 펫시터에게 예약을 요청합니다")
    @PostMapping("/request")
    public ResponseEntity<ReservationResponse> requestReservation(
            @RequestBody ReservationCreateRequest request,
            HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        return ResponseEntity.ok(reservationService.createReservation(email, request));
    }
} 