package com.kt.demo.pet.service;

import com.kt.demo.pet.dto.response.PetTypeResponse;
import com.kt.demo.pet.repository.PetTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetTypeService {

    private final PetTypeRepository petTypeRepository;

    public List<PetTypeResponse> getAllPetTypes() {
        return petTypeRepository.findAll().stream()
            .map(PetTypeResponse::from)
            .toList();
    }
} 