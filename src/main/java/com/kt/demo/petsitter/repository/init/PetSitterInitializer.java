package com.kt.demo.petsitter.repository.init;

import com.kt.demo.global.util.DummyDataInit;
import com.kt.demo.pet.domain.PetType;
import com.kt.demo.pet.repository.PetTypeRepository;
import com.kt.demo.petsitter.domain.PetSitter;
import com.kt.demo.petsitter.domain.PetSitterPetType;
import com.kt.demo.petsitter.domain.PetSitterStatus;
import com.kt.demo.petsitter.repository.PetSitterPetTypeRepository;
import com.kt.demo.petsitter.repository.PetSitterRepository;
import com.kt.demo.user.domain.User;
import com.kt.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.time.LocalTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@DummyDataInit
public class PetSitterInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PetSitterRepository petSitterRepository;
    private final PetTypeRepository petTypeRepository;
    private final PetSitterPetTypeRepository petSitterPetTypeRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (petSitterRepository.count() > 0) {
            log.info("[PetSitter]더미 데이터 존재");
            return;
        }

        // 펫시터 생성
        User user1 = userRepository.findById(1L).orElseThrow();
        User user2 = userRepository.findById(2L).orElseThrow();
        User user3 = userRepository.findById(3L).orElseThrow();

        PetSitter petSitter1 = PetSitter.builder()
                .user(user1)
                .price(10000)
                .region("서울")
                .availableStartTime(LocalTime.of(10, 0))
                .availableEndTime(LocalTime.of(20, 0))
                .status(PetSitterStatus.ACTIVATED)
                .build();

        PetSitter petSitter2 = PetSitter.builder()
                .user(user2)
                .price(20000)
                .region("경기")
                .availableStartTime(LocalTime.of(9, 0))
                .availableEndTime(LocalTime.of(19, 0))
                .status(PetSitterStatus.WAITING)
                .build();

        PetSitter petSitter3 = PetSitter.builder()
                .user(user3)
                .price(30000)
                .region("인천")
                .availableStartTime(LocalTime.of(8, 0))
                .availableEndTime(LocalTime.of(18, 0))
                .status(PetSitterStatus.ACTIVATED)
                .build();

        List<PetSitter> petSitters = List.of(petSitter1, petSitter2, petSitter3);
        petSitterRepository.saveAll(petSitters);

        // 펫 타입 연결
        List<PetType> petTypes = petTypeRepository.findAll();
        
        // 펫시터1: 강아지, 고양이
        savePetSitterPetType(petSitter1, petTypes.subList(0, 2));
        // 펫시터2: 고양이, 햄스터, 토끼
        savePetSitterPetType(petSitter2, petTypes.subList(1, 4));
        // 펫시터3: 모든 동물
        savePetSitterPetType(petSitter3, petTypes);

        log.info("[PetSitter]더미 데이터 생성 완료");
    }

    private void savePetSitterPetType(PetSitter petSitter, List<PetType> petTypes) {
        petTypes.forEach(petType -> 
            petSitterPetTypeRepository.save(PetSitterPetType.builder()
                .petSitter(petSitter)
                .petType(petType)
                .build())
        );
    }
}
