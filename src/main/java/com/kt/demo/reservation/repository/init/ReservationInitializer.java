package com.kt.demo.reservation.repository.init;

import com.kt.demo.global.util.DummyDataInit;
import com.kt.demo.petsitter.domain.PetSitter;
import com.kt.demo.petsitter.repository.PetSitterRepository;
import com.kt.demo.reservation.domain.Reservation;
import com.kt.demo.reservation.repository.ReservationRepository;
import com.kt.demo.user.domain.User;
import com.kt.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Order(3)
@DummyDataInit
public class ReservationInitializer implements ApplicationRunner {

    private final ReservationRepository reservationRepository;
    private final PetSitterRepository petSitterRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (reservationRepository.count() > 0) {
            log.info("[Reservation]더미 데이터 존재");
            return;
        }

        List<PetSitter> petSitters = petSitterRepository.findAll();
        List<User> users = userRepository.findAll();
        
        List<Reservation> reservations = new ArrayList<>();
        
        // 첫 번째 펫시터의 예약들
        reservations.add(Reservation.builder()
            .petSitter(petSitters.get(2))
            .user(users.get(0))
            .startTime(LocalDateTime.now().plusDays(1))
            .endTime(LocalDateTime.now().plusDays(1).plusHours(2))
            .message("강아지 돌봄 부탁드립니다")
            .build());

        reservations.add(Reservation.builder()
            .petSitter(petSitters.get(0))
            .user(users.get(1))
            .startTime(LocalDateTime.now().plusDays(2))
            .endTime(LocalDateTime.now().plusDays(2).plusHours(3))
            .message("고양이 돌봄 부탁드립니다")
            .build());

        // 두 번째 펫시터의 예약들
        reservations.add(Reservation.builder()
            .petSitter(petSitters.get(1))
            .user(users.get(2))
            .startTime(LocalDateTime.now().plusDays(1))
            .endTime(LocalDateTime.now().plusDays(1).plusHours(4))
            .message("토끼 돌봄 부탁드립니다")
            .build());

        reservationRepository.saveAll(reservations);
        log.info("[Reservation]더미 데이터 생성 완료");
    }
} 