CREATE TABLE market_instruments (
                                    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                    symbol     VARCHAR(50)  UNIQUE NOT NULL,
                                    name       VARCHAR(255) NOT NULL,
                                    type       VARCHAR(50)  NOT NULL,   -- STOCK, CURRENCY, FUND, BOND
                                    exchange   VARCHAR(100),
                                    source_url VARCHAR(500),
                                    active     BOOLEAN NOT NULL DEFAULT true
);