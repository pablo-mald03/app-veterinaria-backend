-- ============================================================
-- Carnet de vacunación (1 por mascota)
-- ============================================================
CREATE TABLE vaccination_cards (
                                   id_card         BIGSERIAL       PRIMARY KEY,
                                   id_pet          BIGINT          NOT NULL,
                                   creation_date   DATE            NOT NULL,
                                   status          BOOLEAN         NOT NULL DEFAULT TRUE,

                                   created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   updated_at      TIMESTAMP,
                                   created_by      VARCHAR(100),
                                   updated_by      VARCHAR(100),

                                   CONSTRAINT uk_vaccination_cards_pet UNIQUE (id_pet)
    -- , CONSTRAINT fk_vaccination_cards_pet FOREIGN KEY (id_pet) REFERENCES pets (id_pet)
);

-- ============================================================
-- Dosis aplicadas
-- ============================================================
CREATE TABLE vaccination_records (
                                     id_record         BIGSERIAL     PRIMARY KEY,
                                     id_card           BIGINT        NOT NULL,
                                     id_vaccine        BIGINT        NOT NULL,
                                     id_doctor         BIGINT        NOT NULL,
                                     dose_number       INTEGER       NOT NULL,
                                     application_date  DATE          NOT NULL,
                                     next_dose_date    DATE,
                                     batch_number      VARCHAR(100),
                                     notes             VARCHAR(500),

                                     created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     updated_at      TIMESTAMP,
                                     created_by      VARCHAR(100),
                                     updated_by      VARCHAR(100),

                                     CONSTRAINT fk_vaccination_records_card
                                         FOREIGN KEY (id_card) REFERENCES vaccination_cards (id_card),
                                     CONSTRAINT fk_vaccination_records_vaccine
                                         FOREIGN KEY (id_vaccine) REFERENCES vaccines (id_vaccine),
    -- , CONSTRAINT fk_vaccination_records_doctor FOREIGN KEY (id_doctor) REFERENCES users (id_user)
                                     CONSTRAINT uk_vaccination_records_dose
                                         UNIQUE (id_card, id_vaccine, dose_number),
                                     CONSTRAINT chk_vaccination_records_dose CHECK (dose_number > 0)
);

CREATE INDEX idx_vaccination_records_card ON vaccination_records (id_card);
CREATE INDEX idx_vaccination_records_next_dose ON vaccination_records (next_dose_date);