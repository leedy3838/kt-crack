package com.kt.demo.reservation.domain;

import com.kt.demo.petsitter.domain.PetSitter;
import com.kt.demo.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petsitter_id")
    private PetSitter petSitter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @Column(name = "message")
    private String message;

    @Builder
    public Reservation(PetSitter petSitter, User user, LocalDateTime startTime, 
                      LocalDateTime endTime, String message) {
        this.petSitter = petSitter;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.message = message;
        this.status = ReservationStatus.PENDING;
    }

    public void accept() {
        this.status = ReservationStatus.ACCEPTED;
    }

    public void reject() {
        this.status = ReservationStatus.REJECTED;
    }

    public void complete() {
        this.status = ReservationStatus.COMPLETED;
    }
} 