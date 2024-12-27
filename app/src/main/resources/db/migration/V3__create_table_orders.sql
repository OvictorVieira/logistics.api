CREATE TABLE IF NOT EXISTS logistics.orders (
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    purchase_date DATE NOT NULL,
    PRIMARY KEY (order_id, user_id, product_id),
    FOREIGN KEY (user_id) REFERENCES logistics.users(user_id),
    FOREIGN KEY (product_id) REFERENCES logistics.products(product_id)
);
