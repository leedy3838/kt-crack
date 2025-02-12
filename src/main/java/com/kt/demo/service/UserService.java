package com.kt.demo.service;

import com.kt.demo.domain.User;
import com.kt.demo.dto.request.ChangeUserInfoRequest;
import com.kt.demo.dto.request.LoginRequest;
import com.kt.demo.dto.response.UserInfoResponse;
import com.kt.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<UserInfoResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserInfoResponse::fromEntity)
                .toList();
    }

    public UserInfoResponse getUserInfo(String email) {
        return userRepository.findByEmail(email)
                .map(UserInfoResponse::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    @Transactional
    public void deleteUser(String email) {
        userRepository.findByEmail(email)
                .ifPresentOrElse(
                        userRepository::delete,
                        () -> {
                            throw new IllegalArgumentException("삭제할 사용자가 존재하지 않습니다.");
                        });
    }

    public void login(LoginRequest request, HttpSession session) {
        userRepository.findByEmailAndPassword(request.email(), request.password())
                .orElseThrow(() -> new IllegalArgumentException("로그인 정보가 올바르지 않습니다."));

        session.setAttribute("loginUser", request.email());
    }

    @Transactional
    public String resetPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        user.changePassword(String.valueOf(UUID.randomUUID()));

        return user.getPassword();
    }

    @Transactional
    public void changeUserInfo(String email, ChangeUserInfoRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        user.changeUserInfo(request);
    }
}
