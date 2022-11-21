# create admin role
DROP ROLE IF EXISTS musicstore_dbadmin;

CREATE ROLE musicstore_dbadmin;

GRANT ALL
ON musicstore.*
TO musicstore_dbadmin;

# create analytics role
DROP ROLE IF EXISTS musicstore_analytics;

CREATE ROLE musicstore_analytics;

GRANT SELECT
ON musicstore.*
TO musicstore_analytics;

# create manager role
DROP ROLE IF EXISTS musicstore_manager;

CREATE ROLE musicstore_manager;

GRANT SELECT
ON musicstore.*
TO musicstore_analytics;

GRANT SELECT, INSERT, UPDATE, DELETE
ON musicstore.person
TO musicstore_manager;

GRANT SELECT, INSERT, UPDATE, DELETE
ON musicstore.customer
TO musicstore_manager;

GRANT SELECT, INSERT, UPDATE, DELETE
ON musicstore.employee
TO musicstore_manager;

GRANT SELECT, INSERT, UPDATE, DELETE
ON musicstore.position
TO musicstore_manager;

GRANT SELECT, INSERT, UPDATE, DELETE
ON musicstore.employee_position
TO musicstore_manager;

GRANT SELECT, INSERT, UPDATE, DELETE
ON musicstore.discount
TO musicstore_manager;

GRANT SELECT, INSERT, UPDATE, DELETE
ON musicstore.instrument_type
TO musicstore_manager;

GRANT SELECT, INSERT, UPDATE, DELETE
ON musicstore.product_type
TO musicstore_manager;

GRANT SELECT, INSERT, UPDATE, DELETE
ON musicstore.product
TO musicstore_manager;

GRANT SELECT, INSERT, UPDATE, DELETE
ON musicstore.space
TO musicstore_manager;

# create clerk role
DROP ROLE IF EXISTS musicstore_clerk;

CREATE ROLE musicstore_clerk;

GRANT SELECT, INSERT, UPDATE
ON musicstore.person
TO musicstore_clerk;

GRANT SELECT, INSERT, UPDATE
ON musicstore.customer
TO musicstore_clerk;

GRANT SELECT, INSERT
ON musicstore.purchase
TO musicstore_clerk;

GRANT SELECT, INSERT
ON musicstore.product_purchase
TO musicstore_clerk;

GRANT SELECT, INSERT
ON musicstore.purchase_discount
TO musicstore_clerk;

GRANT SELECT, INSERT
ON musicstore.space_rental
TO musicstore_clerk;

GRANT SELECT
ON musicstore.space
TO musicstore_clerk;

GRANT SELECT
ON musicstore.product
TO musicstore_clerk;

GRANT SELECT
ON musicstore.discount
TO musicstore_clerk;

GRANT SELECT
ON musicstore.instrument_type
TO musicstore_clerk;

GRANT SELECT
ON musicstore.product_type
TO musicstore_clerk;

# create test user
DROP USER IF EXISTS musicstore_test@'localhost';

CREATE USER musicstore_test@'localhost'
IDENTIFIED BY "s3cret";

GRANT musicstore_clerk
TO musicstore_test@'localhost';

FLUSH PRIVILEGES;
SET DEFAULT ROLE ALL TO 
	musicstore_clerk@'localhost',
	musicstore_test@'localhost';
SHOW GRANTS FOR musicstore_test@'localhost';