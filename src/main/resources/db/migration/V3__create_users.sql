CREATE SEQUENCE IF NOT EXISTS seq_users_id START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT DEFAULT nextval('seq_users_id'),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    CONSTRAINT pk_users_id PRIMARY KEY (id)
    );