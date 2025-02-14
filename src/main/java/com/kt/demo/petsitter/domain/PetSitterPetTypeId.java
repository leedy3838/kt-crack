package com.kt.demo.petsitter.domain;

import com.kt.demo.pet.domain.PetType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PetSitterPetTypeId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petsitter_id", insertable = false, updatable = false)
    private PetSitter petSitter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_type_id", insertable = false, updatable = false)
    private PetType petType;

    @Builder
    public PetSitterPetTypeId(PetSitter petSitter, PetType petType) {
        this.petSitter = petSitter;
        this.petType = petType;
    }
} 