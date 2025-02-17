package com.kt.demo.user.controller;

import com.kt.demo.global.dto.ResponseTemplate;
import com.kt.demo.user.domain.User;
import com.kt.demo.user.dto.request.ChangeUserInfoRequest;
import com.kt.demo.user.dto.request.LoginRequest;
import com.kt.demo.user.dto.request.PasswordResetRequest;
import com.kt.demo.user.dto.request.UserEnrollRequest;
import com.kt.demo.user.dto.response.UserInfoResponse;
import com.kt.demo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 가입", description = "회원 가입")
    @PostMapping
    public ResponseEntity<ResponseTemplate<?>> createUser(@Valid @RequestBody UserEnrollRequest user) {
        User createdUser = userService.saveUser(user.toEntity());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseTemplate.from(createdUser));
    }

    @Operation(summary = "모든 유저 조회", description = "모든 유저 정보 조회")
    @GetMapping
    public ResponseEntity<ResponseTemplate<?>> getAllUsers() {
        List<UserInfoResponse> users = userService.getAllUsers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(users));
    }

    @Operation(summary = "내 정보 조회", description = "내 정보 조회")
    @GetMapping("/my-info")
    public ResponseEntity<ResponseTemplate<?>> getUserById(HttpSession session) {
        UserInfoResponse user = userService.getUserInfo((String) session.getAttribute("loginUser"));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(user));
    }

    @Operation(summary = "로그인", description = "로그인")
    @PostMapping("/login")
    public ResponseEntity<ResponseTemplate<?>> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        userService.login(request, session);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "로그아웃", description = "로그아웃")
    @GetMapping("/logout")
    public ResponseEntity<ResponseTemplate<?>> logout(HttpSession session) {
        session.invalidate();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "회원탈퇴", description = "회원탈퇴")
    @DeleteMapping("/withdraw")
    public ResponseEntity<ResponseTemplate<?>> withdraw(HttpSession session) {
        userService.deleteUser((String) session.getAttribute("loginUser"));
        session.invalidate();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "비밀번호 리셋", description = "비밀번호 리셋")
    @PutMapping("/reset-password")
    public ResponseEntity<ResponseTemplate<?>> resetPassword(
            HttpSession session,
            @Valid @RequestBody PasswordResetRequest request
    ) {
        userService.resetPassword((String) session.getAttribute("loginUser"), request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "회원 정보 수정", description = "회원 정보 수정")
    @PutMapping("/update")
    public ResponseEntity<ResponseTemplate<?>> updateUser(
            @Valid @RequestBody ChangeUserInfoRequest request,
            HttpSession session
    ) {
        userService.changeUserInfo((String) session.getAttribute("loginUser"), request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }
}