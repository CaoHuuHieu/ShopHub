CREATE TABLE IF NOT EXISTS admins (
     id BIGINT PRIMARY KEY NOT NULL,
     created_at TIMESTAMP,
     updated_at TIMESTAMP,
     first_name VARCHAR(50),
     last_name VARCHAR(50),
     username VARCHAR(30) NOT NULL,
     email VARCHAR(100) NOT NULL,
     mobile VARCHAR(20),
     active INT,
     created_by_id BIGINT NULL,
     updated_by_id BIGINT NULL,
     role_id BIGINT,
     FOREIGN KEY (role_id) REFERENCES roles(id),
     FOREIGN KEY (created_by_id) REFERENCES admins(id),
     FOREIGN KEY (updated_by_id) REFERENCES admins(id)
);