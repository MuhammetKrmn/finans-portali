CREATE TABLE users (
                       id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       keycloak_id VARCHAR(255) UNIQUE NOT NULL,
                       email       VARCHAR(255) UNIQUE NOT NULL,
                       full_name   VARCHAR(255),
                       role        VARCHAR(50) NOT NULL DEFAULT 'USER',
                       created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);