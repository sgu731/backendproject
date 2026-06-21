CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX idx_products_deleted
    ON products(deleted);

CREATE INDEX idx_products_price
    ON products(price);

CREATE INDEX idx_products_name_trgm
    ON products
        USING gin(name gin_trgm_ops);