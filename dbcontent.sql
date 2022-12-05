USE musicstore;

# People
INSERT INTO `person` (`FirstName`, `LastName`, `PhoneNumber`, `Email`, `Address`)
VALUES ("Joe", "Schmoe", "(619) 638-5939", "JoeSchmoe@jss.com", "Australia Av");

INSERT INTO `person` (`FirstName`, `LastName`, `PhoneNumber`, `Email`, `Address`)
VALUES ("Mary", "Sue", "(111) 111-1111", "MarySue@perfecteverything.com", "Enterprise Ln");

INSERT INTO `person` (`FirstName`, `LastName`, `PhoneNumber`, `Email`, `Address`)
VALUES ("Jane", "Doe", "(012) 345-6789", "JaneDoe@gmail.com", "Wherever Way");

INSERT INTO `person` (`FirstName`, `LastName`, `PhoneNumber`, `Email`, `Address`)
VALUES ("Ben", "Dover", "(101) 404-1234", "Ben@Dover.com", "Fireman St");

# Customers
INSERT INTO `customer` (`PersonID`)
VALUES (1);

INSERT INTO `customer` (`PersonID`)
VALUES (2);

INSERT INTO `customer` (`PersonID`)
VALUES (3);

# Employees
INSERT INTO `employee` (`PersonID`, `CheckingNumber`)
VALUES (4, "ABC123");

# Instrument Types
INSERT INTO `instrument_type` (`Type`)
VALUES ("Flute");

INSERT INTO `instrument_type` (`Type`)
VALUES ("Piano");

# Product Types
INSERT INTO `product_type` (`Type`)
VALUES ("Instrument");

INSERT INTO `product_type` (`Type`)
VALUES ("Case");

INSERT INTO `product_type` (`Type`)
VALUES ("Other");

# Products
INSERT INTO `product` (`Name`, `InstrumentTypeID`, `ProductTypeID`, `RetailPrice`, `WholesalePrice`)
VALUES ("Plastic Flute", 1, 1, 10.00, 5.00);

INSERT INTO `product` (`Name`, `InstrumentTypeID`, `ProductTypeID`, `RetailPrice`, `WholesalePrice`)
VALUES ("Metal Flute", 1, 1, 50.00, 35.00);

INSERT INTO `product` (`Name`, `InstrumentTypeID`, `ProductTypeID`, `RetailPrice`, `WholesalePrice`)
VALUES ("Flute Case", 1, 2, 25.00, 15.00);

INSERT INTO `product` (`Name`, `InstrumentTypeID`, `ProductTypeID`, `RetailPrice`, `WholesalePrice`)
VALUES ("Digital Piano", 2, 1, 400.00, 350.00);

INSERT INTO `product` (`Name`, `InstrumentTypeID`, `ProductTypeID`, `RetailPrice`, `WholesalePrice`)
VALUES ("Digital Piano Case", 2, 2, 60.00, 30.00);

INSERT INTO `product` (`Name`, `InstrumentTypeID`, `ProductTypeID`, `RetailPrice`, `WholesalePrice`)
VALUES ("Upright Piano", 2, 1, 5000.00, 4000.00);

INSERT INTO `product` (`Name`, `InstrumentTypeID`, `ProductTypeID`, `RetailPrice`, `WholesalePrice`)
VALUES ("Your First 15 Piano Songs", 2, 3, 15.00, 5.00);

# Discounts
INSERT INTO `discount` (`Name`, `StartDate`, `EndDate`, `DiscountJson`)
VALUES ("Christmas sale", "2022-12-01", "2022-12-31", "");

# Purchases
INSERT INTO `purchase` (`CustomerID`, `Date`)
VALUES (1, "2022-12-3");

INSERT INTO `purchase` (`CustomerID`, `Date`)
VALUES (1, "2022-12-5");

INSERT INTO `purchase` (`CustomerID`, `Date`)
VALUES (2, "2022-12-4");

# Purchase Products
INSERT INTO `product_purchase` (`PurchaseID`, `ProductID`, `ProductCount`)
VALUES (1, 1, 3);

INSERT INTO `product_purchase` (`PurchaseID`, `ProductID`, `ProductCount`)
VALUES (1, 3, 3);

INSERT INTO `product_purchase` (`PurchaseID`, `ProductID`, `ProductCount`)
VALUES (2, 4, 1);

INSERT INTO `product_purchase` (`PurchaseID`, `ProductID`, `ProductCount`)
VALUES (3, 7, 1);

# Purchase Discount
 INSERT INTO `purchase_discount` (`PurchaseID`, `DiscountID`, `DiscountAmount`)
VALUES (1, 1, 10.00);

# Spaces
INSERT INTO `space` (`Name`, `HourlyCost`)
VALUES("Classroom 1", 30.00);

INSERT INTO `space` (`Name`, `HourlyCost`)
VALUES("Classroom 2", 30.00);

INSERT INTO `space` (`Name`, `HourlyCost`)
VALUES("Concert Hall", 400.00);

INSERT INTO `space` (`Name`, `HourlyCost`)
VALUES("General Event Space", 300.00);

# Rentals
INSERT INTO `space_rental` (`SpaceID`, `CustomerID`, `StartTimestamp`, `EndTimestamp`)
VALUES (1, 3, "2022-12-6 10:30:00", "2022-12-6 11:30:00");
