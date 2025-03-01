package com.kt.demo.pet.repository.init;

import com.kt.demo.global.util.DummyDataInit;
import com.kt.demo.pet.domain.PetType;
import com.kt.demo.pet.repository.PetTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@DummyDataInit
@Component
public class PetTypeInitializer implements ApplicationRunner {

    private final PetTypeRepository petTypeRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (petTypeRepository.count() > 0) {
            log.info("[PetType]더미 데이터 존재");
            return;
        }

        List<PetType> petTypes = List.of(
                PetType.builder()
                        .name("강아지")
                        .code("DOG")
                        .build(),
                PetType.builder()
                        .name("고양이")
                        .code("CAT")
                        .build(),
                PetType.builder()
                        .name("햄스터")
                        .code("HAMSTER")
                        .build(),
                PetType.builder()
                        .name("토끼")
                        .code("RABBIT")
                        .build(),
                PetType.builder()
                        .name("앵무새")
                        .code("PARROT")
                        .build()
        );

        petTypeRepository.saveAll(petTypes);
        log.info("[PetType]더미 데이터 생성 완료");
    }
} 