-- Purchases table
CREATE TABLE purchases
(
    id          BIGSERIAL PRIMARY KEY,
    description VARCHAR(255),
    date        TIMESTAMPTZ,
    total       NUMERIC(12, 2) NOT NULL DEFAULT 0,
    subtotal    NUMERIC(12, 2) NOT NULL DEFAULT 0,
    status      VARCHAR(20)    NOT NULL DEFAULT 'COMPLETED',
    user_id     BIGINT,
    created_at  TIMESTAMPTZ    NOT NULL,
    updated_at  TIMESTAMPTZ,
    CONSTRAINT fk_purchases_user FOREIGN KEY (user_id) REFERENCES users (id)
);