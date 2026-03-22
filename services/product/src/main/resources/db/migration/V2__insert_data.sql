-- Insert categories
INSERT INTO category (id, name, description) VALUES
                                                 (1, 'Electronics', 'Electronic devices and gadgets'),
                                                 (2, 'Books', 'Educational and programming books'),
                                                 (3, 'Clothing', 'Men and women apparel'),
                                                 (4, 'Home Appliances', 'Appliances used in households'),
                                                 (5, 'Sports', 'Sports equipment and accessories');

-- Insert products
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES
                                                                                        (1, 'Laptop', 'High performance gaming laptop', 15, 1500.00, 1),
                                                                                        (2, 'Smartphone', 'Latest Android smartphone', 40, 799.99, 1),
                                                                                        (3, 'Wireless Headphones', 'Noise cancelling headphones', 30, 199.99, 1),
                                                                                        (4, 'Java Programming Book', 'Comprehensive guide to Java', 25, 49.99, 2),
                                                                                        (5, 'Spring Boot Guide', 'Learn Spring Boot development', 20, 59.99, 2),
                                                                                        (6, 'Rust Programming Book', 'Systems programming with Rust', 18, 54.99, 2),
                                                                                        (7, 'Men T-Shirt', 'Cotton casual t-shirt', 50, 19.99, 3),
                                                                                        (8, 'Women Jacket', 'Winter jacket for women', 22, 89.99, 3),
                                                                                        (9, 'Running Shoes', 'Lightweight running shoes', 35, 120.00, 3),
                                                                                        (10, 'Microwave Oven', '1000W microwave oven', 10, 250.00, 4),
                                                                                        (11, 'Refrigerator', 'Double door refrigerator', 8, 899.00, 4),
                                                                                        (12, 'Vacuum Cleaner', 'Powerful vacuum cleaner', 12, 180.00, 4),
                                                                                        (13, 'Football', 'Professional football', 45, 25.00, 5),
                                                                                        (14, 'Cricket Bat', 'Premium English willow bat', 20, 150.00, 5),
                                                                                        (15, 'Tennis Racket', 'Professional tennis racket', 16, 130.00, 5);