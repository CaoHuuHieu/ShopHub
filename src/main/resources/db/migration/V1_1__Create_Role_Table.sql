CREATE TABLE IF NOT EXISTS roles (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
    role_name varchar(20) NOT NULL,
    role_code varchar(20) NOT NULL,
    created_at TIMESTAMP NULL,
    updated_at TIMESTAMP NULL
);

INSERT INTO roles(role_name, role_code)
values('Super Admin', 'SUPER_ADMIN'),
('Organization Admin', 'ORG_ADMIN'),
('Venue Admin', 'VENUE_ADMIN')