CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,

    username VARCHAR(50) NOT NULL UNIQUE,

    password VARCHAR(255) NOT NULL,

    role VARCHAR(20) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users
(
    username,
    password,
    role
)
VALUES
    (
        'admin',
        '$2a$10$G7R6vFrLvso4NPY8QhU2wuXpm2xn3OJzmQA24.EtksRVK1JgmWQt.',
        'ADMIN'
    );