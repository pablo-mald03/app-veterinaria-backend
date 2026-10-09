CREATE TABLE rooms (
       id BIGSERIAL PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       location VARCHAR(150) NOT NULL,
       description VARCHAR(255),
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);