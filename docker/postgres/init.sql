CREATE TABLE IF NOT EXISTS product (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    value DECIMAL(10,2) NOT NULL CHECK (value >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS raw_material (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    stock_quantity INTEGER NOT NULL CHECK (stock_quantity >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS product_material (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES product(id) ON DELETE CASCADE,
    material_id BIGINT NOT NULL REFERENCES raw_material(id) ON DELETE CASCADE,
    required_quantity INTEGER NOT NULL CHECK (required_quantity > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(product_id, material_id)
);

CREATE INDEX idx_product_code ON product(code);
CREATE INDEX idx_material_code ON raw_material(code);
CREATE INDEX idx_product_material_product ON product_material(product_id);
CREATE INDEX idx_product_material_material ON product_material(material_id);

CREATE OR REPLACE VIEW production_capacity AS
SELECT 
    p.id as product_id,
    p.code as product_code,
    p.name as product_name,
    p.value as product_value,
    MIN(FLOOR(rm.stock_quantity / pm.required_quantity)) as max_producible
FROM product p
JOIN product_material pm ON p.id = pm.product_id
JOIN raw_material rm ON pm.material_id = rm.id
GROUP BY p.id, p.code, p.name, p.value;
