CREATE TABLE orders
(
    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,

    product_id BIGINT NOT NULL,

    quantity INT NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_order_user
        FOREIGN KEY(user_id)
            REFERENCES users(id),

    CONSTRAINT fk_order_product
        FOREIGN KEY(product_id)
            REFERENCES products(id)
);