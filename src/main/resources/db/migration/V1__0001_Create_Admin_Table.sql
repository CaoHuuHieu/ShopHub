CREATE TABLE IF NOT EXISTS admins (
    id BIGINT PRIMARY KEY NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    username VARCHAR(255),
    email VARCHAR(255),
    mobile VARCHAR(255),
    active INT
);