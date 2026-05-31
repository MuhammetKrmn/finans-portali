package com.finansportal.backend.repository;

import com.finansportal.backend.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {
    List<Portfolio> findByUserId(UUID userİd);

    List<Portfolio> findByUserKeycloakId(String keycloakId);

}
