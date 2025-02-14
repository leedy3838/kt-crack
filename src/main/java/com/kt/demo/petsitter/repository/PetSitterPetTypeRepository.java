package com.kt.demo.petsitter.repository;

import com.kt.demo.petsitter.domain.PetSitterPetType;
import com.kt.demo.petsitter.domain.PetSitterPetTypeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetSitterPetTypeRepository extends JpaRepository<PetSitterPetType, PetSitterPetTypeId> {
    void deleteAllByIdPetSitterId(Long petSitterId);
} 