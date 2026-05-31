package com.finansportal.backend.repository;

import com.finansportal.backend.entity.MarketInstrument;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MarketInstrumentRepository extends
        JpaRepository<MarketInstrument, UUID> {
    Optional<MarketInstrumentRepository> findBySymbol(String symbol);
    List<MarketInstrumentRepository> findByType(String type);
    List<MarketInstrumentRepository> findByActiveTrue();
}
