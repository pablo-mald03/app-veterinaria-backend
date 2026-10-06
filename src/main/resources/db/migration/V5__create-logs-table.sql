-- Logs table
CREATE TABLE logs
(
    id         BIGSERIAL PRIMARY KEY,
    module     VARCHAR(70),
    action     VARCHAR(70),
    detail     TEXT,
    user_id    BIGINT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_logs_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL
);