package com.finansportal.backend.service;

import com.finansportal.backend.dto.UserResponse;
import com.finansportal.backend.entity.User;
import com.finansportal.backend.exception.ResourceNotFoundException;
import com.finansportal.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    // Login olan kullanıcıyı DB'ye kaydet / güncelle
    @Transactional
    public UserResponse syncUser(Jwt jwt) {
        String keycloakId = jwt.getSubject();
        String email      = jwt.getClaimAsString("email");
        String fullName   = jwt.getClaimAsString("name");

        User user = userRepository.findByKeycloakId(keycloakId)
                .orElseGet(() -> {
                    log.info("Yeni kullanıcı oluşturuluyor: {}", email);
                    User newUser = new User();
                    newUser.setKeycloakId(keycloakId);
                    return newUser;
                });

        user.setEmail(email);
        user.setFullName(fullName);

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    public UserResponse getUserByKeycloakId(String keycloakId) {
        User user = userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Kullanıcı bulunamadı"));
        return toResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}