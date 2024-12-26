CREATE TABLE IF NOT EXISTS logistics.orders (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    purchase_date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES logistics.users(id),
    FOREIGN KEY (product_id) REFERENCES logistics.products(id)
);
