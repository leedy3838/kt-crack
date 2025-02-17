package com.kt.demo.pet.domain;

import com.kt.demo.petsitter.domain.PetSitterPetType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "pet_types")
public class PetType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String code;

    @OneToMany(mappedBy = "petType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PetSitterPetType> petSitterPetTypes;

    @Builder
    public PetType(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public void update(String name, String code) {
        this.name = name;
        this.code = code;
    }
} 