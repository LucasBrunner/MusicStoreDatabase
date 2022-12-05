# create database
DROP DATABASE IF EXISTS musicstore;

CREATE DATABASE musicstore;

USE musicstore;

# create people tables
CREATE TABLE person(
	PersonID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    FirstName VARCHAR(64) NOT NULL,
    LastName VARCHAR(64) NOT NULL,
    PhoneNumber VARCHAR(20),
    Email VARCHAR(64),
    Address VARCHAR(128),
    
    PRIMARY KEY(PersonID)
);

CREATE TABLE employee(
	EmployeeID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	PersonID INT UNSIGNED,
    CheckingNumber VARCHAR(20),
    
    FOREIGN KEY(PersonID) REFERENCES person(PersonID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT,    
    PRIMARY KEY(EmployeeID)
);

CREATE TABLE customer(
	CustomerID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	PersonID INT UNSIGNED,
    
    FOREIGN KEY(PersonID) REFERENCES person(PersonID)
		ON DELETE SET NULL
		ON UPDATE RESTRICT,    
    PRIMARY KEY(CustomerID)
);

# create employee position tables
CREATE TABLE `position`(
	PositionID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    Title VARCHAR(64) NOT NULL,
    
    PRIMARY KEY(PositionID)
);

CREATE TABLE employee_position(
	EmployeePositionID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    EmployeeID INT UNSIGNED NOT NULL,
    PositionID INT UNSIGNED NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE,
    
    FOREIGN KEY(EmployeeID) REFERENCES employee(EmployeeID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT, 
    FOREIGN KEY(PositionID) REFERENCES `position`(PositionID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT, 
    PRIMARY KEY(EmployeePositionID)
);

# create product tables
CREATE TABLE instrument_type(
	InstrumentTypeID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `Type` VARCHAR(64),
    
    PRIMARY KEY(InstrumentTypeID)
);

CREATE TABLE product_type(
	ProductTypeID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `Type` VARCHAR(64),
    
    PRIMARY KEY(ProductTypeID)
);

CREATE TABLE product(
	ProductID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `Name` VARCHAR(64) NOT NULL,
    InstrumentTypeID INT UNSIGNED NOT NULL DEFAULT 1,
    ProductTypeID INT UNSIGNED NOT NULL DEFAULT 1,
    RetailPrice DECIMAL(9, 4) NOT NULL,
    WholesalePrice DECIMAL(9, 4) NOT NULL,
    
    FOREIGN KEY(InstrumentTypeID) REFERENCES instrument_type(InstrumentTypeID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT,   
    FOREIGN KEY(ProductTypeID) REFERENCES product_type(ProductTypeID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT,   
    PRIMARY KEY(ProductID)
);

# create purchase tables
CREATE TABLE purchase(
	PurchaseID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    CustomerID INT UNSIGNED NOT NULL,
    Date DATE NOT NULL,
    
    FOREIGN KEY(CustomerID) REFERENCES customer(CustomerID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT,    
    PRIMARY KEY(PurchaseID)
);

CREATE TABLE product_purchase(
	PurchaseID INT UNSIGNED NOT NULL,
    ProductID INT UNSIGNED NOT NULL,
    ProductCount INT UNSIGNED NOT NULL DEFAULT 1,
    
    FOREIGN KEY(PurchaseID) REFERENCES purchase(PurchaseID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT,   
    FOREIGN KEY(ProductID) REFERENCES product(ProductID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT,  
    PRIMARY KEY(PurchaseID, ProductID)
);

# create discount tables
CREATE TABLE discount(
	DiscountID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `Name` VARCHAR(64) NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    DiscountJson TEXT NOT NULL,
     
    PRIMARY KEY(DiscountID)
);

CREATE TABLE purchase_discount(
	PurchaseID INT UNSIGNED NOT NULL,
	DiscountID INT UNSIGNED NOT NULL,
    DiscountAmount DECIMAL(9, 4) NOT NULL,
    
    PRIMARY KEY(PurchaseID, DiscountAmount)
);

# create space tables
CREATE TABLE space(
	SpaceID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `Name` VARCHAR(64) NOT NULL,
    HourlyCost DECIMAL(9, 4) NOT NULL,
    
    PRIMARY KEY(SpaceID)
);

CREATE TABLE space_rental(
	RentalID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    SpaceID INT UNSIGNED NOT NULL,
    CustomerID INT UNSIGNED NOT NULL,
    StartTimestamp TIMESTAMP NOT NULL,
    EndTimestamp TIMESTAMP NOT NULL,
    
    FOREIGN KEY(SpaceID) REFERENCES space(SpaceID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT,   
    FOREIGN KEY(CustomerID) REFERENCES customer(CustomerID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT,  
    PRIMARY KEY(RentalID)
);

