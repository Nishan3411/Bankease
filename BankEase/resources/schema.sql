CREATE DATABASE bankease;

USE bankease;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    is_admin BOOLEAN DEFAULT FALSE
);

CREATE TABLE accounts (
    account_number INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    balance DOUBLE,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_number INT,
    type VARCHAR(20),
    amount DOUBLE,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);
