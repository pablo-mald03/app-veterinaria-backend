-- Categories table
CREATE TABLE categories
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(120) NOT NULL,
    alias       VARCHAR(120) NOT NULL UNIQUE,
    description VARCHAR(255),
    status      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ  NOT NULL,
    updated_at  TIMESTAMPTZ
);