CREATE TABLE price_history (
                               id            UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
                               instrument_id UUID    NOT NULL REFERENCES market_instruments(id),
                               price         DECIMAL(18,4) NOT NULL,
                               open_price    DECIMAL(18,4),
                               high_price    DECIMAL(18,4),
                               low_price     DECIMAL(18,4),
                               price_date    DATE    NOT NULL,
                               UNIQUE(instrument_id, price_date)   -- aynı gün iki kez kayıt yok
);

CREATE INDEX idx_price_history_instrument_date
    ON price_history(instrument_id, price_date);