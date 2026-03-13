package com.cinematch.lucasximenes30.controller;

import com.cinematch.lucasximenes30.dto.UserCreateDto;
import com.cinematch.lucasximenes30.dto.UserResponseDto;
import com.cinematch.lucasximenes30.dto.LoginDto;
import com.cinematch.lucasximenes30.dto.LoginResponseDto;
import com.cinematch.lucasximenes30.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        UserResponseDto userResponseDto = userService.createUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        UserResponseDto userResponseDto = userService.loginUser(loginDto);
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .message("Login realizado com sucesso")
                .user(userResponseDto)
                .build();
        return ResponseEntity.ok(loginResponseDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID userId) {
        UserResponseDto userResponseDto = userService.getUserById(userId);
        return ResponseEntity.ok(userResponseDto);
    }
}

