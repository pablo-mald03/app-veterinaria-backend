-- Product discounts

ALTER TABLE products
    ADD COLUMN discount_percentage INTEGER,
    ADD COLUMN expiration_date TIMESTAMP;

UPDATE products
SET discount_percentage = 0
WHERE discount_percentage IS NULL;

UPDATE products
SET expiration_date = NOW() + INTERVAL '1 year'
WHERE expiration_date IS NULL;

ALTER TABLE products
    ALTER COLUMN discount_percentage SET DEFAULT 0,
ALTER COLUMN discount_percentage SET NOT NULL,
    ALTER COLUMN expiration_date SET NOT NULL,
    ADD CONSTRAINT chk_products_discount_percentage
        CHECK (discount_percentage BETWEEN 0 AND 100);