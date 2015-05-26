DROP TABLE IF EXISTS product, address, customer, cart, productline, wishlist, "order";

CREATE TABLE cart
(
	id INT PRIMARY KEY
);

CREATE TABLE product
(
    model VARCHAR(25) PRIMARY KEY,
    price DECIMAL(19,4),
    "type" VARCHAR(15),
    name VARCHAR(50)
);

CREATE TABLE address
(
    id INT PRIMARY KEY,
    inhabitant_name VARCHAR(100),
    street_address VARCHAR(100),
    zip_code INT,
    city VARCHAR(50),
    country VARCHAR(50)
);

CREATE TABLE customer
(
    id VARCHAR(20) PRIMARY KEY,
    first_name VARCHAR(50),
    sur_name VARCHAR(50),
    email VARCHAR(100),
    phonenumber VARCHAR(20),
    address INT REFERENCES address(id) ON DELETE SET NULL ON UPDATE CASCADE,
    shopping_cart INT NOT NULL REFERENCES cart(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE productline
(
    cart_id INT REFERENCES cart(id) ON DELETE CASCADE ON UPDATE CASCADE,
    product_model VARCHAR(25) REFERENCES product(model) ON DELETE CASCADE ON UPDATE CASCADE,
    quantity INT,
    CONSTRAINT productline_pkey PRIMARY KEY (cart_id, product_model)
);

CREATE TABLE wishlist
(
    customer_id VARCHAR(20) REFERENCES customer(id) ON DELETE CASCADE ON UPDATE CASCADE,
    id INT REFERENCES cart(id) ON DELETE CASCADE ON UPDATE CASCADE,
    name VARCHAR(30)
);

CREATE TABLE "order"
(
    customer_id VARCHAR(20) REFERENCES customer(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    id INT PRIMARY KEY,
    price DECIMAL(19,4),
    status INT,
    payment_method VARCHAR(50),
    purchase_date DATE,
    dispatched_date DATE,
    cart_id INT NOT NULL REFERENCES cart(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    delivery_address INT REFERENCES address(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

INSERT INTO product(model, name, type, price) VALUES
('FD8350FRW8KHK', 'AMD FX-8350', 'Processor', 179.99),
('FD9590FHW8KHK', 'AMD FX-9590', 'Processor', 239.99),
('FD6300WMW6KHK', 'AMD FX-6300', 'Processor', 109.99),
('FD4100WMW4KGU', 'AMD FX-4100', 'Processor', 79.99),
('FD8120FRW8KGU', 'AMD FX-8120', 'Processor', 100),
('AD740XOKA44HJ', 'AMD 740', 'Processor', 72.85),
('AD750KWOA44HJ', 'AMD 750k', 'Processor', 76.99),
('BX80646I74770K', 'Intel i7-4770K', 'Processor', 335.99),
('BX80646I74790K', 'Intel i7-4790K', 'Processor', 339.99),
('BX80646I54670K', 'Intel i5-4670K', 'Processor', 234.99),
('BX80646I54690K', 'Intel i5-4690K', 'Processor', 239.99),
('BX80637I53570K', 'Intel i5-3570K', 'Processor', 239.99),
('N82E16813131876', 'Asus ROG Crosshair V Formula-Z', 'Bundkort', 229.99),
('N82E16813131883', 'Asus F2A85-M PRO', 'Bundkort', 94.99),
('N82E16813131975', 'Asus ROG Maximus VI Gene', 'Bundkort', 159.99),
('N82E16813131854', 'Asus ROG MAXIMUS V FORMULA', 'Bundkort', 299.99),
('N82E16813132247', 'ASUS MAXIMUS VII FORMULA', 'Bundkort', 349.99),
('N82E16813132125', 'Asus MAXIMUS VII HERO', 'Bundkort', 214.99),
('KHX16C9T3K2/8X', 'Kingston DDR3 HyperX Beast 1600mhz 8GB', 'Hukommelse', 112.86),
('KHX16C9T3K2/16X', 'Kingston DDR3 HyperX Beast 1600mhz 16GB', 'Hukommelse', 199.79),
('KHX16C9T3K4/32X', 'Kingston DDR3 HyperX Beast 1600mhz 32GB', 'Hukommelse', 346.28),
('HX324C11T3K2/16', 'Kingston DDR3 HyperX Beast 2400mhz 16GB', 'Hukommelse', 187.62),
('CMD16GX3M4A1866C9', 'Corsair Dominator DDR3 1866mhz 16GB', 'Hukommelse', 273.84),
('CMD16GX4M4B3000C14', 'Corsair Dominator DDR4 3000mhz 16GB', 'Hukommelse', 868.90),
('STRIX-GTX970-DC2OC-4GD5', 'ASUS GeForce STRIX GTX 970 4GB', 'Grafikkort', 454.65),
('04G-P4-2974-KR', 'EVGA GeForce GTX 970 4GB', 'Grafikkort', 417.12),
('GTXTITANX-12GD5', 'ASUS GeForce GTX TITAN X 12GB', 'Grafikkort', 1000.00),
('ZT-90301-10M', 'ZOTAC GeForce GTX 960 2GB', 'Grafikkort', 260.63),
('GV-R928XOC-3GD', 'Gigabyte Radeon R9 280X 3GB', 'Grafikkort', 301.20),
('100361-2SR', 'SAPPHIRE TRI-X OC 290X 4GB', 'Grafikkort', 339.99),
('02G-P4-3753-KR', 'EVGA GeForce GTX 750Ti 2GB', 'Grafikkort', 188.25),
('FD-CA-DEF-R5-BK', 'Fractal Design Define R5 Black', 'Kabinetter', 121.59),
('CC-9011035-WW', 'Corsair Obsidian 750D Big Tower Black', 'Kabinetter', 192.54),
('ROC-11-80', 'ROCCAT Kone XTD - Max Customization', 'Mus & Keyboards', 94.49),
('RZ-01-01040100-R3G1', 'Razer Naga', 'Mus & Keyboards', 72.62),
('90LM00U0-B013L0', 'Asus 27\" LED G-Sync ROG SWIFT PG278QE', 'Skærme', 769.81),
('WD1003FZEX', 'WD Desktop Black 1TB', 'Harddiske', 94.44);

INSERT INTO cart(id) VALUES
(1),
(2),
(3),
(4),
(5);

INSERT INTO customer(id, first_name, sur_name, email, phonenumber, shopping_cart) VALUES
('c123456', 'Hans', 'Hansen', 'hans.hansen@hotmail.com', '+4512345678', 1),
('c223456', 'Peter', 'Petersen', 'peter.petersen@hotmail.com', '+4522345678', 2),
('c323456', 'Gorm', 'Gormsen', 'gorm.gormsen@hotmail.com', '+4532345678', 3),
('c423456', 'Lars', 'Larsen', 'lars.larsen@hotmail.com', '+4542345678', 4),
('c523456', 'Hanne', 'Hansen', 'hanne.hansen@hotmail.com', '+4552345678', 5);

