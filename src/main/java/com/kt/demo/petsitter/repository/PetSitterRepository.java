package com.kt.demo.petsitter.repository;

import com.kt.demo.petsitter.domain.PetSitter;
import com.kt.demo.petsitter.domain.PetSitterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetSitterRepository extends JpaRepository<PetSitter, Long> {
    Optional<PetSitter> findByUserEmail(String email);
    boolean existsByUserEmail(String email);
    List<PetSitter> findAllByStatus(PetSitterStatus status);
}
