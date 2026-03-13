package com.cinematch.lucasximenes30.service;

import com.cinematch.lucasximenes30.dto.UserCreateDto;
import com.cinematch.lucasximenes30.dto.UserResponseDto;
import com.cinematch.lucasximenes30.dto.LoginDto;
import com.cinematch.lucasximenes30.exception.BusinessException;
import com.cinematch.lucasximenes30.exception.ResourceNotFoundException;
import com.cinematch.lucasximenes30.model.User;
import com.cinematch.lucasximenes30.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto createUser(UserCreateDto userCreateDto) {
        if (userRepository.existsByEmail(userCreateDto.getEmail())) {
            throw new BusinessException("Email já cadastrado no sistema");
        }

        User user = User.builder()
                .name(userCreateDto.getName())
                .email(userCreateDto.getEmail())
                .password(userCreateDto.getPassword())
                .build();

        User savedUser = userRepository.save(user);
        return convertToResponseDto(savedUser);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + userId));
        return convertToResponseDto(user);
    }

    public UserResponseDto loginUser(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado: " + loginDto.getEmail()));

        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new BusinessException("Senha incorreta");
        }

        return convertToResponseDto(user);
    }

    public User getUserByIdOrThrow(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + userId));
    }

    private UserResponseDto convertToResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}

