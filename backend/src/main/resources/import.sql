INSERT INTO product (id, code, name, value) 
VALUES 
    (1, 'PROD001', 'Smartphone X', 1500.00),
    (2, 'PROD002', 'Notebook Pro', 3500.00),
    (3, 'PROD003', 'Tablet Lite', 800.00)
ON CONFLICT (id) DO NOTHING;

INSERT INTO raw_material (id, code, name, stock_quantity)
VALUES
    (1, 'MAT001', 'Processor i7', 50),
    (2, 'MAT002', '16GB RAM', 100),
    (3, 'MAT003', '512GB SSD', 75),
    (4, 'MAT004', 'OLED Screen', 40),
    (5, 'MAT005', 'Battery 5000mAh', 60)
ON CONFLICT (id) DO NOTHING;