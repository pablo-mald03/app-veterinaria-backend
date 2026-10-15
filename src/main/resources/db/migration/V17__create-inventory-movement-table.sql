-- Inventory movement table
CREATE TABLE inventory_movement
(
    id          BIGSERIAL PRIMARY KEY,
    type        VARCHAR(10)    NOT NULL,
    quantity    BIGINT         NOT NULL DEFAULT 0,
    unit_cost   NUMERIC(14, 4) NOT NULL DEFAULT 0,
    date        TIMESTAMPTZ,
    origin      VARCHAR(255),
    product_id  BIGINT,
    batch_id    BIGINT,
    -- sale_id     BIGINT,
    purchase_id BIGINT,
    created_at  TIMESTAMPTZ    NOT NULL,
    updated_at  TIMESTAMPTZ,
    CONSTRAINT fk_inventory_movement_product FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT fk_inventory_movement_batch FOREIGN KEY (batch_id) REFERENCES inventory_batch (id),
    -- CONSTRAINT fk_inventory_movement_sale FOREIGN KEY (sale_id) REFERENCES sales (id),
    CONSTRAINT fk_inventory_movement_purchase FOREIGN KEY (purchase_id) REFERENCES purchases (id)
);