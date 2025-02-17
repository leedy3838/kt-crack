package com.kt.demo.user.repository.init;

import com.kt.demo.global.util.DummyDataInit;
import com.kt.demo.user.domain.User;
import com.kt.demo.user.domain.type.Role;
import com.kt.demo.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@DummyDataInit
public class UserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) {
            log.info("[User]더미 데이터 존재");
        } else {
            List<User> userList = new ArrayList<>();

            User user1 = User.builder()
                    .email("user1@email.com")
                    .password("pwpw1234")
                    .name("user1")
                    .phoneNumber("010-1234-5678")
                    .role(Role.ADMIN)
                    .build();
            User user2 = User.builder()
                    .email("user2@email.com")
                    .password("pwpw1234")
                    .name("user2")
                    .phoneNumber("010-1234-5678")
                    .role(Role.USER)
                    .build();
            User user3 = User.builder()
                    .email("user3@email.com")
                    .password("pwpw1234")
                    .name("user3")
                    .phoneNumber("010-1234-5678")
                    .role(Role.USER)
                    .build();

            userList.add(user1);
            userList.add(user2);
            userList.add(user3);

            userRepository.saveAll(userList);
        }
    }
}
