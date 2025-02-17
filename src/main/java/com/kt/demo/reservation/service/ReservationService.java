package com.kt.demo.reservation.service;

import com.kt.demo.petsitter.exception.PetSitterNotFoundException;
import com.kt.demo.petsitter.exception.errorcode.PetSitterErrorCode;
import com.kt.demo.reservation.domain.Reservation;
import com.kt.demo.reservation.domain.ReservationStatus;
import com.kt.demo.reservation.dto.request.ReservationCreateRequest;
import com.kt.demo.reservation.dto.response.ReservationResponse;
import com.kt.demo.reservation.repository.ReservationRepository;
import com.kt.demo.user.domain.User;
import com.kt.demo.user.exception.UserNotFoundException;
import com.kt.demo.user.exception.errorcode.UserErrorCode;
import com.kt.demo.user.repository.UserRepository;
import com.kt.demo.petsitter.domain.PetSitter;
import com.kt.demo.petsitter.repository.PetSitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final PetSitterRepository petSitterRepository;

    public List<ReservationResponse> getPendingReservations(String email) {
        return reservationRepository.findAllByPetSitterUserEmailAndStatus(email, ReservationStatus.PENDING)
            .stream()
            .map(ReservationResponse::from)
            .toList();
    }

    public List<ReservationResponse> getAllReservations(String email) {
        return reservationRepository.findAllByPetSitterUserEmail(email)
            .stream()
            .map(ReservationResponse::from)
            .toList();
    }

    @Transactional
    public void acceptReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        reservation.accept();
    }

    @Transactional
    public void rejectReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        reservation.reject();
    }

    @Transactional
    public ReservationResponse createReservation(String email, ReservationCreateRequest request) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        PetSitter petSitter = petSitterRepository.findById(request.petSitterId())
            .orElseThrow(() -> new PetSitterNotFoundException(PetSitterErrorCode.PET_SITTER_NOT_FOUND));

        validateReservationTime(petSitter, request.startTime(), request.endTime());

        Reservation reservation = request.toEntity(user, petSitter);
        return ReservationResponse.from(reservationRepository.save(reservation));
    }

    private void validateReservationTime(PetSitter petSitter, LocalDateTime startTime, LocalDateTime endTime) {
        LocalTime startLocalTime = startTime.toLocalTime();
        LocalTime endLocalTime = endTime.toLocalTime();

        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("시작 시간이 종료 시간보다 늦을 수 없습니다.");
        }

        if (startLocalTime.isBefore(petSitter.getAvailableStartTime()) || 
            endLocalTime.isAfter(petSitter.getAvailableEndTime())) {
            throw new IllegalArgumentException("펫시터의 가능 시간대가 아닙니다.");
        }
    }

    public List<ReservationResponse> getUserReservations(String email) {
        return reservationRepository.findAllByPetSitterUserEmail(email).stream()
            .map(ReservationResponse::from)
            .toList();
    }
} 