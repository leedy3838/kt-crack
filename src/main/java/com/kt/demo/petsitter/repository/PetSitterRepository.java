package com.kt.demo.petsitter.repository;

import com.kt.demo.petsitter.domain.PetSitter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetSitterRepository extends JpaRepository<PetSitter, Long> {
    Optional<PetSitter> findByUserEmail(String email);
    boolean existsByUserEmail(String email);
}
