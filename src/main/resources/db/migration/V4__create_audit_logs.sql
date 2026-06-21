CREATE TABLE audit_logs
(
    id BIGSERIAL PRIMARY KEY,

    actor VARCHAR(50) NOT NULL,

    action VARCHAR(50) NOT NULL,

    before_value TEXT,

    after_value TEXT,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);