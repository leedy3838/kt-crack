package com.kt.demo.user.service;

import com.kt.demo.user.domain.User;
import com.kt.demo.user.dto.request.ChangeUserInfoRequest;
import com.kt.demo.user.dto.request.LoginRequest;
import com.kt.demo.user.dto.request.PasswordResetRequest;
import com.kt.demo.user.dto.response.UserInfoResponse;
import com.kt.demo.user.exception.UserNotFoundException;
import com.kt.demo.user.exception.errorcode.UserErrorCode;
import com.kt.demo.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import java.util.List;
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
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public void deleteUser(String email) {
        userRepository.findByEmail(email)
                .ifPresentOrElse(
                        userRepository::delete,
                        () -> {
                            throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
                        });
    }

    public void login(LoginRequest request, HttpSession session) {
        userRepository.findByEmailAndPassword(request.email(), request.password())
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        session.setAttribute("loginUser", request.email());
    }

    @Transactional
    public void resetPassword(String email, PasswordResetRequest request) {
        userRepository.findByEmail(email)
                .ifPresentOrElse(
                        user -> user.changePassword(request.password()),
                        () -> {
                            throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
                        });
    }

    @Transactional
    public void changeUserInfo(String email, ChangeUserInfoRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        user.changeUserInfo(request);
    }
}
