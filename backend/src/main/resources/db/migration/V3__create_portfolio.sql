CREATE TABLE portfolios (
                            id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            user_id     UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                            name        VARCHAR(255) NOT NULL,
                            description TEXT,
                            created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE portfolio_items (
                                 id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                 portfolio_id  UUID NOT NULL REFERENCES portfolios(id) ON DELETE CASCADE,
                                 instrument_id UUID NOT NULL REFERENCES market_instruments(id),
                                 quantity      DECIMAL(18,8) NOT NULL,
                                 buy_price     DECIMAL(18,4) NOT NULL,
                                 buy_date      DATE NOT NULL
);