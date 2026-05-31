package com.finansportal.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PortfolioResponse(
        UUID id,
        String name,
        String description,
        BigDecimal totalValue,
        BigDecimal totalPnl,
        BigDecimal totalPnlPercent,
        LocalDateTime createdAt
) {}