package com.kt.demo.petsitter.repository;

import com.kt.demo.petsitter.domain.PetSitterPetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetSitterPetTypeRepository extends JpaRepository<PetSitterPetType, Long> {
    @Modifying
    @Query("delete from PetSitterPetType p where p.petSitter.id = :petSitterId")
    void deleteByPetSitterId(Long petSitterId);
} 