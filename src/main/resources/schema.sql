CREATE TABLE IF NOT EXISTS product
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    price       NUMERIC,
    active      BOOLEAN,
    start_date  DATE
);

CREATE TABLE IF NOT EXISTS sku
(
    id         SERIAL PRIMARY KEY,
    product_id INT          NOT NULL,
    sku_code   VARCHAR(255) NOT NULL,
    quantity   INT,
    color      VARCHAR(50),
    available  BOOLEAN,
    FOREIGN KEY (product_id) REFERENCES product (id)
);

INSERT INTO product (name, description, price, active, start_date)
VALUES ('Product1', 'Description1', 10.0, true, '2023-01-01'),
       ('Product2', 'Description2', 20.0, false, '2023-02-01');

INSERT INTO sku (product_id, sku_code, quantity, color, available)
VALUES (1, 'SKU1-1', 100, 'Red', true),
       (1, 'SKU1-2', 150, 'Blue', false),
       (2, 'SKU2-1', 200, 'Green', true);
