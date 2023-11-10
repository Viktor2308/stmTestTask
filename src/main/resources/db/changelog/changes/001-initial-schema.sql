--liquibase formatted sql
-- changeset derkachev:1000000-1
-- comment: Initial creation of tables

CREATE SCHEMA IF NOT EXISTS entity;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS entity.users
(
    id       UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    login    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    fullName VARCHAR(255) NOT NULL,
    role     VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS entity.shipping_company
(
    id    UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    name  VARCHAR(255) NOT NULL,
    phone VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS entity.route
(
    id                    UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    departure_point       VARCHAR(255) NOT NULL,
    destination_point     VARCHAR(255) NOT NULL,
    shipping_company_id   UUID,
    route_duration_in_min INT,
    CONSTRAINT fk_shipping_company FOREIGN KEY (shipping_company_id) REFERENCES entity.shipping_company (id)
);

CREATE TABLE IF NOT EXISTS entity.ticket
(
    id           UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    route_id     UUID,
    date_time    DATE,
    place_number VARCHAR(255),
    price        DECIMAL,
    CONSTRAINT fk_route FOREIGN KEY (route_id) REFERENCES entity.route (id)
);

-- changeset derkachev:1000000-2
-- comment: add column in Ticket entity.ticket

ALTER TABLE entity.ticket
    ADD available BOOLEAN DEFAULT true;

-- changeset derkachev:1000000-3
-- comment: add table users ticket

CREATE TABLE IF NOT EXISTS entity.users_ticket
(
    users_id  UUID NOT NULL,
    ticket_id UUID NOT NULL,
    CONSTRAINT fk_users FOREIGN KEY (users_id) REFERENCES entity.users (id),
    CONSTRAINT fk_ticket FOREIGN KEY (ticket_id) REFERENCES entity.ticket (id)
);