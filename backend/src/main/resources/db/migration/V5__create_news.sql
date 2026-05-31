CREATE TABLE news_articles (
                               id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               title        VARCHAR(500) NOT NULL,
                               source       VARCHAR(255),
                               url          VARCHAR(1000) UNIQUE NOT NULL,
                               category     VARCHAR(100),
                               summary      TEXT,
                               image_url    VARCHAR(1000),
                               published_at TIMESTAMP,
                               fetched_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_news_category_published
    ON news_articles(category, published_at DESC);