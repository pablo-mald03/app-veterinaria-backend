-- Sales detail table
CREATE TABLE sales_detail
(
    id         BIGSERIAL PRIMARY KEY,
    quantity   BIGINT         NOT NULL DEFAULT 0,
    unit_price NUMERIC(12, 2) NOT NULL DEFAULT 0,
    subtotal   NUMERIC(12, 2) NOT NULL DEFAULT 0,
    sale_id    BIGINT,
    product_id BIGINT,
    created_at TIMESTAMPTZ    NOT NULL,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_sales_detail_sale FOREIGN KEY (sale_id) REFERENCES sales (id),
    CONSTRAINT fk_sales_detail_product FOREIGN KEY (product_id) REFERENCES products (id)
);