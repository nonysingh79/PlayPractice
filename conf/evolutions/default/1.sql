# Users schema

# --- !Ups

CREATE TABLE Invoice (
    taxId varchar(255) NOT NULL,
    product varchar(255) NOT NULL,
    salesAmount DECIMAL(10,2) NOT NULL,
    taxCategory varchar(255) NOT NULL,
    PRIMARY KEY (taxId)
);

# --- !Downs

DROP TABLE Invoice;