CREATE TABLE products
(
    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(100) NOT NULL,

    price NUMERIC(12,2) NOT NULL,

    stock INT NOT NULL,

    deleted BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_product_deleted
    ON products(deleted);

CREATE INDEX idx_product_name
    ON products(name);