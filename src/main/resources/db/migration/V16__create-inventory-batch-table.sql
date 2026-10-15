-- Inventory batch table
CREATE TABLE inventory_batch
(
    id                 BIGSERIAL PRIMARY KEY,
    unitary_cost       NUMERIC(14, 4) NOT NULL DEFAULT 0,
    profit_percentage  INTEGER        NOT NULL DEFAULT 0,
    available_quantity BIGINT         NOT NULL DEFAULT 0,
    original_quantity  BIGINT         NOT NULL DEFAULT 0,
    entry_date         TIMESTAMPTZ,
    status             BOOLEAN        NOT NULL DEFAULT TRUE,
    product_id         BIGINT,
    purchase_detail_id BIGINT,
    created_at         TIMESTAMPTZ    NOT NULL,
    updated_at         TIMESTAMPTZ,
    CONSTRAINT fk_inventory_batch_product FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT fk_inventory_batch_purchase_detail FOREIGN KEY (purchase_detail_id) REFERENCES purchases_detail (id)
);