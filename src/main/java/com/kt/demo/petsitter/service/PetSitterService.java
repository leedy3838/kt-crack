package com.kt.demo.petsitter.service;

import com.kt.demo.pet.repository.PetTypeRepository;
import com.kt.demo.petsitter.domain.PetSitter;
import com.kt.demo.petsitter.domain.PetSitterPetType;
import com.kt.demo.petsitter.dto.request.PetSitterCreateRequest;
import com.kt.demo.petsitter.dto.response.PetSitterResponse;
import com.kt.demo.petsitter.dto.response.PetSitterStatusResponse;
import com.kt.demo.petsitter.exception.PetSitterAlreadyExistsException;
import com.kt.demo.petsitter.exception.PetSitterNotFoundException;
import com.kt.demo.petsitter.exception.errorcode.PetSitterErrorCode;
import com.kt.demo.petsitter.repository.PetSitterPetTypeRepository;
import com.kt.demo.petsitter.repository.PetSitterRepository;
import com.kt.demo.user.domain.User;
import com.kt.demo.user.exception.UserNotFoundException;
import com.kt.demo.user.exception.errorcode.UserErrorCode;
import com.kt.demo.user.repository.UserRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetSitterService {

    private final PetSitterRepository petSitterRepository;
    private final UserRepository userRepository;
    private final PetTypeRepository petTypeRepository;
    private final PetSitterPetTypeRepository petSitterPetTypeRepository;

    @Transactional
    public PetSitterResponse createProfile(String email, PetSitterCreateRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        PetSitter petSitter = request.toEntity(user);
        petSitter = petSitterRepository.save(petSitter);

        savePetTypes(petSitter, request.petTypeIds());

        return PetSitterResponse.from(petSitter);
    }

    public List<PetSitterResponse> getAllProfiles() {
        return petSitterRepository.findAll().stream()
                .map(PetSitterResponse::from)
                .toList();
    }

    public PetSitterResponse getProfile(Long petSitterId) {
        return petSitterRepository.findById(petSitterId)
                .map(PetSitterResponse::from)
                .orElseThrow(() -> new PetSitterNotFoundException(PetSitterErrorCode.PET_SITTER_NOT_FOUND));
    }

    @Transactional
    public PetSitterResponse updateProfile(String email, PetSitterCreateRequest request) {
        PetSitter petSitter = petSitterRepository.findByUserEmail(email)
                .orElseThrow(() -> new PetSitterNotFoundException(PetSitterErrorCode.PET_SITTER_NOT_FOUND));

        petSitter.update(
                request.region(),
                request.availableStartTime(),
                request.availableEndTime(),
                request.price()
        );

        petSitterPetTypeRepository.deleteAllByIdPetSitterId(petSitter.getId());
        savePetTypes(petSitter, request.petTypeIds());

        return PetSitterResponse.from(petSitter);
    }

    @Transactional
    public void apply(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        if (petSitterRepository.existsByUserEmail(email)) {
            throw new PetSitterAlreadyExistsException(PetSitterErrorCode.PET_SITTER_ALREADY_EXIST);
        }

        PetSitter petSitter = PetSitter.builder()
                .user(user)
                .isActivated(false)
                .build();

        petSitterRepository.save(petSitter);
    }

    public PetSitterStatusResponse getStatus(String email) {
        return petSitterRepository.findByUserEmail(email)
                .map(PetSitterStatusResponse::from)
                .orElse(PetSitterStatusResponse.notExists());
    }

    public boolean existsByUserEmail(String email) {
        return petSitterRepository.existsByUserEmail(email);
    }

    @Transactional
    public void withdraw(String email) {
        PetSitter petSitter = petSitterRepository.findByUserEmail(email)
                .orElseThrow(() -> new PetSitterNotFoundException(PetSitterErrorCode.PET_SITTER_NOT_FOUND));

        petSitterPetTypeRepository.deleteAllByIdPetSitterId(petSitter.getId());
        petSitterRepository.delete(petSitter);
    }

    private void savePetTypes(PetSitter petSitter, Set<Long> petTypeIds) {
        petTypeRepository.findAllById(petTypeIds)
                .forEach(petType -> petSitterPetTypeRepository.save(PetSitterPetType.builder()
                        .petSitter(petSitter)
                        .petType(petType)
                        .build()));
    }
} 