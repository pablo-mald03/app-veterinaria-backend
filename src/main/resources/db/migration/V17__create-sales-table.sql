-- Sales table
CREATE TABLE sales
(
    id          BIGSERIAL PRIMARY KEY,
    bill_number VARCHAR(30) UNIQUE,
    date        TIMESTAMPTZ,
    subtotal    NUMERIC(12, 2) NOT NULL DEFAULT 0,
    iva         NUMERIC(12, 2) NOT NULL DEFAULT 0,
    total       NUMERIC(12, 2) NOT NULL DEFAULT 0,
    status      VARCHAR(20)    NOT NULL DEFAULT 'COMPLETED',
    user_id     BIGINT,
    created_at  TIMESTAMPTZ    NOT NULL,
    updated_at  TIMESTAMPTZ,
    CONSTRAINT fk_sales_user FOREIGN KEY (user_id) REFERENCES users (id)
);