CREATE TABLE product(
		modelNumber VARCHAR(25),
		name VARCHAR(50),
		"type" VARCHAR(15),
		price DECIMAL(19,4)
);

CREATE TABLE productLine(
		product VARCHAR(25),
		quantity INT
);

CREATE TABLE cart(
		id INT,
		customerID VARCHAR(20)
);

CREATE TABLE wishList(
		id INT,
		customerID VARCHAR(20),
		name VARCHAR(30)
);

CREATE TABLE "order"(
		id INT,
		purchaseDate CHAR(10),
		peliveryDate CHAR(10),
		Price DECIMAL(19,4),
		CartID INT
);

CREATE TABLE customer(
		ID VARCHAR(20),
		FirstName VARCHAR(20),
		Surname VARCHAR(20),
		Email VARCHAR(50),
		PhoneNumber VARCHAR(20)
);