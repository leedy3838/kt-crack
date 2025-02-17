package com.kt.demo.pet.service;

import com.kt.demo.pet.domain.PetType;
import com.kt.demo.pet.dto.request.PetTypeCreateRequest;
import com.kt.demo.pet.dto.request.PetTypeUpdateRequest;
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

    @Transactional
    public PetTypeResponse createPetType(PetTypeCreateRequest request) {
        PetType petType = PetType.builder()
            .name(request.name())
            .code(request.code())
            .build();
        
        return PetTypeResponse.from(petTypeRepository.save(petType));
    }

    @Transactional
    public PetTypeResponse updatePetType(Long id, PetTypeUpdateRequest request) {
        PetType petType = petTypeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 반려동물 타입이 존재하지 않습니다."));

        petType.update(request.name(), request.code());
        return PetTypeResponse.from(petType);
    }

    @Transactional
    public void deletePetType(Long id) {
        petTypeRepository.deleteById(id);
    }
} 