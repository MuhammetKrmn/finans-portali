package com.finansportal.backend.repository;

import com.finansportal.backend.entity.PortfolioItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PortfolioItemRepository extends JpaRepository<PortfolioItem, UUID> {
    List<PortfolioItem> findByPortfolioId(UUID portfolioId);
}
