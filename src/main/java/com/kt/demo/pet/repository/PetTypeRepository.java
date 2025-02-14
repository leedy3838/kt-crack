package com.kt.demo.pet.repository;

import com.kt.demo.pet.domain.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetTypeRepository extends JpaRepository<PetType, Long> {
} 