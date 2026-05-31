package com.finansportal.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "market_instruments")
@Getter @Setter @NoArgsConstructor
public class MarketInstrument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String symbol;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;        // STOCK, CURRENCY, FUND, BOND

    private String exchange;

    @Column(name = "source_url")
    private String sourceUrl;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "instrument", cascade = CascadeType.ALL)
    private List<PriceHistory> priceHistories = new ArrayList<>();
}