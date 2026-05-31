package com.finansportal.backend.controller;

import com.finansportal.backend.dto.ApiResponse;
import com.finansportal.backend.dto.MarketDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/market")
@RequiredArgsConstructor
public class MarketController {

    @GetMapping("/rates")
    public ResponseEntity<ApiResponse<List<MarketDataDto>>> getRates() {
        // Hafta 5'te gerçek TCMB verisiyle değişecek
        var mockRates = List.of(
                new MarketDataDto("USD/TRY", "Amerikan Doları", "CURRENCY",
                        new BigDecimal("32.84"), new BigDecimal("0.42"), LocalDateTime.now()),
                new MarketDataDto("EUR/TRY", "Euro", "CURRENCY",
                        new BigDecimal("35.17"), new BigDecimal("-0.18"), LocalDateTime.now()),
                new MarketDataDto("GBP/TRY", "İngiliz Sterlini", "CURRENCY",
                        new BigDecimal("41.53"), new BigDecimal("0.31"), LocalDateTime.now()),
                new MarketDataDto("CHF/TRY", "İsviçre Frangı", "CURRENCY",
                        new BigDecimal("37.22"), new BigDecimal("-0.09"), LocalDateTime.now())
        );
        return ResponseEntity.ok(ApiResponse.ok(mockRates));
    }

    @GetMapping("/stocks")
    public ResponseEntity<ApiResponse<List<MarketDataDto>>> getStocks() {
        var mockStocks = List.of(
                new MarketDataDto("THYAO", "Türk Hava Yolları", "STOCK",
                        new BigDecimal("284.50"), new BigDecimal("2.14"), LocalDateTime.now()),
                new MarketDataDto("GARAN", "Garanti Bankası", "STOCK",
                        new BigDecimal("116.30"), new BigDecimal("1.08"), LocalDateTime.now()),
                new MarketDataDto("ASELS", "Aselsan", "STOCK",
                        new BigDecimal("97.45"), new BigDecimal("-0.72"), LocalDateTime.now()),
                new MarketDataDto("EREGL", "Ereğli Demir Çelik", "STOCK",
                        new BigDecimal("43.18"), new BigDecimal("0.88"), LocalDateTime.now())
        );
        return ResponseEntity.ok(ApiResponse.ok(mockStocks));
    }
}