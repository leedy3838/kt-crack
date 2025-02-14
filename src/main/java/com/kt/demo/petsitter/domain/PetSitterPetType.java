package com.kt.demo.petsitter.domain;

import com.kt.demo.pet.domain.PetType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EmbeddedId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "petsitter_pet_types")
public class PetSitterPetType {

    @EmbeddedId
    private PetSitterPetTypeId id;

    @Builder
    public PetSitterPetType(PetSitter petSitter, PetType petType) {
        this.id = new PetSitterPetTypeId(petSitter, petType);
    }
} 