CREATE TYPE account_status AS ENUM ('PENDING', 'ACTIVE', 'REJECTED', 'SUSPENDED');
CREATE TYPE user_role AS ENUM ('CUSTOMER', 'ADMIN');

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role user_role DEFAULT 'CUSTOMER',
    status account_status DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT NOW(),
    account_type VARCHAR(50)
);

CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    account_no VARCHAR(20) UNIQUE NOT NULL,
    balance DECIMAL(15,2) DEFAULT 0.00,
    opened_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    account_id INT REFERENCES accounts(id),
    type VARCHAR(20),   -- 'CREDIT' or 'DEBIT'
    amount DECIMAL(15,2),
    description TEXT,
    txn_time TIMESTAMP DEFAULT NOW()
);
