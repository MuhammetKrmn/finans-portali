package com.finansportal.backend.repository;

import com.finansportal.backend.entity.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PriceHistoryRepository
        extends JpaRepository<PriceHistory, UUID> {
    List<PriceHistory> findByInstrumentIdAndPriceDateBetweenOrderByPriceDateAsc(
            UUID instrumentId, LocalDate start,LocalDate end
            );
    boolean existsByInstrumentIdAndPriceDate(UUID instrumentId, java.time.LocalDate priceDate);
}
