-- ============================================================
-- V21: reutilizar tablas de vacunas para tratamientos
-- ============================================================

-- 1) Catálogo: vaccines -> treatments
ALTER TABLE vaccines RENAME TO treatments;
ALTER TABLE treatments RENAME COLUMN id_vaccine TO id_treatment;
ALTER TABLE treatments DROP COLUMN species;          -- también elimina idx_vaccines_species
ALTER TABLE treatments DROP COLUMN doses_required;   -- también elimina chk_vaccines_doses_required
ALTER TABLE treatments ALTER COLUMN description DROP NOT NULL;
-- interval_days = cada cuántos días reaplicar (0 = aplicación única)

-- Especies válidas del tratamiento (una o varias)
CREATE TABLE treatment_species (
                                   id_treatment  BIGINT       NOT NULL,
                                   species       VARCHAR(50)  NOT NULL,
                                   PRIMARY KEY (id_treatment, species),
                                   CONSTRAINT fk_treatment_species_treatment
                                       FOREIGN KEY (id_treatment) REFERENCES treatments (id_treatment) ON DELETE CASCADE
);

-- Productos que componen el tratamiento (definidos al crearlo)
CREATE TABLE treatment_products (
                                    id            BIGSERIAL PRIMARY KEY,
                                    id_treatment  BIGINT    NOT NULL,
                                    product_id    BIGINT    NOT NULL,
                                    quantity      INTEGER   NOT NULL CHECK (quantity > 0),
                                    CONSTRAINT uk_treatment_products UNIQUE (id_treatment, product_id),
                                    CONSTRAINT fk_treatment_products_treatment
                                        FOREIGN KEY (id_treatment) REFERENCES treatments (id_treatment) ON DELETE CASCADE,
                                    CONSTRAINT fk_treatment_products_product
                                        FOREIGN KEY (product_id) REFERENCES products (id)
);

-- 2) Carnet: vaccination_cards -> treatment_cards
ALTER TABLE vaccination_cards RENAME TO treatment_cards;

-- 3) Aplicaciones: vaccination_records -> treatment_applications
ALTER TABLE vaccination_records RENAME TO treatment_applications;
ALTER TABLE treatment_applications RENAME COLUMN id_record TO id_application;
ALTER TABLE treatment_applications RENAME COLUMN id_vaccine TO id_treatment;
ALTER TABLE treatment_applications RENAME COLUMN dose_number TO application_number;
ALTER TABLE treatment_applications DROP COLUMN batch_number;   -- el lote ahora viene de inventory_batch
ALTER TABLE treatment_applications
    ADD COLUMN total_cost NUMERIC(12, 2) NOT NULL DEFAULT 0;

-- 4) Lotes consumidos en cada aplicación (costo real y trazabilidad)
CREATE TABLE treatment_application_items (
                                             id              BIGSERIAL      PRIMARY KEY,
                                             id_application  BIGINT         NOT NULL,
                                             product_id      BIGINT         NOT NULL,
                                             batch_id        BIGINT         NOT NULL,
                                             quantity        BIGINT         NOT NULL CHECK (quantity > 0),
                                             unit_cost       NUMERIC(14, 4) NOT NULL,
                                             CONSTRAINT fk_tai_application FOREIGN KEY (id_application)
                                                 REFERENCES treatment_applications (id_application) ON DELETE CASCADE,
                                             CONSTRAINT fk_tai_product FOREIGN KEY (product_id) REFERENCES products (id),
                                             CONSTRAINT fk_tai_batch   FOREIGN KEY (batch_id)   REFERENCES inventory_batch (id)
);

-- 5) Movimientos de inventario ligados a la aplicación
ALTER TABLE inventory_movement
    ADD COLUMN treatment_application_id BIGINT,
    ADD CONSTRAINT fk_inventory_movement_treatment_application
        FOREIGN KEY (treatment_application_id) REFERENCES treatment_applications (id_application);