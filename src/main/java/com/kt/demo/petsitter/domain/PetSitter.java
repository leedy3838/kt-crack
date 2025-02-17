package com.kt.demo.petsitter.domain;

import com.kt.demo.reservation.domain.Reservation;
import com.kt.demo.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PetSitter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(name = "region")
    private String region;

    @Column(name = "available_start_time")
    private LocalTime availableStartTime;

    @Column(name = "available_end_time")
    private LocalTime availableEndTime;

    @Column(name = "price")
    private Integer price;

    @OneToMany(mappedBy = "petSitter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PetSitterPetType> petSitterPetTypes = new ArrayList<>();

    @OneToMany(mappedBy = "petSitter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PetSitterStatus status;

    @Builder
    public PetSitter(User user, String region, LocalTime availableStartTime,
                     LocalTime availableEndTime, Integer price, PetSitterStatus status) {
        this.user = user;
        this.region = region;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
        this.price = price;
        this.status = status != null ? status : PetSitterStatus.WAITING;
    }

    public void update(String region, LocalTime availableStartTime,
                       LocalTime availableEndTime, Integer price) {
        this.region = region;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
        this.price = price;
    }

    public void activate() {
        this.status = PetSitterStatus.ACTIVATED;
    }

    public void reject() {
        this.status = PetSitterStatus.REJECTED;
    }

    public void setStatus(PetSitterStatus status) {
        this.status = status;
    }

    public void withdraw() {
        this.status = PetSitterStatus.REJECTED;
    }

    public int calculatePrice(LocalDateTime startTime, LocalDateTime endTime) {
        return price * (int) startTime.until(endTime, MINUTES);
    }
}
