CREATE TABLE vaccines (
                          id_vaccine      BIGSERIAL       PRIMARY KEY,
                          name            VARCHAR(150)    NOT NULL,
                          description     VARCHAR(255)    NOT NULL,
                          species         VARCHAR(150)    NOT NULL,
                          doses_required  INTEGER         NOT NULL,
                          interval_days   INTEGER         NOT NULL,
                          status          BOOLEAN         NOT NULL DEFAULT TRUE,

                          created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at      TIMESTAMP,
                          created_by      VARCHAR(100),
                          updated_by      VARCHAR(100),

                          CONSTRAINT uk_vaccines_name UNIQUE (name),
                          CONSTRAINT chk_vaccines_doses_required CHECK (doses_required > 0),
                          CONSTRAINT chk_vaccines_interval_days CHECK (interval_days >= 0)
);

CREATE INDEX idx_vaccines_species ON vaccines (species);