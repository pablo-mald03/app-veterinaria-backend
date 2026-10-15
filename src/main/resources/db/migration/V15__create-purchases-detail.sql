-- Purchases detail table
CREATE TABLE purchases_detail
(
    id                BIGSERIAL PRIMARY KEY,
    quantity          BIGINT         NOT NULL DEFAULT 0,
    unit_price        NUMERIC(12, 2) NOT NULL DEFAULT 0,
    iva               NUMERIC(12, 2) NOT NULL DEFAULT 0,
    profit_percentage INTEGER        NOT NULL DEFAULT 0,
    purchases_id      BIGINT,
    product_id        BIGINT,
    created_at        TIMESTAMPTZ    NOT NULL,
    updated_at        TIMESTAMPTZ,
    CONSTRAINT fk_purchases_detail_purchase FOREIGN KEY (purchases_id) REFERENCES purchases (id),
    CONSTRAINT fk_purchases_detail_product FOREIGN KEY (product_id) REFERENCES products (id)
);