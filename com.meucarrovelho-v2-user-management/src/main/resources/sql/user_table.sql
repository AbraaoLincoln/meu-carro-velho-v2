CREATE TABLE user (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    password VARCHAR(255),
    email VARCHAR(255)
);