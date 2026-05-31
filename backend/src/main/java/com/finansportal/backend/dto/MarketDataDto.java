package com.finansportal.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MarketDataDto(
        String symbol,
        String name,
        String type,
        BigDecimal price,
        BigDecimal change,
        LocalDateTime updatedAt
) {}