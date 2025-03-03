CREATE TABLE IF NOT EXISTS  organizations (
    id BIGINT PRIMARY KEY NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    name VARCHAR(255),
    code VARCHAR(255),
    logo VARCHAR(255),
    website VARCHAR(255),
    email VARCHAR(255),
    address VARCHAR(255),
    terms_url VARCHAR(255),
    privacy_url VARCHAR(255),
    created_by_id BIGINT,
    updated_by_id BIGINT,
    status int
    CONSTRAINT fk_created_by FOREIGN KEY (created_by_id) REFERENCES admins(id) ON DELETE SET NULL,
    CONSTRAINT fk_updated_by FOREIGN KEY (updated_by_id) REFERENCES admins(id) ON DELETE SET NULL
);