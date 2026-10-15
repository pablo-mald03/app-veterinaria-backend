-- Products table
CREATE TABLE products
(
    id                BIGSERIAL PRIMARY KEY,
    sku               VARCHAR(12) UNIQUE,
    name              VARCHAR(120),
    description       VARCHAR(255),
    brand             VARCHAR(120),
    minimum_stock     BIGINT         NOT NULL DEFAULT 0,
    profit_percentage INTEGER        NOT NULL DEFAULT 0 CHECK (profit_percentage BETWEEN 0 AND 100),
    price             NUMERIC(12, 2) NOT NULL DEFAULT 0,
    provider          VARCHAR(150),
    status            BOOLEAN        NOT NULL DEFAULT TRUE,
    category_id       BIGINT,
    created_at        TIMESTAMPTZ    NOT NULL,
    updated_at        TIMESTAMPTZ,
    CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories (id)
);