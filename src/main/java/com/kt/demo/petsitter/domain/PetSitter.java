package com.kt.demo.petsitter.domain;

import com.kt.demo.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "is_activated")
    private Boolean isActivated;

    @Column(name = "region")
    private String region;
    
    @Column(name = "available_start_time")
    private LocalTime availableStartTime;
    
    @Column(name = "available_end_time")
    private LocalTime availableEndTime;
    
    @Column(name = "price")
    private Integer price;

    @OneToMany(mappedBy = "id.petSitter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PetSitterPetType> petSitterPetTypes = new ArrayList<>();

    @Builder
    public PetSitter(User user, String region, LocalTime availableStartTime, 
                    LocalTime availableEndTime, Integer price, Boolean isActivated) {
        this.user = user;
        this.region = region;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
        this.price = price;
        this.isActivated = isActivated != null ? isActivated : true;
    }

    public void update(String region, LocalTime availableStartTime, 
                      LocalTime availableEndTime, Integer price) {
        this.region = region;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
        this.price = price;
    }
}
