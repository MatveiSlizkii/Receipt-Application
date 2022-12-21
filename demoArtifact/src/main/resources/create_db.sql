-- Database: store_service

-- DROP DATABASE IF EXISTS store_service;

CREATE DATABASE store_service
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Belarusian_Belarus.1251'
    LC_CTYPE = 'Belarusian_Belarus.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;


-- SCHEMA: app

-- DROP SCHEMA IF EXISTS app ;

CREATE SCHEMA IF NOT EXISTS app
    AUTHORIZATION postgres;

-- Table: app.product

-- DROP TABLE IF EXISTS app.product;

CREATE TABLE IF NOT EXISTS app.product
(
    id uuid NOT NULL,
    barcode bigint NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    cost double precision NOT NULL,
    quantity bigint NOT NULL,
    dt_create timestamp(3) without time zone NOT NULL,
    dt_update timestamp(3) without time zone NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS app.product
    OWNER to postgres;


-- Table: app.discount_card

-- DROP TABLE IF EXISTS app.discount_card;

CREATE TABLE IF NOT EXISTS app.discount_card
(
    id uuid NOT NULL,
    discount bigint NOT NULL,
    dt_create timestamp(3) without time zone NOT NULL,
    dt_update timestamp(3) without time zone NOT NULL,
    barcode bigint NOT NULL,
    CONSTRAINT discount_card_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS app.discount_card
    OWNER to postgres;



-- Table: app.sales_receipt

-- DROP TABLE IF EXISTS app.sales_receipt;

CREATE TABLE IF NOT EXISTS app.sales_receipt
(
    id uuid NOT NULL,
    id_final_receipt uuid NOT NULL,
    id_product uuid NOT NULL,
    quantity bigint NOT NULL,
    subtotal double precision NOT NULL,
    quantity_discount double precision DEFAULT 0,
    total double precision NOT NULL,
    price double precision NOT NULL,
    dt_create timestamp(3) without time zone NOT NULL,
    CONSTRAINT sales_receipt_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS app.sales_receipt
    OWNER to postgres;



-- Table: app.final_receipt

-- DROP TABLE IF EXISTS app.final_receipt;

CREATE TABLE IF NOT EXISTS app.final_receipt
(
    id uuid NOT NULL,
    discount_card uuid,
    subtotal double precision NOT NULL,
    total double precision NOT NULL,
    dt_create timestamp(3) without time zone NOT NULL,
    CONSTRAINT final_receipt_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS app.final_receipt
    OWNER to postgres;