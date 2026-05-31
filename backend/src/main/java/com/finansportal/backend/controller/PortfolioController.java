package com.finansportal.backend.controller;

import com.finansportal.backend.dto.ApiResponse;
import com.finansportal.backend.dto.PortfolioRequest;
import com.finansportal.backend.dto.PortfolioResponse;
import com.finansportal.backend.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;

    // GET /api/portfolio → giriş yapan kullanıcının portföyleri
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<PortfolioResponse>>> getMyPortfolios(
            @AuthenticationPrincipal Jwt jwt) {
        String keycloakId = jwt.getSubject();
        return ResponseEntity.ok(
                ApiResponse.ok(portfolioService.getPortfoliosByKeycloakId(keycloakId))
        );
    }

    // POST /api/portfolio → yeni portföy oluştur
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PortfolioResponse>> createPortfolio(
            @RequestBody PortfolioRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        String keycloakId = jwt.getSubject();
        return ResponseEntity.ok(
                ApiResponse.ok(portfolioService.createPortfolio(request, keycloakId))
        );
    }

    // DELETE /api/portfolio/{id} → portföy sil
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deletePortfolio(
            @PathVariable UUID id,
            @AuthenticationPrincipal Jwt jwt) {
        String keycloakId = jwt.getSubject();
        portfolioService.deletePortfolio(id, keycloakId);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}