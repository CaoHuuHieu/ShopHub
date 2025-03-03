CREATE TABLE IF NOT EXISTS roles (
    id BIGINT PRIMARY KEY NOT NULL,
    role_name nvarchar(20) NOT NULL,
    role_code varchar(20) NOTNULL,
    created_at TIMESTAMP NULL,
    updated_at TIMESTAMP NULL
)