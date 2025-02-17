package com.kt.demo.reservation.controller;

import com.kt.demo.global.dto.ResponseTemplate;
import com.kt.demo.reservation.dto.request.ReservationCreateRequest;
import com.kt.demo.reservation.dto.response.ReservationResponse;
import com.kt.demo.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseTemplate<?>> getPendingReservations(HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        List<ReservationResponse> reservations = reservationService.getPendingReservations(email);
        
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(reservations));
    }

    @Operation(summary = "펫시터의 모든 예약 목록 조회", description = "펫시터의 모든 예약 목록 조회")
    @GetMapping
    public ResponseEntity<ResponseTemplate<?>> getAllReservations(HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        List<ReservationResponse> reservations = reservationService.getAllReservations(email);
        
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(reservations));
    }

    @Operation(summary = "예약 수락", description = "펫시터가 예약을 수락합니다")
    @PostMapping("/{reservationId}/accept")
    public ResponseEntity<ResponseTemplate<?>> acceptReservation(@PathVariable Long reservationId) {
        reservationService.acceptReservation(reservationId);
        
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "예약 거절", description = "펫시터가 예약을 거절합니다")
    @PostMapping("/{reservationId}/reject")
    public ResponseEntity<ResponseTemplate<?>> rejectReservation(@PathVariable Long reservationId) {
        reservationService.rejectReservation(reservationId);
        
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "예약 요청", description = "사용자가 펫시터에게 예약을 요청합니다")
    @PostMapping("/request")
    public ResponseEntity<ResponseTemplate<?>> requestReservation(
            @Valid @RequestBody ReservationCreateRequest request,
            HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        ReservationResponse reservation = reservationService.createReservation(email, request);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseTemplate.from(reservation));
    }

    @Operation(summary = "사용자가 예약한 펫시터 예약 목록 조회", description = "사용자가 예약한 펫시터 예약 목록을 조회합니다")
    @GetMapping("/my-reservations")
    public ResponseEntity<ResponseTemplate<?>> getUserReservations(HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        List<ReservationResponse> reservations = reservationService.getUserReservations(email);
        
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(reservations));
    }
} 