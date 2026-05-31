package com.finansportal.backend.service;

import com.finansportal.backend.dto.PortfolioRequest;
import com.finansportal.backend.dto.PortfolioResponse;
import com.finansportal.backend.entity.Portfolio;
import com.finansportal.backend.entity.User;
import com.finansportal.backend.exception.ResourceNotFoundException;
import com.finansportal.backend.repository.PortfolioRepository;
import com.finansportal.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final UserRepository      userRepository;

    // ── Kullanıcının portföylerini getir ─────────────────────────
    public List<PortfolioResponse> getPortfoliosByKeycloakId(String keycloakId) {
        return portfolioRepository
                .findByUserKeycloakId(keycloakId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ── Yeni portföy oluştur ──────────────────────────────────────
    @Transactional
    public PortfolioResponse createPortfolio(PortfolioRequest request, String keycloakId) {
        User user = userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Kullanıcı bulunamadı. Önce /api/users/me çağrın."
                        ));

        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolio.setName(request.name());
        portfolio.setDescription(request.description());

        Portfolio saved = portfolioRepository.save(portfolio);
        log.info("Yeni portföy oluşturuldu: {} — kullanıcı: {}", saved.getName(), keycloakId);
        return toResponse(saved);
    }

    // ── Portföy sil ───────────────────────────────────────────────
    @Transactional
    public void deletePortfolio(java.util.UUID portfolioId, String keycloakId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Portföy bulunamadı: " + portfolioId));

        if (!portfolio.getUser().getKeycloakId().equals(keycloakId)) {
            throw new SecurityException("Bu portföye erişim yetkiniz yok");
        }
        portfolioRepository.delete(portfolio);
        log.info("Portföy silindi: {}", portfolioId);
    }

    // ── Entity → Record dönüşümü ──────────────────────────────────
    private PortfolioResponse toResponse(Portfolio portfolio) {
        return new PortfolioResponse(
                portfolio.getId(),
                portfolio.getName(),
                portfolio.getDescription(),
                BigDecimal.ZERO,     // totalValue  → hafta 8'de hesaplanacak
                BigDecimal.ZERO,     // totalPnl    → hafta 8'de hesaplanacak
                BigDecimal.ZERO,     // totalPnlPct → hafta 8'de hesaplanacak
                portfolio.getCreatedAt()
        );
    }
}