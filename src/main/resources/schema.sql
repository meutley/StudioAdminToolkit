DROP TABLE IF EXISTS client;
CREATE TABLE client (
    id 					SERIAL 			PRIMARY KEY,
	name 				VARCHAR(100) 	NOT NULL,
	email 				VARCHAR(255),
	is_active 			BOOLEAN		 	DEFAULT TRUE,
    mailing_address_id	INT,
    CONSTRAINT fk_client_mailing_address FOREIGN KEY (mailing_address_id)
        REFERENCES public.mailing_address (id)
);

DROP INDEX IF EXISTS ix_uq_client_email;
CREATE UNIQUE INDEX ix_uq_client_email
    ON client(email);
	

DROP TABLE IF EXISTS mailing_address;
CREATE TABLE mailing_address (
    id 			SERIAL 			PRIMARY KEY,
    line_1 		VARCHAR(255)	NOT NULL,
    line_2 		VARCHAR(100),
    line_3 		VARCHAR(100),
    city 		VARCHAR(255)	NOT NULL,
    state 		VARCHAR(50)		NOT NULL,
    zip_code 	INT				NOT NULL
);
	

DROP TABLE IF EXISTS invoice;
CREATE TABLE invoice (
	id				SERIAL		PRIMARY KEY,
    client_id 		INT			NOT NULL,
    invoice_number	VARCHAR(20) NOT NULL,
    is_active       BOOLEAN     NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_invoice_client_id FOREIGN KEY (client_id)
        REFERENCES client (id)
);

DROP INDEX IF EXISTS ix_uq_invoice_invoice_number;
CREATE UNIQUE INDEX ix_uq_invoice_invoice_number
    ON invoice(invoice_number);
	

DROP FUNCTION IF EXISTS finalize_invoice_number;
CREATE FUNCTION finalize_invoice_number()
    RETURNS trigger
    LANGUAGE 'plpgsql' 
AS $BODY$
BEGIN
    NEW.invoice_number = CONCAT(NEW.invoice_number, LPAD(NEW.id::text, 6, '0'));
    RETURN new;
END;
$BODY$;

DROP TRIGGER IF EXISTS finalize_invoice_number_on_insert;
CREATE TRIGGER finalize_invoice_number_on_insert
    BEFORE INSERT
    ON invoice
    FOR EACH ROW
    EXECUTE PROCEDURE public.finalize_invoice_number();
														 

DROP TABLE IF EXISTS invoice_line_item;
CREATE TABLE invoice_line_item (
    id 			SERIAL 			PRIMARY KEY,
    invoice_id 	INT				NOT NULL,
    name 		VARCHAR(50)		NOT NULL,
    description VARCHAR(255),
    is_billable	BOOLEAN			NOT NULL DEFAULT TRUE,
    unit_price 	NUMERIC(18,2) 	NOT NULL DEFAULT 0.00,
    quantity 	NUMERIC(18,2) 	NOT NULL DEFAULT 0.00,
    CONSTRAINT invoice_line_item_invoice_id_fkey FOREIGN KEY (invoice_id)
        REFERENCES invoice (id)
);
														 

DROP TABLE IF EXISTS product;
CREATE TABLE product (
    id 			SERIAL 			PRIMARY KEY,
    name 		VARCHAR(50)		NOT NULL,
    description VARCHAR(150)	NOT NULL,
    is_billable BOOLEAN			NOT NULL DEFAULT TRUE,
    unit_price  NUMERIC(18,2) 	NOT NULL DEFAULT 0.0,
    is_active 	BOOLEAN 		NOT NULL DEFAULT true
);
